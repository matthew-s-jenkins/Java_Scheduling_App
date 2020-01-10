/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Matthew Jenkins
 */
public class DBConnection {
    
    private static final String DATABASE_NAME = "U04FnY";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + DATABASE_NAME;
    private static final String USERNAME = "U04FnY";
    private static final String PASSWORD = "53688224122";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    /**
     * Creates a connection to the database.
     */
    public static void makeConnection(){
        try {
        Class.forName(DRIVER);
        conn = (Connection) DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        System.out.println("Connected to database " + DATABASE_NAME);
        } catch (SQLException e) {
            System.out.println("SQL Exception:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unhandled Exception: " + e.getMessage());            
        }
    }
    
    /**
     * Closes the connection to the database.
     */
    public static void closeConnection() {
        try {
        conn.close();
        System.out.println("Connection to " + DATABASE_NAME + " successfully closed!");
        } catch (SQLException e) {
            System.out.println("Failed to close connection to: " + DATABASE_NAME);            
        } catch (Exception e) {
            System.out.println("Unhandled Exception: " + e.getMessage());
        }
    }

    /**
     * returns a connection.
     * @return the connection
     */
    public static Connection getConnection() {
        return conn;
    }
}
