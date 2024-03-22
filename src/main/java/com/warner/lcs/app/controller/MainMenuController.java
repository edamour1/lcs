package com.warner.lcs.app.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;

@Component
public class MainMenuController {

    @FXML
    private Menu fileMenu;

    @FXML
    private Menu languageMenu;

    @FXML
    private CheckMenuItem FXMenuItem;

    @FXML
    private void handleClientsButtonClick(ActionEvent event) {
        // Add action for handling the "Clients" button click
        System.out.println("Clients button clicked");
    }

    @FXML
    private void handleInvoicesButtonClick(ActionEvent event) {
        // Add action for handling the "Invoices" button click
        System.out.println("Invoices button clicked");
    }

    @FXML
    private void handleLogoutButtonClick(ActionEvent event) {
        // Add action for handling the "Logout" button click
        System.out.println("Logout button clicked");
    }

}

