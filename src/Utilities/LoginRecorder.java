/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import static model.User.currentUser;

/**
 *
 * @author mjenk
 */
public class LoginRecorder {
    
    /**
     *  Checks if a login record exists, creates one if not, and records the users login
     */
    public static void recordLogin(){
        File loginFile = new File("SuccessfulLogins.txt");
        if(!loginFile.exists()){
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("SuccessfulLogins.txt"), "utf-8"))) {
                    writer.write(currentUser.getUsername() + " signed in at " + 
                            Calendar.getInstance().getTime() +  "\r\n");
            } catch (IOException ex) {
                System.out.println("IOEception: " + ex);
            }
        }        
        else {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("SuccessfulLogins.txt", true), "utf-8"))) {
                    writer.write(currentUser.getUsername() + " signed in at " + 
                            Calendar.getInstance().getTime() + "\r\n");
            } catch (IOException ex) {
                System.out.println("IOEception: " + ex);
            }
        }
    }
}
