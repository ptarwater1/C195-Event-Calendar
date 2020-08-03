package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppointmentDatabase {

    public static ObservableList<Appointments> allAppointmentsTableList = FXCollections.observableArrayList();
    public static ObservableList<Appointments> monthlyAppointmentsTableList = FXCollections.observableArrayList();
    public static ObservableList<Appointments> weeklyAppointmentsTableList = FXCollections.observableArrayList();


    public static void addAppt(Appointments appointments){
        allAppointmentsTableList.add(appointments);
        monthlyAppointmentsTableList.add(appointments);
        weeklyAppointmentsTableList.add(appointments);

    }

    public static ObservableList<Appointments> getAllAppointmentsTableList() {
        return allAppointmentsTableList;
    }

    public static ObservableList<Appointments> getMonthlyAppointmentsTableList() {
        return monthlyAppointmentsTableList;
    }

    public static ObservableList<Appointments> getWeeklyAppointmentsTableList() {
        return weeklyAppointmentsTableList;
    }

    public static void populateAllViewAppointments() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Appointments> allAppointments = AppointmentDatabase.getAllAppointmentsTableList();
            ResultSet allApptsData = statement.executeQuery("SELECT * FROM appointment WHERE appointmentId IS NOT NULL");

            ArrayList<Integer> selectedAppointments = new ArrayList<>();

            while (allApptsData.next()) {
                selectedAppointments.add(allApptsData.getInt(1));
            }

            for (int apptIdIteration : selectedAppointments) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT customer.customerName, appointmentId, appointment.customerId, userId, title, description, location, contact, type, url, start, end FROM appointment JOIN customer ON customer.customerId = appointment.customerId WHERE appointmentId = " + apptIdIteration);
                appointmentsTableData.next();

                int appointmentId = appointmentsTableData.getInt("appointmentId");
                int customerId = appointmentsTableData.getInt("customerId");
                String customerName = appointmentsTableData.getString("customerName");
                String Title = appointmentsTableData.getString("title");
                String Type = appointmentsTableData.getString("type");
                String Location = appointmentsTableData.getString("location");
                String Contact = appointmentsTableData.getString("contact");
                String Start = appointmentsTableData.getString("start");
                String End = appointmentsTableData.getString("end");


                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(Start);
                appointments.setApptEnd(End);
                allAppointments.add(appointments);

            }

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }


    }

    public static void populateMonthlyViewAppointments() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Appointments> allAppointments = AppointmentDatabase.getMonthlyAppointmentsTableList();
            ResultSet allApptsData = statement.executeQuery("SELECT * FROM appointment WHERE start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 30 DAY)");

            ArrayList<Integer> selectedAppointments = new ArrayList<>();

            while (allApptsData.next()) {
                selectedAppointments.add(allApptsData.getInt(1));
            }

            for (int apptIdIteration : selectedAppointments) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT customer.customerName, appointmentId, appointment.customerId, userId, title, description, location, contact, type, url, start, end FROM appointment JOIN customer ON customer.customerId = appointment.customerId WHERE appointmentId = " + apptIdIteration);
                appointmentsTableData.next();

                int appointmentId = appointmentsTableData.getInt("appointmentId");
                int customerId = appointmentsTableData.getInt("customerId");
                String customerName = appointmentsTableData.getString("customerName");
                String Title = appointmentsTableData.getString("title");
                String Type = appointmentsTableData.getString("type");
                String Location = appointmentsTableData.getString("location");
                String Contact = appointmentsTableData.getString("contact");
                String Start = appointmentsTableData.getString("start");
                String End = appointmentsTableData.getString("end");

                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(Start);
                appointments.setApptEnd(End);
                allAppointments.add(appointments);

            }

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }


    }

    public static void populateWeeklyViewAppointments() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Appointments> weeklyAppointments = AppointmentDatabase.getWeeklyAppointmentsTableList();
            ResultSet weeklyApptsData = statement.executeQuery("SELECT * FROM appointment WHERE start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)");

            ArrayList<Integer> selectedAppointments = new ArrayList<>();

            while (weeklyApptsData.next()) {
                selectedAppointments.add(weeklyApptsData.getInt(1));
            }

            for (int apptIdIteration : selectedAppointments) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT customer.customerName, appointmentId, appointment.customerId, userId, title, description, location, contact, type, url, start, end FROM appointment JOIN customer ON customer.customerId = appointment.customerId WHERE appointmentId = " + apptIdIteration);
                appointmentsTableData.next();

                int appointmentId = appointmentsTableData.getInt("appointmentId");
                int customerId = appointmentsTableData.getInt("customerId");
                String customerName = appointmentsTableData.getString("customerName");
                String Title = appointmentsTableData.getString("title");
                String Location = appointmentsTableData.getString("location");
                String Contact = appointmentsTableData.getString("contact");
                String Type = appointmentsTableData.getString("type");
                String Start = appointmentsTableData.getString("start");
                String End = appointmentsTableData.getString("end");

                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(Start);
                appointments.setApptEnd(End);
                weeklyAppointments.add(appointments);
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public static boolean deleteAllViewAppt(int apptId) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String deleteApptQuery = "DELETE FROM appointment WHERE appointmentId = " + apptId;
            int deletion = statement.executeUpdate(deleteApptQuery);
            if (deletion == 1) {
                System.out.println("Appointment deleted.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteMonthlyViewAppt(int apptId) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String deleteApptQuery = "DELETE FROM appointment WHERE appointmentId = " + apptId;
            int deletion = statement.executeUpdate(deleteApptQuery);
            if (deletion == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteWeeklyViewAppt(int apptId) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String deleteApptQuery = "DELETE FROM appointment WHERE appointmentId = " + apptId;
            int deletion = statement.executeUpdate(deleteApptQuery);
            if (deletion == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    //Created information alert for when an appointment begins in within 15 minutes for logged in user.
    public static Appointments appt15Minutes() {
        try {
            Statement statement = Database.getConnection().createStatement();
            String query15 = "SELECT user.userName, appointment.userId FROM user INNER JOIN appointment ON user.userId = appointment.userId WHERE start BETWEEN NOW() AND NOW() + INTERVAL 15 MINUTE AND user.userName ='" + UserDatabase.getActiveUser().getUsername() + "'";
            ResultSet resultSet = statement.executeQuery(query15);
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upcoming appointment.");
                alert.setHeaderText("15 Minute Warning");
                alert.setContentText("You have an appointment within the next 15 min.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            System.out.println("Error1: " + e.getMessage());
        }
        return null;
    }

}

