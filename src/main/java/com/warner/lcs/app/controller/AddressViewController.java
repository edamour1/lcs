package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Address;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddressViewController implements Initializable {

    private Client client;
    private Address address;
    private Admin admin;
    private FxmlView CLIENT_MENU, CLIENT_VIEW;

    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;
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

    @FXML
    private Text streetParagraph;

    @FXML
    private Text cityParagraph;

    @FXML
    private Text stateParagraph;

    @FXML
    private Text zipcodeParagraph;
    @FXML
    private Text lmUserParagraph;

    @FXML
    private Text lmDateParagraph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
        this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        // Initialize paragraph tags with client information
        if (client != null) {
            clientFirstNameParagraph.setText(client.getFirstName());
            clientLastNameParagraph.setText(client.getLastName());
            clientMiddleNameParagraph.setText(client.getMiddleName());
            clientPhoneNumberParagraph.setText(client.getPhoneNumber());
            clientEmailParagraph.setText(client.getEmail());
        }

        if (address != null) {
            streetParagraph.setText(address.getStreet());
            cityParagraph.setText(address.getCity().getCity());
            stateParagraph.setText(address.getState().getState());
            zipcodeParagraph.setText(address.getZipcode().getZipcode());
            lmUserParagraph.setText(address.getLmUser());
            lmDateParagraph.setText(address.getLmDate().toString());
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        this.sceneController.setScene(this.CLIENT_VIEW.getTitle(), this.CLIENT_VIEW.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Back button clicked");
    }

    void initData(Client client, Admin admin, Address address) {
        this.client = client;
        this.admin = admin;
        this.address = address;
    }

}
