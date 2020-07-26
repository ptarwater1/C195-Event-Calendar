package View_Controller;

import Model.AppointmentDatabase;
import Model.Appointments;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsTable implements Initializable {

    @FXML
    private Tab tabMonthly;

    @FXML
    private TableView<Appointments> tableViewMonthly;

    @FXML
    private TableColumn<Appointments,String> monthlyCustomerName;

    @FXML
    private TableColumn<Appointments,String> monthlyContact;

    @FXML
    private TableColumn<Appointments,String>  monthlyTitle;

    @FXML
    private TableColumn<Appointments,String>  monthlyType;

    @FXML
    private TableColumn<Appointments,String>  monthlyLocation;

    @FXML
    private TableColumn<Appointments,String>  monthlyStart;

    @FXML
    private TableColumn<Appointments,String>  monthlyEnd;

    @FXML
    private Tab tabWeekly;

    @FXML
    private TableView<Appointments> tableViewWeekly;

    @FXML
    private TableColumn<Appointments,String>  weeklyCustomerName;

    @FXML
    private TableColumn<Appointments,String>  weeklyContact;

    @FXML
    private TableColumn<Appointments,String>  weeklyTitle;

    @FXML
    private TableColumn<Appointments,String>  weeklyType;

    @FXML
    private TableColumn<Appointments,String>  weeklyLocation;

    @FXML
    private TableColumn<Appointments,String>  weeklyStart;

    @FXML
    private TableColumn<Appointments,String>  weeklyEnd;

    @FXML
    private Tab tabAll;

    @FXML
    private TableView<Appointments> tableViewAll;

    @FXML
    private TableColumn<Appointments,String> allCustomerName;

    @FXML
    private TableColumn<Appointments,String> allContact;

    @FXML
    private TableColumn<Appointments,String> allTitle;

    @FXML
    private TableColumn<Appointments,String> allType;

    @FXML
    private TableColumn<Appointments,String> allLocation;

    @FXML
    private TableColumn<Appointments,String> allStart;

    @FXML
    private TableColumn<Appointments,String> allEnd;

        @FXML
        void addApptEvent(ActionEvent event) throws IOException {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        @FXML
        void backApptTableEvent(ActionEvent event) {
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.close();
    }

        @FXML
        void modifyApptEvent(ActionEvent event) throws IOException {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

        @Override
        public void initialize(URL url, ResourceBundle rb) {

            AppointmentDatabase.getAllAppointmentsTableList().clear();
            AppointmentDatabase.getMonthlyAppointmentsTableList().clear();
            AppointmentDatabase.getWeeklyAppointmentsTableList().clear();
            tableViewMonthly.setItems(AppointmentDatabase.getMonthlyAppointmentsTableList());
            tableViewWeekly.setItems(AppointmentDatabase.getWeeklyAppointmentsTableList());
            tableViewAll.setItems(AppointmentDatabase.getAllAppointmentsTableList());

            AppointmentDatabase populateAllAppointments = new AppointmentDatabase();
            populateAllAppointments.populateAllViewAppointments();

            tableViewMonthly.getSelectionModel();
            monthlyCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            monthlyContact.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getContact()));
            monthlyTitle.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getTitle()));
            monthlyType.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getType()));
            monthlyLocation.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getLocation()));
            monthlyStart.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getApptStart()));
            monthlyEnd.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getapptEnd()));

            //AppointmentDatabase populateMonthlyAppointments = new AppointmentDatabase();
            populateAllAppointments.populateMonthlyViewAppointments();

            tableViewWeekly.getSelectionModel();
            weeklyCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            weeklyContact.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getContact()));
            weeklyTitle.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getTitle()));
            weeklyType.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getType()));
            weeklyLocation.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getLocation()));
            weeklyStart.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getApptStart()));
            weeklyEnd.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getapptEnd()));

            populateAllAppointments.populateWeeklyViewAppointments();
            tableViewAll.getSelectionModel();
            allCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            allContact.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getContact()));
            allTitle.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getTitle()));
            allType.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getType()));
            allLocation.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getLocation()));
            allStart.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getApptStart()));
            allEnd.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getapptEnd()));
            
            

        }
}




