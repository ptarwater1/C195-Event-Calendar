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

    public static ObservableList<Appointments> getAllAppointmentsTableList() {
        return allAppointmentsTableList;
    }

    public static void populateMonthlyAppointments() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Appointments> allAppointments = AppointmentDatabase.getAllAppointmentsTableList();
            ResultSet selectAppointmentID = statement.executeQuery("SELECT * FROM appointment WHERE appointmentId IS NOT NULL");

            ArrayList<Integer> activeAppointmentID = new ArrayList<>();

            while(selectAppointmentID.next()){
                activeAppointmentID.add(selectAppointmentID.getInt(1));
            }

            for (int appointmentIDLoop : activeAppointmentID) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT appointmentId, customerId, title, type, location, contact, start, end FROM appointment WHERE appointmentId = ' " + appointmentIDLoop + "'");
                appointmentsTableData.next();

                int appointmentID = appointmentsTableData.getInt("appointmentId");
                int customerID = appointmentsTableData.getInt("customerId");
                String Title = appointmentsTableData.getString("title");
                String Type = appointmentsTableData.getString("type");
                String Location = appointmentsTableData.getString("location");
                String Contact = appointmentsTableData.getString("contact");
                String Start = appointmentsTableData.getString("start");
                String End = appointmentsTableData.getString("end");


                ResultSet appointmentsCustTableData = statement.executeQuery("SELECT customerName FROM customer WHERE customerId = ' " + appointmentIDLoop + "'");
                appointmentsCustTableData.next();

                String customerName = appointmentsCustTableData.getString("customerName");


                appointments.setAppointmentId(appointmentID);
                appointments.setCustomerId(customerID);
                appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(Start);
                appointments.setApptEnd(End);
                allAppointments.add(appointments);

            }



        } catch(SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }




}
