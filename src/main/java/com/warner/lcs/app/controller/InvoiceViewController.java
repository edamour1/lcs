package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.PdfGenerator;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class InvoiceViewController implements Initializable {

    private FxmlView CLIENT_MENU, CLIENT_VIEW;
    private Admin admin;
    private Client client, billingClient;
    private InvoiceInformation invoiceInformation;
    private PdfGenerator pdfGenerator;
    private  Address address, billingAddress;

    private Business business;
    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;

    @FXML
    private Text paymentDueDateText;

    @FXML
    private Text startDateText;

    @FXML
    private Text endDateText;

    @FXML
    private Text billingClientText;

    @FXML
    private Text billingAddressText;

    @FXML
    private Text addressText;

    @FXML
    private Text notesText;
    @FXML
    private ListView<String> treatmentListView;

    @FXML
    private ListView<String> additionalCostServicesListView;

//    @FXML
//    private Text lm_user;
//
//    @FXML
//    private Text lm_date;

    @FXML
    private Text invoiceNumberParagraph;

    @FXML
    private Text clientFirstNameParagraph;

    @FXML
    private Text clientMiddleNameParagraph;

    @FXML
    private Text clientLastNameParagraph;

    @FXML
    private Text clientPhoneNumberParagraph;

    @FXML
    private Text clientEmailParagraph;

    @FXML
    private Button generatePDFButton;

    @FXML
    private Button viewPDFButton;


    @FXML
    private Button emailButton;

    @FXML
    private Button backButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //*************************************************************************************get Data*************************************************************
        this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.paymentDueDateText.setText(this.invoiceInformation.getPaymentDueDate().toString());

        this.startDateText.setText(this.invoiceInformation.getStartDate().toString());
        this.endDateText.setText(this.invoiceInformation.getEndDate().toString());
        this.address = new Address();
        this.billingAddress = new Address();
        address.setId(this.invoiceInformation.getAddressId());
        billingAddress.setId(this.invoiceInformation.getBillingAddressId());
        try {
            address =  this.lcsService.getAddressById(address);
            billingAddress =  this.lcsService.getAddressById(billingAddress);
            this.billingClient = this.lcsService.getClientById(billingAddress.getClientId());
            String fullAddress = address.getStreet()+ " "+ address.getCity().getCity() + ", "+ address.getState().getState() +" "+ address.getZipcode().getZipcode();
            String fullBillingAddress = billingAddress.getStreet()+ " "+ billingAddress.getCity().getCity() + ", "+ billingAddress.getState().getState() +" "+ billingAddress.getZipcode().getZipcode();

            for(Treatment t : this.invoiceInformation.getTreatments())
            {
                this.treatmentListView.getItems().add(t.getTreatmentName()+" : "+t.getQty()+" "+t.getUnit());
            }
            for(AdditionalCostService a : this.invoiceInformation.getAdditionalCostServices())
            {
                this.additionalCostServicesListView.getItems().add(a.getTreatmentName()+" : "+a.getQty()+" "+a.getUnit());
            }
            this.business = this.lcsService.getBusiness();

            //**************************************************set UI***************************************************************************************
            this.addressText.setText(fullAddress);
            this.billingAddressText.setText(fullBillingAddress);
            this.invoiceNumberParagraph.setText(this.invoiceInformation.getNo());
            this.paymentDueDateText.setText(this.invoiceInformation.getPaymentDueDate().toString());
            this.startDateText.setText(this.invoiceInformation.getStartDate().toString());
            String billledClientFullname = this.billingClient.getFirstName()+" "+this.billingClient.getMiddleName()+" "+this.billingClient.getLastName();
            this.billingClientText.setText(billledClientFullname);
            this.notesText.setText(invoiceInformation.getNotes());
            this.clientFirstNameParagraph.setText(this.client.getFirstName());
            this.clientMiddleNameParagraph.setText(this.client.getMiddleName());
            this.clientLastNameParagraph.setText(this.client.getLastName());
            this.clientEmailParagraph.setText(this.client.getEmail());
            this.clientPhoneNumberParagraph.setText(this.client.getPhoneNumber());
//            this.lm_date.setText(this.invoiceInformation);
            this.pdfGenerator = new PdfGenerator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void generatePDFButton() throws Exception {
        // Method for handling Generate PDF button click
        // Implement your logic here
        this.pdfGenerator.setAddress(this.address);
        this.pdfGenerator.setBillingAddress(this.billingAddress);
        this.pdfGenerator.setClient(this.client);
        this.pdfGenerator.setBillingClient(this.billingClient);
        this.pdfGenerator.setInvoiceInformation(this.invoiceInformation);
        this.pdfGenerator.setBusiness(this.business);
        this.pdfGenerator.setPath("invoice.pdf");
        this.pdfGenerator.generatePdf();
    }

    @FXML
    public void handleEmail() {
        // Method for handling Email button click
        // Implement your logic here
    }

    @FXML
    public void handleViewPDF() {
        // Method for handling View PDF button click
        // Implement your logic here
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        this.sceneController.setScene(this.CLIENT_VIEW.getTitle(), this.CLIENT_VIEW.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Back button clicked");
    }

    void initData(Client client, Admin admin, InvoiceInformation invoiceInformation) {
        this.admin = admin;
        this.client = client;
        this.invoiceInformation = invoiceInformation;
    }

}