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

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class InvoiceRegisterController implements Initializable {

    private FxmlView CLIENT_MENU, CLIENT_VIEW;
    private Admin admin;
    private Client client, billedClient;
    private List<Client> clients;
    private Address selectedAddress, selectedBillingAddress;
    private boolean notTheSameAsBillingAddress, r1Selected;
    private List<Address> addresses, billingClientaddresses;
    private Map<String,Treatment> saveTreatments;
    private Map<String,AdditionalCostService> saveAdditionalCostServices;
    private Treatment selectedTreatment;
    private List<Treatment> treatments;

    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    private AdditionalCostService selectedAdditionalCostService;
    private List<AdditionalCostService> additionalCostServices;
    private Unit unit,unit2;
    private InvoiceInformation invoiceInformation;
    private InvoiceNumberGenerator invoiceNumberGenerator;
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
    private TextField  treatmentQtyInputTextField, additionalCostServiceQtyInputTextField;

    @FXML
    private TextArea notesTextArea;

    @FXML
    private ChoiceBox unitComboBox, unitComboBox2;

    @FXML
    private CheckBox checkBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
            this.CLIENT_MENU = FxmlView.CLIENT_MENU;
            this.clients = this.lcsService.getClients();
            this.treatments = this.lcsService.getAllTreatments();
            this.additionalCostServices = this.lcsService.getAllAdditionalCostServices();
            this.addresses = lcsService.getAddressesByClientId(this.client.getId());
            List<String> clientsNamesList = new ArrayList<>();


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

            for(Unit currentUnit : Unit.values()){
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
        this.saveTreatments = new HashMap<>();
        this.saveAdditionalCostServices = new HashMap<>();

        //***************************************** dates ***************************************************
        // Set the default value to today's date
        this.paymentDueDatePicker.setValue(LocalDate.now());
        this.startDatePicker.setValue(LocalDate.now());

        // create a label
        Label l = new Label("This is a Radiobutton example ");

        //***************************************** dates ***************************************************
        toggleGroup = new ToggleGroup();
        // create radiobuttons
        RadioButton r1 = new RadioButton("Billing Client");
        RadioButton r2 = new RadioButton("Billing Address");
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
    }

    @FXML
    private void addTreatment()
    {
        if(!this.saveTreatments.containsKey(this.selectedTreatment.getTreatmentName()))
        {
            Unit saveUnit = (Unit) this.unitComboBox.getSelectionModel().getSelectedItem();
            this.selectedTreatment.setUnit(saveUnit.getFullName());
            this.selectedTreatment.setUpdateQty(false);
            this.selectedTreatment.setQty(Double.valueOf(this.treatmentQtyInputTextField.getText()));
            this.saveTreatments.put(this.selectedTreatment.getTreatmentName().toLowerCase().trim(),this.selectedTreatment);
            System.out.println(this.saveTreatments.toString());
            this.treatmentListView.getItems().add(this.selectedTreatment.getTreatmentName()+" : "+this.selectedTreatment.getQty()+" "+this.selectedTreatment.getUnit());
        }
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
        if(this.saveTreatments.containsKey(sb.toString().toLowerCase().trim()))
        {
            this.saveTreatments.remove(sb.toString().toLowerCase().trim());
            this.treatmentListView.getItems().remove(selectId);
            System.out.println("treatment size:"+this.saveTreatments.size());
        }

    } @FXML
    private void addAdditionalCostService()
    {
        if(!this.saveAdditionalCostServices.containsKey(this.selectedAdditionalCostService.getTreatmentName()))
        {
            Unit saveUnit = (Unit) this.unitComboBox2.getSelectionModel().getSelectedItem();
            this.selectedAdditionalCostService.setUnit(saveUnit.getFullName());
            this.selectedAdditionalCostService.setUpdateQty(false);
            this.selectedAdditionalCostService.setQty(Double.valueOf(this.additionalCostServiceQtyInputTextField.getText()));
            this.saveAdditionalCostServices.put(this.selectedAdditionalCostService.getTreatmentName().toLowerCase().trim(),this.selectedAdditionalCostService);
            System.out.println(this.saveAdditionalCostServices.toString());
            this.additionalCostServicesListView.getItems().add(this.selectedAdditionalCostService.getTreatmentName()+" : "+this.selectedAdditionalCostService.getQty()+" "+this.selectedAdditionalCostService.getUnit());
        }
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
        System.out.println(sb.toString().toLowerCase().trim());
        if(this.saveAdditionalCostServices.containsKey(sb.toString().toLowerCase().trim()))
        {
            this.saveAdditionalCostServices.remove(sb.toString().toLowerCase().trim());
            this.additionalCostServicesListView.getItems().remove(selectId);
            System.out.println("additional cost service size:"+this.saveAdditionalCostServices.size());
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
        List<Treatment> saveTreatmentsArrayList = new ArrayList<>(this.saveTreatments.values());
        List<AdditionalCostService> saveAdditionalCostServicesArrayList = new ArrayList<>(this.saveAdditionalCostServices.values());
        InvoiceInformation saveInvoice = new InvoiceInformation();
        saveInvoice.setNo(this.invoiceNumberGenerator.generateInvoiceNo());
        saveInvoice.setPaymentDueDate(Date.valueOf(this.paymentDueDatePicker.getValue()));
        saveInvoice.setStartDate(Date.valueOf(this.startDatePicker.getValue()));
        saveInvoice.setEndDate(Date.valueOf(this.startDatePicker.getValue()));
        saveInvoice.setTreatments(saveTreatmentsArrayList);
        saveInvoice.setAdditionalCostServices(saveAdditionalCostServicesArrayList);
        saveInvoice.setAddressId(this.selectedAddress.getId());
        int billingAddressId = this.notTheSameAsBillingAddress ? this.selectedBillingAddress.getId() : this.selectedAddress.getId();
        saveInvoice.setBillingAddressId(billingAddressId);
        saveInvoice.setClientId(this.client.getId());
        saveInvoice.setNotes(this.notesTextArea.getText());
        saveInvoice.setActive(true);
        this.formValid();
//        this.lcsService.saveInvoiceInformation(saveInvoice,this.client,this.selectedAddress,this.admin);
//        this.sceneController.setScene(this.CLIENT_VIEW.getTitle(), this.CLIENT_VIEW.getFxmlFilePath());
//        this.sceneController.switchToScene(event);

        System.out.println("Submit button clicked");
    }

    private void showError(TextField textField, String errorMessage, Label errorLabel) {
        // Apply CSS style to indicate error
        textField.getStyleClass().add("invalid-text-field");

        // Display error message
        // You can choose how to display the error message (e.g., tooltip, label)
        // Here, we'll set the prompt text to the error message temporarily
        textField.setPromptText(errorMessage);

        // Show error message
        errorLabel.setText(errorMessage);
    }

    private void clearError(TextField textField,Label errorLabel) {
        // Clear CSS style to indicate no error
        textField.getStyleClass().remove("invalid-text-field");
        textField.setPromptText(null);
        // Clear error message
        errorLabel.setText("");
    }

    private void showDateError(DatePicker datePicker, String errorMessage, Label errorLabel)
    {
        // Apply CSS style to indicate error
        datePicker.getStyleClass().add("invalid-text-field");

        // Display error message
        // You can choose how to display the error message (e.g., tooltip, label)
        // Here, we'll set the prompt text to the error message temporarily
        datePicker.setPromptText(errorMessage);

        // Show error message
        errorLabel.setText(errorMessage);

    }

    private void clearDateError(DatePicker datePicker,Label errorLabel) {
        // Clear CSS style to indicate no error
        datePicker.getStyleClass().remove("invalid-text-field");
        datePicker.setPromptText(null);
        // Clear error message
        errorLabel.setText("");
    }

    private boolean validateDate(DatePicker datePicker, Label label) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        TextField dateTextField = datePicker.getEditor();
        String dateText = dateTextField.getText();
        Matcher matcher = pattern.matcher(dateText);
        if(!dateText.isEmpty())
        {
            this.clearDateError(datePicker,label);
        } else { // Clear CSS style to indicate no error
            label.setText("please enter a valid Date example: 04/16/2024");
        }
        return matcher.matches();
    }

    private boolean formValid()
    {
        if(this.validateDate(this.paymentDueDatePicker,this.paymentDueDateErrorLabel))
        {
            return true;
        } else{
            return false;
        }


    }



    void initData(Client client, Admin admin) {
        this.admin = admin;
        this.client = client;
    }
}
