/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utilities.DBConnection;
import Utilities.DateTimeConverters;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

/**
 *
 * @author mjenk
 */
public class AppointmentDAO {
    
    /**
     *
     * @return observable list of all appointments.
     * @throws ParseException
     */
    public static ObservableList<Appointment> getAllAppointments() throws ParseException{
        ObservableList<Appointment> allAppointments=FXCollections.observableArrayList();
        try (Statement statement = DBConnection.getConnection().createStatement()) {
            String query = "SELECT appointment.appointmentId, customer.customerName, appointment.title, "
                    + "appointment.description, appointment.location, appointment.contact, appointment.url, "
                    + "appointment.start, appointment.end"
                    + " FROM appointment INNER JOIN customer ON appointment.customerId = customer.customerId ORDER BY appointmentId ASC;";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int apptId = rs.getInt("appointment.appointmentId");
                String cusName = rs.getString("customer.customerName");
                String apptTitle = rs.getString("appointment.title");
                String apptDesc = rs.getString("appointment.description");
                String apptLoc = rs.getString("appointment.location");
                String apptContact = rs.getString("appointment.contact");
                String apptUrl = rs.getString("appointment.url");
                String startTime = rs.getString("appointment.start");
                Calendar startTimeUTC = DateTimeConverters.stringToCalendar(startTime);
                String endTime = rs.getString("appointment.end");
                Calendar endTimeUTC = DateTimeConverters.stringToCalendar(endTime);
                Appointment appointment = new Appointment(apptId, cusName, apptTitle, apptLoc, apptDesc, apptContact, apptUrl, startTimeUTC, endTimeUTC);
                allAppointments.add(appointment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allAppointments;
    }
    
    /**
     *
     * @return observable list of appointments for upcoming week.
     * @throws ParseException
     */
    public static ObservableList<Appointment> getWeeklyAppointments() throws ParseException{
        ObservableList<Appointment> allAppointments=FXCollections.observableArrayList();
        LocalDate beginSort = LocalDate.now();
        LocalDate endSort = LocalDate.now().plusWeeks(1);
        try (Statement statement = DBConnection.getConnection().createStatement()) {
            String query = "SELECT appointment.appointmentId, customer.customerName, appointment.title, "
                    + "appointment.description, appointment.location, appointment.contact, appointment.url, "
                    + "appointment.start, appointment.end FROM appointment INNER JOIN customer ON appointment.customerId "
                    + "= customer.customerId WHERE start >= '" + beginSort + "' AND end <= '" + endSort + "';";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int apptId = rs.getInt("appointment.appointmentId");
                String cusName = rs.getString("customer.customerName");
                String apptTitle = rs.getString("appointment.title");
                String apptDesc = rs.getString("appointment.description");
                String apptLoc = rs.getString("appointment.location");
                String apptContact = rs.getString("appointment.contact");
                String apptUrl = rs.getString("appointment.url");
                String startTime = rs.getString("appointment.start");
                Calendar startTimeUTC = DateTimeConverters.stringToCalendar(startTime);
                String endTime = rs.getString("appointment.end");
                Calendar endTimeUTC = DateTimeConverters.stringToCalendar(endTime);
                Appointment appointment = new Appointment(apptId, cusName, apptTitle, apptLoc, apptDesc, apptContact, apptUrl, startTimeUTC, endTimeUTC);
                allAppointments.add(appointment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allAppointments;
    }
    
    /**
     *
     * @return observable list of appointments for upcoming month.
     * @throws ParseException
     */
    public static ObservableList<Appointment> getMonthlyAppointments(Integer month, String year) throws ParseException{
        ObservableList<Appointment> allAppointments=FXCollections.observableArrayList();
        String monthPlusOne = Integer.toString(month+1);
        try (Statement statement = DBConnection.getConnection().createStatement()) {
            String query = "SELECT appointment.appointmentId, customer.customerName, appointment.title, "
                    + "appointment.description, appointment.location, appointment.contact, appointment.url, "
                    + "appointment.start, appointment.end FROM appointment INNER JOIN customer ON appointment.customerId "
                    + "= customer.customerId WHERE YEAR(start) = " + year + " AND MONTH(start) = " + monthPlusOne + ";";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int apptId = rs.getInt("appointment.appointmentId");
                String cusName = rs.getString("customer.customerName");
                String apptTitle = rs.getString("appointment.title");
                String apptDesc = rs.getString("appointment.description");
                String apptLoc = rs.getString("appointment.location");
                String apptContact = rs.getString("appointment.contact");
                String apptUrl = rs.getString("appointment.url");
                String startTime = rs.getString("appointment.start");
                Calendar startTimeUTC = DateTimeConverters.stringToCalendar(startTime);
                String endTime = rs.getString("appointment.end");
                Calendar endTimeUTC = DateTimeConverters.stringToCalendar(endTime);
                Appointment appointment = new Appointment(apptId, cusName, apptTitle, apptLoc, apptDesc, apptContact, apptUrl, startTimeUTC, endTimeUTC);
                allAppointments.add(appointment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allAppointments;
    }
    
    /**
     *
     * @return A string of appointments in the next 15 minutes.
     * @throws ParseException
     */
    public static String getAppointmentsFor15Mins() throws ParseException{
        String upcomingAppointments = "";
        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zoneNow = now.atZone(zoneId);
        LocalDateTime localnow = zoneNow.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime localInFifteen = localnow.plusMinutes(15);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        try (Statement statement = DBConnection.getConnection().createStatement()) {
            String query = "SELECT appointment.appointmentId, customer.customerName, appointment.title, "
                    + "appointment.description, appointment.location, appointment.contact, appointment.url, "
                    + "appointment.start, appointment.end"
                    + " FROM appointment INNER JOIN customer ON appointment.customerId = customer.customerId"
                    + " WHERE start BETWEEN '" + localnow + "' AND '" + localInFifteen + "';";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                String customer = rs.getString("customer.customerName");
                Timestamp startTimeStamp = rs.getTimestamp("appointment.start");
                LocalDateTime startTime = startTimeStamp.toLocalDateTime();
                String location = rs.getString("appointment.location");
                String contact = rs.getString("appointment.contact");
                LocalDateTime localDateTime = LocalDateTime.parse(startTime.toString());
                ZonedDateTime startZonedTime = localDateTime.atZone(ZoneId.of("UTC"));
                ZonedDateTime localStart = startZonedTime.withZoneSameInstant(zoneId);
                upcomingAppointments += customer + " has an appointment at " + localStart.format(timeFormatter) + " with " + contact + " at the " + location + " location. \n";
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex);
        }
        return upcomingAppointments;
}
    
    /**
     * Adds an appointment to the database.
     * @param apptId
     * @param cusId
     * @param title
     * @param location
     * @param description
     * @param contact
     * @param url
     * @param start
     * @param end
     */
    public static void addAppointment(int apptId, int cusId, String title, String location, 
            String description, String contact, String url, Timestamp start, Timestamp end){
        PreparedStatement statement;
        try {
            statement = DBConnection.getConnection().prepareStatement("INSERT INTO "
                    + "appointment (appointmentId, customerId, title, description, location,"
                    + " contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"
                    + "VALUES ('" + apptId + "', '" + cusId + "', '" + title
                    + "', '" + description + "', '" + location + "', '" + contact + "', '" + url 
                    + "', '" + start + "', '" + end + "', CURRENT_TIMESTAMP, '" + User.currentUser 
                    + "', CURRENT_TIMESTAMP, '" + User.currentUser +  "');");
        statement.executeUpdate();
        System.out.println("Employee succesfully added!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception : " + ex);
        }
    }
    
    /**
     * Modifies an appointment in the database.
     * @param apptId
     * @param cusId
     * @param title
     * @param location
     * @param description
     * @param contact
     * @param url
     * @param start
     * @param end
     */
    public static void modifyAppointment(int apptId, int cusId, String title, String location, 
            String description, String contact, String url, Timestamp start, Timestamp end){
        PreparedStatement statement;
        try {
            statement = DBConnection.getConnection().prepareStatement("UPDATE appointment "
            + "SET customerId = '" + cusId + "', title = '" 
            + title + "', location = '" + location + "', description = '" + description 
            + "', contact = '" + contact + "', url = '" + url + "', start = '" + start 
            + "', end = '" + end + "', lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = '" 
            + User.currentUser +  "' WHERE appointmentId = '" + apptId + "';");
        statement.executeUpdate();
        System.out.println("Appointment succesfully modified!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Deletes an appointment.
     * @param apptToDelete
     */
    public static void deleteAppointment(Appointment apptToDelete){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM appointment WHERE appointmentId = '" + apptToDelete.getAppointmentId() + "';");
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     *
     * @return returns a new appointment Id.
     */
    public static int generateAppointmentId() {
        int appointmentIdNum = 0;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT appointmentId FROM appointment ORDER BY appointmentId ASC;");
            ResultSet rs = statement.executeQuery();
            if (rs.last()){
                appointmentIdNum = ((Number) rs.getObject(1)).intValue() + 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return appointmentIdNum;        
    }
    
    /**
     *
     * @param customerId
     * @return true if customer already has an appointment, otherwise false.
     */
    public static boolean doesCustomerHaveAppointment(int customerId){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT customerId FROM appointment;");
            ResultSet rs = statement.executeQuery();
            if (rs.last()){
                if (customerId == rs.getInt("customerId"))
                    return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
    
    /**
     *
     * @param contact
     * @return true if employee contact already has an appointment, otherwise false.
     */
    public static boolean doesEmployeeHaveAppointment(String contact){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT contact FROM appointment;");
            ResultSet rs = statement.executeQuery();
            if (rs.last()){
                if (contact.equals(rs.getString("contact")))
                    return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
    
    /**
     * Checks if appointment is overlapping for customer or user.
     * @param start
     * @param end
     * @param customerId
     * @param employee
     * @param apptId
     * @return a string with information about the overlapping appointment
     */
    public static String isAppointmentOverlapping(Timestamp start, Timestamp end, int customerId, String employee, int apptId){
        String overlapMessage = "";
        Boolean errorMessage1 = false;
        Boolean errorMessage2 = false;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT COUNT(*) FROM appointment WHERE (contact = '" + employee + "') AND (( '" + start + "' BETWEEN start AND end) OR ( '" +
                    end + "' BETWEEN start AND end) OR (start < '" + start + "' AND end > '" + end + "')) and (appointmentId <> '" + apptId + "');");
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                if (rs.getInt(1)>0){
                    errorMessage1 = true;
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT COUNT(*) FROM appointment WHERE (customerId = '" + customerId + "') AND (( '" + start + "' BETWEEN start AND end) OR ( '" +
                    end + "' BETWEEN start AND end) OR (start < '" + start + "' AND end > '" + end + "')) and (appointmentId <> '" + apptId + "');");
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                if (rs.getInt(1)>0){
                    errorMessage2 = true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
        if (errorMessage1) overlapMessage = overlapMessage + "The selected employee contact has an overlapping appointment.\n";
        if (errorMessage2) overlapMessage = overlapMessage + "The selected customer has an overlapping appointment.\n";
        return overlapMessage;
    }
    
    /**
     * Finds appointment titles in the database skipping duplicates.
     * @return a list of titles.
     */
    public static ObservableList<String> populateTitleList() { 
        ObservableList<String> titles = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT title FROM appointment ORDER BY title ASC;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            if(!titles.contains(rs.getString("appointment.title"))){
                titles.add(rs.getString("appointment.title"));
            }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return titles;
    }
    
    /**
     * Finds appointment contacts in the database skipping duplicates.
     * @return a list of contacts.
     */
    public static ObservableList<String> populateContactList() { 
        ObservableList<String> contacts = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT contact FROM appointment ORDER BY title ASC;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            if(!contacts.contains(rs.getString("appointment.contact"))){
                contacts.add(rs.getString("appointment.contact"));
            }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return contacts;
    }
    
    /**
     * Finds appointment locations in the database skipping duplicates.
     * @return a list of locations.
     */
    public static ObservableList<String> populateLocationList() { 
        ObservableList<String> contacts = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT location FROM appointment ORDER BY title ASC;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            if(!contacts.contains(rs.getString("appointment.location"))){
                contacts.add(rs.getString("appointment.location"));
            }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return contacts;
    }
    
}
