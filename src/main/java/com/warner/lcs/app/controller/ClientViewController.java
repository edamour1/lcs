package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.domain.table_ui.AddressTableData;
import com.warner.lcs.app.domain.table_ui.InvoiceInformationTableData;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.warner.lcs.common.util.TableUtil.createTableColumn;
import static com.warner.lcs.common.util.TableUtil.getObservableList;

@Component
public class ClientViewController implements Initializable {

    private FxmlView CLIENT_MENU, ADDRESS_VIEW, ADDRESS_REGISTER, ADDRESS_UPDATE, ADDRESS_DELETE, INVOICE_REGISTER, INVOICE_VIEW, INVOICE_DELETE, INVOICE_UPDATE;
    private Client client;
    private AddressTableData selectedAddress;
    private InvoiceInformationTableData selectedInvoiceInformationTableData;
    private Address address;

    @FXML
    private // Create a ScrollPane
    ScrollPane scrollPane;
    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;
    @Autowired
    private AddressViewController addressViewController;
    @Autowired
    private AddressRegisterController addressRegisterController;
    @Autowired
    private AddressUpdateController addressUpdateController;

    @Autowired
    private AddressDeleteController addressDeleteController;

    @Autowired
    private InvoiceDeleteController invoiceDeleteController;

    @Autowired
    private InvoiceRegisterController invoiceRegisterController;

    @Autowired
    private InvoiceUpdateController invoiceUpdateController;

    @Autowired
    private InvoiceViewController invoiceViewController;

    private Admin admin;

    private List<Address> addresses;

    private List<InvoiceInformation> invoices;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<AddressTableData> tableView;

    @FXML
    private TableView<InvoiceInformationTableData> invoicesTableView;

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
    private TextField streetFilterTextField;
    @FXML
    private  TextField cityFilterTextField;
    @FXML
    private TextField stateFilterTextField;
    @FXML
    private TextField zipcodeFilterTextField;

    @FXML
    private TextField noColumnFilterTextField;

    @FXML
    private TextField paymentDueDateColumnFilterTextField;

    @FXML
    private TextField startDateColumnFilterTextField;

    @FXML
    private TextField notesColumnFilterTextField;

    @FXML
    private TextField treatmentsQtyColumnFilterTextField;

    @FXML
    private TextField additionalCostServicesQtyColumnFilterTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.ADDRESS_REGISTER = FxmlView.ADDRESS_REGISTER;
        this.ADDRESS_VIEW = FxmlView.ADDRESS_VIEW;
        this.ADDRESS_UPDATE = FxmlView.ADDRESS_UPDATE;
        this.ADDRESS_DELETE = FxmlView.ADDRESS_DELETE;
        this.INVOICE_VIEW = FxmlView.INVOICE_VIEW;
        this.INVOICE_REGISTER = FxmlView.INVOICE_REGISTER;
        this.INVOICE_UPDATE = FxmlView.INVOICE_UPDATE;
        this.INVOICE_DELETE = FxmlView.INVOICE_DELETE;

        // Initialize paragraph tags with client information
        if (client != null) {
            clientFirstNameParagraph.setText(client.getFirstName());
            clientLastNameParagraph.setText(client.getLastName());
            clientMiddleNameParagraph.setText(client.getMiddleName());
            clientPhoneNumberParagraph.setText(client.getPhoneNumber());
            clientEmailParagraph.setText(client.getEmail());
        }
        try {
            this.addresses = this.lcsService.getAddressesByClientId((int) this.client.getId());
            this.invoices = this.lcsService.getInvoiceInformationByClientId(this.client);
            if(!addresses.isEmpty())
            {
                List<AddressTableData> list = new ArrayList<>();
                for(Address address : addresses)
                {
                    list.add(new AddressTableData(Integer.valueOf(address.getId()),
                            address.getStreet(),
                            address.getUnit(),
                            address.getCity().getCity(),
                            address.getState().getState(),
                            address.getZipcode().getZipcode(),
                            address.isBilling() ,
                            address.getQuantity()));
                }

                // Converting the list of strings to an observable list of strings
                ObservableList<AddressTableData> observableStringList = getObservableList(list);

                TableColumn<AddressTableData, Integer> idColumn = createTableColumn("Address ID", "id");
                TableColumn<AddressTableData, String> streetColumn = createTableColumn("Street", "street");
                TableColumn<AddressTableData, String> cityColumn = createTableColumn("City", "city");
                TableColumn<AddressTableData, String> stateColumn = createTableColumn("State", "state");
                TableColumn<AddressTableData, String> zipcodeColumn = createTableColumn("Zipcode", "zipcode");
                TableColumn<AddressTableData, String> unitColumn = createTableColumn("Unit", "unit");
                TableColumn<AddressTableData, Double> quantityColumn = createTableColumn("Quantity", "quantity");
                TableColumn<AddressTableData, Boolean> billingColumn = createTableColumn("Billing", "isBilling");

                // Create a TableView with a list of addresses
                tableView.setItems(observableStringList);

                // Create a TableView with a list of addresses
                tableView.getColumns().addAll(streetColumn,cityColumn,stateColumn,zipcodeColumn);
            }

            // Add listener to handle row selection
            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    this.selectedAddress = newSelection;
                }
            });


            //*********************************************invoice table**********************************************
            if(!invoices.isEmpty())
            {
                List<InvoiceInformationTableData> invoiceList = new ArrayList<>();
                for(InvoiceInformation invoice : invoices)
                {
                    invoiceList.add(new InvoiceInformationTableData(invoice.getPaymentDueDate().toString(),
                                                                    invoice.getStartDate().toString(),
                                                                    invoice.getDate().toString(),
                                                                    Integer.valueOf(invoice.getTreatments().size()),
                                                                    Integer.valueOf(invoice.getAdditionalCostServices().size()),
                                                                    invoice.getNotes(),
                                                                    invoice.getNo()));
                }

                TableColumn<InvoiceInformationTableData, String> paymentDueDateColumn = createTableColumn("Payment Due Date", "paymentDueDate");
                TableColumn<InvoiceInformationTableData, String> startDateColumn = createTableColumn("Start Date", "startDate");
                TableColumn<InvoiceInformationTableData, String> dateColumn = createTableColumn("Created On Date", "date");
                TableColumn<InvoiceInformationTableData, Integer> treatmentsQtyColumn = createTableColumn("Number Of Treatments", "treatmentsQty");
                TableColumn<InvoiceInformationTableData, Integer> additionalCostServicesQtyColumn = createTableColumn("Number Of AD Services", "additionalCostServicesQty");
                TableColumn<InvoiceInformationTableData, String> notesColumn = createTableColumn("Notes", "notes");
                TableColumn<InvoiceInformationTableData, String> noColumn = createTableColumn("Inovice No.", "no");

                // Converting the list of strings to an observable list of strings
                ObservableList<InvoiceInformationTableData> observableStringList = getObservableList(invoiceList);

                // Create a TableView with a list of invoices
                invoicesTableView.getColumns().addAll(noColumn,dateColumn,paymentDueDateColumn,startDateColumn,notesColumn,treatmentsQtyColumn,additionalCostServicesQtyColumn);
                invoicesTableView.setItems(observableStringList);
            }

            // Add listener to handle row selection
            invoicesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    this.selectedInvoiceInformationTableData = newSelection;
                }
            });

            //*********************************************filters****************************************************

            // Wrap the observable list in a FilteredList (initially display all data)
            FilteredList<AddressTableData> filteredData = new FilteredList<>(tableView.getItems(), p -> true);

            // Set the filter predicate whenever the filter changes
            streetFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(street -> {
                    // If filter text is empty, display all streets
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare street's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (street.getStreet().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches street's name
                    }
                    return false; // Filter does not match street's name
                });
            });

            // Set the filter predicate whenever the filter changes
            cityFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(city -> {
                    // If filter text is empty, display all citys
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare city's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (city.getCity().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches city's name
                    }
                    return false; // Filter does not match city's name
                });
            });


            // Set the filter predicate whenever the filter changes
            stateFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(state -> {
                    // If filter text is empty, display all states
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare state's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (state.getState().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches state's name
                    }
                    return false; // Filter does not match state's name
                });
            });

            // Set the filter predicate whenever the filter changes
            zipcodeFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(zipcode -> {
                    // If filter text is empty, display all zipcodes
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare zipcode's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (zipcode.getZipcode().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches zipcode's name
                    }
                    return false; // Filter does not match zipcode's name
                });
            });

            // Wrap the filtered list in a SortedList
            SortedList<AddressTableData> sortedData = new SortedList<>(filteredData);

            // Bind the SortedList comparator to the TableView comparator
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            // Add sorted (and filtered) data to the TableView
            tableView.setItems(sortedData);

            //***************************************************invoice table filters***********************************************************************************
            // Wrap the observable list in a FilteredList (initially display all data)

            FilteredList<InvoiceInformationTableData> filteredInvoiceData = new FilteredList<>(invoicesTableView.getItems(), p -> true);

            // Set the filter predicate whenever the filter changes
            noColumnFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredInvoiceData.setPredicate(invoiceNo -> {
                    // If filter text is empty, display all invoiceNos
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare invoiceNo's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (invoiceNo.getNo().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches invoiceNo's name
                    }
                    return false; // Filter does not match invoiceNo's name
                });
            });

            // Set the filter predicate whenever the filter changes
            paymentDueDateColumnFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredInvoiceData.setPredicate(invoiceNo -> {
                    // If filter text is empty, display all invoiceNos
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare invoiceNo's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (invoiceNo.getPaymentDueDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches invoiceNo's name
                    }
                    return false; // Filter does not match invoiceNo's name
                });
            });

            // Set the filter predicate whenever the filter changes
            startDateColumnFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredInvoiceData.setPredicate(invoiceNo -> {
                    // If filter text is empty, display all invoiceNos
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare invoiceNo's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (invoiceNo.getStartDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches invoiceNo's name
                    }
                    return false; // Filter does not match invoiceNo's name
                });
            });

            // Set the filter predicate whenever the filter changes
            treatmentsQtyColumnFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredInvoiceData.setPredicate(invoiceNo -> {
                    // If filter text is empty, display all invoiceNos
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare invoiceNo's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    String value = Integer.toString(invoiceNo.getTreatmentsQty());
                    if (value.toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches invoiceNo's name
                    }
                    return false; // Filter does not match invoiceNo's name
                });
            });

            // Set the filter predicate whenever the filter changes
            additionalCostServicesQtyColumnFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredInvoiceData.setPredicate(invoiceNo -> {
                    // If filter text is empty, display all invoiceNos
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare invoiceNo's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Integer.toString(invoiceNo.getAdditionalCostServicesQty()).toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches invoiceNo's name
                    }
                    return false; // Filter does not match invoiceNo's name
                });
            });

            // Set the filter predicate whenever the filter changes
            notesColumnFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredInvoiceData.setPredicate(invoiceNo -> {
                    // If filter text is empty, display all invoiceNos
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare invoiceNo's name with the filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (invoiceNo.getNotes().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches invoiceNo's name
                    }
                    return false; // Filter does not match invoiceNo's name
                });
            });





            // Wrap the filtered list in a SortedList
            SortedList<InvoiceInformationTableData> invoiceSortedData = new SortedList<>(filteredInvoiceData);

            // Bind the SortedList comparator to the TableView comparator
            invoiceSortedData.comparatorProperty().bind(invoicesTableView.comparatorProperty());

            // Add sorted (and filtered) data to the TableView
            invoicesTableView.setItems(invoiceSortedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Method to handle the "View Address" button action
    @FXML
    private void viewAddress(ActionEvent event) throws Exception{
        Address viewAddress = new Address();

        for(Address address : this.addresses) {
            if(address.getId() == this.selectedAddress.getId())
            {
                viewAddress = address;
            }
        }
        if (selectedAddress != null) {
            this.addressViewController.initData(this.client,this.admin,viewAddress);
            // Add your logic to view the selected client
            this.sceneController.setScene(this.ADDRESS_VIEW.getTitle(), this.ADDRESS_VIEW.getFxmlFilePath());
            this.sceneController.switchToScene(event);
        } else {
            System.out.println("No client selected.");
        }
    }
//
    @FXML
    private void updateAddress(ActionEvent event) throws Exception {
        Address updateAddress = new Address();

        for(Address address : this.addresses) {
            if(address.getId() == this.selectedAddress.getId())
            {
                updateAddress = address;
            }
        }

        // Your logic for updating client
        this.addressUpdateController.initData(this.client,this.admin,updateAddress);
        this.sceneController.setScene(this.ADDRESS_UPDATE.getTitle(),this.ADDRESS_UPDATE.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void deleteAddress(ActionEvent event) throws Exception {
        Address deleteAddress = new Address();

        for(Address address : this.addresses) {
            if(address.getId() == this.selectedAddress.getId())
            {
                deleteAddress = address;
            }
        }

        this.addressDeleteController.initData(this.client,this.admin,deleteAddress);

        // Your logic for deleting client
        this.sceneController.setScene(this.ADDRESS_DELETE.getTitle(),this.ADDRESS_DELETE.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void registerAddress(ActionEvent event) throws Exception {
        if(this.selectedAddress != null) {
            City city = new City();
            city.setCity(this.selectedAddress.getCity());
            State state = new State();
            state.setState(selectedAddress.getState());
            Zipcode zipcode;
            zipcode = new Zipcode();
            zipcode.setZipcode(selectedAddress.getZipcode());

            city = this.lcsService.getCity(city);
            state = this.lcsService.getState(selectedAddress.getState());
            zipcode = this.lcsService.getZipcode(selectedAddress.getZipcode());
            Address registerAddress = new Address();
            registerAddress.setStreet(selectedAddress.getStreet());
            registerAddress.setCity(city);
            registerAddress.setState(state);
            registerAddress.setZipcode(zipcode);
        }

        // Your logic for registering client
        this.addressRegisterController.initData(this.client,this.admin);
        this.sceneController.setScene(this.ADDRESS_REGISTER.getTitle(),this.ADDRESS_REGISTER.getFxmlFilePath());
        this.sceneController.switchToScene(event);

        // Wrap the observable list in a FilteredList (initially display all data)
        FilteredList<AddressTableData> filteredData = new FilteredList<>(tableView.getItems(), p -> true);

    }

    @FXML
    private void viewInvoice(ActionEvent event) throws IOException {
        // Add your logic to handle the "View Invoice" button action
        System.out.println("View Invoice button clicked");
        for(InvoiceInformation i : this.invoices)
        {
            if(i.getNo().toLowerCase().equals(this.selectedInvoiceInformationTableData.getNo().toLowerCase()))
            {
                this.invoiceViewController.initData(this.client,this.admin,i);
                this.sceneController.setScene(this.INVOICE_VIEW.getTitle(),this.INVOICE_VIEW.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                System.out.println(i.getNo());
            }
        }
    }

    @FXML
    private void updateInvoice(ActionEvent event) throws IOException {
        // Add your logic to handle the "Update Invoice" button action
        System.out.println("Update Invoice button clicked");
        for(InvoiceInformation i : this.invoices)
        {
            if(i.getNo().toLowerCase().equals(this.selectedInvoiceInformationTableData.getNo().toLowerCase()))
            {
                this.invoiceUpdateController.initData(this.client,this.admin,i);
                this.sceneController.setScene(this.INVOICE_UPDATE.getTitle(),this.INVOICE_UPDATE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                System.out.println(i.getNo());
            }
        }
    }

    @FXML
    private void deleteInvoice(ActionEvent event) throws IOException {
        // Add your logic to handle the "Update Invoice" button action
        System.out.println("Update Invoice button clicked");
        for(InvoiceInformation i : this.invoices)
        {
            if(i.getNo().toLowerCase().equals(this.selectedInvoiceInformationTableData.getNo().toLowerCase()))
            {
                this.invoiceDeleteController.initData(this.client,this.admin,i);
                this.sceneController.setScene(this.INVOICE_DELETE.getTitle(),this.INVOICE_DELETE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                System.out.println(i.getNo());
            }
        }
    }

    @FXML
    private void registerInvoice(ActionEvent event) throws IOException {
        // Add your logic to handle the "Register Invoice" button action
        System.out.println("Register Invoice button clicked");

                this.invoiceRegisterController.initData(this.client,this.admin);
                this.sceneController.setScene(this.INVOICE_REGISTER.getTitle(),this.INVOICE_REGISTER.getFxmlFilePath());
                this.sceneController.switchToScene(event);
    }


    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        this.sceneController.setScene(this.CLIENT_MENU.getTitle(), this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Back button clicked");
    }

    void initData(Client client, Admin admin) {
        this.admin = admin;
        this.client = client;
    }

}