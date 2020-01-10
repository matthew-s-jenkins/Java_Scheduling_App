/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author mjenk
 */
public class Validations {

    /**
     * Checks customers information to see if its valid.
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param boxToValidate
     * @return a string of exceptions from the validation rules.
     */
    public static String validateCustomer(String name, String address, String postalCode, String phone, ComboBox boxToValidate){
        String validationExceptions = "";
        if(name.isEmpty()) validationExceptions += "Please enter a customer name.\n";
        if(address.isEmpty()) validationExceptions += "Customer address can not be empty.\n";
        if(postalCode.isEmpty()) validationExceptions += "Please enter a vaild postal code.\n";
        if(phone.length()<10||phone.length()>15) validationExceptions += "Please enter a valid phone number including area code.\n";
        if(boxToValidate.getSelectionModel().isEmpty()) validationExceptions += "Please select a city from the drop down menu.\n";
        return validationExceptions;
    }
    
    /**
     * Validates a username
     * @param username
     * @return true if valid, shows an alert if not valid and returns false.
     */
    public static boolean validateUsername(String username) {
        if (username.length() > 2 && username.length() < 30) {
            return true;
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Username not valid.");
            errorAlert.setContentText("Please enter a valid username between 2 and 30 characters and try again.");
            errorAlert.showAndWait();
            return false;
        }
    }
    
    /**
     * Checks the fields and returns a string of errors.
     * @param titleComboBox
     * @param employeeContactBox
     * @param locationBox
     * @param date
     * @param startTimeHourBox
     * @param startTimeMinuteBox
     * @param endTimeHourBox
     * @param endTimeMinuteBox
     * @return string of validation exceptions.
     */
    public static String validateAppointment(String titleComboBox, ComboBox<String> employeeContactBox, 
            ComboBox<String> locationBox, DatePicker date, ComboBox<String> startTimeHourBox, ComboBox<String> startTimeMinuteBox,
            ComboBox<String> endTimeHourBox, ComboBox<String> endTimeMinuteBox){
        String validationExceptions = "";
        if(titleComboBox == null) validationExceptions += "Please select a title or write one in.\n";
        if(employeeContactBox.getSelectionModel().isEmpty()) validationExceptions += "Please select an employee contact.\n";
        if(locationBox.getSelectionModel().isEmpty()) validationExceptions += "Please select an appointment location.\n";
        if(date.getValue() == null) validationExceptions += "Please select a date for the appointment.\n";
        if(startTimeHourBox.getSelectionModel().isEmpty()) validationExceptions += "Please select a start time (hour) for the appointment.\n";
        if(startTimeMinuteBox.getSelectionModel().isEmpty()) validationExceptions += "Please select a start time (minute) for the appointment.\n";
        if(endTimeHourBox.getSelectionModel().isEmpty()) validationExceptions += "Please select a end time (hour) for the appointment.\n";
        if(endTimeMinuteBox.getSelectionModel().isEmpty()) validationExceptions += "Please select a end time (minute) for the appointment.\n";
        return  validationExceptions;
    }
    
    /**
     * Validates if a customer is selected.
     * @param customerName
     * @return A string asking to select a customer.
     */
    public static String validateAppointmentCustomer(ComboBox<String> customerName){
        String validationExceptions = "";
        if(customerName.getSelectionModel().isEmpty()) validationExceptions += "Please select a customer.\n";
        return validationExceptions;
    }
            
            
}

