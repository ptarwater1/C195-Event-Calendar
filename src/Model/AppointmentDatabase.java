package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentDatabase {

    public static ObservableList<Appointments> getMonthlyAppointments (int id) {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        Appointments appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " +
                    "start >= '" + begin + "' AND start <= '" + end + "'";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                appointment = new Appointments(results.getInt("appointmentId"), results.getInt("customerId"),
                        results.getString("title"), results.getString("description"), results.getString("location"),
                        results.getString("contact"), results.getString("start"),
                        results.getString("end"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    // Get Weekly Appointments
    public static ObservableList<Appointments> getWeeklyAppoinments(int id) {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        Appointments appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " +
                    "start >= '" + begin + "' AND start <= '" + end + "'";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                appointment = new Appointments(results.getInt("appointmentId"), results.getInt("customerId"),
                        results.getString("title"), results.getString("description"), results.getString("location"),
                        results.getString("contact"), results.getString("start"),
                        results.getString("end"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    // 15 Minute Appointment Alert
    public static Appointments appt15Min() {
        Appointments appointment;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zid);
        LocalDateTime ldt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime ldt2 = ldt.plusMinutes(15);
        String user = UserDatabase.getActiveUser().getUsername();
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN '" + ldt + "' AND '" + ldt2 + "' AND " +
                    "contact='" + user + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                appointment = new Appointments(results.getInt("appointmentId"), results.getInt("customerId"),
                        results.getString("title"), results.getString("description"), results.getString("location"),
                        results.getString("contact"), results.getString("start"),
                        results.getString("end"));
                return appointment;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    // Save Appointment
    public static boolean saveAppointment(int id, String type, String contact, String location, String date, String time) {
        String title = type.split(":")[0];
        String description = type.split(":")[1];
        String tsStart = createTimeStamp(date, time, location, true);
        String tsEnd = createTimeStamp(date, time, location, false);
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "INSERT INTO appointment SET customerId='" + id + "', title='" + title + "', description='" +
                    description + "', contact='" + contact + "', location='" + location + "', start='" + tsStart + "', end='" +
                    tsEnd + "', url='', createDate=NOW(), createdBy='', lastUpdate=NOW(), lastUpdateBy=''";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    // Update Appointment
    public static boolean updateAppointment(int id, String type, String contact, String location, String date, String time) {
        String title = type.split(":")[0];
        String description = type.split(":")[1];
        String tsStart = createTimeStamp(date, time, location, true);
        String tsEnd = createTimeStamp(date, time, location, false);
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "UPDATE appointment SET title='" + title + "', description='" + description + "', contact='" +
                    contact + "', location='" + location + "', start='" + tsStart + "', end='" + tsEnd + "' WHERE " +
                    "appointmentId='" + id + "'";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    // Delete Appointment
    public static boolean deleteAppointment(int id) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "DELETE FROM appointment WHERE appointmentId = " + id;
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    // Disallow Overlapping Appointment
    public static boolean overlappingAppointment(int id, String location, String date, String time) {
        String start = createTimeStamp(date, time, location, true);
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start = '" + start + "' AND location = '" + location + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                if(results.getInt("appointmentId") == id) {
                    statement.close();
                    return false;
                }
                statement.close();
                return true;
            } else {
                statement.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return true;
        }
    }

    // Timestamp for Appointment based on location
    public static String createTimeStamp(String date, String time, String location, boolean startMode) {
        String h = time.split(":")[0];
        int rawH = Integer.parseInt(h);
        if(rawH < 9) {
            rawH += 12;
        }
        if(!startMode) {
            rawH += 1;
        }
        String rawD = String.format("%s %02d:%s", date, rawH, "00");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime ldt = LocalDateTime.parse(rawD, df);
        ZoneId zid;
        if(location.equals("New York")) {
            zid = ZoneId.of("America/New_York");
        } else if(location.equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
        } else {
            zid = ZoneId.of("Europe/London");
        }
        ZonedDateTime zdt = ldt.atZone(zid);
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        ldt = utcDate.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);
        return ts.toString();
    }
}
