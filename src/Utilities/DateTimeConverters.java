/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 *
 * @author mjenk
 */
public class DateTimeConverters {
    
    /**
     * Converts calendar object to string.
     * @param strDate
     * @return formated string containing date
     * @throws ParseException
     */
    public static Calendar stringToCalendar(String strDate) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date date = sdf. parse(strDate);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
   } 
    
    /**
     * Converts calendar object to formated string date.
     * @param cal
     * @return formated date String.
     */
    public static String calToStringDate(Calendar cal) {
    DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    return sdf.format(cal.getTime());
    }
    
    /**
     * Converts calendar object to formated time String.
     * @param cal
     * @return formated time String.
     */
    public static String calToStringTime(Calendar cal) {
    DateFormat sdf = new SimpleDateFormat("hh:mm a");
    return sdf.format(cal.getTime());
    }
    
    /**
     * Finds the time in UTC and returns as timestamp.
     * @param date
     * @param hourOfDay
     * @param minuteOfDay
     * @param location
     * @return Timestamp for chosen time
     */
    public static Timestamp generateTimestamp(DatePicker date, ComboBox<String> hourOfDay, ComboBox<String> minuteOfDay, ComboBox<String> location){
        LocalDate day = date.getValue();
        Integer hours = Integer.parseInt((hourOfDay.getValue()).substring(0, (hourOfDay.getValue().length() - 5)));
        if (hours < 8) hours += 12;
        Integer minutes = Integer.parseInt(minuteOfDay.getValue());
        int offsetFromUtc;
        switch (location.getValue()) {
            case "London, England":
                offsetFromUtc = -1;
                break;
            case "New York, New York":
                offsetFromUtc = 4;
                break;
            default:
                offsetFromUtc = 7;
                break;
        }
        LocalDateTime local = LocalDateTime.of(day.getYear(), day.getMonthValue(), 
        day.getDayOfMonth(), (hours + offsetFromUtc), minutes);
        Timestamp timestamp = Timestamp.valueOf(local);
        
        
        return timestamp;
    }
    
    /**
     * Changes the time to reflect the time zone of location.
     * @param cal
     * @param location
     * @return calendar object in time zone of location.
     */
    public static Calendar convertToLocalTimezone (Calendar cal, String location){
        int offsetFromUtc;
        switch (location) {
            case "London, England":
                offsetFromUtc = 1;
                break;
            case "New York, New York":
                offsetFromUtc = -4;
                break;
            default:
                offsetFromUtc = -7;
                break;
        }

        cal.add(Calendar.HOUR, offsetFromUtc);
        
        return cal;
    }
    
    /**
     * Converts calendar object to formated local date object. 
     * @param cal
     * @return local date reflecting input calendar object.
     */
    public static LocalDate calToLocalDate(Calendar cal) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate local = LocalDate.parse(date , dateFormat);
        return local;
    }
    
    /**
     * Finds the minute of a calendar object.
     * @param cal
     * @return String of minute
     */
    public static String calToStringMin(Calendar cal) {
    String min;
    min = Integer.toString(cal.get(Calendar.MINUTE));
    if(min.equals("0")) min = "00";
    return min;
    }
    
    /**
     * Finds the hour of a calendar object.
     * @param cal
     * @return String of hour.
     */
    public static String calToStringHour(Calendar cal) {
    String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
    return hour;
    }
    
    /**
     * Finds the correct combo box for the hour.
     * @param cal
     * @param location
     * @return a string of the combo box selection.
     */
    public static String calToComboBoxHour(Calendar cal, String location){
        
        String hour = calToStringHour(cal);
        String hourWithMeridium;
        switch (hour) {
            case "9":
                hourWithMeridium = "9 a.m.";
                break;
            case "10":
                hourWithMeridium = "10 a.m.";
                break;
            case "11":
                hourWithMeridium = "11 a.m.";
                break;
            case "12":
                hourWithMeridium = "12 p.m.";
                break;
            case "1":
            case "13":
                hourWithMeridium = "1 p.m.";
                break;
            case "2":
            case "14":
                hourWithMeridium = "2 p.m.";
                break; 
            case "3":
            case "15":
                hourWithMeridium = "3 p.m.";
                break;
            case "4":
            case "16":
                hourWithMeridium = "4 p.m.";
                break;
            default:
                hourWithMeridium = "5 p.m.";
                break;
        }
        return (hourWithMeridium);
    }
}
