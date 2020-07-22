package View_Controller;

import Model.Customer;
import Model.CustomerDatabase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerTable implements Initializable {

    @FXML
    private TableView<Customer> custTableView;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> cityColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    void addCustTableEvent(ActionEvent event) throws IOException
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void backCustTableEvent(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void modifyCustTableEvent(ActionEvent event) {

        //gets selection from customer table to modify
        Customer customerToModify = custTableView.getSelectionModel().getSelectedItem();
            if(customerToModify == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No customer selected.");
                alert.setContentText("Select a customer to modify.");
                alert.showAndWait();
            }

        custIndexMod = CustomerDatabase.getAllCustomersTableList().indexOf(customerToModify);

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.getMessage();
        }

    }

    @FXML
    void deleteCustTableEvent(ActionEvent event)
    {
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION,("Delete this customer?"));
     alert.setTitle("Confirm Delete.");
     Optional<ButtonType> result = alert.showAndWait();
     if(result.isPresent() && result.get() == ButtonType.OK) {

         ObservableList<Customer> allCustomers, singleCustomer;
         allCustomers = custTableView.getItems();
         singleCustomer = custTableView.getSelectionModel().getSelectedItems();
         singleCustomer.forEach(allCustomers::remove);
         selectedCustomer = custTableView.getSelectionModel().getSelectedItem();

         deleteCustomer(selectedCustomer.getCustomerId());


        }
    }

    private Customer selectedCustomer;

    public static void deleteCustomer(int selectedId) {

        ++selectedId;

        try {
            Statement statement = Database.getConnection().createStatement();
            String deleteCustomer = "DELETE FROM customer WHERE customerId =" + selectedId;
            int deletedCustomer = statement.executeUpdate(deleteCustomer);

        if(deletedCustomer == 1) {
            String deleteAddress = "DELETE FROM address WHERE addressId =" + selectedId;
            int deletedAddress = statement.executeUpdate(deleteAddress);

            if(deletedAddress == 1) {
                System.out.println("Deletion successful.");
            }
        }
            } catch(SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public static int getCustIndexToMod(){
        return custIndexMod;
    }

    private static int custIndexMod;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CustomerDatabase.getAllCustomersTableList().clear();
        custTableView.setItems(CustomerDatabase.getAllCustomersTableList());

        CustomerDatabase populateCustomers = new CustomerDatabase();
        populateCustomers.populateCustomerTable();

        //USE OF LAMBDAS TO INCREASE EFFICIENCY OF POPULATING CUSTOMER TABLE

        custTableView.getSelectionModel();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getCustomerName()));
        addressColumn.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getAddress()));
        cityColumn.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getCity()));
        countryColumn.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getCountry()));
        phoneColumn.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getPhone()));
    }

}

