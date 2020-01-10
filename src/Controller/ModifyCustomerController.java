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
public class ModifyCustomerController implements Initializable {

    @FXML
    private TextField nameText;
    @FXML
    private TextField addressText1;
    @FXML
    private TextField postCodeText;
    @FXML
    private TextField phoneText;
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TextField addressText2;
    
    private static Customer customerToModify;
    @FXML
    private Button save;
    
    

    /**
     * Initializes the controller class.
     * Fills the input boxes with customers information.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityComboBox.setItems(CustomerDAO.populateCityList());
        customerToModify = ManageCustomersController.getSelectedCustomer();
        nameText.setText(customerToModify.getCustomerName());
        addressText1.setText(customerToModify.getCustomerAddress());
        addressText2.setText(customerToModify.getCustomerAddress2());
        postCodeText.setText(customerToModify.getCustomerZipCode());
        phoneText.setText(customerToModify.getCustomerPhone());
        cityComboBox.setValue(customerToModify.getCustomerCity());
    }
    
    /**
     * Saves the modified Customer to the database.
     * Checks if information is valid.
     * Returns to the manage customers screen.
     */
    @FXML
    private void handleSave() {
        String custName = nameText.getText();
        String custAddress = addressText1.getText();
        String custAddress2 = addressText2.getText();
        String custPostalCode = postCodeText.getText();
        String custPhone = phoneText.getText();
        int custAddressId = CustomerDAO.findAddressId(customerToModify.getCustomerId());
        String validate = Validations.validateCustomer(custName, custAddress, custPostalCode, custPhone, cityComboBox);
        if (!validate.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText(validate);
            errorAlert.showAndWait();
            return;
        }
        int cityId = CustomerDAO.findCountryId(cityComboBox.getValue());
        CustomerDAO.modifyCustomer(custAddressId, custAddress, custAddress2, cityId, custPostalCode, custPhone, custAddressId, custName);
        
        try {
            Stage stage;
            Parent root;
            stage = (Stage) save.getScene().getWindow();
            stage.close();
            root = FXMLLoader.load(getClass().getResource("/view/ManageCustomers.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
    /**
     * Loads the add city screen.
     */
    @FXML
    private void handleAddCity(ActionEvent event) {
        Customer.addOrModify = "ModifyCustomer";
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddCity.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("SQLException: " + ex);
        }
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
