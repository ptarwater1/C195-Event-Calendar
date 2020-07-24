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
            tableViewMonthly.setItems(AppointmentDatabase.getAllAppointmentsTableList());

            AppointmentDatabase populateMonthlyAppointments = new AppointmentDatabase();
            populateMonthlyAppointments.populateMonthlyAppointments();

            tableViewMonthly.getSelectionModel();
            monthlyCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            monthlyContact.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getContact()));
            monthlyTitle.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getTitle()));
            monthlyType.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getType()));
            monthlyLocation.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getLocation()));
            monthlyStart.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getApptStart()));
            monthlyEnd.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getapptEnd()));

        }
}




