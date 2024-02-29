package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class InvoiceInformation {
    private int id, clientId;
    private Date paymentDueDate;
    private Date startDate;
    private Date endDate;
    private List<Treatment> treatments;
    private List<AdditionalCostService> additionalCostServices;
    private String notes;

    private int addressId;


    public InvoiceInformation(){
        this.treatments = new ArrayList<>();
        this.additionalCostServices = new ArrayList<>();
    }

    public InvoiceInformation(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.paymentDueDate = resultSet.getDate("payment_due_date");
        this.startDate = resultSet.getDate("start_date");
        this.endDate = resultSet.getDate("end_date");
        this.notes = resultSet.getString("notes");
        this.clientId = resultSet.getInt("client_id");
        this.addressId = resultSet.getInt("address_id");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AdditionalCostService> getAdditionalCostServices() {
        return additionalCostServices;
    }

    public void setAdditionalCostServices(List<AdditionalCostService> additionalCostServices) {
        this.additionalCostServices = additionalCostServices;
    }

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
