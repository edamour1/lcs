package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Business {
    private int id;
    private String name;
    private String email;
    private String phoneNo,faxPhoneNo;
    private Address address;

    public
    Business(){}

    public Business(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("business_id");
        this.name = resultSet.getString("name");
        this.email = resultSet.getString("email");
        this.phoneNo = resultSet.getString("phone");
        this.faxPhoneNo = resultSet.getString("fax_phone_No");
        this.address = new Address();
        this.address.setId(resultSet.getInt("address_id"));
        this.address.setStreet(resultSet.getString("street"));
        this.address.setBilling(false);
        this.address.setActive(false);
    }

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
