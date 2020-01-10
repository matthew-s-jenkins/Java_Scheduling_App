 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import LambraInterfaces.UpcomingAppointmentInterface;
import Utilities.LoginRecorder;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Locale;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mjenk
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField usernameText;
    @FXML
    private Button loginButton;
    @FXML
    private Text loginLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    private String errorLabel;
    private String errorMessage;
    @FXML
    private Text languageLabel;
    @FXML
    private ComboBox<String> laguageComboBox;
    ObservableList<String> languages = FXCollections.observableArrayList();
    String languageCode = "";
    String upcoming = "";
    

    /**
     * Initializes the controller class.
     * Sets supported languages to combo box.
     * sets the login credentials to the test user.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        languages.addAll("English", "French", "Spanish");
        laguageComboBox.setItems(languages);
        setLanguage("");
        usernameText.setText("test");
        passwordText.setText("test");
        
    }    
    
    private void setLanguage(String languageCode){
        Locale english = new Locale("en", "US");
        Locale france = new Locale("fr", "FR");
        Locale espanol = new Locale("es", "ES");
        if(languageCode.equals("English"))
            Locale.setDefault(english);
        if(languageCode.equals("French"))
            Locale.setDefault(france);
        else if(languageCode.equals("Spanish"))
            Locale.setDefault(espanol);
        ResourceBundle rb = ResourceBundle.getBundle("Languages/LoginLanguage", Locale.getDefault());
        laguageComboBox.setValue(rb.getString("language"));
        loginLabel.setText(rb.getString("loginLabel"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        loginButton.setText(rb.getString("buttonLabel"));
        languageLabel.setText(rb.getString("languageLabel"));
        errorLabel = rb.getString("errorLabel");
        errorMessage = rb.getString("errorMessage");
    }
    
    // Lambra Expression to display uplcoming appointments as an alert
        UpcomingAppointmentInterface appointmentAlert = () -> {
            String upcomingAppointments = "";
            try {
                upcomingAppointments = AppointmentDAO.getAppointmentsFor15Mins();
            } catch (ParseException ex) {
                System.out.println("ParseException: " + ex);
            }
        return upcomingAppointments;
    };
        
    /**
     * Handles the login by checking username and password,
     * validating login, if login credentials are valid - checking for upcoming
     * appointments in the next 15 minutes and displaying an alert if found,
     * then loading the appointment main screen.
     * If login credentials are invalid, an error message is displayed and the
     * user is returned to the login screen.
     */  
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();
        boolean loginAccepted = UserDAO.validateLogin(username, password);
        if (loginAccepted){
            upcoming = appointmentAlert.returnUpcomingAppointments();
            try {
                if (upcoming.length() > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("There are appointments in the next 15 minutes.");
                    alert.setContentText(upcoming);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                System.out.println("Exception: "+ e);
            }
            LoginRecorder.recordLogin();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorLabel);
            alert.setContentText(errorMessage);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }

    /**
     * Switches the display language.
     */
    @FXML
    private void onActionChangeLanguage(ActionEvent event) {
        languageCode = laguageComboBox.getValue();
        setLanguage(languageCode);
    }
    
}
