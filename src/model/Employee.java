/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Utilities.DateTimeConverters;
import java.util.Calendar;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mjenk
 */
public final class Employee {
 
    private final SimpleIntegerProperty employeeId = new SimpleIntegerProperty();
    private final SimpleStringProperty employeeName = new SimpleStringProperty();
    private final SimpleStringProperty employeeDepartment = new SimpleStringProperty();
    public static Calendar employeeHireDate;
    private String empHireDateString;

    /**
     *
     * @param id
     * @param name
     * @param department
     * @param hireDate
     */
    public Employee(int id, String name, String department, Calendar hireDate) {
        setEmployeeId(id);
        setEmployeeName(name);
        setEmployeeDepartment(department);
        setEmployeeHireDate(hireDate);
        setEmpHireDateString(DateTimeConverters.calToStringDate(hireDate));
    }

    /**
     * @return the empHireDateString
     */
    public String getEmpHireDateString() {
        return empHireDateString;
    }

    /**
     * @param empHireDateString the empHireDateString to set
     */
    public void setEmpHireDateString(String empHireDateString) {
        this.empHireDateString = empHireDateString;
    }

    /**
     * sets an employees id.
     * @param id
     */
    public void setEmployeeId(Integer id) {
        this.employeeId.set(id);
    }
    
    /**
     * gets an employees id
     * @return an integer of the employees id number
     */
    public Integer getEmployeeId() {
        return employeeId.get();
    }
    
    /**
     * sets an employees name.
     * @param name the employees name.
     */
    public void setEmployeeName(String name) {
        this.employeeName.set(name);
    }
    
    /**
     * gets the employees name.
     * @return a string containing the employees name.
     */
    public String getEmployeeName() {
        return employeeName.get();
    }
    
    /**
     * sets the employees department.
     * @param department 
     */
    public void setEmployeeDepartment(String department) {
        this.employeeDepartment.set(department);
    }
    
    /**
     * gets the employees department.
     * @return a string of the employees department.
     */
    public String getEmployeeDepartment() {
        return employeeDepartment.get();
    }
    
    /**
     * sets the employees hire date
     * @param hireDate 
     */
    public void setEmployeeHireDate(Calendar hireDate) {
        Employee.employeeHireDate = hireDate;
    }
    
    /**
     * gets employees hire date.
     * @return a calendar object of employees hire date.
     */
    public Calendar getEmployeeHireDate() {
        return employeeHireDate;
    }

}
