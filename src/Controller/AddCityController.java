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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class AddCityController implements Initializable {

    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private TextField cityText;
    @FXML
    private Button saveCity;

    /**
     * Initializes the controller class.
     * populates the country list.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryComboBox.setItems(CustomerDAO.populateCountryList());
    }    
    
    /**
     * Loads the add country screen
     */
    @FXML
    private void handleAddCountry(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCountry.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Adds a city to database.
     * returns the to add customer screen.
     */
    @FXML
    private void handleSaveCity(ActionEvent event) throws IOException {
        int countryId = CustomerDAO.findCountryId(countryComboBox.getValue());
        String cityName = cityText.getText();
        CustomerDAO.addCity(countryId, cityName);
        Stage stage = (Stage) saveCity.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/view/" + Customer.addOrModify + ".fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + Customer.addOrModify + ".fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IOExcption: " + ex);
        }
    }

}
