package Model;


import java.time.LocalDateTime;

public class Appointments {

    private String customerName;
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String location;
    private String contact;
    private String type;
    private String apptStart;
    private String apptEnd;


   public Appointments() {}

   public Appointments(String customerName, int appointmentId, int customerId, int userId, String title, String location, String contact, String type, String apptStart, String apptEnd) {

        customerName = this.customerName;
        appointmentId = this.appointmentId;
        customerId = this.customerId;
        userId = this.userId;
        title = this.title;
        location = this.location;
        contact = this.contact;
        type = this.type;
        apptStart = this.apptStart;
        apptEnd = this.apptEnd;
    }

    public Appointments(int appointmentId, int customerId, int userId, String title, String type, String location, String contact, String apptStart, String apptEnd) {
    
        setAppointmentId(appointmentId);
        setCustomerId(customerId);
        setUserId(userId);
        setContact(contact);
        setTitle(title);
        setType(type);
        setLocation(location);
        setApptStart(apptStart);
        setApptEnd(apptEnd);

    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId(){
        return userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getApptStart() {
        return apptStart;
    }

    public String getApptEnd() {
        return apptEnd;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setApptEnd(String apptEnd) {
        this.apptEnd = apptEnd;
    }

    public void setApptStart(String apptStart) {
        this.apptStart = apptStart;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact= contact;
    }

    public void setType(String type) {
        this.type = type;
    }



}