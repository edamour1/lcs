package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Treatment {

    private int id;
    private String treatmentName;
    private String treatmentDescription;
    private double price;

    public Treatment(){}

    public Treatment(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.treatmentName = resultSet.getString("treatment_name");
        this.treatmentDescription = resultSet.getString("treatment_description");
        this.price = resultSet.getDouble("price");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
