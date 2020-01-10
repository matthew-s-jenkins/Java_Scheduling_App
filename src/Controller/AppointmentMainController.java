/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class AppointmentMainController implements Initializable {

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
    private TableColumn<Appointment, String> startTime;
    @FXML
    private TableColumn<Appointment, String> endTime;

    private final ObservableList<Appointment> Appointments = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Appointment, String> appointmentDate;
    static Appointment selectedAppointment;
    @FXML
    private ToggleGroup chartSort;
    @FXML
    private ComboBox<String> yearPicker;
    @FXML
    private ComboBox<String> monthPicker;
    ObservableList<String> years = FXCollections.observableArrayList();
    ObservableList<String> months = FXCollections.observableArrayList();
    @FXML
    private RadioButton monthlyRadio;

    /**
     * Initializes the controller class.
     * Loads information to the appointment table
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
        years.addAll("2017", "2018", "2019", "2020", "2021");
        months.addAll("January", "February", "March", "April", "May" ,"June" ,"July" ,"August" ,"September" ,"October" ,"November" ,"December");
        yearPicker.setItems(years);
        monthPicker.setItems(months);
        Calendar now = Calendar.getInstance();
        yearPicker.setValue(Integer.toString(now.get(Calendar.YEAR)));
        monthPicker.getSelectionModel().select(now.get(Calendar.MONTH));
        try {
            apptTable.setItems(AppointmentDAO.getAllAppointments());
        } catch (ParseException ex) {
            System.out.println("ParseException: " + ex);
        }
    }    

    /**
     * Displays a confirmation screen.
     * Exits the program.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit confirmation.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
        
         
    }

    /**
     * Loads the add appointment screen.
     */
    @FXML
    private void handleAddAppointment(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    /**
     * Stores the selected appointment to variable.
     * Loads the modify appointment screen.
     * Displays an error message if no appointment is selected.
     */
    @FXML
    private void handleModifyAppointment(ActionEvent event) {
        if(apptTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = apptTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected an appointment to modify.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    /**
     * Deletes an appointment.
     */
    @FXML
    private void handleDeleteAppointment(ActionEvent event) throws ParseException {        
        if(apptTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppointment = apptTable.getSelectionModel().getSelectedItem();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("You have not selected an appointment to delete.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            return;
        }
        AppointmentDAO.deleteAppointment(selectedAppointment);
        Appointments.clear();
        Appointments.addAll(AppointmentDAO.getAllAppointments());
        apptTable.setItems(Appointments);
    }

    /**
     * Loads the generate reports screen.
     */
    @FXML
    private void handleGenerateReports(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
         // getter for selectedAppointment
    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     * Displays only appointments for the upcoming week.
     */
    @FXML
    private void handleWeeklySortAction(ActionEvent event) {
        try {
            apptTable.getItems().clear();
            apptTable.setItems(AppointmentDAO.getWeeklyAppointments());
        } catch (ParseException ex) {
            Logger.getLogger(AppointmentMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Displays only appointments for the upcoming month.
     */
    @FXML
    private void handleMonthlySortAction(ActionEvent event) {
        if(monthlyRadio.isSelected()){
            try {
                apptTable.getItems().clear();
                apptTable.setItems(AppointmentDAO.getMonthlyAppointments(monthPicker.getSelectionModel().getSelectedIndex(), yearPicker.getValue()));
            } catch (ParseException ex) {
                Logger.getLogger(AppointmentMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Displays all appointments.
     */
    @FXML
    private void handleViewAllSortAction(ActionEvent event) {
        try {
            apptTable.getItems().clear();
            apptTable.setItems(AppointmentDAO.getAllAppointments());
        } catch (ParseException ex) {
            Logger.getLogger(AppointmentMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loads the manage customers screen.
     */
    @FXML
    private void handleManageCustomers(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ManageCustomers.fxml"));
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    /**
     * Loads the manage employee or user screen.
     */
    @FXML
    private void handleManageUsers(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/UserAdminAccess.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ManageCustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
