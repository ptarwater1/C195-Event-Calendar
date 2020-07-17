package View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {

    @FXML
    private TextField addCustName;

    @FXML
    private TextField addCustAddress;

    @FXML
    private ComboBox<String> addCustCity;

    @FXML
    private Label addCustCountry;

    @FXML
    private TextField addCustPhone;

    @FXML
    void addCustBackEvent(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    //Set choices for cities and corresponding countries
    ObservableList<String> cities = FXCollections.observableArrayList("Phoenix", "New York", "London");
    ObservableList<String> countries = FXCollections.observableArrayList("United States", "United Kingdom");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addCustCity.setItems(cities);

    }

    @FXML
    void chooseCountry(ActionEvent event) {
        String selectedCity = addCustCity.getSelectionModel().getSelectedItem();
        if(selectedCity.equals("Phoenix")||selectedCity.equals("New York")) {
            addCustCountry.setText("United States");
        } else addCustCountry.setText("United Kingdom");

    }








}
