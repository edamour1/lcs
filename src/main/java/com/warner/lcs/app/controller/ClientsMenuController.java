package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Contact;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ClientsMenuController {


    @FXML
    private Label title;

    @FXML
    private TableView<Contact> tvContacts;

    @FXML
    private Label response;

    public void initialize() {
        title.setText("Contact List Using a TableView");
        title.setTextFill(Color.CADETBLUE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ObservableList<Contact> contactList = FXCollections.observableArrayList(
                new Contact("Peggy", "Fisher", "717-555-1212"),
                new Contact("Jim", "Freed", "441-456-1345"),
                new Contact("Pat", "Keegan", "717-363-1432"),
                new Contact("Jane", "Slattery", "441-478-4488"),
                new Contact("Cy", "Young", "970-554-1265"),
                new Contact("Rob", "Jones", "570-655-1587"),
                new Contact("Carol", "King", "215-547-5958"),
                new Contact("Bob", "Kauffman", "215-456-6345"),
                new Contact("Gloria", "Shilling", "717-785-6092"),
                new Contact("Bill", "Sigler", "441-444-1345")
        );

        TableColumn<Contact, String> fNameColumn = new TableColumn<>("First Name");
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        fNameColumn.setPrefWidth(150); // Set preferred width

        TableColumn<Contact, String> lNameColumn = new TableColumn<>("Last Name");
        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lNameColumn.setPrefWidth(150); // Set preferred width

        TableColumn<Contact, String> cellColumn = new TableColumn<>("Cell Phone Number");
        cellColumn.setCellValueFactory(new PropertyValueFactory<>("cellPhone"));
        cellColumn.setPrefWidth(200); // Set preferred width

        tvContacts.setItems(contactList);
        tvContacts.getColumns().addAll(fNameColumn, lNameColumn, cellColumn);

        tvContacts.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> changed, Number oldVal, Number newVal) {
                int index = (int)newVal;
                response.setText("The cell number for the contact selected is " + contactList.get(index).getCellPhone());
            }
        });

        response.setFont(Font.font("Arial", 14));
    }


}
