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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
    private ComboBox<Integer> modifyApptId;

    @FXML
    void modifyApptBackEvent(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public static int customerId;
    public static int apptId;

    @FXML
    void modifyApptSaveEvent(ActionEvent event) throws IOException {

        apptId = modifyApptId.getSelectionModel().getSelectedItem();
        customerId = modifyApptCustId.getSelectionModel().getSelectedItem();
        String apptTitle = modifyApptTitle.getText();
        String apptType = modifyApptType.getSelectionModel().getSelectedItem();
        String apptLocation = modifyApptLocation.getSelectionModel().getSelectedItem();
        String apptContact = modifyApptContact.getText();
        String apptStart = modifyApptStart.getSelectionModel().getSelectedItem();
        String apptEnd = modifyApptEnd.getSelectionModel().getSelectedItem();
        LocalDate apptDate = modifyApptDate.getValue();

        String startDateNTime = apptDate + " " + apptStart;
        String endDateNTime = apptDate + " " + apptEnd;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        LocalDateTime dateTimeToStringStart = LocalDateTime.parse(startDateNTime, formatter);
        ZonedDateTime dateTimeStartLocal = ZonedDateTime.of(dateTimeToStringStart, localZoneId);
        ZonedDateTime dateTimeStartUTC = dateTimeStartLocal.withZoneSameInstant(ZoneId.of("Z"));
        LocalDateTime currentTime = java.time.LocalDateTime.now();

        int startUTCYear = dateTimeStartUTC.getYear();
        int startUTCMonth = dateTimeStartUTC.getMonthValue();
        int startUTCDay = dateTimeStartUTC.getDayOfMonth();
        int startUTCHour = dateTimeStartUTC.getHour();
        int startUTCMinute = dateTimeStartUTC.getMinute();

        String convertedZoneLocalToUtcStart = Integer.toString(startUTCYear) + "-" + Integer.toString(startUTCMonth) + "-" + Integer.toString(startUTCDay) + " "
                + Integer.toString(startUTCHour) + ":" + Integer.toString(startUTCMinute) + ":" + Integer.toString(startUTCMinute);


        LocalDateTime dateTimeToStringEnd = LocalDateTime.parse(endDateNTime, formatter);
        ZonedDateTime dateTimeEndLocal = ZonedDateTime.of(dateTimeToStringEnd, localZoneId);
        ZonedDateTime dateTimeEndUTC = dateTimeEndLocal.withZoneSameInstant(ZoneId.of("Z"));

        int endUTCYear = dateTimeEndUTC.getYear();
        int endUTCMonth = dateTimeEndUTC.getMonthValue();
        int endUTCDay = dateTimeEndUTC.getDayOfMonth();
        int endUTCHour = dateTimeEndUTC.getHour();
        int endUTCMinute = dateTimeEndUTC.getMinute();

        String convertedLocalToUtcZoneEnd = Integer.toString(endUTCYear) + "-" + Integer.toString(endUTCMonth) + "-" + Integer.toString(endUTCDay) + " "
                + Integer.toString(endUTCHour) + ":" + Integer.toString(endUTCMinute) + ":" + Integer.toString(endUTCMinute);




        if(checkOverlap(startDateNTime, endDateNTime)){
            try {
                Statement statement = Database.getConnection().createStatement();

                String modifyApptQuery = "UPDATE appointment SET title ='" + apptTitle + "', location ='" + apptLocation + "', contact ='" + apptContact + "', type ='" + apptType + "', start ='" + startDateNTime + "', end ='" + endDateNTime + "', lastUpdate ='" + currentTime + "', lastUpdateBy ='" + UserDatabase.getActiveUser().getUsername() + "' WHERE appointmentId = " + apptId;

                int apptExecuteUpdate = statement.executeUpdate(modifyApptQuery);
                if (apptExecuteUpdate == 1) {
                    System.out.println("Appointment modification was a success.");

                }
            } catch (SQLException e) {
                System.out.println("Error " + e.getMessage());
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.close();
        }
    }



    Appointments apptSelection;
    int apptIndexMod = AppointmentsTable.getApptIndexToMod();
    private Appointments appointment;


    ObservableList<String> apptTypesData = FXCollections.observableArrayList("Interview", "Review", "Presentation", "Other");
    ObservableList<String> apptLocationsData = FXCollections.observableArrayList("Phoenix", "New York", "London");
    ObservableList<String> apptStartData = FXCollections.observableArrayList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00");
    ObservableList<String> apptEndData = FXCollections.observableArrayList("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00");
    ObservableList<Integer> customerIdData = FXCollections.observableArrayList(getCustomerIdData());
    ObservableList<Integer> appointmentIdData = FXCollections.observableArrayList(getAppointmentIdData());

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        modifyApptCustId.setItems(customerIdData);
        modifyApptType.setItems(apptTypesData);
        modifyApptLocation.setItems(apptLocationsData);
        modifyApptStart.setItems(apptStartData);
        modifyApptEnd.setItems(apptEndData);
        modifyApptId.setItems(appointmentIdData);

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


        modifyApptCustId.getSelectionModel().select(customerId);
        modifyApptTitle.setText(title);
        modifyApptType.getSelectionModel().select(type);
        modifyApptLocation.getSelectionModel().select(location);
        modifyApptContact.setText(contact);
        modifyApptId.getSelectionModel().select(appointmentId);


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

    public boolean checkOverlap(String checkStart, String checkEnd) {

        try {
            Statement statement = Database.getConnection().createStatement();
            String timeCheckQuery = "SELECT * FROM appointment WHERE ('" + checkStart + "' BETWEEN start AND end OR '" + checkEnd + "' BETWEEN start AND end OR '" + checkStart + "' > start AND '" + checkEnd + "' < end)";
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


}
