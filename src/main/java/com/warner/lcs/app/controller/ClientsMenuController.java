package com.warner.lcs.app.controller;


import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsMenuController {

//    @FXML
//    private TableView<Person> tableView;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // Add prepopulated data to the table
//        tableView.getItems().addAll(
//                new Person("John", "25"),
//                new Person("Alice", "30"),
//                new Person("Bob", "40")
//        );
//
//        // Create button column
//        TableColumn<Person, Button> buttonColumn = new TableColumn<>("Button");
//        buttonColumn.setCellValueFactory(param -> new SimpleStringProperty("Click Me"));
//        buttonColumn.setCellFactory(col -> new ButtonCell());
//        tableView.getColumns().add(buttonColumn);
//    }
//
//    // Class representing a Person
//    public static class Person {
//        private final SimpleStringProperty name;
//        private final SimpleStringProperty age;
//
//        public Person(String name, String age) {
//            this.name = new SimpleStringProperty(name);
//            this.age = new SimpleStringProperty(age);
//        }
//
//        public String getName() {
//            return name.get();
//        }
//
//        public String getAge() {
//            return age.get();
//        }
//    }
//
//    // Custom cell factory for the button column
//    private class ButtonCell extends TableCell<Person, Button> {
//        private final Button button = new Button("Print");
//
//        ButtonCell() {
//            button.setOnAction(event -> {
//                Person person = getTableView().getItems().get(getIndex());
//                System.out.println("Name: " + person.getName() + ", Age: " + person.getAge());
//            });
//        }
//
//        @Override
//        protected void updateItem(Button item, boolean empty) {
//            super.updateItem(item, empty);
//            if (!empty) {
//                setGraphic(button);
//            } else {
//                setGraphic(null);
//            }
//        }
//    }


}
