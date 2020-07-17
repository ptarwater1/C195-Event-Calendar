package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerTable {

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> cityColumn;

    @FXML
    private TableColumn<?, ?> countryColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    void addCustTableEvent(ActionEvent event) throws IOException
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void backCustTableEvent(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void modifyCustTableEvent(ActionEvent event) throws IOException
        {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

}

