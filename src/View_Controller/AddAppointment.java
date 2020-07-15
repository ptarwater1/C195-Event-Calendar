package View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    //Arrays for available times and for errors.
    private final ObservableList<String> apptTimes = FXCollections.observableArrayList("8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM, 12:30 PM", "1:00 PM, 1:30 PM", "2:00 PM, 2:30 PM", "3:00 PM, 3:30 PM", "4:00 PM, 4:30 PM");

    private final ObservableList<String> errors = FXCollections.observableArrayList();
    private URL url;
    private ResourceBundle rb;

    public void handleAddAppointment(int id) {
        errors.clear();
        int apptStartTime = addApptStartTime.getSelectionModel().getSelectedIndex();
        int apptEndTime = addApptEndTime.getSelectionModel().getSelectedIndex();
        LocalDate ld = addApptDate.getValue();


    }
    //Check start and end times are not overlapping.


    // Handle Contact Verification


    // Handle Type Verification
    public boolean verifyType(int addApptType) {
        if(addApptType == -1) {
            errors.add("Type of appointment must be chosen.");
            return false;
        } else {
            return true;
        }
    }

    // Handle Time Verification
    public boolean verifyStartTime(int addApptStartTime) {
        if(addApptStartTime == -1) {
            errors.add("An Appointment Start Time must be chosen.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verifyEndTime(int addApptEndTime) {
        if(addApptEndTime == -1) {
            errors.add("An Appointment End Time must be chosen.");
            return false;
        } else {
            return true;
        }
    }


    // Handle Date Verification
    public boolean verifyDate(LocalDate aptDate) {
        if(aptDate == null) {
            errors.add("An Appointment Date must be selected");
            return false;
        } else {
            return true;
        }
    }

    // Handle DB Errors
    public String displayErrors(){
        String s = "";
        if(errors.size() > 0) {
            for(String err : errors) {
                s = s.concat(err);
            }
            return s;
        } else {
            s = "Database Error";
            return s;
        }
    }


    // Set Customer Name
    public void populateAddApptCustomer(String name) {
        addApptCustomer.setText(name);
    }

    // Set ResourceBundle
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addApptDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(
                        empty ||
                                date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                                date.isBefore(LocalDate.now()));
                if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #670007;");
                }
            }
        });
    }


    //Declaration of variables
    @FXML
    private Button addApptBack;

    @FXML
    private Button addApptSave;

    @FXML
    private DatePicker addApptDate;

    @FXML
    private ComboBox addApptStartTime;

    @FXML
    private ComboBox addApptEndTime;

    @FXML
    private TextField addApptLocation;

    @FXML
    private TextField addApptTitle;

    @FXML
    private TextField addApptType;

    @FXML
    private TextField addApptContact;

    @FXML
    private TextField addApptCustomer;

}

