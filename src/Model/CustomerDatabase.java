package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class CustomerDatabase {


    public static ObservableList<Customer> allCustomersTableList = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomersTableList() {
        return allCustomersTableList;
    }
    //public CustomerDatabase() {}



    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        Statement statement = Database.getConnection().createStatement();
        String customerQuery = "SELECT customer.customerId, customer.customerName, address.address, address.phone, address.postalCode, city.city, country.country"
                + " FROM customer INNER JOIN address ON customer.addressId = address.addressId"
                + "INNER JOIN city ON address.cityId = city.cityId"
                + "INNER JOIN country ON city.countryId = country.countryId";
        ResultSet customerResults = statement.executeQuery(customerQuery);

        while(customerResults.next()) {

            Customer customer = new Customer(customerResults.getInt("customerId"), customerResults.getString("customerName"), customerResults.getString("address"), customerResults.getString("city"), customerResults.getString("country"),customerResults.getString("phone"));
            CustomerDatabase.allCustomersTableList.add(customer);

        }

        return CustomerDatabase.allCustomersTableList;

    }

    public static void addCustomer(Customer customer) {

        allCustomersTableList.add(customer);
    }


    public static void populateCustomerTable() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Customer> allCustomers = CustomerDatabase.getAllCustomersTableList();
            ResultSet selectCustomerID = statement.executeQuery("SELECT customerId FROM customer WHERE active = 1");

            ArrayList<Integer> activeCustomerID = new ArrayList<>();

            while(selectCustomerID.next()) {
                activeCustomerID.add(selectCustomerID.getInt(1));
            }

            for (int customerIDLoop : activeCustomerID) {

                Customer customer = new Customer();
                ResultSet customerTableData = statement.executeQuery("SELECT customerId, customerName, addressId FROM customer WHERE customerId = '" + customerIDLoop + "'");
                customerTableData.next();

                int customerID = customerTableData.getInt("customerId");
                int addressID = customerTableData.getInt("addressId");
                String customerName = customerTableData.getString("customerName");



                ResultSet addressTableData = statement.executeQuery("SELECT address, cityId, postalCode, phone FROM address WHERE addressId = '" + addressID + "'");
                addressTableData.next();

                String address = addressTableData.getString("address");
                String cityId = addressTableData.getString("cityId");
                String phone = addressTableData.getString("phone");



                int cityID = addressTableData.getInt("cityId");

                ResultSet cityTableData = statement.executeQuery("SELECT city, countryId FROM city WHERE cityId = '" + cityID + "'");
                cityTableData.next();

                String city = cityTableData.getString("city");
                int countryID = cityTableData.getInt("countryId");

                ResultSet countryTableData = statement.executeQuery("SELECT country FROM country WHERE countryId = '" + countryID + "'");
                countryTableData.next();

                String country = countryTableData.getString("country");

                customer.setCustomerID(customerID);
                customer.setCustomerName(customerName);
                customer.setAddress(address);
                customer.setCity(city);
                customer.setCountry(country);
                customer.setPhone(phone);
                allCustomers.add(customer);
            }
        }
        catch(SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }




}

