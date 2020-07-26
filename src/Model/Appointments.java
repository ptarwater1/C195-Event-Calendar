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
    private final SimpleStringProperty customerName = new SimpleStringProperty();
    private final SimpleStringProperty contact = new SimpleStringProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty type = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty apptStart = new SimpleStringProperty();
    private final SimpleStringProperty apptEnd = new SimpleStringProperty();



    public Appointments() {}

public Appointments(int appointmentId, int customerId, String customerName, String title, String type, String location, String contact, String apptStart, String apptEnd) {
    
    setAppointmentId(appointmentId);
    setCustomerId(customerId);
    setCustomerName(customerName);
    setContact(contact);
    setTitle(title);
    setType(type);
    setLocation(location);

    setApptStart(apptStart);
    setApptEnd(apptEnd);
               
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public String getApptStart() {
        return apptStart.get();
    }

    public String getapptEnd() {
        return apptEnd.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getContact() {
        return contact.get();
    }

    public String getType() {
        return type.get();
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
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

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public void setType(String type) {
        this.type.set(type);
    }



}