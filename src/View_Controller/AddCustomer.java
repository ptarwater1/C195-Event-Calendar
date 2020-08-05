package View_Controller;

import Model.Customer;
import Model.CustomerDatabase;
import Model.UserDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
    void addCustSaveEvent(ActionEvent event)  throws IOException {

        int customerId = 1;

        for(Customer customer : CustomerDatabase.allCustomersTableList) {

            if(customer.getCustomerId() > customerId)

                customerId = customer.getCustomerId();

        }

        int addressId = 1;
        int custId = customerId;
        int customerCity = addCustCity.getSelectionModel().getSelectedIndex() + 1;
        String customerCityChoice = addCustCity.getSelectionModel().getSelectedItem();

        customerId = ++customerId;
        String customerName = addCustName.getText();
        String customerAddress = addCustAddress.getText();
        String customerCityChoiceValue = customerCityChoice;
        String customerCountry = addCustCountry.getText();
        String customerPhone = addCustPhone.getText();

        if ((verifyName(customerName) && verifyAddress(customerAddress)) && verifyCity(customerCityChoiceValue) && verifyPhone(customerPhone)) {

            try {



            Statement statement = Database.getConnection().createStatement();

            ResultSet addressResultSet = statement.executeQuery("SELECT MAX(addressId) FROM address");
            if(addressResultSet.next()){
                addressId = addressResultSet.getInt(1);
                addressId++;
            }

            ResultSet customerResultSet = statement.executeQuery("SELECT MAX(customerId) FROM customer");
            if(customerResultSet.next()){
                custId = customerResultSet.getInt(1);
                custId++;
            }

            String addressQuery = "INSERT INTO address SET addressId=" + addressId + ", address='"
                    + customerAddress + "', address2='', phone='" + customerPhone + "', postalCode='', cityId= "
                    + customerCity + ", createDate=NOW(), createdBy='', lastUpdate=NOW(), lastUpdateBy=''";

            int addressExecuteUpdate = statement.executeUpdate(addressQuery);

            if(addressExecuteUpdate == 1) {

                String customerQuery = "INSERT INTO customer SET customerId="+ customerId + ", customerName='"
                        + customerName + "', addressId=" + addressId +", active=1, createDate=NOW(), createdBy='" + UserDatabase.getActiveUser().getUsername() + "', lastUpdate=NOW(), lastUpdateBy='" + UserDatabase.getActiveUser().getUsername() + "'";

                int customerExecuteUpdate = statement.executeUpdate(customerQuery);

                if(customerExecuteUpdate == 1) {
                    System.out.println("Successfully added customer.");

                }
            }
        }

        catch(SQLException e) {
            System.out.println("Error " + e.getMessage());
        }



            Customer customer = new Customer(customerId, customerName, customerAddress, customerCityChoiceValue, customerCountry, customerPhone);
            CustomerDatabase.addCustomer(customer);

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTable.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.close();
        }

    }

    //Set choices for city selections
    ObservableList<String> cities = FXCollections.observableArrayList("Phoenix", "New York", "London");


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
