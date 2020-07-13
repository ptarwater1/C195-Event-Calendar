package Model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Appointments {

    private final SimpleIntegerProperty appointmentId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty customerId = new SimpleIntegerProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty contact = new SimpleStringProperty();
    private final SimpleStringProperty apptStart = new SimpleStringProperty();
    private final SimpleStringProperty apptEnd = new SimpleStringProperty();    


    public Appointments() {}

public Appointments(int appointmentId, int customerId, String title, String description, String location, String contact, String apptStart, String apptEnd) {
    
    setAppointmentId(appointmentId);
    setCustomerId(customerId);
    setTitle(title);
    setDescription(description);
    setLocation(location);
    setContact(contact);
    setApptStart(apptStart);
    setApptEnd(apptEnd);
               
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public String getapptEnd() {
        return apptEnd.get();
    }

    public String getApptStart() {
        return apptStart.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getContact() {
        return contact.get();
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public void setApptEnd(String apptEnd) {
        this.apptEnd.set(apptEnd);
    }

    public void setApptStart(String apptTimeStart) {
        this.apptStart.set(apptTimeStart);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty getApptEndProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.apptEnd.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }

    public StringProperty getApptStartProperty() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.apptStart.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }

    public StringProperty getApptTitleProperty() {
        return this.title;
    }

    public StringProperty getApptDescriptionProperty() {
        return this.description;
    }

    public StringProperty getApptLocationProperty() {
        return this.location;
    }

    public StringProperty getApptContactProperty() {
        return this.contact;
    }

    public LocalDate getDateOnly() {
        Timestamp ts = Timestamp.valueOf(this.apptStart.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalDate ld;
        if(this.location.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
        } else if(this.location.get().equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
        } else {
            zid = ZoneId.of("Europe/London");
        }
        zdt = ts.toLocalDateTime().atZone(zid);
        ld = zdt.toLocalDate();
        return ld;
    }

    public String getTimeOnly() {
        Timestamp ts = Timestamp.valueOf(this.apptStart.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalTime lt;
        if(this.location.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(4);
        } else if(this.location.get().equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(7);
        } else {
            zid = ZoneId.of("Europe/London");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().plusHours(1);
        }
        int rawH = Integer.parseInt(lt.toString().split(":")[0]);
        if(rawH > 12) {
            rawH -= 12;
        }
        String ampm;
        if(rawH < 9 || rawH == 12) {
            ampm = "PM";
        } else {
            ampm = "AM";
        }
        String time = rawH + ":00 " + ampm;
        return time;
    }


    // 15 Minute Alert System
    public String get15Time() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt = LocalDateTime.parse(this.apptStart.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid);
        DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime localTime = LocalTime.parse(utcDate.toString().substring(11,16), tFormatter);
        return localTime.toString();
    }

}