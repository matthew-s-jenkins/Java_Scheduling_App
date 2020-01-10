/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class UserAdminAccessController implements Initializable {

    @FXML
    private Button enter;
    @FXML
    private Button back;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Checks if the password is correct.
     * Displays error if password is incorrect.
     * Loads the manage employee or user screen.
     */
    @FXML
    private void handleEnter(ActionEvent event) {
        String pw = password.getText();
        if ("admin".equals(pw)){
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = null;
            try {
              root = FXMLLoader.load(getClass().getResource("/view/ManageEmpUser.fxml"));
              Scene scene = new Scene(root);
              stage.setScene(scene);
              stage.show();
            } catch (IOException ex) {
                System.out.println("IO Exception: " + ex);
            }
        } else {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Password is incorrect.");
            errorAlert.setContentText("Please check your password and try again.");
            errorAlert.showAndWait();               
    }
    }

    /**
     * Returns to the appointment main screen.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AppointmentMain.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ManageCustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
}
