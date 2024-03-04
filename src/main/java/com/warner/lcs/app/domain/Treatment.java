package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Treatment {

    private int id, qty;
    private String treatmentName, treatmentDescription;
    private double price;
    private boolean updateQty, removeFromList;

    public Treatment(){}

    public Treatment(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.treatmentName = resultSet.getString("treatment_name");
        this.treatmentDescription = resultSet.getString("treatment_description");
        this.price = resultSet.getDouble("price");
        this.removeFromList = false;
        this.updateQty = false;
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

    public boolean getRemoveFromList() {
        return removeFromList;
    }

    public void setRemoveFromList(boolean removeFromList) {
        this.removeFromList = removeFromList;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isUpdateQty() {
        return updateQty;
    }

    public void setUpdateQty(boolean updateQty) {
        this.updateQty = updateQty;
    }

}
