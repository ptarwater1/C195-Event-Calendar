package View_Controller;

import Model.UserDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Label welcomeMessage;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    void attemptLogin(ActionEvent event) throws IOException {
        String username =  usernameField.getText();
        String password = passwordField.getText();
        boolean authorizedUser = UserDatabase.login(username, password);
        if(authorizedUser) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titleError);
            alert.setHeaderText(headerError);
            alert.setContentText(contentError);
            alert.showAndWait();
        }

    }

    private String titleError;
    private String headerError;
    private String contentError;


    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("Languages/Nat", locale);
        welcomeMessage.setText(rb.getString("welcomeMessage"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        titleError = rb.getString("titleError");
        headerError = rb.getString("headerError");
        contentError = rb.getString("contentError");
    }



}