/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CustomerDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class AddCountryController implements Initializable {

    @FXML
    private TextField countryText;
    @FXML
    private Button saveCountry;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    /**
     * Loads the add country screen.
     */
    @FXML
    private void handleAddCountry(ActionEvent event) throws IOException {
        String country = countryText.getText();
        CustomerDAO.addCountry(country);
        Stage stage = (Stage) saveCountry.getScene().getWindow();
        stage.close();
        System.out.println(country + " succesfully added!");
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCity.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * returns the to add customer screen.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddCity.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IOExcption: " + ex);
        }
    }
    
    
}
