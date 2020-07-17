package View_Controller;

import Model.AppointmentDatabase;
import Model.Appointments;
import Model.Customer;
import Model.CustomerDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;


public class Main implements Initializable {

    @FXML
    private Button custTableButton;

    @FXML
    private Button apptsTableButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button exitButton;

    @FXML
    void customerTableEvent(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTable.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void appointmentsTableEvent(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void reportsEvent(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/Reports.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }


    @FXML
    void exitEvent(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }


    //Appointment in the next 15 minutes alert setup
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Appointments appointment = AppointmentDatabase.appt15Min();
        if(appointment != null) {
            Customer customer = CustomerDatabase.getCustomer(appointment.getCustomerId());
            String text = String.format("You have a %s appointment with %s at %s",
                    appointment.getDescription(),
                    customer.getCustomerName(),
                    appointment.get15Time());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Alert");
            alert.setHeaderText("Appointment in Next 15 Minutes");
            alert.setContentText(text);
            alert.showAndWait();
        }
    }


}
