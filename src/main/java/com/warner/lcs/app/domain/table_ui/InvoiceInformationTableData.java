package com.warner.lcs.app.domain.table_ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class InvoiceInformationTableData {

    private String paymentDueDate, startDate, date;
    private int treatmentsQty;
    private int additionalCostServicesQty;
    private String notes;
    private String no;
    private String lmUser;
    private String lmDate;
    private String firstName;
    private String lastName;

    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public InvoiceInformationTableData() {}


    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public InvoiceInformationTableData(String paymentDueDate, String startDate, String date, int treatmentsQty, int additionalCostServicesQty, String notes, String no, String firstName, String lastName) {
        this.paymentDueDate = paymentDueDate;
        this.startDate = startDate;
        this.date = date;
        this.treatmentsQty = treatmentsQty;
        this.additionalCostServicesQty = additionalCostServicesQty;
        this.notes = notes;
        this.no = no;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTreatmentsQty() {
        return treatmentsQty;
    }

    public void setTreatmentsQty(int treatmentsQty) {
        this.treatmentsQty = treatmentsQty;
    }

    public int getAdditionalCostServicesQty() {
        return additionalCostServicesQty;
    }

    public void setAdditionalCostServicesQty(int additionalCostServicesQty) {
        this.additionalCostServicesQty = additionalCostServicesQty;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getLmUser() {
        return lmUser;
    }

    public void setLmUser(String lmUser) {
        this.lmUser = lmUser;
    }

    public String getLmDate() {
        return lmDate;
    }

    public void setLmDate(String lmDate) {
        this.lmDate = lmDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
