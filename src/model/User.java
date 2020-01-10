/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Utilities.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author mjenk
 */
public final class User {
    public static User currentUser;
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private final SimpleIntegerProperty userId = new SimpleIntegerProperty();
    private final SimpleStringProperty username = new SimpleStringProperty();
    private final SimpleStringProperty userPassword = new SimpleStringProperty();

    public User (int id, String name, String password) {
        setUserId(id);
        setUsername(name);
        setUserPassword(password);
    }
    
    public void setUsername(String username) {
        this.username.set(username);
    }
    public String getUsername() {
        return username.get();
    }
    
    public void setUserId(int userId) {
        this.userId.set(userId);
    }
    
    public int getUserId() {
        return userId.get();
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword.set(userPassword);
    }
    
    public String getUserPassword() {
        return userPassword.get();
    }
    
    
    
    public static ObservableList<User> getAllUsers() {
    allUsers.clear();
        try {
            try (Statement statement = DBConnection.getConnection().createStatement()) {
                String query = "SELECT userId, userName, password FROM user;";
                ResultSet results = statement.executeQuery(query);
                while(results.next()) {
                    User user = new User(
                            results.getInt("userId"),
                            results.getString("userName"),
                            results.getString("password"));
                    allUsers.add(user);
                }
            }
            return allUsers;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
