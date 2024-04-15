package com.warner.lcs.app.domain.table_ui;

public class InvoiceInformationTableData {

    private String paymentDueDate, startDate, date;
    private int treatmentsQty;
    private int additionalCostServicesQty;
    private String notes;
    private String no;

    public InvoiceInformationTableData() {}

    public InvoiceInformationTableData(String paymentDueDate, String startDate, String date, int treatmentsQty, int additionalCostServicesQty, String notes, String no) {
        this.paymentDueDate = paymentDueDate;
        this.startDate = startDate;
        this.date = date;
        this.treatmentsQty = treatmentsQty;
        this.additionalCostServicesQty = additionalCostServicesQty;
        this.notes = notes;
        this.no = no;
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
}
