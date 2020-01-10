/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.UserDAO;
import Utilities.Validations;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class AddUserController implements Initializable {

    @FXML
    private TextField userNameText;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField password2Text;
    @FXML
    private Button save;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Returns to the manage employees or users screen.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ManageEmpUser.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IOExcption: " + ex);
        }
    }

    /**
     * Adds an employee to the database.
     * Checks sure the passwords are matching.
     * Displays an error message if the passwords do not match.
     */
    @FXML
    private void handleSave(ActionEvent event) {
        String username = userNameText.getText();
        if(!Validations.validateUsername(username))
            return;
        String password;
        if (passwordText.getText() == null ? password2Text.getText() == null : passwordText.getText().equals(password2Text.getText())) {
        password = passwordText.getText();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Passwords do not match.");
            errorAlert.setContentText("Please ensure your password was entered correctly and try again.");
            errorAlert.showAndWait();
            return;
        }
        int userId = UserDAO.generateUserId();
        UserDAO.addUser(userId, username, password);
        Stage stage;
        stage = (Stage) save.getScene().getWindow();
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ManageEmpUser.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
        
    }
    
    
    
}
