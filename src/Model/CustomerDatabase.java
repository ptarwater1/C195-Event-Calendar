package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class CustomerDatabase {

    public CustomerDatabase() {}

    public static ObservableList<Customer> allCustomersTableList = FXCollections.observableArrayList();

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


    public static ObservableList<Customer> getAllCustomersTableList() {
        return allCustomersTableList;
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

    /*

    public static ObservableList<Customer> customersList = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        Statement statement = Database.getConnection().createStatement();
        String customerQuery = "SELECT customer.customerId, customer.customerName, address.address, city.city, country.country address.phone"
               + " FROM customer INNER JOIN address ON customer.addressId = address.address.Id"
                + "INNER JOIN city ON address.cityId = city.cityId"
                + "INNER JOIN country ON city.countryId = country.countryId";
        ResultSet customerResult = statement.executeQuery(customerQuery);

        while(customerResult.next()) {

            Customer customer = new Customer(customerResult.getInt("customerId"), customerResult.getString("customerName"),  customerResult.getString("address"), customerResult.getString("city"), customerResult.getString("country"), customerResult.getString("phone"));
            CustomerDatabase.customersList.add(customer);
        }

        return CustomerDatabase.customersList;

    }

    public static ObservableList<Customer> getCustomersList() {
        return customersList;
    }

    public static void fillCustomerTable() {
        try {
            Statement statement = Database.getConnection().createStatement();
            ObservableList<Customer> allCustomers = CustomerDatabase.getAllCustomers();
            ResultSet selectCustomerId = statement.executeQuery("SELECT customerId FROM customer WHERE active =1");

            ArrayList<Integer> activeCustomerId = new ArrayList<>();

            while (selectCustomerId.next()) {
                activeCustomerId.add(selectCustomerId.getInt(1));
            }

            for (int customerIdIteration : activeCustomerId) {
                Customer customer = new Customer();
                ResultSet customerData = statement.executeQuery("SELECT customerId, customerName, addressId FROM customer WHERE customerId = '" + customerIdIteration + "'");

                int customerId = customerData.getInt("customerId");
                int addressId = customerData.getInt("addressId");
                String customerName = customerData.getString("customerName");

                ResultSet addressData = statement.executeQuery("SELECT address, cityId, phone FROM address WHERE addressId = '" + addressId + "'");
                addressData.next();

                String address = addressData.getString("address");
                String cityId = addressData.getString("cityId");
                String phone = addressData.getString("phone");

                int cityIdNumber = addressData.getInt("cityId");

                ResultSet cityData = statement.executeQuery("SELECT city, countryId FROM city WHERE cityId = '" + cityIdNumber + "'");
                cityData.next();

                String city = addressData.getString("city");
                int countryIdNumber = cityData.getInt("countryId");

                ResultSet countryData = statement.executeQuery("SELECT country FROM country WHERE countryId = '" + countryIdNumber + "'");
                countryData.next();

                String country = countryData.getString("country");

                customer.setCustomerID(customerId);
                customer.setCustomerName(customerName);
                customer.setAddress(address);
                customer.setCity(city);
                customer.setCountry(country);
                customer.setPhone(phone);
                allCustomers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }






    /*

    public static Customer getCustomer(int id) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT * FROM customer WHERE customerId='" + id + "'";
            ResultSet results = statement.executeQuery(query);
            if (results.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(results.getString("customerName"));
                statement.close();
                return customer;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    //Display list of all customers
    public static ObservableList<Customer> getCustomersList() {
        customersList.clear();
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT customer.customerId, customer.customerName, address.address, city.city, country.country, address.phone"
                    + " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
                    + "INNER JOIN city ON address.cityId = city.cityId";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Customer customer = new Customer(
                        results.getInt("customerId"),
                        results.getString("customerName"),
                        results.getString("address"),
                        results.getString("city"),
                        results.getString("country"),
                        results.getString("phone"));
                customersList.add(customer);
            }
            statement.close();
            return customersList;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    // Saves new Customer to Database
    public static boolean saveCustomer(String name, String address, String city, String country, String phone) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String queryOne = "INSERT INTO address SET address='" + address + "', city=" + city + ", country='" + country + "', phone='" + phone + "' ";
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "INSERT INTO customer SET customerName='" + name + "', address=" + address;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    // Update existing Customer in Database
    public static boolean updateCustomer(int id, String customerName, String address, String city, String country, String phone) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String queryOne = "UPDATE address SET address='" + address + "', city=" + city + ", country='" + country + "', phone='" + phone + "' "
                    + "WHERE address=" + id;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "UPDATE customer SET customerName='" + customerName + "', addressId=" + id + " WHERE customerId=" + id;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    // Delete Customer from Database
    public static boolean deleteCustomer(int id) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String queryOne = "DELETE FROM address WHERE addressId=" + id;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "DELETE FROM customer WHERE customerId=" + id;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
*/
