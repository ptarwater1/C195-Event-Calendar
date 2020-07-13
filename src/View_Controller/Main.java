package View_Controller;

import Model.AppointmentDatabase;
import Model.Appointments;
import Model.Customer;
import Model.CustomerDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;


public class Main implements Initializable {

    @FXML
    public void handleCustomerButton() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Customer Main Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleAppointmentButton() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AppointmentsTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleReportsButton() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Report Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleLogsButton() {
        File file = new File("log.txt");
        if(file.exists()) {
            if(Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    System.out.println("Error Opening Log File: " + e.getMessage());
                }
            }
        }
    }

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
