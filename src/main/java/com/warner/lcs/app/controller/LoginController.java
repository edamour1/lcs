package com.warner.lcs.app.controller;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Text actiontarget;

    @FXML
    private TextField usernameField; // Assuming you've defined fx:id="usernameField" in your FXML

    @FXML
    private PasswordField passwordField; // Assuming you've defined fx:id="passwordField" in your FXML

    @Autowired
    private LcsService lcsService;

    @Autowired
    private ClientsMenuController clientsMenuController;

    @Autowired
    private SceneController sceneController;

    private FxmlView fxmlView;
    private FxmlView MAIN_MENU;
    public LoginController() {
            fxmlView = FxmlView.MAIN_MENU;
        this.MAIN_MENU = fxmlView.MAIN_MENU;
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Admin admin;
        // Ensure lcsService is not null before invoking methods on it
        if (lcsService != null) {
            try {
                admin = lcsService.adminLogin(username, password);
                // Now you can use the username and password as needed
                this.clientsMenuController.initData(admin);
                sceneController.setScene(this.MAIN_MENU.getTitle(),this.MAIN_MENU.getFxmlFilePath());
                sceneController.switchToScene(event);
                // Add your logic here
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception
            }
        } else {
            System.err.println("lcsService is null. Make sure it is properly autowired.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
