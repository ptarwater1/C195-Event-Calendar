package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppointmentDatabase {

    public static ObservableList<Appointments> allAppointmentsTableList = FXCollections.observableArrayList();
    public static ObservableList<Appointments> monthlyAppointmentsTableList = FXCollections.observableArrayList();
    public static ObservableList<Appointments> weeklyAppointmentsTableList = FXCollections.observableArrayList();
    public static ObservableList<Appointments> apptAssociatedCustomer =FXCollections.observableArrayList();

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

            while(allApptsData.next()) {
                selectedAppointments.add(allApptsData.getInt(1));
            }

            for (int apptIdIteration : selectedAppointments) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT appointmentId, customerId, title, type, location, contact, start, end FROM appointment WHERE appointmentId = ' " + apptIdIteration + "'");
                appointmentsTableData.next();

                int appointmentId = appointmentsTableData.getInt("appointmentId");
                int customerId = appointmentsTableData.getInt("customerId");
                String Title = appointmentsTableData.getString("title");
                String Type = appointmentsTableData.getString("type");
                String Location = appointmentsTableData.getString("location");
                String Contact = appointmentsTableData.getString("contact");
                String Start = appointmentsTableData.getString("start");
                String End = appointmentsTableData.getString("end");


                /*ResultSet appointmentsCustTableData = statement.executeQuery("SELECT customerName FROM customer WHERE customerId = ' " + apptIdIteration + "'");
                appointmentsCustTableData.next();
                String customerName = appointmentsCustTableData.getString("customerName");*/


                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                //appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(Start);
                appointments.setApptEnd(End);
                allAppointments.add(appointments);

            }

        }catch(SQLException e) {
            System.out.println("Error " + e.getMessage());
        }



    }

    public static void populateMonthlyViewAppointments() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Appointments> allAppointments = AppointmentDatabase.getMonthlyAppointmentsTableList();
            ResultSet allApptsData = statement.executeQuery("SELECT * FROM appointment WHERE start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 30 DAY)");

            ArrayList<Integer> selectedAppointments = new ArrayList<>();

            while(allApptsData.next()) {
                selectedAppointments.add(allApptsData.getInt(1));
            }

            for (int apptIdIteration : selectedAppointments) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT appointmentId, customerId, title, type, location, contact, start, end FROM appointment WHERE appointmentId = ' " + apptIdIteration + "'");
                appointmentsTableData.next();

                int appointmentId = appointmentsTableData.getInt("appointmentId");
                int customerId = appointmentsTableData.getInt("customerId");
                String Title = appointmentsTableData.getString("title");
                String Type = appointmentsTableData.getString("type");
                String Location = appointmentsTableData.getString("location");
                String Contact = appointmentsTableData.getString("contact");
                String Start = appointmentsTableData.getString("start");
                String End = appointmentsTableData.getString("end");


                /*ResultSet appointmentsCustTableData = statement.executeQuery("SELECT customerName FROM customer WHERE customerId = ' " + apptIdIteration + "'");
                appointmentsCustTableData.next();
                String customerName = appointmentsCustTableData.getString("customerName");*/


                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                //appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(Start);
                appointments.setApptEnd(End);
                allAppointments.add(appointments);

            }

        }catch(SQLException e) {
            System.out.println("Error " + e.getMessage());
        }



    }

    public static void populateWeeklyViewAppointments() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Appointments> weeklyAppointments = AppointmentDatabase.getWeeklyAppointmentsTableList();
            ResultSet weeklyApptsData = statement.executeQuery("SELECT * FROM appointment WHERE start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)");

            ArrayList<Integer> selectedAppointments = new ArrayList<>();

            while(weeklyApptsData.next()) {
                selectedAppointments.add(weeklyApptsData.getInt(1));
            }

            for (int apptIdIteration : selectedAppointments) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT appointmentId, customerId, title, type, location, contact, start, end FROM appointment WHERE appointmentId = ' " + apptIdIteration + "'");
                appointmentsTableData.next();

                int appointmentId = appointmentsTableData.getInt("appointmentId");
                int customerId = appointmentsTableData.getInt("customerId");
                String Title = appointmentsTableData.getString("title");
                String Type = appointmentsTableData.getString("type");
                String Location = appointmentsTableData.getString("location");
                String Contact = appointmentsTableData.getString("contact");
                String Start = appointmentsTableData.getString("start");
                String End = appointmentsTableData.getString("end");


                /*ResultSet appointmentsCustTableData = statement.executeQuery("SELECT customerName FROM customer WHERE customerId = ' " + apptIdIteration + "'");
                appointmentsCustTableData.next();
                String customerName = appointmentsCustTableData.getString("customerName");*/


                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                //appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(Start);
                appointments.setApptEnd(End);
                weeklyAppointments.add(appointments);

            }

        }catch(SQLException e) {
            System.out.println("Error " + e.getMessage());
        }



    }



}
