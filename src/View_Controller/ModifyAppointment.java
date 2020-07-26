package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class ModifyAppointment {

    @FXML
    private Button modifyApptBackButton;

    @FXML
    private DatePicker modifyApptDate;

    @FXML
    private ComboBox<String> modifyApptStart;

    @FXML
    private ComboBox<String> modifyApptEnd;

    @FXML
    private ComboBox<String> modifyApptType;

    @FXML
    private ComboBox<String> modifyApptLocation;

    @FXML
    private TextField modifyApptContact;

    @FXML
    private ComboBox<String> modifyApptCustName;


    @FXML
    void modifyCustBackEvent(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
