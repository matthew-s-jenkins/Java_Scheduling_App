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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

/**
 *
 * @author mjenk
 */
public class CustomerDAO {
    
    /**
     * Adds a customer and address to the database.
     * @param addressId
     * @param address
     * @param address2
     * @param cityId
     * @param postalCode
     * @param phone
     * @param customerId
     * @param customerName
     */
    public static void addCustomer(int addressId, String address, String address2, int cityId,
            String postalCode, String phone, int customerId, String customerName){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO address (addressId, address, address2, cityId, "
                    + "postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                    + " VALUES ('" + addressId + "', '" + address + "', '"
                    + address2 + "', '" + cityId + "', '" + postalCode
                    + "', '" + phone + "', CURRENT_TIMESTAMP, '" + User.currentUser.getUsername()
                    + "', CURRENT_TIMESTAMP, '" + User.currentUser.getUsername() + "')");
            statement.executeUpdate();
            System.out.println("Address succesfully added!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        try {
            PreparedStatement statement2 = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO customer (customerId, customerName, addressId, active, "
                    + "createDate, createdBy, lastUpdate, lastUpdateBy)" + " VALUES ('" 
                    + customerId + "', '" + customerName + "', '" + addressId + "', '0', "
                    + "CURRENT_TIMESTAMP, '" + User.currentUser.getUsername() + "', CURRENT_TIMESTAMP, '" 
                    + User.currentUser.getUsername() + "')");
            statement2.executeUpdate();
            System.out.println("Customer succesfully added!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Modifies a customer and address in the database.
     * @param addressId
     * @param address
     * @param address2
     * @param cityId
     * @param postalCode
     * @param phone
     * @param customerId
     * @param customerName
     */
    public static void modifyCustomer(int addressId, String address, String address2, int cityId,
            String postalCode, String phone, int customerId, String customerName){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE address SET address = '" + address + "', address2 = '" + address2
                    + "', cityId = '" + cityId + "', postalCode = '" + postalCode + "', phone = '"
                    + phone + "', lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = '" + User.currentUser.getUsername()
                    + "' WHERE addressId = '" + addressId + "';");
            statement.executeUpdate();
            System.out.println("Address updated successfully!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        try {
            PreparedStatement statement2 = DBConnection.getConnection().prepareStatement(
                    "UPDATE customer SET customerName = '" + customerName + "', createDate "
                    + "= CURRENT_TIMESTAMP, createdBy = '" + User.currentUser.getUsername() + "' WHERE customerId " 
                    + "= '" + customerId + "';");
            statement2.executeUpdate();
            System.out.println("Customer information succesfully updated!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Deletes a customer and address from the database.
     * @param customerId
     */
    public static void deleteCustomer(int customerId){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM address WHERE addressId = '" + findAddressId(customerId) + "';");
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM customer WHERE customerId = '" + customerId + "';");
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
    }
    
    /**
     * Adds a city to the database.
     * @param countryId
     * @param cityName
     */
    public static void addCity(int countryId, String cityName){
        try {
            int cityId = populateCityList().size() + 1;
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO city(cityId, city, countryId, createDate, "
                            + "createdBy, lastUpdate, lastUpdateBy) VALUES ('" + cityId
                            + "', '" + cityName + "', '" + countryId + "', CURRENT_TIMESTAMP, '" + User.currentUser
                            + "', CURRENT_TIMESTAMP, '" + User.currentUser.getUsername() + "')");
            statement.executeUpdate();
            System.out.println(cityName + " succesfully added!");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Adds a country to the database.
     * @param countryName
     */
    public static void addCountry(String countryName){
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO country(countryId, country, createDate, "
                            + "createdBy, lastUpdate, lastUpdateBy) VALUES (" + (populateCountryList().size() +1)
                            + ", '" + countryName + "', CURRENT_TIMESTAMP, '" + User.currentUser
                            + "', CURRENT_TIMESTAMP, '" + User.currentUser.getUsername() + "')");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Finds all customers and their addresses in the database.
     * @return a list of all customers.
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            try (Statement statement = DBConnection.getConnection().createStatement()) {
                String query = "SELECT customer.customerId, customer.customerName, address.address, "
                        + "address.address2, address.phone, address.postalCode, city.city"
                        + " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
                        + "INNER JOIN city ON address.cityId = city.cityId";
                ResultSet results = statement.executeQuery(query);
                while(results.next()) {
                    Customer customer = new Customer(
                            results.getInt("customerId"),
                            results.getString("customerName"),
                            results.getString("address"),
                            results.getString("address2"),
                            results.getString("city"),
                            results.getString("phone"),
                            results.getString("postalCode"));
                    allCustomers.add(customer);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
        return allCustomers;
    }
    
    /**
     * Finds a customers ID number.
     * @param customerName
     * @return the customers Id number.
     */
    public static int findCustomerId(String customerName){
        int foundId = 0;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "SELECT customerId, customerName FROM customer" );
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                if (customerName.equals(rs.getString("customerName"))){
                    foundId = rs.getInt("customerId");
                }
            }
            System.out.println("Customer ID Found!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
        return foundId;
    }
    
     
    /**
     * Finds a customers address Id number.
     * @param customerId
     * @return the customers address ID
     */
    public static int findAddressId(int customerId) {
        int addressId = 0;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "SELECT address.addressId FROM customer INNER JOIN address ON "
                            + "customer.addressId = address.addressId WHERE customer.customerId = '"
                            + customerId + "';");
            ResultSet rs = statement.executeQuery();
            rs.first();
            addressId = rs.getInt("address.addressId");            
            System.out.println("Address ID Found!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }
        return addressId;
    }
    
    /**
     * Finds a cities country Id.
     * @param city
     * @return The cities country Id.
     */
    public static int findCountryId(String city){
        int selectedCityId = 0;
        try {

            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT cityId, city FROM city;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                if (city.equals(rs.getString("city.city"))){
                    selectedCityId = rs.getInt("city.cityId");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return selectedCityId;
    }
    
    /**
     * generates a new address Id.
     * @return the id for a new address.
     */
    public static int generateAddressId() {
        int addressIdNum = 0;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT "
                    + "addressId FROM address ORDER BY addressId ASC;");
            ResultSet rs = statement.executeQuery();
            if (rs.last()){
                addressIdNum = ((Number) rs.getObject(1)).intValue() + 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return addressIdNum;        
    }
    
    /**
     * finds a new id number for a customer.
     * @return the new id number for a customer.
     */
    public static int generateCustomerId() {
        int customerIdNum = 0;
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT customerId FROM customer ORDER BY customerId ASC;");
            ResultSet rs = statement.executeQuery();
            if (rs.last()){
                customerIdNum = ((Number) rs.getObject(1)).intValue() + 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return customerIdNum;        
    }
    
    /**
     * Finds the cities in the database skipping duplicates.
     * @return a list of cities.
     */
    public static ObservableList<String> populateCityList() { 
        ObservableList<String> cities = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT city FROM city ORDER BY city ASC;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if(!cities.contains(rs.getString("city.city"))){
                cities.add(rs.getString("city.city"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return cities;
    }
    
    /**
     * Finds countries in the database skipping duplicates.
     * @return a list of countries.
     */
    public static ObservableList<String> populateCountryList() { 
        ObservableList<String> countries = FXCollections.observableArrayList();
        try {
            
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT country FROM country ORDER BY country ASC;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if(!countries.contains(rs.getString("country.country"))){
                countries.add(rs.getString("country.country"));
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return countries;
    }
    
    /**
     * Finds customer names in the database skipping duplicates.
     * @return a list of customer names.
     */
    public static ObservableList<String> populateCustomerList() { 
        ObservableList<String> customers = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("SELECT customerName FROM customer ORDER BY customerName ASC;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if(!customers.contains(rs.getString("customerName"))){
                customers.add(rs.getString("customerName"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
        return customers;
    }
    
    
}
