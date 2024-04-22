package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.MessagingException;

@Component
public class InvoiceViewController implements Initializable {

    private FxmlView CLIENT_MENU, CLIENT_VIEW;
    private Admin admin;
    private Client client, billingClient;
    private InvoiceInformation invoiceInformation;
    private PdfGenerator pdfGenerator;
    private  Address address, billingAddress;

    private Business business;

    private String pdfFilePath;
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

    @FXML
    private AnchorPane anchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //*************************************************************************************get Data*************************************************************
        this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
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
            this.paymentDueDateText.setText(invoiceInformation.getPaymentDueDateFormatedString());
            this.startDateText.setText(this.invoiceInformation.getStartDateFormatedString());
            this.endDateText.setText(this.invoiceInformation.getEndDateFormatedString());
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
    public void generatePDFButton(ActionEvent event) throws Exception {
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
        this.pdfFilePath = this.pdfGenerator.getPdfFullPath();

        Stage stage = (Stage) anchorPane.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type,"");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        StringBuilder stbr = new StringBuilder("Invoice: "+this.invoiceInformation.getNo()+" Generated successfully! \nFor: ");
        stbr.append(this.client.getFirstName()+" ");
        stbr.append(this.client.getMiddleName()+" ");
        stbr.append(this.client.getLastName());

        alert.getDialogPane().setContentText(stbr.toString());
        alert.getDialogPane().setHeaderText("Generate Invoice");

        Optional<ButtonType> result = alert.showAndWait();
    }

    @FXML
    public void handleEmail(ActionEvent event) throws Exception {
        // Method for handling Email button click
        // Implement your logic here
        String emailSubject = "Invoice", emailString = SQL.get("lcsSql","getEmailText"),pdfFilePath = this.pdfFilePath,companyAddress;
        String[] emailReceipients = {this.client.getEmail(), "warnerlf@gmail.com"};

        companyAddress = "\n\t   "+this.business.getAddress().getStreet()+",\n\t    "+business.getAddress().getCity().getCity()+" "+business.getAddress().getState().getState()+" "+business.getAddress().getZipcode().getZipcode();
        String emailBody =  String.format(emailString, this.client.getFirstName(), business.getName(), business.getEmail(), business.getPhoneNo(), business.getFaxPhoneNo(),companyAddress);
        System.out.println(emailBody);
        Email email = new Email(emailSubject,emailBody,emailReceipients,pdfFilePath);
        email.sendEmail();

        Stage stage = (Stage) anchorPane.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type,"");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        StringBuilder stbr = new StringBuilder("Invoice: "+this.invoiceInformation.getNo()+" email was sent to "+this.client.getEmail()+" successfully! \nFor: ");
        stbr.append(this.client.getFirstName()+" ");
        stbr.append(this.client.getMiddleName()+" ");
        stbr.append(this.client.getLastName());

        alert.getDialogPane().setContentText(stbr.toString());
        alert.getDialogPane().setHeaderText("Email Invoice");

        Optional<ButtonType> result = alert.showAndWait();

        this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);

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