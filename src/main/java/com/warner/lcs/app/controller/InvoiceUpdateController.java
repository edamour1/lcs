package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.domain.table_ui.AdditionalCostServiceStringConverter;
import com.warner.lcs.app.domain.table_ui.AddressStringConverter;
import com.warner.lcs.app.domain.table_ui.TreatmentStringConverter;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.InvoiceNumberGenerator;
import com.warner.lcs.common.util.SceneController;
import com.warner.lcs.common.util.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
public class InvoiceUpdateController implements Initializable {

    private FxmlView CLIENT_MENU, CLIENT_VIEW;
    private Admin admin;
    private Client client, billedClient;
    private List<Client> clients;
    private Address selectedAddress, selectedBillingAddress;
    private boolean notTheSameAsBillingAddress, r1Selected;
    private List<Address> addresses, billingClientaddresses;
    private Map<String, Treatment> updateTreatments;
    private Map<String,AdditionalCostService> updateAdditionalCostServices;
    private Treatment selectedTreatment;
    private List<Treatment> treatments;
    private RadioButton r1,r2;

    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    private AdditionalCostService selectedAdditionalCostService;
    private List<AdditionalCostService> additionalCostServices;
    private List<String> clientsNamesList;
    private Unit unit,unit2;
    private InvoiceInformation invoiceInformation;
    private InvoiceNumberGenerator invoiceNumberGenerator;
    private StringBuilder updateTreatmentQtyKey, updateAdditionalCostServiceQtyKey;
    private  int updateTreatmentSelectionId, updateAdditionalCostServicesSelectionId;
    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;
    @FXML
    private AnchorPane anchorPane;

    // Paragraph tags corresponding to client information
    @FXML
    private Text clientFirstNameParagraph;

    @FXML
    private Text clientLastNameParagraph;

    @FXML
    private Text clientMiddleNameParagraph;

    @FXML
    private Text clientPhoneNumberParagraph;

    @FXML
    private Text clientEmailParagraph;

    @FXML
    private Text streetParagraph;

    @FXML
    private Text cityParagraph;

    @FXML
    private Text stateParagraph;

    @FXML
    private Text zipcodeParagraph;

    @FXML
    private DatePicker paymentDueDatePicker;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField billingClientComboBox;

    @FXML
    private Label billingClientComboBoxLabel;

    //***********************error labels******************************************************

    @FXML
    private Label treatmentListErrorLabel;

    @FXML
    private Label additionalCostServicesListErrorLabel;

    @FXML
    private Label paymentDueDateErrorLabel;

    @FXML
    private Label startDateErrorLabel;

    @FXML
    private Label addressComboBoxErrorLabel;

    @FXML
    private Label billingClientComboBoxErrorLabel;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private Label notesErrorLabel;


    private ToggleGroup toggleGroup;

    @FXML
    private ChoiceBox<Treatment> treatmentComboBox;

    @FXML
    private ChoiceBox<AdditionalCostService> additionalCostServiceComboBox;

    @FXML
    private ListView<String> treatmentListView;

    @FXML
    private ListView<String> additionalCostServicesListView;

    @FXML
    private ChoiceBox<Address> addressComboBox, billingAddressComboBox;

    @FXML
    private TextField  treatmentQtyInputTextField;

    @FXML
    private TextField additionalCostServiceQtyInputTextField;

    @FXML
    private TextField tPrice;

    @FXML
    private TextField acsPrice;

    @FXML
    private TextArea notesTextArea;

    @FXML
    private ChoiceBox unitComboBox, unitComboBox2;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Text invoiceNumberParagraph;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
            this.CLIENT_MENU = FxmlView.CLIENT_MENU;
            this.clients = this.lcsService.getClients();
            this.treatments = this.lcsService.getAllTreatments();
            this.additionalCostServices = this.lcsService.getAllAdditionalCostServices();
            this.addresses = lcsService.getAddressesByClientId(this.client.getId());
            clientsNamesList = new ArrayList<>();
            this.r1Selected = false;
            this.updateTreatmentQtyKey = new StringBuilder();
            this.updateAdditionalCostServiceQtyKey = new StringBuilder();
            this.updateTreatmentSelectionId = 0;
            this.updateAdditionalCostServicesSelectionId = 0;

            for(Client c : this.clients)
            {
                clientsNamesList.add(c.getFirstName()+", "+c.getMiddleName()+", "+c.getLastName());
            }
            String[] possibleWords = clientsNamesList.toArray(new String[0]);
            TextFields.bindAutoCompletion(billingClientComboBox,possibleWords);

            billingClientComboBox.textProperty().addListener((observable, oldValue, newValue) -> {
                String firstName = "", middlName = "", lastName = "";
                if(clientsNamesList.contains(newValue))
                {
                    StringBuilder sb = new StringBuilder();

                    int nameCount = 0;
                    String x;
                    newValue = newValue+",";
                    for(char c : newValue.toCharArray())
                    {
                        sb.append(c);
                        x = sb.toString();
                        if(c == ',')
                        {
                            nameCount++;

                            switch (nameCount) {

                                case 1 :
                                    firstName = sb.toString().substring(0,sb.length()-1).trim();
                                    // Clear the contents of the StringBuilder
                                    sb.setLength(0);
                                    break;

                                case 2 :
                                    middlName = sb.toString().substring(0,sb.length()-1).trim();
                                    // Clear the contents of the StringBuilder
                                    sb.setLength(0);
                                    break;

                                default:
                                    lastName = sb.toString().substring(0,sb.length()-1).trim();
                                    // Clear the contents of the StringBuilder
                                    sb.setLength(0);
                                    break;
                            }
                        }
                    }

                    // newValue contains the currently entered text or the selected suggestion
                    System.out.println("Selected: " + newValue);
                    // You can now use newValue as needed, such as updating other fields based on the selection

                    for(Client c : this.clients)
                    {
                        if(c.getFirstName().toLowerCase().equals(firstName.toLowerCase())
                                && c.getMiddleName().toLowerCase().equals(middlName.toLowerCase())
                                && c.getLastName().toLowerCase().equals(lastName.toLowerCase())
                        )
                        {
                            this.billedClient = c;
                            try {
                                this.billingClientaddresses = this.lcsService.getAddressesByClientId(this.billedClient.getId());
                                for(Address a : this.billingClientaddresses)
                                {
                                    if(a.isBilling())
                                    {
                                        this.selectedBillingAddress = a;
                                    }
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Selected: " + this.client.getId()+" name: "+c.getFirstName()+" "+this.selectedAddress.getStreet());
                        }
                    }

                }

            });

            //***********************************treatment choicebox setup******************************************************************************
            this.treatmentComboBox.setConverter(new TreatmentStringConverter());

            for(Treatment t : this.treatments) {

                treatmentComboBox.getItems().add(t);
            }
            this.treatmentComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Do something with the selected item
                    this.selectedTreatment = newValue;
                    System.out.println("Selected item: " + this.selectedTreatment.getId()+", "+this.selectedTreatment.getTreatmentName());
                }
            });

            this.treatmentComboBox.setValue(this.treatments.get(0));
            this.selectedTreatment = this.treatments.get(0);
            this.selectedTreatment.setQty(0.0);
            this.selectedTreatment.setUnit(Unit.MILLILITER.getFullName());

            //***********************************additonal cost services choicebox setup******************************************************************************
            this.additionalCostServiceComboBox.setConverter(new AdditionalCostServiceStringConverter());

            for(AdditionalCostService a : this.additionalCostServices) {

                additionalCostServiceComboBox.getItems().add(a);
            }
            this.additionalCostServiceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Do something with the selected item
                    this.selectedAdditionalCostService = newValue;
                    System.out.println("Selected item: " + this.selectedAdditionalCostService.getId()+", "+this.selectedAdditionalCostService.getTreatmentName());
                }
            });

            this.additionalCostServiceComboBox.setValue(this.additionalCostServices.get(0));
            this.selectedAdditionalCostService = this.additionalCostServices.get(0);
            this.selectedAdditionalCostService.setQty(0.0);
            this.selectedAdditionalCostService.setUnit(Unit.MILLILITER.getFullName());


            //***********************************address choicebox setup******************************************************************************
            this.addressComboBox.setConverter(new AddressStringConverter());

            for(Address a : this.addresses) {
                addressComboBox.getItems().add(a);
            }

            this.addressComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Do something with the selected item
                    this.selectedAddress = newValue;
                    System.out.println("Selected item: " + this.selectedAddress.getId()+", "+this.selectedAddress.getStreet());
                }
            });

            this.addressComboBox.setValue(addresses.get(0));

            //***********************************billing address choicebox setup******************************************************************************
            this.billingAddressComboBox.setConverter(new AddressStringConverter());

            for(Address b : this.addresses) {
                billingAddressComboBox.getItems().add(b);
            }

            this.billingAddressComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Do something with the selected item
                    this.selectedBillingAddress = newValue;
                    System.out.println("Selected item: " + this.selectedBillingAddress.getId()+", "+this.selectedBillingAddress.getStreet());
                }
            });


            //***********************************check box setup******************************************************************************
            // Bind the visibility of billingClientComboBox to the selectedProperty of the checkBox
            this.billingClientComboBox.visibleProperty().bind(checkBox.selectedProperty());
            this.billingClientComboBoxLabel.visibleProperty().bind(checkBox.selectedProperty());
            this.billingAddressComboBox.visibleProperty().bind(checkBox.selectedProperty());

            this.checkBox.setOnAction(event -> {
//                this.billingClientComboBox.setVisible(!checkBox.isSelected());
                this.notTheSameAsBillingAddress = checkBox.isSelected();
            });

            this.notTheSameAsBillingAddress = false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //************************************************client information**************************************************
        // Initialize paragraph tags with client information
        if (client != null) {
            clientFirstNameParagraph.setText(client.getFirstName());
            clientLastNameParagraph.setText(client.getLastName());
            clientMiddleNameParagraph.setText(client.getMiddleName());
            clientPhoneNumberParagraph.setText(client.getPhoneNumber());
            clientEmailParagraph.setText(client.getEmail());
        }

        //***********************************unit2 choicebox setup******************************************************************************


        // Set a custom string converter to display enum's full name
        unitComboBox.setConverter(new StringConverter<Unit>() {
            @Override
            public String toString(Unit unit) {
                return unit.getFullName();
            }

            @Override
            public Unit fromString(String s) {
                // Not needed for this example
                return null;
            }
        });

        this.unit = Unit.MILLILITER;

        // Get the values of the enum and convert them to an observable list
        ObservableList<Unit> options = FXCollections.observableArrayList(Unit.values());

        unitComboBox.setItems(options);

        unitComboBox.setValue(Unit.MILLILITER);

        unitComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {

            for(Unit currentUnit : Unit.values()){
                if(currentUnit.toString().toLowerCase().equals(newValue.toString().toLowerCase()))
                {
                    this.unit = currentUnit;
                    System.out.println(this.unit);
                    break;
                }
            }
        });
        // Set a custom string converter to display enum's full name
        unitComboBox2.setConverter(new StringConverter<Unit>() {
            @Override
            public String toString(Unit unit) {
                return unit.getFullName();
            }

            @Override
            public Unit fromString(String s) {
                // Not needed for this example
                return null;
            }
        });

        this.unit2 = Unit.MILLILITER;

        unitComboBox2.setItems(options);

        unitComboBox2.setValue(Unit.MILLILITER);

        unitComboBox2.valueProperty().addListener((observable, oldValue, newValue) -> {

            for(Unit currentUnit : Unit.values()) {
                if(currentUnit.toString().toLowerCase().equals(newValue.toString().toLowerCase()))
                {
                    this.unit2 = currentUnit;
                    System.out.println(this.unit2);
                    break;
                }
            }
        });
        this.treatmentQtyInputTextField.setText("0.0");
        this.additionalCostServiceQtyInputTextField.setText("0.0");
        this.updateTreatments = new HashMap<>();
        this.updateAdditionalCostServices = new HashMap<>();

        //***************************************** dates ***************************************************
        // Set the default value to today's date
        this.paymentDueDatePicker.setValue(LocalDate.now());
        this.startDatePicker.setValue(LocalDate.now());

        // create a label
        Label l = new Label("This is a Radiobutton example ");

        //***************************************** dates ***************************************************
        toggleGroup = new ToggleGroup();
        // create radiobuttons
        r1 = new RadioButton("Billing Client");
        r2 = new RadioButton("Billing Address");
        // add label
        r1.setToggleGroup(toggleGroup);
        r2.setToggleGroup(toggleGroup);
        r1.setUserData("billed");
        r2.setUserData("address");
        // Assuming you have a TextField named textField1 and a ToggleGroup named toggleGroup
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            this.r1Selected = (newValue == r1);
            this.billingAddressComboBox.setDisable(this.r1Selected); // Enable textField1 if r1 is selected
            this.billingClientComboBox.setDisable(!this.r1Selected); // Disable textField1 if any other radio button is selected
        });

        toggleGroup.selectToggle(r2); // Select the first radio button as the default


        //Adding the toggle button to the pane
        VBox box = new VBox(5);
        box.setFillWidth(false);
        box.setPadding(new Insets(5, 5, 5, 50));
        box.getChildren().addAll(r1,r2);

        // Set the new layout position for the VBox
        double newX = 200; // New X-coordinate
        double newY = 200; // New Y-coordinate
        box.setLayoutX(newX);
        box.setLayoutY(newY);

        box.visibleProperty().bind(checkBox.selectedProperty());

        this.anchorPane.getChildren().add(box);


        try {
            this.paymentDueDatePicker.setValue(this.invoiceInformation.getPaymentDueDate().toLocalDate());
            this.startDatePicker.setValue(this.invoiceInformation.getStartDate().toLocalDate());
            this.endDatePicker.setValue(this.invoiceInformation.getEndDate().toLocalDate());
            Address addressParameter = new Address();
            addressParameter.setId(invoiceInformation.getAddressId());
            this.addressComboBox.setValue(this.lcsService.getAddressById(addressParameter));
            this.checkBox.setSelected(addressParameter.isBilling());
            for(Treatment t : this.invoiceInformation.getTreatments())
            {
                t.setOldItems(true);
                t.setUpdateQty(true);
                this.treatmentListView.getItems().add(t.getTreatmentName()+" : "+t.getQty()+" "+t.getUnit()+" $"+t.getPrice());
                this.updateTreatments.put(t.getTreatmentName().toLowerCase().trim(),t);//zoom
            }
            for(AdditionalCostService a : this.invoiceInformation.getAdditionalCostServices())
            {
                a.setOldItems(true);
                a.setUpdateQty(true);
                this.additionalCostServicesListView.getItems().add(a.getTreatmentName()+" : "+a.getQty()+" "+a.getUnit()+" $"+a.getPrice());
                this.updateAdditionalCostServices.put(a.getTreatmentName().toLowerCase().trim(),a);
            }

            this.notesTextArea.setText(this.invoiceInformation.getNotes());
            this.invoiceNumberParagraph.setText(this.invoiceInformation.getNo());
            //***************************************billing addrress*****************************************************************
            boolean isNotBillingAddress = !(this.invoiceInformation.getAddressId() == this.invoiceInformation.getBillingAddressId());//correct
            this.checkBox.setSelected(isNotBillingAddress);//correct
            this.notTheSameAsBillingAddress = isNotBillingAddress;//correct
            Address billingAddress = new Address(), temporaryAddress;
            billingAddress.setId(this.invoiceInformation.getBillingAddressId());//correct
            billingAddress = this.lcsService.getAddressById(billingAddress);//correct
            this.selectedBillingAddress = billingAddress;//correct

            boolean adressesContainsBillingAddress = false;

            for(Address a : this.addresses)
            {//check if billing
                if(a.getId() == this.invoiceInformation.getBillingAddressId())
                {
                    adressesContainsBillingAddress = true;//corect
                }
            }
            boolean usingClientForBillingAddress = !adressesContainsBillingAddress;//correct


            if(isNotBillingAddress)
            {
                if(adressesContainsBillingAddress)
                {
                    toggleGroup.selectToggle(r2); // Select the second radio button as the default
                    this.billingAddressComboBox.setValue(this.selectedBillingAddress);
                }

                if(usingClientForBillingAddress)
                {

                    this.toggleGroup.selectToggle(r1); // Select the first radio button as the default
                    List<Address> billingClientAddresses = null;
                    for(Client c : this.clients)
                    {

                        if(billingAddress.getClientId() == c.getId())
                        {
                            billingClientAddresses = lcsService.getAddressesByClientId(c.getId());
                            this.billedClient = c;
                            break;
                        }
                    }
                    if(billingClientAddresses != null)
                    {//it is a billing client address being used
                        for(Address a : billingClientAddresses)//set defualt billing address based off of billed client
                        {//check all billing addresses
                            if(a.getId() == this.invoiceInformation.getBillingAddressId())
                            {
                                this.selectedBillingAddress = a;
                                String billedClientString = this.billedClient.getFirstName()+", "+this.billedClient.getMiddleName()+", "+this.billedClient.getLastName();
                                this.billingClientComboBox.setText(billedClientString);
                                this.billingAddressComboBox.setValue(null);
                                break;
                            }
                        }
                    }
                }
            }

            // Add an event listener to the treatmentListView
            treatmentListView.setOnMouseClicked((event) -> {
                this.updateTreatmentSelectionId = this.treatmentListView.getSelectionModel().getSelectedIndex();

                StringBuilder sb = new StringBuilder();
                for(char letter : this.treatmentListView.getSelectionModel().getSelectedItem().toCharArray())
                {
                    if(letter == ':') {break;}//we just want the name of the treatmeant
                    sb.append(letter);
                }
                System.out.println(sb.toString().toLowerCase().trim());
                String key = sb.toString().toLowerCase().trim();
                this.updateTreatmentQtyKey.setLength(0);
                this.updateTreatmentQtyKey.append(key);
                Treatment obj = this.updateTreatments.get(key);

                this.treatmentQtyInputTextField.setText(Double.toString(obj.getQty()));
                this.tPrice.setText(Double.toString(obj.getPrice()));
                String unitStringValue = obj.getUnit();
                StringBuilder unitStringBuilder = new StringBuilder();

                if(unitStringValue.toString().contains(" "))
                {
                    for(char c : unitStringValue.toCharArray())
                    {
                        if(c==' ')
                        {
                            unitStringBuilder.append("_");
                        }
                        else
                        {
                            unitStringBuilder.append(c);
                        }
                    }
                } else { unitStringBuilder.append(unitStringValue); }

                for(Unit currentUnit : Unit.values()){
                    if(currentUnit.toString().toLowerCase().equals(unitStringBuilder.toString().toLowerCase()))
                    {
                        this.unitComboBox.setValue(currentUnit);
                        System.out.println(unitStringBuilder.toString().toLowerCase());
                        System.out.println(currentUnit);
                        break;
                    }
                }
                this.treatmentComboBox.setValue(obj);
            });

            // Add an event listener to the additionalCostServicesListView
            additionalCostServicesListView.setOnMouseClicked((event) -> {
                this.updateAdditionalCostServicesSelectionId = this.additionalCostServicesListView.getSelectionModel().getSelectedIndex();

                StringBuilder sb = new StringBuilder();
                for(char letter : this.additionalCostServicesListView.getSelectionModel().getSelectedItem().toCharArray())
                {
                    if(letter == ':') {break;}//we just want the name of the treatmeant
                    sb.append(letter);
                }

                System.out.println(sb.toString().toLowerCase().trim());
                String key = sb.toString().toLowerCase().trim();
                this.updateAdditionalCostServiceQtyKey.setLength(0);
                this.updateAdditionalCostServiceQtyKey.append(key);
                AdditionalCostService obj = this.updateAdditionalCostServices.get(key);

                this.additionalCostServiceQtyInputTextField.setText(Double.toString(obj.getQty()));
                this.acsPrice.setText(Double.toString(obj.getPrice()));
                String unitStringValue = obj.getUnit();
                StringBuilder unitStringBuilder = new StringBuilder();

                if(unitStringValue.toString().contains(" "))
                {
                    for(char c : unitStringValue.toCharArray())
                    {
                        if(c==' ')
                        {
                            unitStringBuilder.append("_");
                        }
                        else
                        {
                            unitStringBuilder.append(c);
                        }
                    }
                } else { unitStringBuilder.append(unitStringValue); }

                for(Unit currentUnit : Unit.values()){
                    if(currentUnit.toString().toLowerCase().equals(unitStringBuilder.toString().toLowerCase()))
                    {
                        this.unitComboBox2.setValue(currentUnit);
                        System.out.println(unitStringBuilder.toString().toLowerCase());//zoom
                        System.out.println(currentUnit);//zoom
                        break;
                    }
                }

                this.additionalCostServiceComboBox.setValue(obj);

            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addTreatment()
    {
        if(!this.updateTreatments.containsKey(this.selectedTreatment.getTreatmentName()))
        {
            this.selectedTreatment.setOldItems(false);
            Unit saveUnit = (Unit) this.unitComboBox.getSelectionModel().getSelectedItem();
            this.selectedTreatment.setUnit(saveUnit.getFullName());
            this.selectedTreatment.setUpdateQty(false);
            this.selectedTreatment.setQty(Double.valueOf(this.treatmentQtyInputTextField.getText()));
            this.selectedTreatment.setPrice(Double.valueOf(this.tPrice.getText()));
            this.updateTreatments.put(this.selectedTreatment.getTreatmentName().toLowerCase().trim(),this.selectedTreatment);
            System.out.println(this.updateTreatments.toString());
            this.treatmentListView.getItems().add(this.selectedTreatment.getTreatmentName()+" : "+this.selectedTreatment.getQty()+" "+this.selectedTreatment.getUnit()+" $"+this.selectedTreatment.getPrice());
        }
    }

    @FXML
    private void updateTreatment()
    {
       Treatment objToUpdate = this.updateTreatments.get(this.updateTreatmentQtyKey.toString());
       objToUpdate.setQty(Double.parseDouble(this.treatmentQtyInputTextField.getText()));
       objToUpdate.setPrice(Double.parseDouble(this.tPrice.getText()));
       Unit unitObjToUpdate = (Unit) this.unitComboBox.getSelectionModel().getSelectedItem();
       objToUpdate.setUnit(unitObjToUpdate.getFullName());
       this.updateTreatments.put(this.updateTreatmentQtyKey.toString(),objToUpdate);
       String updatedTreatmentListViewItem = objToUpdate.getTreatmentName()+" : "+objToUpdate.getQty()+" "+objToUpdate.getUnit()+" $"+objToUpdate.getPrice();
       this.treatmentListView.getItems().set(this.updateTreatmentSelectionId,updatedTreatmentListViewItem);
    }


    @FXML
    private void removeTreatment()
    {
        int selectId = this.treatmentListView.getSelectionModel().getSelectedIndex();
        StringBuilder sb = new StringBuilder();
        for(char letter : this.treatmentListView.getSelectionModel().getSelectedItem().toCharArray())
        {
            if(letter == ':') {break;}//we just want the name of the treatmeant
            sb.append(letter);
        }
        System.out.println(sb.toString().toLowerCase().trim());
        String key = sb.toString().toLowerCase().trim();
        if(this.updateTreatments.containsKey(key))
        {
            if(this.updateTreatments.get(key).isOldItems())
            {
                Treatment obj = this.updateTreatments.get(sb.toString().toLowerCase().trim());
                obj.setRemoveFromList(true);
                this.updateTreatments.put(key,obj);
            }
            else
            {
                this.updateTreatments.remove(key);
            }


            this.treatmentListView.getItems().remove(selectId);
            System.out.println("treatment size:"+this.updateTreatments.size());
        }

    }

    @FXML
    private void addAdditionalCostService()
    {
        if(!this.updateAdditionalCostServices.containsKey(this.selectedAdditionalCostService.getTreatmentName()))
        {
            this.selectedAdditionalCostService.setOldItems(false);
            Unit saveUnit = (Unit) this.unitComboBox2.getSelectionModel().getSelectedItem();
            this.selectedAdditionalCostService.setUnit(saveUnit.getFullName());
            this.selectedAdditionalCostService.setUpdateQty(false);
            this.selectedAdditionalCostService.setQty(Double.valueOf(this.additionalCostServiceQtyInputTextField.getText()));
            this.selectedAdditionalCostService.setPrice(Double.valueOf(this.acsPrice.getText()));
            this.updateAdditionalCostServices.put(this.selectedAdditionalCostService.getTreatmentName().toLowerCase().trim(),this.selectedAdditionalCostService);
            System.out.println(this.updateAdditionalCostServices.toString());
            this.additionalCostServicesListView.getItems().add(this.selectedAdditionalCostService.getTreatmentName()+" : "+this.selectedAdditionalCostService.getQty()+" "+this.selectedAdditionalCostService.getUnit()+" $"+this.selectedAdditionalCostService.getPrice());
        }
    }

    @FXML
    private void updateAdditionalCostService()
    {
        AdditionalCostService objToUpdate = this.updateAdditionalCostServices.get(this.updateAdditionalCostServiceQtyKey.toString());
        objToUpdate.setQty(Double.parseDouble(this.additionalCostServiceQtyInputTextField.getText()));
        objToUpdate.setPrice(Double.parseDouble(this.acsPrice.getText()));
        Unit unitObjToUpdate = (Unit) this.unitComboBox2.getSelectionModel().getSelectedItem();
        objToUpdate.setUnit(unitObjToUpdate.getFullName());
        this.updateAdditionalCostServices.put(this.updateAdditionalCostServiceQtyKey.toString(),objToUpdate);
        String updatedAdditionalCostServiceListViewItem = objToUpdate.getTreatmentName()+" : "+objToUpdate.getQty()+" "+objToUpdate.getUnit()+" $"+objToUpdate.getPrice();
        this.additionalCostServicesListView.getItems().set(this.updateAdditionalCostServicesSelectionId,updatedAdditionalCostServiceListViewItem);
    }

    @FXML
    private void removeAdditionalCostService()
    {
        int selectId = this.additionalCostServicesListView.getSelectionModel().getSelectedIndex();
        StringBuilder sb = new StringBuilder();
        for(char letter : this.additionalCostServicesListView.getSelectionModel().getSelectedItem().toCharArray())
        {
            if(letter == ':') {break;}//we just want the name of the treatmeant
            sb.append(letter);
        }
        String key = sb.toString().toLowerCase().trim();
        System.out.println(sb.toString().toLowerCase().trim());
        if(this.updateAdditionalCostServices.containsKey(key))
        {
            if(this.updateAdditionalCostServices.get(key).isOldItems())
            {
                AdditionalCostService obj = this.updateAdditionalCostServices.get(key);
                obj.setRemoveFromList(true);
                this.updateAdditionalCostServices.put(key,obj);
            }
            else
            {
                AdditionalCostService obj = this.updateAdditionalCostServices.get(key);
                this.updateAdditionalCostServices.remove(key);
//                obj.setRemoveFromList(true);
//                this.updateAdditionalCostServices.remove(sb.toString().toLowerCase().trim());
            }

            this.additionalCostServicesListView.getItems().remove(selectId);
            System.out.println("additional cost service size:"+this.updateAdditionalCostServices.size());
        }

    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        this.sceneController.setScene(this.CLIENT_VIEW.getTitle(), this.CLIENT_VIEW.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Back button clicked");
    }

    @FXML
    private void handleSubmit(ActionEvent event) throws Exception
    {
        this.invoiceNumberGenerator = new InvoiceNumberGenerator();
        // Convert the map to an ArrayList
        List<Treatment> saveTreatmentsArrayList = new ArrayList<>(this.updateTreatments.values());
        List<AdditionalCostService> saveAdditionalCostServicesArrayList = new ArrayList<>(this.updateAdditionalCostServices.values());
        InvoiceInformation saveInvoice = new InvoiceInformation();
        saveInvoice.setNo(this.invoiceInformation.getNo());
        saveInvoice.setPaymentDueDate(Date.valueOf(this.paymentDueDatePicker.getValue()));
        saveInvoice.setStartDate(Date.valueOf(this.startDatePicker.getValue()));
        saveInvoice.setEndDate(Date.valueOf(this.endDatePicker.getValue()));
        saveInvoice.setTreatments(saveTreatmentsArrayList);
        saveInvoice.setAdditionalCostServices(saveAdditionalCostServicesArrayList);
        int billingAddressId = this.notTheSameAsBillingAddress ? this.selectedBillingAddress.getId() : this.selectedAddress.getId();
        this.selectedAddress.setBilling(!this.notTheSameAsBillingAddress);
        saveInvoice.setAddressId(this.selectedAddress.getId());
        saveInvoice.setBillingAddressId(billingAddressId);
        saveInvoice.setClientId(this.client.getId());
        saveInvoice.setNotes(this.notesTextArea.getText());
        saveInvoice.setActive(true);
        if(this.notTheSameAsBillingAddress)
        {
            this.selectedBillingAddress.setBilling(true);
            if(!this.r1Selected){this.lcsService.updateAddress(this.selectedBillingAddress,this.client,this.admin);}
        }
        this.lcsService.updateInvoiceInformation(saveInvoice,this.client,this.admin);
        this.sceneController.setScene(this.CLIENT_VIEW.getTitle(), this.CLIENT_VIEW.getFxmlFilePath());
        this.sceneController.switchToScene(event);


        System.out.println("Submit button clicked");
    }


    void initData(Client client, Admin admin,InvoiceInformation invoiceInformation) {
        this.admin = admin;
        this.client = client;
        this.invoiceInformation = invoiceInformation;
    }


}