package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {

    private int id;
    private String street;
    private City city;
    private State state;
    private Zipcode zipcode;

    public  Address(){}

    public  Address(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.street = resultSet.getString("street");
        this.state = new State(resultSet);
        this.city = new City(resultSet);
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
}

