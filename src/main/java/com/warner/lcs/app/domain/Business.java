package com.warner.lcs.app.domain;

import java.sql.ResultSet;

public class Business {
    private int id;
    private String name;
    private String phone;

    public Business(){}

    public Business(ResultSet resultSet){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
