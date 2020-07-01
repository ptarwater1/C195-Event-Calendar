package Model;


import View_Controller.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDatabase {

    private static ObservableList<Customer> customersList = FXCollections.observableArrayList();

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
            String query = "SELECT customer.customerId, customer.customerName, address.address, city.city, address.zipCode, address.phone"
                    + " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
                    + "INNER JOIN city ON address.cityId = city.cityId";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Customer customer = new Customer(
                        results.getInt("customerId"),
                        results.getString("customerName"),
                        results.getString("address"),
                        results.getString("city"),
                        results.getString("zipCode"),
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
    public static boolean saveCustomer(String name, String address, int city, String zipCode, String country, String phone) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String queryOne = "INSERT INTO address SET address='" + address + "', city=" + city + ", zipCode='" + zipCode + "', country='" + country + "', phone='" + phone + "' ";
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
    public static boolean updateCustomer(int id, String customerName, String address, String city, String zipCode, String country, String phone) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String queryOne = "UPDATE address SET address='" + address + "', city=" + city + ", zipCode='" + zipCode + "', country='" + country + "', phone='" + phone + "' "
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

}