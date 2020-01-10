/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LambraInterfaces;

import javafx.collections.ObservableList;
import model.Appointment;

/**
 *
 * @author mjenk
 */
public interface ReportsInterface {
    
    // Filter Appointments abstract method returning ObservableList<Appointment>
    ObservableList<Appointment> filter(ObservableList<Appointment> all, String s);
    
}
