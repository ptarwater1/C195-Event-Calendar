package View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ModifyCustomer {


    ObservableList<String> cityIds = FXCollections.observableArrayList("Phoenix", " New York", "London");
    ObservableList<String> countryIds = FXCollections.observableArrayList("United States", "United Kingdom");


    @FXML
    void modifyCustBackEvent(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
