package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class InvoiceInformation {
    private int clientId, addressId,billingAddressId;
    private Date paymentDueDate, startDate, endDate, date;
    private List<Treatment> treatments;
    private List<AdditionalCostService> additionalCostServices;
    private String notes;
    private boolean isActive;
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
        this.billingAddressId = resultSet.getInt("billing_address_id");
        this.no = resultSet.getString("invoice_no");
        this.date = resultSet.getDate("lm_date");
        int isActiveNumberValue =  resultSet.getInt("is_active");
        this.isActive = isActiveNumberValue == 1 ? true : false;
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

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public String getDateFormatedString() {
        String output = this.getFormatedDate(this.date);
        return output;
    }
    public String getPaymentDueDateFormatedString() {
            String output = this.getFormatedDate(this.paymentDueDate);
            return output;
    }

    public String getStartDateFormatedString() {
        String output = this.getFormatedDate(this.startDate);
        return output;
    }

    public String getEndDateFormatedString() {
        String output = this.getFormatedDate(this.endDate);
        return output;
    }



    private String getFormatedDate(Date dateInput)
    {
        String input = dateInput.toString();
        // Parse the input string into a LocalDate object
        LocalDate date = LocalDate.parse(input);

        // Define the output format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Format the LocalDate object into the desired output format
        String output = date.format(outputFormatter);
        return output;
    }
}
