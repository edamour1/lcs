package com.warner.lcs.app.domain.table_ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AdditionalCostServiceTableData {
    private Integer id;
    private String treatmentName, treatmentDescription;
    private Double price;
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public AdditionalCostServiceTableData(Integer id, String treatmentName, String treatmentDescription, String unit, Double price, Double qty) {
        this.id = id;
        this.treatmentName = treatmentName;
        this.treatmentDescription = treatmentDescription;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
