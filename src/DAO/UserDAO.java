/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.User;
import static model.User.currentUser;

/**
 *
 * @author mjenk
 */
public class UserDAO {
    
    /**
     * Adds a user to the database.
     * @param userId
     * @param username
     * @param password
     */
    public static void addUser(int userId, String username, String password){
        PreparedStatement statement;
        try {
            statement = DBConnection.getConnection().prepareStatement("INSERT INTO "
                    + "user (userId, userName, password, active, createBy, createDate, lastUpdate,"
                    + " lastUpdatedBy) VALUES ('" + userId + "', '" + username + "', '" + password
                    + "', '0', '" + currentUser.getUsername() + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '" + currentUser.getUsername() + "');");
        statement.executeUpdate();
        System.out.println("User succesfully added!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Modifies a user in the database.
     * @param userId
     * @param username
     * @param password
     * @param currentUser
     */
    public static void modifyUser(int userId, String username, String password, String currentUser){
        PreparedStatement statement;
        try {
            statement = DBConnection.getConnection().prepareStatement("UPDATE user "
            + "SET userName = '" + username + "', password = '" 
            + password + "', lastUpdate = CURRENT_TIMESTAMP, lastUpdatedBy = '" 
            + currentUser + "' WHERE userId = '" + userId + "';");
        statement.executeUpdate();
        System.out.println("User succesfully modified!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Deletes a user from the database.
     * @param userId
     */
    public static void deleteUser(int userId){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM user WHERE userId = '" + userId + "';");
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Generates a new employee Id number.
     * @return the new id number.
     */
    public static int generateUserId() {
        int userId = 0;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT "
                    + "userId FROM user ORDER BY userId ASC;");
            ResultSet rs = statement.executeQuery();
            if (rs.last()){
                userId = ((Number) rs.getObject(1)).intValue() + 1;
            }
            
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
        return userId;
    }
    
    /**
     * Validates the users login information.
     * @param username
     * @param password
     * @return true if valid, false if not valid.
     */
    public static Boolean validateLogin(String username, String password) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT * FROM user WHERE userName='" + username + "' AND password='" + password +"'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                currentUser = new User(results.getInt("userId"),
                            results.getString("userName"),
                            results.getString("password"));
                statement.close();
                return true;
            } else {
                return false;                
        }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            return false;
        }
        
    }
}
