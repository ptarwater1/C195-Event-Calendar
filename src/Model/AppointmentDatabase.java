package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
                ResultSet appointmentsTableData = statement.executeQuery("SELECT customer.customerName, appointmentId, appointment.customerId, userId, title, description, location, contact, type, url, start, end, appointment.createdBy FROM appointment JOIN customer ON customer.customerId = appointment.customerId WHERE appointmentId = " + apptIdIteration);
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
                String consultant = appointmentsTableData.getString("createdBy");



                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
                ZoneId localZoneId = ZoneId.systemDefault();
                ZoneId utcZoneId = ZoneId.of("Z");

                LocalDateTime utcDateTimeToStringStart = LocalDateTime.parse(Start, formatter);
                ZonedDateTime dateTimeStartUTC = ZonedDateTime.of(utcDateTimeToStringStart, utcZoneId);
                ZonedDateTime utcDateTimeStartToLocal = dateTimeStartUTC.withZoneSameInstant(localZoneId);

                int startLocalYear = utcDateTimeStartToLocal.getYear();
                int startLocalMonth = utcDateTimeStartToLocal.getMonthValue();
                int startLocalDay = utcDateTimeStartToLocal.getDayOfMonth();
                String monthStartString = String.valueOf(startLocalMonth);
                if (monthStartString.length() == 1) {
                    monthStartString = "0" + monthStartString;
                }
                String dayStartString = String.valueOf(startLocalDay);
                if (dayStartString.length() == 1) {
                    dayStartString = "0" + dayStartString;
                }
                int startLocalHour = utcDateTimeStartToLocal.getHour();
                String hourStartString = String.valueOf(startLocalHour);
                if (hourStartString.length() == 1) {
                    hourStartString = "0" + hourStartString;
                }
                int startLocalMinute = utcDateTimeStartToLocal.getMinute();
                String minuteStartString = String.valueOf(startLocalMinute);
                if (minuteStartString.length() == 1) {
                    minuteStartString = "0" + minuteStartString;
                }
                int startLocalSecond = utcDateTimeStartToLocal.getSecond();
                String secondStartString = String.valueOf(startLocalSecond);
                if (secondStartString.length() == 1) {
                    secondStartString = "0" + secondStartString;
                }

                String utcStartTimeToLocal = Integer.toString(startLocalYear) + "-" + monthStartString + "-" + dayStartString + " " + hourStartString + ":" + minuteStartString + ":" + secondStartString;

                String localStartTimestamp = hourStartString + ":" + minuteStartString + ":" + secondStartString;

                String localDate = startLocalYear + "-" + monthStartString + "-" + dayStartString;



                LocalDateTime utcDateTimeToStringEnd = LocalDateTime.parse(End, formatter);
                ZonedDateTime dateTimeEndUTC = ZonedDateTime.of(utcDateTimeToStringEnd, utcZoneId);
                ZonedDateTime utcDateTimeEndToLocal = dateTimeEndUTC.withZoneSameInstant(localZoneId);

                int endLocalYear = utcDateTimeEndToLocal.getYear();
                int endLocalMonth = utcDateTimeEndToLocal.getMonthValue();
                String monthEndString = String.valueOf(endLocalMonth);
                if (monthEndString.length() == 1) {
                    monthEndString = "0" + monthEndString;
                }
                int endLocalDay = utcDateTimeEndToLocal.getDayOfMonth();
                String dayEndString = String.valueOf(endLocalDay);
                if (dayEndString.length() == 1) {
                    dayEndString = "0" + dayEndString;
                }
                int endLocalHour = utcDateTimeEndToLocal.getHour();
                String hourEndString = String.valueOf(endLocalHour);
                if (hourEndString.length() == 1) {
                    hourEndString = "0" + hourEndString;
                }
                int endLocalMinute = utcDateTimeEndToLocal.getMinute();
                String minuteEndString = String.valueOf(endLocalMinute);
                if (minuteEndString.length() == 1) {
                    minuteEndString = "0" + minuteEndString;
                }
                int endLocalSecond = utcDateTimeEndToLocal.getSecond();
                String secondEndString = String.valueOf(endLocalSecond);
                if (secondEndString.length() == 1) {
                    secondEndString = "0" + secondEndString;
                }

                String utcEndTimeToLocal = endLocalYear + "-" + monthEndString + "-" + dayEndString + " " + hourEndString + ":" + minuteEndString + ":" + secondEndString;

                String localEndTimeStamp = hourEndString + ":" + minuteEndString + ":" + secondEndString;



                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(utcStartTimeToLocal);
                appointments.setApptEnd(utcEndTimeToLocal);
                appointments.setLocalStartTimeStamp(localStartTimestamp);
                appointments.setLocalEndTimeStamp(localEndTimeStamp);
                appointments.setLocalDate(localDate);
                appointments.setCreatedBy(consultant);
                allAppointments.add(appointments);
            }


        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }


    }

    public static void populateMonthlyViewAppointments() {

        try {

            Statement statement = Database.getConnection().createStatement();
            ObservableList<Appointments> monthlyAppointments = AppointmentDatabase.getMonthlyAppointmentsTableList();
            ResultSet allApptsData = statement.executeQuery("SELECT * FROM appointment WHERE start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 30 DAY)");

            ArrayList<Integer> selectedAppointments = new ArrayList<>();

            while (allApptsData.next()) {
                selectedAppointments.add(allApptsData.getInt(1));
            }

            for (int apptIdIteration : selectedAppointments) {

                Appointments appointments = new Appointments();
                ResultSet appointmentsTableData = statement.executeQuery("SELECT customer.customerName, appointmentId, appointment.customerId, userId, title, description, location, contact, type, url, start, end, appointment.createdBy FROM appointment JOIN customer ON customer.customerId = appointment.customerId WHERE appointmentId = " + apptIdIteration);
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
                String consultant = appointmentsTableData.getString("createdBy");


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
                ZoneId localZoneId = ZoneId.systemDefault();
                ZoneId utcZoneId = ZoneId.of("Z");

                LocalDateTime utcDateTimeToStringStart = LocalDateTime.parse(Start, formatter);
                ZonedDateTime dateTimeStartUTC = ZonedDateTime.of(utcDateTimeToStringStart, utcZoneId);
                ZonedDateTime utcDateTimeStartToLocal = dateTimeStartUTC.withZoneSameInstant(localZoneId);

                int startLocalYear = utcDateTimeStartToLocal.getYear();
                int startLocalMonth = utcDateTimeStartToLocal.getMonthValue();
                String monthStartString = String.valueOf(startLocalMonth);
                if (monthStartString.length() == 1) {
                    monthStartString = "0" + monthStartString;
                }
                int startLocalDay = utcDateTimeStartToLocal.getDayOfMonth();
                String dayStartString = String.valueOf(startLocalDay);
                if (dayStartString.length() == 1) {
                    dayStartString = "0" + dayStartString;
                }
                int startLocalHour = utcDateTimeStartToLocal.getHour();
                String hourStartString = String.valueOf(startLocalHour);
                if (hourStartString.length() == 1) {
                    hourStartString = "0" + hourStartString;
                }
                int startLocalMinute = utcDateTimeStartToLocal.getMinute();
                String minuteStartString = String.valueOf(startLocalMinute);
                if (minuteStartString.length() == 1) {
                    minuteStartString = "0" + minuteStartString;
                }
                int startLocalSecond = utcDateTimeStartToLocal.getSecond();
                String secondStartString = String.valueOf(startLocalSecond);
                if (secondStartString.length() == 1) {
                    secondStartString = "0" + secondStartString;
                }

                String utcStartTimeToLocal = Integer.toString(startLocalYear) + "-" + monthStartString + "-" + dayStartString + " "
                        + hourStartString + ":" + minuteStartString + ":" + secondStartString;


                LocalDateTime utcDateTimeToStringEnd = LocalDateTime.parse(End, formatter);
                ZonedDateTime dateTimeEndUTC = ZonedDateTime.of(utcDateTimeToStringEnd, utcZoneId);
                ZonedDateTime utcDateTimeEndToLocal = dateTimeEndUTC.withZoneSameInstant(localZoneId);

                int endLocalYear = utcDateTimeEndToLocal.getYear();
                int endLocalMonth = utcDateTimeEndToLocal.getMonthValue();
                String monthEndString = String.valueOf(endLocalMonth);
                if (monthEndString.length() == 1) {
                    monthEndString = "0" + monthEndString;
                }
                int endLocalDay = utcDateTimeEndToLocal.getDayOfMonth();
                String dayEndString = String.valueOf(endLocalDay);
                if (dayEndString.length() == 1) {
                    dayEndString = "0" + dayEndString;
                }
                int endLocalHour = utcDateTimeEndToLocal.getHour();
                String hourEndString = String.valueOf(endLocalHour);
                if (hourEndString.length() == 1) {
                    hourEndString = "0" + hourEndString;
                }
                int endLocalMinute = utcDateTimeEndToLocal.getMinute();
                String minuteEndString = String.valueOf(endLocalMinute);
                if (minuteEndString.length() == 1) {
                    minuteEndString = "0" + minuteEndString;
                }
                int endLocalSecond = utcDateTimeEndToLocal.getSecond();
                String secondEndString = String.valueOf(endLocalSecond);
                if (secondEndString.length() == 1) {
                    secondEndString = "0" + secondEndString;
                }

                String utcEndTimeToLocal = Integer.toString(endLocalYear) + "-" + monthEndString + "-" + dayEndString + " "
                        + hourEndString + ":" + minuteEndString + ":" + secondEndString;


                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(utcStartTimeToLocal);
                appointments.setApptEnd(utcEndTimeToLocal);
                appointments.setCreatedBy(consultant);
                monthlyAppointments.add(appointments);

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
                ResultSet appointmentsTableData = statement.executeQuery("SELECT customer.customerName, appointmentId, appointment.customerId, userId, title, description, location, contact, type, url, start, end, appointment.createdBy FROM appointment JOIN customer ON customer.customerId = appointment.customerId WHERE appointmentId = " + apptIdIteration);
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
                String consultant = appointmentsTableData.getString("createdBy");


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");
                ZoneId localZoneId = ZoneId.systemDefault();
                ZoneId utcZoneId = ZoneId.of("Z");

                LocalDateTime utcDateTimeToStringStart = LocalDateTime.parse(Start, formatter);
                ZonedDateTime dateTimeStartUTC = ZonedDateTime.of(utcDateTimeToStringStart, utcZoneId);
                ZonedDateTime utcDateTimeStartToLocal = dateTimeStartUTC.withZoneSameInstant(localZoneId);

                int startLocalYear = utcDateTimeStartToLocal.getYear();
                int startLocalMonth = utcDateTimeStartToLocal.getMonthValue();
                String monthStartString = String.valueOf(startLocalMonth);
                if (monthStartString.length() == 1) {
                    monthStartString = "0" + monthStartString;
                }
                int startLocalDay = utcDateTimeStartToLocal.getDayOfMonth();
                String dayStartString = String.valueOf(startLocalDay);
                if (dayStartString.length() == 1) {
                    dayStartString = "0" + dayStartString;
                }
                int startLocalHour = utcDateTimeStartToLocal.getHour();
                String hourStartString = String.valueOf(startLocalHour);
                if (hourStartString.length() == 1) {
                    hourStartString = "0" + hourStartString;
                }
                int startLocalMinute = utcDateTimeStartToLocal.getMinute();
                String minuteStartString = String.valueOf(startLocalMinute);
                if (minuteStartString.length() == 1) {
                    minuteStartString = "0" + minuteStartString;
                }
                int startLocalSecond = utcDateTimeStartToLocal.getSecond();
                String secondStartString = String.valueOf(startLocalSecond);
                if (secondStartString.length() == 1) {
                    secondStartString = "0" + secondStartString;
                }

                String utcStartTimeToLocal = Integer.toString(startLocalYear) + "-" + monthStartString + "-" + dayStartString + " "
                        + hourStartString + ":" + minuteStartString + ":" + secondStartString;


                LocalDateTime utcDateTimeToStringEnd = LocalDateTime.parse(End, formatter);
                ZonedDateTime dateTimeEndUTC = ZonedDateTime.of(utcDateTimeToStringEnd, utcZoneId);
                ZonedDateTime utcDateTimeEndToLocal = dateTimeEndUTC.withZoneSameInstant(localZoneId);

                int endLocalYear = utcDateTimeEndToLocal.getYear();
                int endLocalMonth = utcDateTimeEndToLocal.getMonthValue();
                String monthEndString = String.valueOf(endLocalMonth);
                if (monthEndString.length() == 1) {
                    monthEndString = "0" + monthEndString;
                }
                int endLocalDay = utcDateTimeEndToLocal.getDayOfMonth();
                String dayEndString = String.valueOf(endLocalDay);
                if (dayEndString.length() == 1) {
                    dayEndString = "0" + dayEndString;
                }
                int endLocalHour = utcDateTimeEndToLocal.getHour();
                String hourEndString = String.valueOf(endLocalHour);
                if (hourEndString.length() == 1) {
                    hourEndString = "0" + hourEndString;
                }
                int endLocalMinute = utcDateTimeEndToLocal.getMinute();
                String minuteEndString = String.valueOf(endLocalMinute);
                if (minuteEndString.length() == 1) {
                    minuteEndString = "0" + minuteEndString;
                }
                int endLocalSecond = utcDateTimeEndToLocal.getSecond();
                String secondEndString = String.valueOf(endLocalSecond);
                if (secondEndString.length() == 1) {
                    secondEndString = "0" + secondEndString;
                }

                String utcEndTimeToLocal = Integer.toString(endLocalYear) + "-" + monthEndString + "-" + dayEndString + " "
                        + hourEndString + ":" + minuteEndString + ":" + secondEndString;
                

                appointments.setAppointmentId(appointmentId);
                appointments.setCustomerId(customerId);
                appointments.setCustomerName(customerName);
                appointments.setTitle(Title);
                appointments.setType(Type);
                appointments.setLocation(Location);
                appointments.setContact(Contact);
                appointments.setApptStart(utcStartTimeToLocal);
                appointments.setApptEnd(utcEndTimeToLocal);
                appointments.setCreatedBy(consultant);
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

