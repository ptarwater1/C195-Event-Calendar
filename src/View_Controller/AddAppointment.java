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
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;


public class AddAppointment implements Initializable {

    @FXML
    private DatePicker addApptDate;

    @FXML
    private ComboBox<String> addApptStart;

    @FXML
    private ComboBox<String> addApptEnd;

    @FXML
    private ComboBox<String> addApptLocation;

    @FXML
    private ComboBox<String> addApptType;

    @FXML
    private ComboBox<Integer> addApptCustId;

    @FXML
    private TextField addApptContact;

    @FXML
    private TableView<Customer> custTableView;

    @FXML
    private TableColumn<Customer, Integer> custTableId;

    @FXML
    private TableColumn<Customer, Integer> custTableName;

    @FXML
    private TextField addApptTitle;

    @FXML
    void addApptBackEvent(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.close();
    }

    public static int customerId;
    public static int userId;

    //Combobox data: only allowed to choose times within business hours. Appts can start at 8AM and must end by 5PM.
    ObservableList<String> apptTypesData = FXCollections.observableArrayList("Interview", "Review", "Presentation", "Other");
    ObservableList<String> apptLocationsData = FXCollections.observableArrayList("Phoenix", "New York", "London");
    ObservableList<String> apptStartData = FXCollections.observableArrayList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00");
    ObservableList<String> apptEndData = FXCollections.observableArrayList("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00");
    ObservableList<Integer> customerIdData = FXCollections.observableArrayList(getCustomerIdData());

    @Override
    public void initialize(URL url, ResourceBundle resources) {



        addApptCustId.setItems(customerIdData);
        addApptType.setItems(apptTypesData);
        addApptLocation.setItems(apptLocationsData);
        addApptStart.setItems(apptStartData);
        addApptEnd.setItems(apptEndData);



        CustomerDatabase.getAllCustomersTableList().clear();
        custTableView.setItems(CustomerDatabase.getAllCustomersTableList());

        CustomerDatabase populateCustomers = new CustomerDatabase();
        populateCustomers.populateCustomerTable();

        custTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
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

    @FXML
    public void addApptSaveEvent(ActionEvent event)  throws IOException {

        customerId = addApptCustId.getSelectionModel().getSelectedItem();

        int appointmentId = 1;
        for (Appointments appointments : AppointmentDatabase.allAppointmentsTableList) {
            if (appointments.getAppointmentId() > appointmentId)
                appointmentId = appointments.getAppointmentId();
        }

        appointmentId = ++appointmentId;
        String apptTitle = addApptTitle.getText();
        String apptType = addApptType.getSelectionModel().getSelectedItem();
        String apptLocation = addApptLocation.getSelectionModel().getSelectedItem();
        String apptContact = addApptContact.getText();
        String apptStart = addApptStart.getSelectionModel().getSelectedItem();
        String apptEnd = addApptEnd.getSelectionModel().getSelectedItem();
        LocalDate apptDate = addApptDate.getValue();

        String startDateNTime = apptDate + " " + apptStart;
        String endDateNTime = apptDate + " " + apptEnd;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
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

        String convertedZoneStart = Integer.toString(startUTCYear) + "-" + Integer.toString(startUTCMonth) + "-" + Integer.toString(startUTCDay) + " "
                + Integer.toString(startUTCHour) + ":" + Integer.toString(startUTCMinute) + ":" + Integer.toString(startUTCMinute);


        LocalDateTime dateTimeToStringEnd = LocalDateTime.parse(endDateNTime, formatter);
        ZonedDateTime dateTimeEndLocal = ZonedDateTime.of(dateTimeToStringEnd, localZoneId);
        ZonedDateTime dateTimeEndUTC = dateTimeEndLocal.withZoneSameInstant(ZoneId.of("Z"));

        int endUTCYear = dateTimeEndUTC.getYear();
        int endUTCMonth = dateTimeEndUTC.getMonthValue();
        int endUTCDay = dateTimeEndUTC.getDayOfMonth();
        int endUTCHour = dateTimeEndUTC.getHour();
        int endUTCMinute = dateTimeEndUTC.getMinute();

        String convertedZoneEnd = Integer.toString(endUTCYear) + "-" + Integer.toString(endUTCMonth) + "-" + Integer.toString(endUTCDay) + " "
                + Integer.toString(endUTCHour) + ":" + Integer.toString(endUTCMinute) + ":" + Integer.toString(endUTCMinute);


        if(checkOverlap(convertedZoneStart, convertedZoneEnd)){

        try {

            Statement statement2 = Database.getConnection().createStatement();

            String addApptQuery = "INSERT INTO appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                    "VALUES ('" + appointmentId + "', '" + customerId + "', '" + UserDatabase.getActiveUserId().getUserId() + "', '" + apptTitle + "', 'not needed', '" + apptLocation + "', '" + apptContact + "', '" + apptType + "', 'not needed', '" + convertedZoneStart + "', '" + convertedZoneEnd + "', '" + currentTime + "','" + UserDatabase.getActiveUser().getUsername() + "', '" + currentTime + "', '" + UserDatabase.getActiveUser().getUsername() + "')";

            int apptExecuteUpdate = statement2.executeUpdate(addApptQuery);

            if (apptExecuteUpdate == 1) {
                System.out.println("Addition of appointment was a success.");
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }

        Appointments appointments = new Appointments(1, customerId, userId, apptTitle, apptType, apptLocation, apptContact, apptStart, apptEnd);
        appointments.setAppointmentId(1);
        appointments.setUserId(userId);
        appointments.setTitle(apptTitle);
        appointments.setType(apptType);
        appointments.setLocation(apptLocation);
        appointments.setContact(apptContact);
        appointments.setApptStart(apptStart);
        appointments.setApptEnd(apptEnd);
        AppointmentDatabase.addAppt(appointments);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.close();
        }

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
