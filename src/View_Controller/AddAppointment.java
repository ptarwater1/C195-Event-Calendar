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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



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

    @FXML //Type 1 of exception control (throws)
    void addApptBackEvent(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.close();
    }

    public static int customerId;
    public static int userId;



    //Appointments cannot start before 8AM and must end by 5PM.
    ObservableList<String> apptStartData = FXCollections.observableArrayList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00");
    ObservableList<String> apptEndData = FXCollections.observableArrayList("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00");

    ObservableList<String> apptTypesData = FXCollections.observableArrayList("Interview", "Review", "Presentation", "Other");
    ObservableList<String> apptLocationsData = FXCollections.observableArrayList("Phoenix", "New York", "London");
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
        String hourStartString = String.valueOf(startUTCHour);
        if (hourStartString.length() == 1) {
            hourStartString = "0" + hourStartString;
        }

        int startUTCMinute = localDateTimeStartToUTC.getMinute();
        String minuteStartString = String.valueOf(startUTCMinute);
        if (minuteStartString.length() == 1) {
                minuteStartString = "0" + minuteStartString;
        }

        int startUTCSecond = localDateTimeStartToUTC.getSecond();
            String secondStartString = String.valueOf(startUTCSecond);
            if (secondStartString.length() == 1) {
                secondStartString = "0" + secondStartString;
            }


        String localStartTimeToUtc = Integer.toString(startUTCYear) + "-" + Integer.toString(startUTCMonth) + "-" + Integer.toString(startUTCDay) + " "
                + Integer.toString(startUTCHour) + ":" + Integer.toString(startUTCMinute) + ":" + Integer.toString(startUTCSecond);

        System.out.println("localStartTime To Utc " + localStartTimeToUtc);

        String utcTimeStampStart = hourStartString + ":" + minuteStartString + ":" + secondStartString;

        System.out.println("utcTimeStampStart " + utcTimeStampStart);




        LocalDateTime localDateTimeToStringEnd = LocalDateTime.parse(endDateNTime, formatter);
        ZonedDateTime dateTimeEndLocal = ZonedDateTime.of(localDateTimeToStringEnd, localZoneId);
        ZonedDateTime localDateTimeEndToUTC = dateTimeEndLocal.withZoneSameInstant(utcZoneId);

        int endUTCYear = localDateTimeEndToUTC.getYear();
        int endUTCMonth = localDateTimeEndToUTC.getMonthValue();
        int endUTCDay = localDateTimeEndToUTC.getDayOfMonth();
        int endUTCHour = localDateTimeEndToUTC.getHour();
        int endUTCMinute = localDateTimeEndToUTC.getMinute();
        int endUTCSecond = localDateTimeEndToUTC.getSecond();

        String localEndTimeToUtc = Integer.toString(endUTCYear) + "-" + Integer.toString(endUTCMonth) + "-" + Integer.toString(endUTCDay) + " "
                + Integer.toString(endUTCHour) + ":" + Integer.toString(endUTCMinute) + ":" + Integer.toString(endUTCSecond);

       /* LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of(ZoneId.systemDefault().getId());
        ZoneOffset localOffset = zone.getRules().getOffset(now);*/

        if(checkOverlap(localStartTimeToUtc, localEndTimeToUtc)){
        try {

            Statement statement2 = Database.getConnection().createStatement();

            String addApptQuery = "INSERT INTO appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                    "VALUES ('" + appointmentId + "', '" + customerId + "', '" + UserDatabase.getActiveUserId().getUserId() + "', '" + apptTitle + "', 'not needed', '" + apptLocation + "', '" + apptContact + "', '" + apptType + "', 'not needed', '" + localStartTimeToUtc + "', '" + localEndTimeToUtc + "', '" + currentTime + "','" + UserDatabase.getActiveUser().getUsername() + "', '" + currentTime + "', '" + UserDatabase.getActiveUser().getUsername() + "')";

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
        //Type 2 of exception control (try catch)
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
            System.out.println("Error: " + e.getMessage());
        }
        return true;

    }



    public boolean checkOutsideBusinessHoursStart(String checkTime, ZoneOffset localOffset){
        try{
            Statement statement = Database.getConnection().createStatement();
            String businessHoursQueryStart = "";

            ResultSet businessHoursCheckStart = statement.executeQuery(businessHoursQueryStart);

            if (businessHoursCheckStart.next()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment Time Error");
                alert.setHeaderText("Invalid time format.");
                alert.setContentText("Appointment cannot start before 8AM.");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }


    public boolean checkOutsideBusinessHoursEnd(String checkTime, ZoneOffset localOffset){
        try{
            Statement statement = Database.getConnection().createStatement();
            String businessHoursQueryStart = "";
            ResultSet businessHoursCheckStart = statement.executeQuery(businessHoursQueryStart);

            if (businessHoursCheckStart.next()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment Time Error");
                alert.setHeaderText("Invalid time format.");
                alert.setContentText("Appointment cannot start before 8AM.");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }




}
