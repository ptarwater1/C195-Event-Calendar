package View_Controller;

import Model.Customer;
import Model.CustomerDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.Database;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
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

    @FXML
    void addCustSaveEvent(ActionEvent event) {


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


    public boolean saveCustomer() {
        String customerName = addCustName.getText();
        String address = addCustAddress.getText();
        String city = addCustCity.getSelectionModel().getSelectedItem();
        String country = addCustCountry.getText();
        String phone = addCustPhone.getText();
        if(!verifyName(customerName)||!verifyAddress(address)||!verifyCity(city)||!verifyPhone(phone)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Check that all required information is entered.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public boolean verifyName(String name) {
        if(name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Name field cannot be left blank.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public boolean verifyAddress(String address) {
        if(address.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Address field cannot be left blank.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public boolean verifyCity(String city) {
        if(city.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A city must be selected.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }


    public boolean verifyPhone(String phone) {
        if(phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A phone number must be entered.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }



}
