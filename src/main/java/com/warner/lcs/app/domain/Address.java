package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {

    private int id;
    private String street;
    private City city;
    private State state;
    private Zipcode zipcode;
    private boolean isActive,isBilling;

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
}

