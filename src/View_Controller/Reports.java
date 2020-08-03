package View_Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML
    private ComboBox<String> chooseReport;

    @FXML
    private TextArea apptTypeByMonth;

    @FXML
    private TextArea apptSchedule;

    @FXML
    private TextArea apptTotalRemaining;

    ObservableList<String> reportTypesData = FXCollections.observableArrayList("Number of Appointment Types by Month", "The Schedule for Each Consultant", "Total Appointments Remaining This Year", "All Reports");

    @FXML
    void generateReport(ActionEvent event) {
        try {
            if (chooseReport.getSelectionModel().isSelected(0)) {
                StringBuilder apptTypeByMonthText;
                Statement statement1 = Database.getConnection().createStatement();
                String queryOne = "SELECT type, MONTHNAME(start) as 'Month', COUNT(*) as 'Total' FROM appointment GROUP BY type, MONTHNAME(start)";
                ResultSet resultSet1 = statement1.executeQuery(queryOne);
                apptTypeByMonthText = new StringBuilder();
                apptTypeByMonthText.append(String.format("%1$-10s %2$-15s %3$-10s \n", "Month", "Type", "Total"));
                apptTypeByMonthText.append(String.join("-", Collections.nCopies(20, "-")));
                apptTypeByMonthText.append("\n");
                while(resultSet1.next()){
                    apptTypeByMonthText.append(String.format("%1s    %2s           %3$d \n", resultSet1.getString("Month"), resultSet1.getString("type"), resultSet1.getInt("Total")));
                }
                apptTypeByMonth.setText(apptTypeByMonthText.toString());


            }
            if (chooseReport.getSelectionModel().isSelected(1)) {
                StringBuilder apptScheduleText;
                Statement statement2 = Database.getConnection().createStatement();
                String queryTwo ="SELECT userId as 'Consultant', start as 'Start',  location as 'Location', title as 'Title',type as 'Type', contact as 'Contact', customerId as 'Customer' FROM appointment GROUP BY userId, start, customerId, title, location, type, contact";
                ResultSet resultSet2 = statement2.executeQuery(queryTwo);
                apptScheduleText = new StringBuilder();
                apptScheduleText.append(String.format("%1$-15s %2$-20s %3$-15s %4$-20s %5$-20s %6$-20s %7$-20s \n", "Consultant", "Start", "Customer", "Location", "Title", "Type", "Contact"));
                apptScheduleText.append(String.join("-", Collections.nCopies(60, "-")));
                apptScheduleText.append("\n");
                while(resultSet2.next()){
                    apptScheduleText.append(String.format("%1s          %2s    %3s                 %4s           %5s            %6s             %7s  \n", resultSet2.getString("Consultant"), resultSet2.getString("Start"), resultSet2.getString("Customer"),resultSet2.getString("Location"), resultSet2.getString("Title"),  resultSet2.getString("Type"), resultSet2.getString("Contact")));
                }
                apptSchedule.setText(apptScheduleText.toString());




            }
            if (chooseReport.getSelectionModel().isSelected(2)) {
                StringBuilder apptTotalRemainingText;
                Statement statement3 = Database.getConnection().createStatement();
                String queryThree = "SELECT YEAR(start) as 'Year', COUNT(*) as 'Total Remaining' FROM appointment GROUP BY YEAR(start)";
                ResultSet resultSet3 = statement3.executeQuery(queryThree);
                apptTotalRemainingText = new StringBuilder();
                apptTotalRemainingText.append(String.format("%1$-10s %2$-10s \n", "Year", "Total Remaining"));
                apptTotalRemainingText.append(String.join("-", Collections.nCopies(16, "-")));
                apptTotalRemainingText.append("\n");
                while(resultSet3.next()){
                    apptTotalRemainingText.append(String.format("%s            %2s \n", resultSet3.getString("Year"), resultSet3.getString("Total Remaining")));
                }
                apptTotalRemaining.setText(apptTotalRemainingText.toString());

            }
            if (chooseReport.getSelectionModel().isSelected(3)) {
                StringBuilder apptTypeByMonthText;
                Statement statement1 = Database.getConnection().createStatement();
                String queryOne = "SELECT type, MONTHNAME(start) as 'Month', COUNT(*) as 'Total' FROM appointment GROUP BY type, MONTHNAME(start)";
                ResultSet resultSet1 = statement1.executeQuery(queryOne);
                apptTypeByMonthText = new StringBuilder();
                apptTypeByMonthText.append(String.format("%1$-10s %2$-15s %3$-10s \n", "Month", "Type", "Total"));
                apptTypeByMonthText.append(String.join("-", Collections.nCopies(20, "-")));
                apptTypeByMonthText.append("\n");
                while(resultSet1.next()){
                    apptTypeByMonthText.append(String.format("%1s    %2s           %3$d \n", resultSet1.getString("Month"), resultSet1.getString("type"), resultSet1.getInt("Total")));
                }
                apptTypeByMonth.setText(apptTypeByMonthText.toString());

                StringBuilder apptScheduleText;
                Statement statement2 = Database.getConnection().createStatement();
                String queryTwo ="SELECT userId as 'Consultant', start as 'Start',  location as 'Location', title as 'Title',type as 'Type', contact as 'Contact', customerId as 'Customer' FROM appointment GROUP BY userId, start, customerId, title, location, type, contact";
                ResultSet resultSet2 = statement2.executeQuery(queryTwo);
                apptScheduleText = new StringBuilder();
                apptScheduleText.append(String.format("%1$-15s %2$-20s %3$-15s %4$-20s %5$-20s %6$-20s %7$-20s \n", "Consultant", "Start", "Customer", "Location", "Title", "Type", "Contact"));
                apptScheduleText.append(String.join("-", Collections.nCopies(60, "-")));
                apptScheduleText.append("\n");
                while(resultSet2.next()){
                    apptScheduleText.append(String.format("%1s          %2s    %3s                 %4s           %5s            %6s             %7s  \n", resultSet2.getString("Consultant"), resultSet2.getString("Start"), resultSet2.getString("Customer"),resultSet2.getString("Location"), resultSet2.getString("Title"),  resultSet2.getString("Type"), resultSet2.getString("Contact")));
                }
                apptSchedule.setText(apptScheduleText.toString());

                StringBuilder apptTotalRemainingText;
                Statement statement3 = Database.getConnection().createStatement();
                String queryThree = "SELECT YEAR(start) as 'Year', COUNT(*) as 'Total Remaining' FROM appointment GROUP BY YEAR(start)";
                ResultSet resultSet3 = statement3.executeQuery(queryThree);
                apptTotalRemainingText = new StringBuilder();
                apptTotalRemainingText.append(String.format("%1$-10s %2$-10s \n", "Year", "Total Remaining"));
                apptTotalRemainingText.append(String.join("-", Collections.nCopies(16, "-")));
                apptTotalRemainingText.append("\n");
                while(resultSet3.next()){
                    apptTotalRemainingText.append(String.format("%s            %2s \n", resultSet3.getString("Year"), resultSet3.getString("Total Remaining")));
                }
                apptTotalRemaining.setText(apptTotalRemainingText.toString());



            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @FXML
    void reportBackEvent(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        chooseReport.setItems(reportTypesData);

    }

    private List<Integer> getUserIdData() {

        List<Integer> choices = new ArrayList<>();

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM appointment");
            while (set.next()) {
                choices.add(set.getInt("userId"));
            }


        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }

        return choices;
    }
}