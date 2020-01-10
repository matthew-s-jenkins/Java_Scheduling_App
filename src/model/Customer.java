/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mjenk
 */
public final class Customer {
    
    
    
    private Integer customerId;
    private String customerName;
    private String customerAddress;
    private String customerAddress2;
    private String customerCity;
    private String customerPhone;
    private String customerZipCode;
    public static String addOrModify;
    
        
    /**
     *
     * @param id
     * @param name
     * @param address
     * @param address2
     * @param city
     * @param phone
     * @param zip
     */
    public Customer(int id, String name, String address, String address2, String city, String phone, String zip) {
        setCustomerId(id);
        setCustomerName(name);
        setCustomerAddress(address);
        setCustomerAddress2(address2);
        setCustomerCity(city);
        setCustomerPhone(phone);
        setCustomerZipCode(zip);
    }


    


    /**
     * @return the customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return the customerAddress2
     */
    public String getCustomerAddress2() {
        return customerAddress2;
    }

    /**
     * @param customerAddress2 the customerAddress2 to set
     */
    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }

    /**
     * @return the customerCity
     */
    public String getCustomerCity() {
        return customerCity;
    }

    /**
     * @param customerCity the customerCity to set
     */
    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    /**
     * @return the customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * @param customerPhone the customerPhone to set
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * @return the customerZipCode
     */
    public String getCustomerZipCode() {
        return customerZipCode;
    }

    /**
     * @param customerZipCode the customerZipCode to set
     */
    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

}
