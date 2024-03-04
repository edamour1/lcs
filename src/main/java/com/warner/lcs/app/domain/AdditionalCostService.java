package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdditionalCostService {

    private int id,qty;
    private String treatmentName, treatmentDescription;
    private double price;

    private boolean updateQty, removeFromList;

    public AdditionalCostService() {
        this.removeFromList = false;
        this.updateQty = false;
    }

    public AdditionalCostService(ResultSet resultset) throws SQLException {
        this.id = resultset.getInt("id");
        this.treatmentName = resultset.getString("treatment_name");
        this.treatmentDescription = resultset.getString("treatment_description");
        this.price = resultset.getDouble("price");
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

