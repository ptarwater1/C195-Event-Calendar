package View_Controller;

import Model.Customer;
import Model.CustomerDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ModifyCustomer implements Initializable {

    @FXML
    private ComboBox<String> modifyCustCity;

    @FXML
    private TextField modifyCustPhone;

    @FXML
    private TextField modifyCustName;

    @FXML
    private TextField modifyCustAddress;

    @FXML
    private Label modifyCustCountry;

    @FXML
    private TextField modifyCustId;


    private Customer customer;
    int custIndexMod = CustomerTable.getCustIndexToMod();

    @FXML
    void modifyCustSaveEvent(ActionEvent event) throws IOException

    {

        int addressId = 0;


 try {
        Integer customerId = Integer.parseInt(modifyCustId.getText());
        String customerName = modifyCustName.getText();
        String customerAddress = modifyCustAddress.getText();
        String customerCountry = modifyCustCountry.getText();
        String customerPhone = modifyCustPhone.getText();
        String customerCity = modifyCustCity.getSelectionModel().getSelectedItem();

        Customer customer = new Customer(customerId, customerName, customerAddress, customerCity, customerCountry, customerPhone);
        CustomerDatabase.getAllCustomersTableList().set(custIndexMod, customer);

        Statement statement = Database.getConnection().createStatement();

        ResultSet selectCustomer = statement.executeQuery("SELECT * FROM customer WHERE customerId= " + customerId + "");

        while (selectCustomer.next()) {

            addressId = selectCustomer.getInt("addressId");
        }

        String updateCustomer = "UPDATE customer SET customerName = '" + customerName + "' WHERE customerId = " + customerId + "";
        int updatedCustomer = statement.executeUpdate(updateCustomer);

        String updateAddress = "UPDATE address SET address = '" + customerAddress + "', postalCode = '', phone = '" + customerPhone + "' WHERE addressID = " + addressId;
        int updatedAddress = statement.executeUpdate(updateAddress);

    }catch(
    SQLException e) {
        System.out.println("Error " + e.getMessage());
    }

    Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
    Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.close();

}

    @FXML
    void chooseCountry(ActionEvent event) {
        String selectedCity = modifyCustCity.getSelectionModel().getSelectedItem();
        if(selectedCity.equals("Phoenix")||selectedCity.equals("New York")) {
            modifyCustCountry.setText("United States");
        } else modifyCustCountry.setText("United Kingdom");
    }

    @FXML
    void modifyCustBackEvent(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //Set choices for city selections
    ObservableList<String> cities = FXCollections.observableArrayList("Phoenix", "New York", "London");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customer = CustomerDatabase.getAllCustomersTableList().get(custIndexMod);

        int customerId = customer.getCustomerId();
        String customerName = customer.getCustomerName();
        String address = customer.getAddress();
        String city = customer.getCity();
        String country = customer.getCountry();
        String phone = customer.getPhone();

        modifyCustId.setText(String.valueOf(customerId));
        modifyCustName.setText(customerName);
        modifyCustAddress.setText(address);
        modifyCustCity.setItems(cities);
        modifyCustCity.setValue(city);
        modifyCustCountry.setText(country);
        modifyCustPhone.setText(phone);

    }
}
