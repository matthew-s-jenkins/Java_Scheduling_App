/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import LambraInterfaces.ReportsInterface;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class ReportsController implements Initializable {

    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<Appointment, Integer> apptId;
    @FXML
    private TableColumn<Appointment, String> cusName;
    @FXML
    private TableColumn<Appointment, String> apptTitle;
    @FXML
    private TableColumn<Appointment, String> apptLocation;
    @FXML
    private TableColumn<Appointment, String> apptDescription;
    @FXML
    private TableColumn<Appointment, String> empContact;
    @FXML
    private TableColumn<Appointment, String> apptUrl;
    @FXML
    private TableColumn<Appointment, String> appointmentDate;
    @FXML
    private TableColumn<Appointment, String> startTime;
    @FXML
    private TableColumn<Appointment, String> endTime;
    @FXML
    private ComboBox<String> titleFilterCombobox;
    ObservableList<Appointment> allAppointments;
    @FXML
    private ComboBox<String> contactFilterComboBox;
    @FXML
    private ComboBox<String> appointmentLocationCombobox;
    

    /**
     * Initializes the controller class.
     * Loads all appointments to appointment table.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        cusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        empContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
        apptUrl.setCellValueFactory(new PropertyValueFactory<>("appointmentUrl"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("appointmentStartTimeString"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("appointmentEndTimeString"));
        appointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        try {
            allAppointments = AppointmentDAO.getAllAppointments();
        } catch (ParseException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        apptTable.setItems(allAppointments);
        titleFilterCombobox.setItems(AppointmentDAO.populateTitleList());
        contactFilterComboBox.setItems(AppointmentDAO.populateContactList());
        appointmentLocationCombobox.setItems(AppointmentDAO.populateLocationList());
    }    
    
    // Lambra Expession to filter by Title
    ReportsInterface filterByTitle = (ol, s) -> {
      
      ObservableList<Appointment> filteredList = FXCollections.observableArrayList();
      ol.forEach((Appointment appointment) -> {
          
          if(appointment.getAppointmentTitle().equals(s)){
              filteredList.add(appointment);
          }
      });
      return filteredList;
    };

    // Lambra Expession to filter by Contact
    ReportsInterface filterByEmployee = (ol, s) -> {
      
      ObservableList<Appointment> filteredList = FXCollections.observableArrayList();
      ol.forEach((Appointment appointment) -> {
          
          if(appointment.getEmployeeContact().equals(s)){
              filteredList.add(appointment);
          }
      });
      return filteredList;
    };
    
    // Lambra Expession to filter by Location
    ReportsInterface filterByLocation = (ol, s) -> {
      
      ObservableList<Appointment> filteredList = FXCollections.observableArrayList();
      ol.forEach((Appointment appointment) -> {
          
          if(appointment.getAppointmentLocation().equals(s)){
              filteredList.add(appointment);
          }
      });
      return filteredList;
    };
    
    /**
     * Filters the table by title.
     * Sets other comboboxes back to null.
     */
    @FXML
    private void onActionFilterByTitle(ActionEvent event) {
        contactFilterComboBox.valueProperty().set(null);
        appointmentLocationCombobox.valueProperty().set(null);
        apptTable.setItems(filterByTitle.filter(allAppointments, titleFilterCombobox.getValue()));
    }

    /**
     * Filters the table by contact.
     * Sets other comboboxes back to null.
     */
    @FXML
    private void onActionFilterByContact(ActionEvent event) {
        titleFilterCombobox.valueProperty().set(null);
        appointmentLocationCombobox.valueProperty().set(null);
        apptTable.setItems(filterByEmployee.filter(allAppointments, contactFilterComboBox.getValue()));
    }

    /**
     * Filters the table by location.
     * Sets other comboboxes back to null.
     */
    @FXML
    private void onActionFilterByLocation(ActionEvent event) {
        titleFilterCombobox.valueProperty().set(null);
        contactFilterComboBox.valueProperty().set(null);
        apptTable.setItems(filterByLocation.filter(allAppointments, appointmentLocationCombobox.getValue()));
    }

    /**
     * Shows all appointments in the table.
     * Sets all comboboxes back to null.
     */
    @FXML
    private void handleClearFilters(ActionEvent event) {
        titleFilterCombobox.valueProperty().set(null);
        contactFilterComboBox.valueProperty().set(null);
        appointmentLocationCombobox.valueProperty().set(null);
        apptTable.setItems(allAppointments);
    }

    /**
     * Returns to the appointment main screen.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AppointmentMain.fxml"));
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}
