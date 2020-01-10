/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import Utilities.DateTimeConverters;
import Utilities.Validations;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class ModifyAppointmentController implements Initializable {

    @FXML
    private DatePicker calendarBox;
    @FXML
    private ComboBox<String> startTimeHourBox;
    @FXML
    private ComboBox<String> endTimeHourBox;
    @FXML
    private TextArea descriptionText;
    @FXML
    private ComboBox<String> locationBox;
    @FXML
    private ComboBox<String> employeeContactBox;
    @FXML
    private ComboBox<String> startTimeMinuteBox;
    @FXML
    private ComboBox<String> endTimeMinuteBox;
    @FXML
    private TextField urlText;
    ObservableList<String> hourList = FXCollections.observableArrayList();
    ObservableList<String> minuteList = FXCollections.observableArrayList();
    ObservableList<String> zeroOnly = FXCollections.observableArrayList();
    ObservableList<String> officeLocations = FXCollections.observableArrayList();
    private static Appointment appointmentToModify;
    @FXML
    private ComboBox<String> customerName;
    @FXML
    private ComboBox<String> titleComboBox;
    
    
    /**
     * Initializes the controller class.
     * Loads selected users information to the fields.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        appointmentToModify = AppointmentMainController.getSelectedAppointment();
        customerName.setItems(CustomerDAO.populateCustomerList());
        employeeContactBox.setItems(EmployeeDAO.populateEmployeeList());
        titleComboBox.setItems(AppointmentDAO.populateTitleList());
        titleComboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            titleComboBox.setValue(newText);
        });
        titleComboBox.setValue(appointmentToModify.getAppointmentTitle());
        zeroOnly.add("00");
        hourList.addAll("9 a.m.", "10 a.m.", "11 a.m.", "12 p.m.", "1 p.m.", "2 p.m.", "3 p.m.", "4 p.m.", "5 p.m.");
        minuteList.addAll("00", "15", "30", "45");
        startTimeHourBox.setItems(hourList);
        endTimeHourBox.setItems(hourList);
        startTimeMinuteBox.setItems(minuteList);
        endTimeMinuteBox.setItems(minuteList);
        officeLocations.addAll("Pheonix, Arizona", "New York, New York", "London, England");
        locationBox.setItems(officeLocations);
        urlText.setText(appointmentToModify.getAppointmentUrl());
        descriptionText.setText(appointmentToModify.getAppointmentDescription());
        calendarBox.setValue(DateTimeConverters.calToLocalDate(appointmentToModify.getAppointmentStartTime()));
        locationBox.setValue(appointmentToModify.getAppointmentLocation());
        employeeContactBox.setValue(appointmentToModify.getEmployeeContact());
        startTimeMinuteBox.setValue(DateTimeConverters.calToStringMin(appointmentToModify.getAppointmentStartTime()));
        endTimeMinuteBox.setValue(DateTimeConverters.calToStringMin(appointmentToModify.getAppointmentEndTime()));
        startTimeHourBox.setValue(DateTimeConverters.calToComboBoxHour(appointmentToModify.getAppointmentStartTime(), appointmentToModify.getAppointmentLocation()));
        endTimeHourBox.setValue(DateTimeConverters.calToComboBoxHour(appointmentToModify.getAppointmentEndTime(), appointmentToModify.getAppointmentLocation()));
        customerName.setValue(appointmentToModify.getCustomerName());
    }    
    
    /**
     * Prevents and appointment from being scheduled to start after 5 p.m.
     */
    @FXML
    private void handleStartHourAction(ActionEvent event) {
        if ("5 p.m.".equals(startTimeHourBox.getValue())) {
            startTimeMinuteBox.setItems(zeroOnly);
            startTimeMinuteBox.setValue("00");
        } else {
            String selectedMinute;
            selectedMinute = startTimeMinuteBox.getValue();
            startTimeMinuteBox.setItems(minuteList);
            startTimeMinuteBox.setValue(selectedMinute);
        }
    }

    /**
     * Prevents and appointment from being scheduled to end after 5 p.m.
     */
    @FXML
    private void handleEndHourAction(ActionEvent event) {
        if ("5 p.m.".equals(endTimeMinuteBox.getValue())) {
            endTimeMinuteBox.setItems(zeroOnly);
            endTimeMinuteBox.setValue("00");
        } else {
            String selectedMinute;
            selectedMinute = endTimeMinuteBox.getValue();
            endTimeMinuteBox.setItems(minuteList);
            endTimeMinuteBox.setValue(selectedMinute);
        }
    }

    /**
     * Returns to the appointment main screen.
     */
    @FXML
    private void handleCancelAppointment(ActionEvent event) {
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
     * Saves the modified appointment to the database.
     * Checks if appointment is overlapping for customer or employee.
     * Returns to the appointment main screen.
     */
    @FXML
    private void handleModifyAppointment(ActionEvent event) {
        String exceptions = "";
        exceptions += Validations.validateAppointmentCustomer(customerName);
        exceptions += Validations.validateAppointment(titleComboBox.getValue(), employeeContactBox, locationBox, calendarBox, startTimeHourBox, startTimeMinuteBox, endTimeHourBox, endTimeMinuteBox);
        if(exceptions.length()!=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText(exceptions);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        int appointmentId = appointmentToModify.getAppointmentId();
        int customerId = CustomerDAO.findCustomerId(customerName.getValue());
        String title = titleComboBox.getValue();
        String description = descriptionText.getText();
        String location = locationBox.getValue();
        String contact = employeeContactBox.getValue();
        String url = urlText.getText();
        Timestamp start = DateTimeConverters.generateTimestamp(calendarBox, startTimeHourBox, startTimeMinuteBox, locationBox);
        Timestamp end = DateTimeConverters.generateTimestamp(calendarBox, endTimeHourBox, endTimeMinuteBox, locationBox);
        String overlap = AppointmentDAO.isAppointmentOverlapping(start, end, customerId, contact, appointmentId);
        if (start.after(end)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Check appointment times");
            errorAlert.setContentText("Ensure the end time is after the start time.");
            errorAlert.showAndWait();
            return;
        }
        if (!overlap.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Appointment is overlapping");
            errorAlert.setContentText(overlap);
            errorAlert.showAndWait();
            return;
        }
        AppointmentDAO.modifyAppointment(appointmentId, customerId, title, location, description, contact, url, start, end);
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
        }
    }
    
    /**
     * Returns the index of the title from the array.
     */
    private int getTitleIndex(String title){
        List<String> titles = AppointmentDAO.populateTitleList().stream().collect(Collectors.toList());        
        ArrayList<String> titleArray = new ArrayList<>(titles.size());
        titleArray.addAll(titles);
        String titleSearch = title;
        int index = titleArray.indexOf(titleSearch);
        return index;
    }
}
