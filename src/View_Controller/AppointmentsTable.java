package View_Controller;


import Model.AppointmentDatabase;
import Model.Appointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsTable implements Initializable {

    @FXML
    private Tab tabMonthly;

    @FXML
    private TableView<Appointments> tableViewMonthly;

    @FXML
    private TableColumn<Appointments,String> monthlyCustomerName;

    @FXML
    private TableColumn<Appointments,String> monthlyContact;

    @FXML
    private TableColumn<Appointments,String>  monthlyTitle;

    @FXML
    private TableColumn<Appointments,String>  monthlyType;

    @FXML
    private TableColumn<Appointments,String>  monthlyLocation;

    @FXML
    private TableColumn<Appointments,String>  monthlyStart;

    @FXML
    private TableColumn<Appointments,String>  monthlyEnd;

    @FXML
    private Tab tabWeekly;

    @FXML
    private TableView<Appointments> tableViewWeekly;

    @FXML
    private TableColumn<Appointments,String>  weeklyCustomerName;

    @FXML
    private TableColumn<Appointments,String>  weeklyContact;

    @FXML
    private TableColumn<Appointments,String>  weeklyTitle;

    @FXML
    private TableColumn<Appointments,String>  weeklyType;

    @FXML
    private TableColumn<Appointments,String>  weeklyLocation;

    @FXML
    private TableColumn<Appointments,String>  weeklyStart;

    @FXML
    private TableColumn<Appointments,String>  weeklyEnd;

    @FXML
    private Tab tabAll;

    @FXML
    private TableView<Appointments> tableViewAll;

    @FXML
    private TableColumn<Appointments,String> allCustomerName;

    @FXML
    private TableColumn<Appointments,String> allContact;

    @FXML
    private TableColumn<Appointments,String> allTitle;

    @FXML
    private TableColumn<Appointments,String> allType;

    @FXML
    private TableColumn<Appointments,String> allLocation;

    @FXML
    private TableColumn<Appointments,String> allStart;

    @FXML
    private TableColumn<Appointments,String> allEnd;

    @FXML
    void addApptEvent(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointment.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backApptTableEvent(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private Appointments apptSelection;

    //The all tab is used only to display all appointments and should not be used to modify or delete appointments.
    @FXML
    void modifyApptEvent(ActionEvent event) throws IOException {
        if(tabWeekly.isSelected()) {
            Appointments apptToModify = tableViewWeekly.getSelectionModel().getSelectedItem();
            apptIndexMod = AppointmentDatabase.getWeeklyAppointmentsTableList().indexOf(apptToModify);

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        if (tabMonthly.isSelected()) {
            Appointments apptToModify = tableViewMonthly.getSelectionModel().getSelectedItem();
            apptIndexMod = AppointmentDatabase.getMonthlyAppointmentsTableList().indexOf(apptToModify);

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } if(tabAll.isSelected()) {
            Appointments apptToModify = tableViewAll.getSelectionModel().getSelectedItem();
            apptIndexMod = AppointmentDatabase.getAllAppointmentsTableList().indexOf(apptToModify);

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.close();

        }
    }

    //The all tab is used only to display all appointments and should not be used to modify or delete appointments.
    @FXML
    void deleteApptEvent(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,("Delete this appointment?"));
        alert.setTitle("Confirm Delete.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){}


        if(tabWeekly.isSelected()) {
            apptSelection = tableViewWeekly.getSelectionModel().getSelectedItem();
            AppointmentDatabase.deleteWeeklyViewAppt(apptSelection.getAppointmentId());
            AppointmentDatabase.deleteMonthlyViewAppt(apptSelection.getAppointmentId());
            AppointmentDatabase.deleteAllViewAppt(apptSelection.getAppointmentId());

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
            if(tabMonthly.isSelected()) {
                apptSelection = tableViewMonthly.getSelectionModel().getSelectedItem();
                AppointmentDatabase.deleteWeeklyViewAppt(apptSelection.getAppointmentId());
                AppointmentDatabase.deleteMonthlyViewAppt(apptSelection.getAppointmentId());
                AppointmentDatabase.deleteAllViewAppt(apptSelection.getAppointmentId());

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
        }
            if(tabAll.isSelected()) {
                apptSelection = tableViewAll.getSelectionModel().getSelectedItem();
                AppointmentDatabase.deleteWeeklyViewAppt(apptSelection.getAppointmentId());
                AppointmentDatabase.deleteMonthlyViewAppt(apptSelection.getAppointmentId());
                AppointmentDatabase.deleteAllViewAppt(apptSelection.getAppointmentId());

                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentsTable.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.close();
            }

    }

    //Selects appointment that should be modified.
    public static int getApptIndexToMod(){
        return apptIndexMod;
    }

    private static int apptIndexMod;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AppointmentDatabase.getAllAppointmentsTableList().clear();
        AppointmentDatabase.getMonthlyAppointmentsTableList().clear();
        AppointmentDatabase.getWeeklyAppointmentsTableList().clear();
        tableViewMonthly.setItems(AppointmentDatabase.getMonthlyAppointmentsTableList());
        tableViewWeekly.setItems(AppointmentDatabase.getWeeklyAppointmentsTableList());
        tableViewAll.setItems(AppointmentDatabase.getAllAppointmentsTableList());

        AppointmentDatabase populateAllAppointments = new AppointmentDatabase();
        populateAllAppointments.populateAllViewAppointments();
        populateAllAppointments.populateMonthlyViewAppointments();
        populateAllAppointments.populateWeeklyViewAppointments();


        tableViewMonthly.getSelectionModel();
        monthlyCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        monthlyTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthlyLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthlyContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        monthlyType.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthlyStart.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        monthlyEnd.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));


        tableViewWeekly.getSelectionModel();
        weeklyCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        weeklyTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        weeklyLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        weeklyContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        weeklyType.setCellValueFactory(new PropertyValueFactory<>("type"));
        weeklyStart.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        weeklyEnd.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));

        tableViewAll.getSelectionModel();
        allCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        allTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        allLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        allContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        allType.setCellValueFactory(new PropertyValueFactory<>("type"));
        allStart.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        allEnd.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
    }
}




