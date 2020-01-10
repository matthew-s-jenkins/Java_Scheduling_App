/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class ManageCustomersController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> cusId;
    @FXML
    private TableColumn<Customer, String> cusName;
    @FXML
    private TableColumn<Customer, String> cusPhone;
    @FXML
    private TableColumn<Customer, String> cusZip;
    @FXML
    private TableColumn<Customer, String> cusAddress;
    @FXML
    private TableColumn<Customer, String> cusCity;
    
    private static Customer selectedCustomer;
    @FXML
    private TableColumn<Customer, String> custAddress2;

    /**
     * Initializes the controller class.
     * Loads information to the customer table
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cusPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        cusAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
        cusZip.setCellValueFactory(new PropertyValueFactory<>("customerZipCode"));
        cusCity.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        customerTable.setItems(CustomerDAO.getAllCustomers());
    }    

    /**
     * Loads the add customer screen.
     */
    @FXML
    private void handleAddCustomer(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
        }
    }

    /**
     * Saves the selected customers information to a variable.
     * Loads the modify customer screen.
     */
    @FXML
    private void handleModifyCustomer(ActionEvent event) {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected a customer to modify.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
        }
    }
    
     // setter for selectedCustomer
    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * Returns to the appointment main screen.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ManageCustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes a customer from the database
     * Refreshes the table to reflect deletion.
     * Displays an error message if no customer is selected.
     */
    @FXML
    private void handleDeleteCustomer(ActionEvent event) {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected a customer to delete.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        if (AppointmentDAO.doesCustomerHaveAppointment(selectedCustomer.getCustomerId())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You can not delete a customer who currently has an appointment scheduled.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        CustomerDAO.deleteCustomer(selectedCustomer.getCustomerId());
        customerTable.setItems(CustomerDAO.getAllCustomers());
    }
    
    
}
