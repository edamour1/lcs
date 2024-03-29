package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class InvoiceInformation {
    private int clientId;
    private Date paymentDueDate, startDate, endDate, date;
    private List<Treatment> treatments;
    private List<AdditionalCostService> additionalCostServices;
    private String notes;

    private int addressId;

    private String no;


    public InvoiceInformation(){
        this.treatments = new ArrayList<>();
        this.additionalCostServices = new ArrayList<>();
    }

    public InvoiceInformation(ResultSet resultSet) throws SQLException {
        this.paymentDueDate = resultSet.getDate("payment_due_date");
        this.startDate = resultSet.getDate("start_date");
        this.endDate = resultSet.getDate("end_date");
        this.notes = resultSet.getString("notes");
        this.clientId = resultSet.getInt("client_id");
        this.addressId = resultSet.getInt("address_id");
        this.no = resultSet.getString("invoice_no");
        this.date = resultSet.getDate("lm_date");
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
