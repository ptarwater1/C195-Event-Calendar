package View_Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;



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



    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}
