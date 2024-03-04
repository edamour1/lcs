package com.warner.lcs.app.domain;

import java.sql.ResultSet;

public class Business {
    private int id;
    private String name;
    private String email;
    private String phoneNo,faxPhoneNo;
    private Address address;

    public Business(){}

    public Business(ResultSet resultSet){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaxPhoneNo() {
        return faxPhoneNo;
    }

    public void setFaxPhoneNo(String faxPhoneNo) {
        this.faxPhoneNo = faxPhoneNo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
