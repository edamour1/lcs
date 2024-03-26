package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.service.LcsService;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.warner.lcs.common.util.TableUtil.createTableColumn;
import static com.warner.lcs.common.util.TableUtil.getObservableList;

@Component
public class ClientsMenuController implements Initializable {

    @Autowired
    private LcsService lcsService;

    @FXML
    private VBox root;

    @FXML
    private TableView<Client> tableView;

    @FXML
    private TextField filterTextField;

    // Field to store selected person
    private Client selectedPerson;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Client, String> idColumn = createTableColumn("Customer ID", "id");
        TableColumn<Client, String> firstNameColumn = createTableColumn("First Name", "firstName");
        TableColumn<Client, String> middleNameColumn = createTableColumn("Middle Name", "middleName");
        TableColumn<Client, String> lastNameColumn = createTableColumn("Last Name", "lastName");
        TableColumn<Client, String> emailColumn = createTableColumn("Email", "email");
        TableColumn<Client, String> phoneNumberColumn = createTableColumn("Phone", "phoneNumber");



        List<Client> list = null;
        try {
            list = lcsService.getClients();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Converting the list of strings to an observable list of strings
        ObservableList<Client> observableStringList = getObservableList(list);

        // Create a TableView with a list of persons
        tableView.setItems(observableStringList);

        // Create a TableView with a list of persons
        tableView.getColumns().addAll(idColumn,firstNameColumn,middleNameColumn,lastNameColumn,emailColumn,phoneNumberColumn);


        // Add listener to handle row selection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedPerson = newSelection;
                printSelectedPerson(selectedPerson);
            }
        });

        // Wrap the observable list in a FilteredList (initially display all data)
        FilteredList<Client> filteredData = new FilteredList<>(tableView.getItems(), p -> true);

        // Set the filter predicate whenever the filter changes
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                // If filter text is empty, display all clients
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare client's name with the filter text
                String lowerCaseFilter = newValue.toLowerCase();
                if (client.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches client's name
                }
                return false; // Filter does not match client's name
            });
        });

        // Wrap the filtered list in a SortedList
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Add sorted (and filtered) data to the TableView
        tableView.setItems(sortedData);
    }

    private void printSelectedPerson(Client person) {
        System.out.println("Selected Person: " + person.getFirstName());
    }

    // Method to handle the "View Client" button action
    @FXML
    private void viewClient() {
        if (selectedPerson != null) {
            System.out.println("Viewing Client: " + selectedPerson.getFirstName());
            // Add your logic to view the selected client
        } else {
            System.out.println("No client selected.");
        }
    }

    @FXML
    private void updateClient() {
        // Your logic for updating client
    }

    @FXML
    private void deleteClient() {
        // Your logic for deleting client
    }

    @FXML
    private void registerClient() {
        // Your logic for registering client
    }

    @FXML
    private void backToDefault() {
        // Your logic for reverting to default state
    }

}
