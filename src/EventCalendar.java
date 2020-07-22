import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Database;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EventCalendar extends Application {

        @Override
        public void start(Stage stage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) throws SQLException {

            Connection conn = Database.startConnection();

            launch(args);

            Database.disconnect();
        }
    }
