package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddAppointment {

    @FXML
    private AnchorPane addApptAnchorPane;

    @FXML
    private Button addApptBackButton;

    @FXML
    private Button addApptSave;

    @FXML
    private DatePicker addApptDate;

    @FXML
    private ComboBox<?> addApptStartTime;

    @FXML
    private ComboBox<?> addApptEndTime;

    @FXML
    private TextField addApptLocation;

    @FXML
    private TextField addApptType;

    @FXML
    private TextField addApptCustomer;

    @FXML
    void addApptBackEvent(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }
}