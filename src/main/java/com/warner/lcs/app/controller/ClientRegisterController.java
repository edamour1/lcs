package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ClientRegisterController implements Initializable {

    private FxmlView CLIENT_MENU;
    @Autowired
    LcsService lcsService;
    @Autowired
    private SceneController sceneController;


    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField middleNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField emailTextField;

    private Client saveClient;

    private  Admin admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize anything if needed
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.saveClient = new Client();
    }

    @FXML
    private void submitForm() throws  Exception{
        // Perform actions with the collected data
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String middleName = middleNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();


        // For example, you can validate the data or send it to a service
        this.saveClient.setFirstName(firstName);
        this.saveClient.setMiddleName(middleName);
        this.saveClient.setLastName(lastName);
        this.saveClient.setEmail(email);
        this.saveClient.setPhoneNumber(phoneNumber);

        this.lcsService.saveClient(this.saveClient,this.admin);
    }

    @FXML
    private void goBack(ActionEvent event) throws  Exception{
        this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    void initData(Admin admin) {
        this.admin = admin;
    }
}
