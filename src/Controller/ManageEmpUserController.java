/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.EmployeeDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Employee;
import model.User;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class ManageEmpUserController implements Initializable {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> userId;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, String> userPass;
    
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> empIdCol;
    @FXML
    private TableColumn<Employee, String> empNameCol;
    @FXML
    private TableColumn<Employee, String> empDepartmentCol;
    @FXML
    private TableColumn<Employee, String> empHireDateCol;
    private static User selectedUser;
    private static Employee selectedEmployee;

    /**
     * Initializes the controller class.
     * Loads information to the user table.
     * Loads information to the employee table.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        userPass.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
        userTable.setItems(User.getAllUsers());
        empIdCol.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        empNameCol.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        empDepartmentCol.setCellValueFactory(new PropertyValueFactory<>("employeeDepartment"));
        empHireDateCol.setCellValueFactory(new PropertyValueFactory<>("empHireDateString"));
        try {
            employeeTable.setItems(EmployeeDAO.getAllEmployees());
        } catch (ParseException ex) {
            Logger.getLogger(ManageEmpUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    /**
     * Loads the add user screen.
     */
    @FXML
    private void handleAddUser(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AddUser.fxml"));
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    /**
     * Saves the selected users information to a variable.
     * Loads the modify user screen.
     * Displays an error message if no user is selected.
     * Displays an error message if the test user is selected.
     */
    @FXML
    private void handleModifyUser(ActionEvent event) {
        if(userTable.getSelectionModel().getSelectedItem() != null) {
            selectedUser = userTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected a user to modify.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        if (selectedUser.getUserId() == 1) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Can not modify.");
            errorAlert.setContentText("The Test user can not be modified.");
            errorAlert.showAndWait();
            return;
        }
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyUser.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
        }
    }

    /**
     * Returns to the add user screen.
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
     *
     * @return selected user.
     */
    public static User getSelectedUser() {
        return selectedUser;
    }
    
    /**
     *
     * @return selected employee.
     */
    public static Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    /**
     * Deletes a user from the database.
     * Displays an error message if no user is selected.
     * Displays an error message if the test user is selected.
     */
    @FXML
    private void handleDelete(ActionEvent event) {
        if(userTable.getSelectionModel().getSelectedItem() != null) {
            selectedUser = userTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected a user to delete.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        if (selectedUser.getUserId() == 1) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Can not delete.");
            errorAlert.setContentText("The Test user can not be deleted.");
            errorAlert.showAndWait();
            return;
        }
        int userToDeleteId = selectedUser.getUserId();
        UserDAO.deleteUser(userToDeleteId);
        userTable.setItems(User.getAllUsers());
    }

    /**
     * Loads the add employee screen.
     */
    @FXML
    private void handleAddEmployee(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddEmployee.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ManageCustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Saves the selected employee information to a variable.
     * Loads the modify employee screen.
     * Displays an error message if no employee is selected.
     */
    @FXML
    private void handleModifyEmployee(ActionEvent event) {
        if(employeeTable.getSelectionModel().getSelectedItem() != null) {
            selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected an employee to modify.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyEmployee.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
        }
    }

    /**
     * Deletes a employee from the database.
     * Displays an error message if no employee is selected.
     */
    @FXML
    private void handleDeleteEmployee(ActionEvent event) throws ParseException {
        if(employeeTable.getSelectionModel().getSelectedItem() != null) {
            selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected an employee to delete.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        if (AppointmentDAO.doesEmployeeHaveAppointment(selectedEmployee.getEmployeeName())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You can not delete a employee who currently has an appointment scheduled.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        EmployeeDAO.deleteEmployee(selectedEmployee.getEmployeeId());
        employeeTable.setItems(EmployeeDAO.getAllEmployees());
        
    }

}
