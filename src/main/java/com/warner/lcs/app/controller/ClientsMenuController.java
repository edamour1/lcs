package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.service.LcsService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

//        Person p1 =
//                new Person(1,"Ashwin", "Sharan", LocalDate.of(2012, 10, 11));
//        Person p2 =
//                new Person(2,"Advik", "Sharan", LocalDate.of(2012, 10, 11));
//        Person p3 =
//                new Person(3,"Layne", "Estes", LocalDate.of(2011, 12, 16));
//        Person p4 =
//                new Person(4,"Mason", "Boyd", LocalDate.of(2003, 4, 20));
//        Person p5 =
//                new Person(5,"Babalu", "Sharan", LocalDate.of(1980, 1, 10));

//        List<Person> list = new ArrayList<>();
//        list.add(p1);
//        list.add(p2);
//        list.add(p3);
//        list.add(p4);
//        list.add(p5);

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
        // Add columns to the TableView
//        tableView.getColumns().addAll(
//                PersonTableUtil.getIdColumn(),
//                PersonTableUtil.getFirstNameColumn(),
//                PersonTableUtil.getLastNameColumn(),
//                PersonTableUtil.getBirthDateColumn());

        tableView.getColumns().addAll(idColumn,firstNameColumn,middleNameColumn,lastNameColumn,emailColumn,phoneNumberColumn);


        // Add listener to handle row selection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedPerson = newSelection;
                printSelectedPerson(selectedPerson);
            }
        });
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
}
