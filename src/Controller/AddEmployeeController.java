/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EmployeeDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class AddEmployeeController implements Initializable {

    @FXML
    private TextField empNameText;
    @FXML
    private DatePicker empHireDatePicker;
    @FXML
    private TextField empDepartmentText;
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
     * Returns to the manage employee or users screen.
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
     * Adds an employee to the database
     * returns to the manage employee or users screen.
     */
    @FXML
    private void handleAddEmployee(ActionEvent event) {
        Integer empId = EmployeeDAO.generateEmployeeId();
        String empName = empNameText.getText();
        String empDepartment = empDepartmentText.getText();
        LocalDate empHireDateTemp = empHireDatePicker.getValue();
        Date empHireDate = Date.valueOf(empHireDateTemp);        
        EmployeeDAO.addEmployee(empId, empName, empDepartment, empHireDate);
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
