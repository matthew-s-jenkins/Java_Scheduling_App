/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CustomerDAO;
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
import javafx.scene.control.Alert.AlertType;
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
public class AddCustomerController implements Initializable {

    @FXML
    private TextField nameText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField addressText1;
    @FXML
    private TextField addressText2;
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TextField postCodeText;

    @FXML
    private Button saveCustomer;



    
    /**
     * Initializes the controller class.
     * Populates the City list.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityComboBox.setItems(CustomerDAO.populateCityList());
    }    

    /**
     * Adds a Customer to database.
     * returns the manage customers screen.
     */
    @FXML
    private void handleSave() {
        String custName = nameText.getText();
        String custAddress = addressText1.getText();
        String custAddress2 = addressText2.getText();
        String custPostalCode = postCodeText.getText();
        String custPhone = phoneText.getText();
        String validate = Validations.validateCustomer(custName, custAddress, custPostalCode, custPhone, cityComboBox);
        if (!validate.isEmpty()){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText(validate);
            errorAlert.showAndWait();
            return;
        }
        int addressId = CustomerDAO.generateAddressId();
        int cityId = CustomerDAO.findCountryId(cityComboBox.getValue());
        int customerId = CustomerDAO.generateCustomerId();
        CustomerDAO.addCustomer(addressId, custAddress, custAddress2, cityId, custPostalCode, custPhone, customerId, custName);
        Stage stage;
        stage = (Stage) saveCustomer.getScene().getWindow();
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ManageCustomers.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }

    }

    /**
     * Loads the add city screen
     */
    @FXML
    private void handleAddCity(ActionEvent event) throws IOException {
        Customer.addOrModify = "AddCustomer";
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCity.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
}
    
    /**
     * Returns to the manage customers screen.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ManageCustomers.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IOExcption: " + ex);
        }
    }
}
