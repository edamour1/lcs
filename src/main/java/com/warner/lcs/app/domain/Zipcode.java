package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Zipcode {

    private int id;
    private String zipcode;

    public Zipcode(){}

    public Zipcode(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.zipcode = resultSet.getString("zipcode");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
