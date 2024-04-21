package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class InvoiceViewController implements Initializable {

    private FxmlView CLIENT_MENU, CLIENT_VIEW;
    private Admin admin;
    private Client client;
    private InvoiceInformation invoiceInformation;
    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;

    @FXML
    private Text paymentDueDateText;

    @FXML
    private Text startDateText;

    @FXML
    private Text billingClient;

    @FXML
    private Text addressText;

    @FXML
    private Text notesText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.paymentDueDateText.setText(this.invoiceInformation.getPaymentDueDate().toString());
        this.startDateText.setText(this.invoiceInformation.getStartDate().toString());
        Address address = new Address(), billingAddress = new Address();
        address.setId(invoiceInformation.getAddressId());
        try {
            address =  this.lcsService.getAddressById(address);
            
            
            
            
            
            
            
            
            
            
            
            
            String fullAddress = address.getStreet()+ " "+ address.getCity().getCity() + ", "+ address.getState().getState() +" "+ address.getZipcode().getZipcode();

            this.addressText.setText(fullAddress);
            this.notesText.setText(invoiceInformation.getNotes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        String fullAddress =
//        this.addressText.setText();

    }

    void initData(Client client, Admin admin, InvoiceInformation invoiceInformation) {
        this.admin = admin;
        this.client = client;
        this.invoiceInformation = invoiceInformation;
    }

}