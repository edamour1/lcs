package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class InvoiceInformation {
    private int id;
    private Date paymentDueDate;
    private Date startDate;
    private Date endDate;
    private Client client;
    private List<Treatment> treatments;
    private List<AdditionalCostService> additionalCostServices;
    private String notes;

    public InvoiceInformation(){}

    public InvoiceInformation(ResultSet resultSet){}

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

    public Date getEndDate() {
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
