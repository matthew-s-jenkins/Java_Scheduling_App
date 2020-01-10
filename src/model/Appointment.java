/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Utilities.DateTimeConverters;
import java.util.Calendar;

/**
 *
 * @author mjenk
 */
public final class Appointment {
    private Integer appointmentId;
    private String customerName;
    private String appointmentTitle;
    private String appointmentLocation;
    private String appointmentDescription;
    private String employeeContact;
    private String appointmentUrl;
    private Calendar appointmentStartTime;
    private String appointmentStartTimeString;
    private Calendar appointmentEndTime;
    private String appointmentEndTimeString;
    private String appointmentDate;
    private Calendar appointmentStartUtc;
    private Calendar appointmentEndUtc;
    

    /**
     * Constructor.
     *  
     * @param id          The id number of the appointment.
     * @param name        The name of the customer for whom the appointment is scheduled for.
     * @param title       The title of the appointment. 
     * @param location    The location of the appointment.
     * @param description The description of the appointment.
     * @param contact     The employee whom the customer will meet with.
     * @param url         The web site address of the customer.
     * @param start       The start time of the appointment in Coordinated Universal Time.
     * @param end         The end time of the appointment in Coordinated Universal Time.
     */
    public Appointment(int id, String name, String title, String location, String description, String contact, String url, Calendar start, Calendar end) {
        setAppointmentId(id);
        setCustomerName(name);
        setAppointmentTitle(title);
        setAppointmentLocation(location);
        setAppointmentDescription(description);
        setEmployeeContact(contact);
        setAppointmentUrl(url);
        setAppointmentStartUtc(start);
        setAppointmentEndUtc(end);        
        setAppointmentStartTime(DateTimeConverters.convertToLocalTimezone(start, location));
        setAppointmentStartTimeString(DateTimeConverters.calToStringTime(start));
        setAppointmentEndTime(DateTimeConverters.convertToLocalTimezone(end, location));
        setAppointmentEndTimeString(DateTimeConverters.calToStringTime(end));
        setAppointmentDate(DateTimeConverters.calToStringDate(start));
    }
    
    /**
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the appointmentTitle
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * @param appointmentTitle the appointmentTitle to set
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * @return the appointmentLocation
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * @param appointmentLocation the appointmentLocation to set
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * @return the appointmentDescription
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * @param appointmentDescription the appointmentDescription to set
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * @return the employeeContact
     */
    public String getEmployeeContact() {
        return employeeContact;
    }

    /**
     * @param employeeContact the employeeContact to set
     */
    public void setEmployeeContact(String employeeContact) {
        this.employeeContact = employeeContact;
    }

    /**
     * @return the appointmentUrl
     */
    public String getAppointmentUrl() {
        return appointmentUrl;
    }

    /**
     * @param appointmentUrl the appointmentUrl to set
     */
    public void setAppointmentUrl(String appointmentUrl) {
        this.appointmentUrl = appointmentUrl;
    }

    /**
     * @return the appointmentStartTime
     */
    public Calendar getAppointmentStartTime() {
        return appointmentStartTime;
    }

    /**
     * @param appointmentStartTime the appointmentStartTime to set
     */
    public void setAppointmentStartTime(Calendar appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    /**
     * @return the appointmentEndTime
     */
    public Calendar getAppointmentEndTime() {
        return appointmentEndTime;
    }

    /**
     * @param appointmentEndTime the appointmentEndTime to set
     */
    public void setAppointmentEndTime(Calendar appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    /**
     * @return the appointmentStartTimeString
     */
    public String getAppointmentStartTimeString() {
        return appointmentStartTimeString;
    }

    /**
     * @param appointmentStartTimeString the appointmentStartTimeString to set
     */
    public void setAppointmentStartTimeString(String appointmentStartTimeString) {
        this.appointmentStartTimeString = appointmentStartTimeString;
    }

    /**
     * @return the appointmentEndTimeString
     */
    public String getAppointmentEndTimeString() {
        return appointmentEndTimeString;
    }

    /**
     * @param appointmentEndTimeString the appointmentEndTimeString to set
     */
    public void setAppointmentEndTimeString(String appointmentEndTimeString) {
        this.appointmentEndTimeString = appointmentEndTimeString;
    }

    /**
     * @return the appointmentDate
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @param appointmentDate the appointmentDate to set
     */
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * @return the appointmentStartUtc
     */
    public Calendar getAppointmentStartUtc() {
        return appointmentStartUtc;
    }

    /**
     * @param appointmentStartUtc the appointmentStartUtc to set
     */
    public void setAppointmentStartUtc(Calendar appointmentStartUtc) {
        this.appointmentStartUtc = appointmentStartUtc;
    }

    /**
     * @return the appointmentEndUtc
     */
    public Calendar getAppointmentEndUtc() {
        return appointmentEndUtc;
    }

    /**
     * @param appointmentEndUtc the appointmentEndUtc to set
     */
    public void setAppointmentEndUtc(Calendar appointmentEndUtc) {
        this.appointmentEndUtc = appointmentEndUtc;
    }
    
    
}
