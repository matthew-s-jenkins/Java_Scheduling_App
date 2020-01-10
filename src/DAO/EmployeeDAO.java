/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utilities.DBConnection;
import Utilities.DateTimeConverters;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;

/**
 *
 * @author mjenk
 */
public class EmployeeDAO {

    /**
     * Adds an employee to the database.
     * @param employeeId
     * @param employeeName
     * @param department
     * @param hireDate
     */
    public static void addEmployee(int employeeId, String employeeName, String department, Date hireDate){
        PreparedStatement statement;
        try {
            statement = DBConnection.getConnection().prepareStatement("INSERT INTO "
                    + "employee_tbl (EmployeeID, EmployeeName, Department, HireDate)"
                    + "VALUES ('" + employeeId + "', '" + employeeName + "', '" + department
                    + "', '" + hireDate + "');");
        statement.executeUpdate();
        System.out.println("Employee succesfully added!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Modifies an employee in he database.
     * @param employeeId
     * @param employeeName
     * @param department
     * @param hireDate
     */
    public static void modifyEmployee(int employeeId, String employeeName, String department, Date hireDate){
        PreparedStatement statement;
        try {
            statement = DBConnection.getConnection().prepareStatement("UPDATE employee_tbl "
            + "SET EmployeeName = '" + employeeName + "', Department = '" 
            + department + "', HireDate = '" 
            + hireDate + "' WHERE EmployeeID = '" + employeeId + "';");
        statement.executeUpdate();
        System.out.println("Employee succesfully modified!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Deletes an employee from the database.
     * @param employeeId
     */
    public static void deleteEmployee(int employeeId){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM employee_tbl WHERE EmployeeID = '" + employeeId + "';");
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Find all employees 
     * @return an employee object containing all employees.
     * @throws ParseException
     */
    public static ObservableList<Employee> getAllEmployees() throws ParseException {
    ObservableList<Employee> allEmployees = FXCollections.observableArrayList(); 
        try (Statement statement = DBConnection.getConnection().createStatement()) {
            String query = "SELECT EmployeeId, EmployeeName, Department, HireDate FROM employee_tbl;";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                int empId = results.getInt("EmployeeId");
                String name =results.getString("EmployeeName");
                String department = results.getString("Department");          
                String hireDate = results.getString("HireDate");
                Calendar hireDateCalendar = DateTimeConverters.stringToCalendar(hireDate);
                Employee employee = new Employee(
                        empId, name, department, hireDateCalendar);
                allEmployees.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allEmployees;
    }
    /**
     * Finds an unused employee Id number
     * @return the id number generated.
     */
    public static int generateEmployeeId() {
        int empId = 0;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT "
                    + "EmployeeId FROM employee_tbl ORDER BY EmployeeId ASC;");
            ResultSet rs = statement.executeQuery();
            if (rs.last()){
                empId = ((Number) rs.getObject(1)).intValue() + 1;
            } else {
                empId = 1;
            }
            
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
        return empId;
    }
    
    /**
     * Finds a list of employees in the database
     * @return An ObservableList containing the names of the current employees as a String.
     */
    public static ObservableList<String> populateEmployeeList() { 
        ObservableList<String> employees = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT EmployeeName FROM employee_tbl ORDER BY EmployeeName;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            employees.add(rs.getString("EmployeeName"));
            
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return employees;
    }


}

