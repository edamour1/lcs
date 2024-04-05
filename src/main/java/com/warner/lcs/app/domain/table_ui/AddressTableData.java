package com.warner.lcs.app.domain.table_ui;

public class AddressTableData {
    Integer id;
    String street;
    String unit;
    String city;
    String state;
    String zipcode;
    Boolean isBilling;
    Double quantity;

    //purpose of this class is simply to transform the address object into an object that will get used by the table.
    public AddressTableData(Integer id, String street, String unit, String city, String state, String zipcode, Boolean isBilling, Double quantity) {
        this.id = id;
        this.street = street;
        this.unit = unit;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.isBilling = isBilling;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Boolean getBilling() {
        return isBilling;
    }

    public void setBilling(Boolean billing) {
        isBilling = billing;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStreet() {
        return street;
    }

    public String getUnit() {
        return unit;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Boolean isBilling() {
        return isBilling;
    }

    public Double getQuantity() {
        return quantity;
    }
}