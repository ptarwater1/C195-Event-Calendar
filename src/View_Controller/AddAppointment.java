package View_Controller;

import Model.AppointmentDatabase;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Database;

import javax.swing.plaf.nimbus.State;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    @FXML
    private AnchorPane addApptAnchorPane;

    @FXML
    private Button addApptBackButton;

    @FXML
    private Button addApptSave;

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
    private Label addApptCustName;

    @FXML
    private TextField addApptContact;

    @FXML
    private ComboBox<Integer> addApptCustId;

    @FXML
    void addApptBackEvent(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    ObservableList<Integer> customerIdData = FXCollections.observableArrayList(getCustomerIdData());

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
    void addApptSaveEvent(ActionEvent event) {

        int appointmentId = 1;

        for (Appointments appointments : AppointmentDatabase.allAppointmentsTableList) {

            if (appointments.getAppointmentId() > appointmentId)

                appointmentId = appointments.getAppointmentId();
        }

        int customerId = 1;
        appointmentId = ++appointmentId;
        int custId = customerId;

        Integer chosenId = addApptCustId.getSelectionModel().getSelectedItem();
        String apptType = addApptType.getSelectionModel().getSelectedItem();
        String apptLocation = addApptLocation.getSelectionModel().getSelectedItem();
        String apptContact = addApptContact.getText();
        String apptStart = addApptStart.getSelectionModel().getSelectedItem();
        String apptEnd = addApptEnd.getSelectionModel().getSelectedItem();

        try {

            Statement statement = Database.getConnection().createStatement();

            ResultSet appointmentResultSet = statement.executeQuery("SELECT MAX(appointmentId) FROM appointment");
            if(appointmentResultSet.next()){
                appointmentId = appointmentResultSet.getInt(1);
                appointmentId++;
            }
//Need to select chosen customer not custid++
            ResultSet customerResultSet = statement.executeQuery("SELECT MAX(customerId) FROM customer");
            if(customerResultSet.next()){
                custId = customerResultSet.getInt(1);
                custId++;
            }

            String appointmentQuery = "INSERT INTO appointment SET appointmentId=" + appointmentId + ", customerId='"
                    + chosenId + "', userId='1', title='', description='', location=" + apptLocation + ", contact=" + apptContact +"', type= "
                    + apptType + ", url='', start= '" + apptStart + "', end= '" + apptEnd
                    + "'createDate=NOW(), createdBy='test', lastUpdate=NOW(), lastUpdateBy='test'";

            int addApptExecuteUpdate = statement.executeUpdate(appointmentQuery);

            if(addApptExecuteUpdate == 1) {
                System.out.println("Successfully added appointment.");
            }


        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    /*

String addressQuery = "INSERT INTO appointment SET appointmentId=" + appointmentId + ", customerId='"
                    + chosenId + "', userId='1', title='', description='', location='" + apptLocation + "',
                     contact=" + apptContact +"', type= " + apptType + ", url='', start= '" + apptStart + "', end= '" + apptEnd + "'
                     createDate=NOW(), createdBy='test', lastUpdate=NOW(), lastUpdateBy='test'";





    */


    ObservableList<String> apptTypesData = FXCollections.observableArrayList("Interview", "Review", "Presentation", "Other");
    ObservableList<String> apptLocationsData = FXCollections.observableArrayList("Phoenix", "New York", "London");
    ObservableList<String> apptStartData = FXCollections.observableArrayList("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00");
    ObservableList<String> apptEndData = FXCollections.observableArrayList("09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00");
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addApptCustId.setItems(customerIdData);
        addApptType.setItems(apptTypesData);
        addApptLocation.setItems(apptLocationsData);
        addApptStart.setItems(apptStartData);
        addApptEnd.setItems(apptEndData);

    }

    //FIGURE OUT HOW TO FIND CUSTOMERNAME BASED ON CUSTOMERID
    @FXML
    void chooseCustomerName(ActionEvent event) {
        Integer selectedCustomerId = addApptCustId.getSelectionModel().getSelectedItem();
        if(selectedCustomerId.equals(1)||selectedCustomerId.equals(2)) {
            addApptCustName.setText("Retrieved");
        } else addApptCustName.setText("CustomerName");

    }

}