package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class City {
    private int id;
    private String city;

    public City(){}

    public City(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.city = resultSet.getString("city");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
