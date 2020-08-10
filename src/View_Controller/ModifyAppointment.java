package View_Controller;


import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ModifyAppointment  implements Initializable {

    @FXML
    private ComboBox<Integer> modifyApptCustId;

    @FXML
    private TextField modifyApptTitle;

    @FXML
    private DatePicker modifyApptDate;

    @FXML
    private ComboBox<String> modifyApptStart;

    @FXML
    private ComboBox<String> modifyApptEnd;

    @FXML
    private ComboBox<String> modifyApptType;

    @FXML
    private ComboBox<String> modifyApptLocation;

    @FXML
    private TextField modifyApptContact;

    @FXML
    private TableView<Customer> custTableView;

    @FXML
    private TableColumn<Customer, Integer> custTableId;

    @FXML
    private TableColumn<Customer, Integer> custTableName;

    @FXML
    private Label apptIdLabel;

    @FXML
    void modifyApptBackEvent(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }


    public static int customerId;
    public static int apptId;
    int apptIndexMod = AppointmentsTable.getApptIndexToMod();
    private Appointments appointment;

    ObservableList<String> apptTypesData = FXCollections.observableArrayList("Interview", "Review", "Presentation", "Other");
    ObservableList<String> apptLocationsData = FXCollections.observableArrayList("Phoenix", "New York", "London");
    ObservableList<String> apptStartData = FXCollections.observableArrayList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00");
    ObservableList<String> apptEndData = FXCollections.observableArrayList("08:59:59", "09:59:59", "10:59:59", "11:59:59", "12:59:59", "13:59:59", "14:59:59", "15:59:59", "16:59:59");
    ObservableList<Integer> customerIdData = FXCollections.observableArrayList(getCustomerIdData());


    @Override
    public void initialize(URL url, ResourceBundle resources) {

        modifyApptCustId.setItems(customerIdData);
        modifyApptType.setItems(apptTypesData);
        modifyApptLocation.setItems(apptLocationsData);
        modifyApptStart.setItems(apptStartData);
        modifyApptEnd.setItems(apptEndData);


        CustomerDatabase.getAllCustomersTableList().clear();
        custTableView.setItems(CustomerDatabase.getAllCustomersTableList());
        CustomerDatabase populateCustomers = new CustomerDatabase();
        populateCustomers.populateCustomerTable();
        custTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        appointment = AppointmentDatabase.getAllAppointmentsTableList().get(apptIndexMod);

        Integer appointmentId = appointment.getAppointmentId();
        Integer customerId = appointment.getCustomerId();
        String title = appointment.getTitle();
        String type = appointment.getType();
        String location = appointment.getLocation();
        String contact = appointment.getContact();
        String startTime = appointment.getLocalStartTimeStamp();
        String endTime = appointment.getLocalEndTimeStamp();
        LocalDate dateValue = LocalDate.parse(appointment.getLocalDate());



        modifyApptCustId.getSelectionModel().select(customerId);
        modifyApptTitle.setText(title);
        modifyApptType.getSelectionModel().select(type);
        modifyApptLocation.getSelectionModel().select(location);
        modifyApptContact.setText(contact);
        apptIdLabel.setText(Integer.toString(appointmentId));
        modifyApptDate.setValue(dateValue);
        modifyApptStart.setValue(startTime);
        modifyApptEnd.setValue(endTime);
    }

    @FXML
    void modifyApptSaveEvent(ActionEvent event) throws IOException, DateTimeParseException {

        apptId = appointment.getAppointmentId();

        customerId = modifyApptCustId.getSelectionModel().getSelectedItem();
        Integer userId = appointment.getUserId();
        String apptTitle = modifyApptTitle.getText();
        String apptType = modifyApptType.getSelectionModel().getSelectedItem();
        String apptLocation = modifyApptLocation.getSelectionModel().getSelectedItem();
        String apptContact = modifyApptContact.getText();
        String apptStart = modifyApptStart.getSelectionModel().getSelectedItem();
        String apptEnd = modifyApptEnd.getSelectionModel().getSelectedItem();
        LocalDate apptDate = modifyApptDate.getValue();
        String createdBy = appointment.getCreatedBy();

        String startDateNTime = apptDate + " " + apptStart;
        String endDateNTime = apptDate + " " + apptEnd;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s");
        LocalDateTime currentTime = java.time.LocalDateTime.now();
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("Z");

        LocalDateTime localDateTimeToStringStart = LocalDateTime.parse(startDateNTime, formatter);
        ZonedDateTime dateTimeStartLocal = ZonedDateTime.of(localDateTimeToStringStart, localZoneId);
        ZonedDateTime localDateTimeStartToUTC = dateTimeStartLocal.withZoneSameInstant(utcZoneId);

        int startUTCYear = localDateTimeStartToUTC.getYear();
        int startUTCMonth = localDateTimeStartToUTC.getMonthValue();
        int startUTCDay = localDateTimeStartToUTC.getDayOfMonth();
        int startUTCHour = localDateTimeStartToUTC.getHour();
        int startUTCMinute = localDateTimeStartToUTC.getMinute();
        int startUTCSecond = localDateTimeStartToUTC.getSecond();

        String localStartTimeToUtc = startUTCYear + "-" + startUTCMonth + "-" + startUTCDay + " "
                + startUTCHour + ":" + startUTCMinute + ":" + startUTCSecond;

        String localStartTimeStamp = startUTCHour + ":" + startUTCMinute + ":" + startUTCSecond;

        LocalDateTime localDateTimeToStringEnd = LocalDateTime.parse(endDateNTime, formatter);
        ZonedDateTime dateTimeEndLocal = ZonedDateTime.of(localDateTimeToStringEnd, localZoneId);
        ZonedDateTime localDateTimeEndToUTC = dateTimeEndLocal.withZoneSameInstant(utcZoneId);

        int endUTCYear = localDateTimeEndToUTC.getYear();
        int endUTCMonth = localDateTimeEndToUTC.getMonthValue();
        int endUTCDay = localDateTimeEndToUTC.getDayOfMonth();
        int endUTCHour = localDateTimeEndToUTC.getHour();
        int endUTCMinute = localDateTimeEndToUTC.getMinute();
        int endUTCSecond = localDateTimeEndToUTC.getSecond();

        String localEndTimeToUtc = endUTCYear + "-" + (endUTCMonth) + "-" + (endUTCDay) + " "
                + endUTCHour + ":" + endUTCMinute + ":" + endUTCSecond;


        String localEndTimeStamp = endUTCHour + ":" + endUTCMinute + ":" + endUTCSecond;


        /*String localStartTimeToUtcSubOne = startUTCYear + "-" + startUTCMonth + "-" + startUTCDay + " "
                + (startUTCHour - 1)+ ":" + "59" + ":" + "59";

        String localEndTimeToUtcAddOne = endUTCYear + "-" + (endUTCMonth) + "-" + (endUTCDay) + " "
                        + (endUTCHour) + ":" + (endUTCMinute) + ":" + (endUTCSecond + 1);*/


            if(checkOverlap(localStartTimeToUtc, localEndTimeToUtc, createdBy) && verifyType(modifyApptType)){
            try {
                Statement statement = Database.getConnection().createStatement();
                String modifyApptQuery = "UPDATE appointment SET customerId='" + customerId + "', title ='" + apptTitle + "', location ='" + apptLocation + "', contact ='" + apptContact + "', type ='" + apptType + "', start ='" + localStartTimeToUtc + "', end ='" + localEndTimeToUtc + "', lastUpdate ='" + currentTime + "', lastUpdateBy ='" + createdBy + "' WHERE appointmentId = " + apptId;

                int apptExecuteUpdate = statement.executeUpdate(modifyApptQuery);
                if (apptExecuteUpdate == 1) {
                    AppointmentDatabase.populateAllViewAppointments();
                    AppointmentDatabase.populateMonthlyViewAppointments();
                    AppointmentDatabase.populateWeeklyViewAppointments();
                    System.out.println("Appointment modification was a success.");

                }
            } catch (SQLException e) {
                System.out.println("Error " + e.getMessage());
            }

            Appointments appointments = new Appointments(1, customerId, userId, apptTitle, apptType, apptLocation, apptContact, apptStart, apptEnd);
            appointments.setAppointmentId(apptId);
            appointments.setUserId(userId);
            appointments.setTitle(apptTitle);
            appointments.setType(apptType);
            appointments.setLocation(apptLocation);
            appointments.setContact(apptContact);
            appointments.setApptStart(apptStart);
            appointments.setApptEnd(apptEnd);
            appointments.setLocalStartTimeStamp(localStartTimeStamp);
            appointments.setLocalEndTimeStamp(localEndTimeStamp);
            AppointmentDatabase.addAppt(appointments);


            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.close();
        }
    }


    public boolean checkOverlap(String checkStart, String checkEnd, String createdBy) {



        try {
            Statement statement = Database.getConnection().createStatement();
            String timeCheckQuery = "SELECT * FROM appointment WHERE ('" + checkStart + "' BETWEEN start AND end OR '" + checkEnd + "' BETWEEN start AND end OR '" + checkStart + "' < start AND '" + checkEnd + "' > end) AND createdBy='" + createdBy + "' AND appointmentId<>'" + apptId +"'";
            ResultSet timeOverlapCheck = statement.executeQuery(timeCheckQuery);

            if (timeOverlapCheck.next()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment Time Error");
                alert.setHeaderText("Invalid time format.");
                alert.setContentText("An appointment already exists during this time frame.");
                alert.showAndWait();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return true;

    }

    public boolean verifyType(ComboBox modifyApptType) {
        if(modifyApptType.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An appointment type must be selected.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    private List<Integer> getCustomerIdData() {

        List<Integer> choices = new ArrayList<>();

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM customer");
            while (set.next()) {
                choices.add(set.getInt("customerId"));
            }


        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }

        return choices;
    }

    private List<Integer> getAppointmentIdData() {

        List<Integer> choices = new ArrayList<>();

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM appointment");
            while (set.next()) {
                choices.add(set.getInt("appointmentId"));
            }


        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }

        return choices;
    }

}
