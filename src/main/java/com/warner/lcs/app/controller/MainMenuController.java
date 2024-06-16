package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;

import java.io.IOException;

@Component
public class MainMenuController {

    private static final String PREF_KEY = "admin";

    @Autowired
    private LcsService lcsService;

    private FxmlView CLIENT_MENU,INVOICE_MENU, MAIN_MENU, TREATMENT_MENU;

    @Autowired
    private SceneController sceneController;

    @Autowired
    private TreatmentMenuController treatmentMenuController;

    @FXML
    private Button clientsButton;

    @FXML
    private Button invoicesButton;

    @FXML
    private Button logoutButton;

    private Admin admin;



    public MainMenuController() {
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.INVOICE_MENU = FxmlView.INVOICE_MAIN_MENU;
        this.TREATMENT_MENU = FxmlView.TREATMENT_MENU;
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
    private void handleTreatments(ActionEvent event) throws Exception {
        this.treatmentMenuController.initData(this.admin);
        this.sceneController.setScene(this.TREATMENT_MENU.getTitle(),this.TREATMENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void handleInvoicesButtonClick(ActionEvent event) throws Exception {
        // Add action for handling the "Invoices" button click
        this.sceneController.setScene(this.INVOICE_MENU.getTitle(),this.INVOICE_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Invoices button clicked");
    }

    @FXML
    private void handleLogoutButtonClick(ActionEvent event) {
        // Add action for handling the "Logout" button click
        System.out.println("Logout button clicked");
    }

    void initData(Admin admin)
    {
        this.admin = admin;
    }
}