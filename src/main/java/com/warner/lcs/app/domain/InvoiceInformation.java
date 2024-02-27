package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class InvoiceInformation {
    private int id;
    private Date paymentDueDate;
    private Date startDate;
    private Date endDate;
    private Client client;
    private Address address;
    private List<Treatment> treatments;
    private List<AdditionalCostService> additionalCostServices;
    private String notes;

    public InvoiceInformation(){
        this.address = new Address();
        this.treatments = new ArrayList<>();
        this.additionalCostServices = new ArrayList<>();
    }

    public InvoiceInformation(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.paymentDueDate = resultSet.getDate("payment_due_date");
        this.startDate = resultSet.getDate("start_date");
        this.client = new Client();
        this.client.setId(resultSet.getInt("client_id"));
        this.endDate = resultSet.getDate("end_date");
        this.notes = resultSet.getString("notes");
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
}
