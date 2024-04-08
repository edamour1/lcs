package com.warner.lcs.app.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {

    private int id;
    private String street, unit;
    private City city;
    private State state;
    private Zipcode zipcode;
    private boolean isActive,isBilling;
    private String lmUser;
    private Date lmDate;
    private double quantity;

    public  Address(){}

    public  Address(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("AddressID");
        this.street = resultSet.getString("Address");
        this.state = new State(resultSet,true);
        this.city = new City(resultSet, true);
        this.zipcode = new Zipcode(resultSet,true);
        int active = resultSet.getInt("is_active");
        this.isActive = active == 1 ? true : false;
        int billing = resultSet.getInt("is_billing");
        this.isBilling = billing == 1 ? true : false;
        this.quantity = resultSet.getDouble("quantity");
        this.unit = resultSet.getString("unit");
        this.lmUser = resultSet.getString("lm_user_id");
        this.lmDate = resultSet.getDate("lm_date");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Zipcode getZipcode() {
        return zipcode;
    }

    public void setZipcode(Zipcode zipcode) {
        this.zipcode = zipcode;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isBilling() {
        return isBilling;
    }

    public void setBilling(boolean billing) {
        isBilling = billing;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getLmUser() {
        return lmUser;
    }

    public void setLmUser(String lmUser) {
        this.lmUser = lmUser;
    }

    public Date getLmDate() {
        return lmDate;
    }

    public void setLmDate(Date lmDate) {
        this.lmDate = lmDate;
    }
}

