/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EmployeeDAO;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Employee;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class ModifyEmployeeController implements Initializable {

    @FXML
    private Button save;
    @FXML
    private TextField empDepartmentText;
    @FXML
    private DatePicker empHireDatePicker;
    @FXML
    private TextField empNameText;
    private static Employee employeeToModify;
    
    /**
     * Initializes the controller class.
     * Loads the selected employees information to the input fields.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeToModify = ManageEmpUserController.getSelectedEmployee();
        empNameText.setText(employeeToModify.getEmployeeName());
        empDepartmentText.setText(employeeToModify.getEmployeeDepartment());
        Date empHireDateDate = Employee.employeeHireDate.getTime();
        empHireDatePicker.setValue(empHireDateDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }    

    /**
     * Saves the modified employees information to the database.
     * Returns to the manage employee user screen.
     */
    @FXML
    private void handleModifyEmployee(ActionEvent event) {
        Integer empId = employeeToModify.getEmployeeId();
        String empName = empNameText.getText();
        String empDepartment = empDepartmentText.getText();
        LocalDate empHireDateTemp = empHireDatePicker.getValue();
        java.sql.Date empHireDate = java.sql.Date.valueOf(empHireDateTemp);
        EmployeeDAO.modifyEmployee(empId, empName, empDepartment, empHireDate);
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

    /**
     * Returns to the manage employee user screen.
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
    
}
