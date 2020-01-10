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
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> cusIdCol;
    @FXML
    private TableColumn<Customer, Integer> cusNameCol;
    @FXML
    private DatePicker calendarBox;
    @FXML
    private ComboBox<String> locationBox;
    @FXML
    private ComboBox<String> employeeContactBox;
    @FXML
    private ComboBox<String> startTimeHourBox;
    @FXML
    private ComboBox<String> endTimeHourBox;
    @FXML
    private ComboBox<String> startTimeMinuteBox;
    @FXML
    private ComboBox<String> endTimeMinuteBox;
    @FXML
    private TextField urlText;
    @FXML
    private TextArea descriptionText;
    ObservableList<String> hourList = FXCollections.observableArrayList();
    ObservableList<String> minuteList = FXCollections.observableArrayList();
    ObservableList<String> zeroOnly = FXCollections.observableArrayList();
    ObservableList<String> officeLocations = FXCollections.observableArrayList();
    private static Customer selectedCustomer;
    @FXML
    private ComboBox<String> titleComboBox;
    
    


    

    /**
     * Initializes the controller class.
     * Adds data to all comboboxes.
     * populates the Customer Table.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        titleComboBox.setItems(AppointmentDAO.populateTitleList());
        titleComboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            titleComboBox.setValue(newText);
        });
        customerTable.setItems(CustomerDAO.getAllCustomers());
        employeeContactBox.setItems(EmployeeDAO.populateEmployeeList());
        zeroOnly.add("00");
        hourList.addAll("9 a.m.", "10 a.m.", "11 a.m.", "12 p.m.", "1 p.m.", "2 p.m.", "3 p.m.", "4 p.m.", "5 p.m.");
        minuteList.addAll("00", "15", "30", "45");
        startTimeHourBox.setItems(hourList);
        endTimeHourBox.setItems(hourList);
        startTimeMinuteBox.setItems(minuteList);
        endTimeMinuteBox.setItems(minuteList);
        officeLocations.addAll("Pheonix, Arizona", "New York, New York", "London, England");
        locationBox.setItems(officeLocations);
        employeeContactBox.setItems(EmployeeDAO.populateEmployeeList());
        
    }    
    
    /**
     * Cancels adding an appointment.
     * Loads the appointment main screen.
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
            System.out.println("IO Exception: " + ex.getMessage());
        }
    }

    /**
     * Adds an appointment to the database.
     * Displays an error message if the appointment is overlapping either for the 
     * customer or the user.
     * Loads the appointment main screen.
     */
    @FXML
    private void handleAddAppointment(ActionEvent event) {
        int appointmentId = AppointmentDAO.generateAppointmentId();
        String exceptions = "";
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        } else {
            exceptions += "You have not selected a customer for appointment scheduling.\n";
        }
        exceptions += Validations.validateAppointment(titleComboBox.getValue(), employeeContactBox, locationBox, calendarBox, startTimeHourBox, startTimeMinuteBox, endTimeHourBox, endTimeMinuteBox);
        if(exceptions.length()!=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText(exceptions);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        int customerId = selectedCustomer.getCustomerId();
        String title = titleComboBox.getValue();
        String description = descriptionText.getText();
        String location = locationBox.getValue();
        String contact = employeeContactBox.getValue();
        String url = urlText.getText();
        Timestamp start = DateTimeConverters.generateTimestamp(calendarBox, startTimeHourBox, startTimeMinuteBox, locationBox);
        Timestamp end = DateTimeConverters.generateTimestamp(calendarBox, endTimeHourBox, endTimeMinuteBox, locationBox);
        if (start.after(end)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Check appointment times");
            errorAlert.setContentText("Ensure the end time is after the start time.");
            errorAlert.showAndWait();
            return;
        }
        String overlap = AppointmentDAO.isAppointmentOverlapping(start, end, customerId, contact, appointmentId);
        if (!overlap.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Appointment is overlapping");
            errorAlert.setContentText(overlap);
            errorAlert.showAndWait();
            return;
        }
        AppointmentDAO.addAppointment(appointmentId, customerId, title, location, description, contact, url, start, end);
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
     * Prevents appointments from starting after 5 p.m.
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
     * Prevents appointments from ending after 5 p.m.
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

    
    
    
}
