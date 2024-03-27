package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ClientDeleteController implements Initializable {

    private FxmlView CLIENT_MENU;
    @Autowired
    LcsService lcsService;
    @Autowired
    private SceneController sceneController;
    private Client client;
    private Admin admin;

    // Paragraph tags corresponding to client information
    @FXML
    private Text clientFirstNameParagraph;

    @FXML
    private Text clientLastNameParagraph;

    @FXML
    private Text clientMiddleNameParagraph;

    @FXML
    private Text clientPhoneNumberParagraph;

    @FXML
    private Text clientEmailParagraph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        // Initialize paragraph tags with client information
        if (client != null) {
            clientFirstNameParagraph.setText(client.getFirstName());
            clientLastNameParagraph.setText(client.getLastName());
            clientMiddleNameParagraph.setText(client.getMiddleName());
            clientPhoneNumberParagraph.setText(client.getPhoneNumber());
            clientEmailParagraph.setText(client.getEmail());
        }

    }

    @FXML
    private void submitForm(ActionEvent event) throws  Exception {
        this.lcsService.deleteClient(this.client,this.admin);
        this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void goBack(ActionEvent event) throws  Exception{
        this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }
    void initData(Client client, Admin admin) {
        this.admin = admin;
        this.client = client;
    }
}
