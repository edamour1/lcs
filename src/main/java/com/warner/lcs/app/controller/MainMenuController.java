package com.warner.lcs.app.controller;

import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;

@Component
public class MainMenuController {
    @Autowired
    private LcsService lcsService;

    private FxmlView CLIENT_MENU,INVOICE_MENU;

    @Autowired
    private SceneController sceneController;

    @FXML
    private Button clientsButton;

    @FXML
    private Button invoicesButton;

    @FXML
    private Button logoutButton;

    public MainMenuController() {
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
    }

    @FXML
    private void initialize() {
        // Add drop shadow effect to buttons when hovered over
        DropShadow dropShadow = new DropShadow();
        clientsButton.setOnMouseEntered(MouseEvent -> clientsButton.setEffect(dropShadow));
        invoicesButton.setOnMouseEntered(MouseEvent -> invoicesButton.setEffect(dropShadow));
        logoutButton.setOnMouseEntered(MouseEvent -> logoutButton.setEffect(dropShadow));

        // Remove drop shadow effect when mouse is no longer hovering
        clientsButton.setOnMouseExited(MouseEvent -> clientsButton.setEffect(null));
        invoicesButton.setOnMouseExited(MouseEvent -> invoicesButton.setEffect(null));
        logoutButton.setOnMouseExited(MouseEvent -> logoutButton.setEffect(null));
    }

    @FXML
    private void handleClientsButtonClick(ActionEvent event) throws Exception {
        this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);

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

