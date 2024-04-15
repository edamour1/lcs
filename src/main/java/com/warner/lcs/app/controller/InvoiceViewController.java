package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class InvoiceViewController implements Initializable {

    private Admin admin;
    private Client client;
    private InvoiceInformation invoiceInformation;
    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;

    // Declaring fields for the FXML elements
    @FXML
    private DatePicker paymentDueDatePicker;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<Client> billingClientComboBox;

    @FXML
    private ComboBox<Address> billingAddressComboBox;

    @FXML
    private ComboBox<Treatment> treatmentComboBox;

    @FXML
    private ComboBox<AdditionalCostService> additionalCostServiceComboBox;

    @FXML
    private ComboBox<Address> addressComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    void initData(Client client, Admin admin, InvoiceInformation invoiceInformation) {
        this.admin = admin;
        this.client = client;
        this.invoiceInformation = invoiceInformation;
    }

}
