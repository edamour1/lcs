package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.domain.InvoiceInformation;
import com.warner.lcs.app.domain.table_ui.InvoiceInformationTableData;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.warner.lcs.common.util.TableUtil.createTableColumn;
import static com.warner.lcs.common.util.TableUtil.getObservableList;

@Component
public class InvoiceMenuController implements Initializable {
    private Admin admin;

    private Client client;

    private FxmlView CLIENT_MENU, INVOICE_REGISTER, INVOICE_VIEW, INVOICE_DELETE, INVOICE_UPDATE;

    @FXML
    private ListView<String> emailQueListView;
    private List<InvoiceInformation> invoices;
    @FXML
    private Button emailButton;
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

    @FXML
    private TableView<InvoiceInformationTableData> invoicesTableView;

    private InvoiceInformationTableData selectedInvoiceInformationTableData;

    @FXML
    private AnchorPane anchorPane;

    private SortedList<InvoiceInformationTableData> invoiceSortedData;

    private ObservableList<InvoiceInformationTableData> observableStringList;
    private ObservableList<InvoiceInformationTableData> completeObservableStringList;

    @FXML
    private // Create a ScrollPane
    ScrollPane scrollPane;


    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    @Autowired
    private InvoiceUpdateController invoiceUpdateController;

    @Autowired
    private InvoiceViewController invoiceViewController;

    @Autowired
    private InvoiceDeleteController invoiceDeleteController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.invoices = this.lcsService.getAllInvoiceInformations();
            this.CLIENT_MENU = FxmlView.CLIENT_MENU;
            this.INVOICE_VIEW = FxmlView.INVOICE_VIEW;
            this.INVOICE_UPDATE = FxmlView.INVOICE_UPDATE;
            this.INVOICE_DELETE = FxmlView.INVOICE_DELETE;

            //*********************************************invoice table**********************************************
            if(!invoices.isEmpty())
            {
                Client currentClient;
                List<InvoiceInformationTableData> invoiceList = new ArrayList<>();
                for(InvoiceInformation invoice : invoices)
                {
                    currentClient = this.lcsService.getClientById(invoice.getClientId());
                    invoiceList.add(new InvoiceInformationTableData(invoice.getPaymentDueDate().toString(),
                            invoice.getStartDate().toString(),
                            invoice.getDate().toString(),
                            Integer.valueOf(invoice.getTreatments().size()),
                            Integer.valueOf(invoice.getAdditionalCostServices().size()),
                            invoice.getNotes(),
                            invoice.getNo(),
                            currentClient.getFirstName(),
                            currentClient.getLastName()));
                }

                TableColumn<InvoiceInformationTableData, String> paymentDueDateColumn = createTableColumn("Payment Due Date", "paymentDueDate");
                TableColumn<InvoiceInformationTableData, String> startDateColumn = createTableColumn("Start Date", "startDate");
                TableColumn<InvoiceInformationTableData, String> dateColumn = createTableColumn("Created On Date", "date");
                TableColumn<InvoiceInformationTableData, String> notesColumn = createTableColumn("Notes", "notes");
                TableColumn<InvoiceInformationTableData, String> noColumn = createTableColumn("Inovice No.", "no");
                TableColumn<InvoiceInformationTableData, String> firstNameColumn = createTableColumn("First Name", "firstName");
                TableColumn<InvoiceInformationTableData, String> lastNameColumn = createTableColumn("Last Name", "lastName");


                // Converting the list of strings to an observable list of strings
                observableStringList = getObservableList(invoiceList);
                this.completeObservableStringList = observableStringList;

                // Create a TableView with a list of invoices
                invoicesTableView.getColumns().addAll(firstNameColumn,lastNameColumn,noColumn,dateColumn,paymentDueDateColumn,startDateColumn,notesColumn);

                invoicesTableView.setItems(observableStringList);
            }

            // Add listener to handle row selection
            invoicesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
            invoiceSortedData = new SortedList<>(filteredInvoiceData);

            // Bind the SortedList comparator to the TableView comparator
            invoiceSortedData.comparatorProperty().bind(invoicesTableView.comparatorProperty());

//            zoom

            // Add sorted (and filtered) data to the TableView
            invoicesTableView.setItems(invoiceSortedData);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEmail(ActionEvent actionEvent)
    {

        ObservableList<InvoiceInformationTableData> selectedItems = invoicesTableView.getSelectionModel().getSelectedItems();
        this.observableStringList.remove(selectedItems.get(0));

        for(InvoiceInformationTableData i : selectedItems)
        {
            System.out.println(i.getNo());
        }
    }

    @FXML
    private void viewInvoice(ActionEvent event) throws Exception {
        // Add your logic to handle the "View Invoice" button action
        System.out.println("View Invoice button clicked");
        ObservableList<InvoiceInformationTableData> selectedItems = invoicesTableView.getSelectionModel().getSelectedItems();
        this.selectedInvoiceInformationTableData = selectedItems.get(0);
        for(InvoiceInformation i : this.invoices)
        {
            if(i.getNo().toLowerCase().equals(this.selectedInvoiceInformationTableData.getNo().toLowerCase()))
            {
                this.client = this.lcsService.getClientById(i.getClientId());
                this.invoiceViewController.initData(this.client,this.admin,i);
                this.sceneController.setScene(this.INVOICE_VIEW.getTitle(),this.INVOICE_VIEW.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                System.out.println(i.getNo());
            }
        }
    }

    @FXML
    private void updateInvoice(ActionEvent event) throws Exception {
        // Add your logic to handle the "Update Invoice" button action
        System.out.println("Update Invoice button clicked");
        ObservableList<InvoiceInformationTableData> selectedItems = invoicesTableView.getSelectionModel().getSelectedItems();
        this.selectedInvoiceInformationTableData = selectedItems.get(0);
        for(InvoiceInformation i : this.invoices)
        {
            if(i.getNo().toLowerCase().equals(this.selectedInvoiceInformationTableData.getNo().toLowerCase()))
            {
                this.client = this.lcsService.getClientById(i.getClientId());
                this.invoiceUpdateController.initData(this.client,this.admin,i);
                this.sceneController.setScene(this.INVOICE_UPDATE.getTitle(),this.INVOICE_UPDATE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                System.out.println(i.getNo());
            }
        }
    }

    @FXML
    private void deleteInvoice(ActionEvent event) throws Exception {
        // Add your logic to handle the "Update Invoice" button action
        System.out.println("Update Invoice button clicked");
        ObservableList<InvoiceInformationTableData> selectedItems = invoicesTableView.getSelectionModel().getSelectedItems();
        this.selectedInvoiceInformationTableData = selectedItems.get(0);
        for(InvoiceInformation i : this.invoices)
        {
            if(i.getNo().toLowerCase().equals(this.selectedInvoiceInformationTableData.getNo().toLowerCase()))
            {
                this.client = this.lcsService.getClientById(i.getClientId());
                this.invoiceDeleteController.initData(this.client,this.admin,i);
                this.sceneController.setScene(this.INVOICE_DELETE.getTitle(),this.INVOICE_DELETE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                System.out.println(i.getNo());
            }
        }
    }

    @FXML
    private void handleEmailQue(ActionEvent event)
    {
//        List<InvoiceInformationTableData> selectedItems = new ArrayList<>(invoicesTableView.getSelectionModel().getSelectedItems());
        ObservableList<InvoiceInformationTableData> selectedItems = invoicesTableView.getSelectionModel().getSelectedItems();
        int stopAt = selectedItems.size();
        for(int i = 0; i < stopAt; i++)
        {
            this.observableStringList.remove(selectedItems.get(i));
        }

        this.invoicesTableView.setItems(this.observableStringList);
    }
    void initData(Client client) {
        this.admin = admin;
    }
}