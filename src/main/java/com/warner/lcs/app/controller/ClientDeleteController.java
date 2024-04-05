package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
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

    @FXML
    private AnchorPane anchorPane;

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
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type,"");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        StringBuilder stbr = new StringBuilder("Are you sure you want to delete ");
        stbr.append(this.client.getFirstName()+" ");
        stbr.append(this.client.getMiddleName()+" ");
        stbr.append(this.client.getLastName()+"?");

        alert.getDialogPane().setContentText(stbr.toString());
        alert.getDialogPane().setHeaderText("Delete client");

        Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                this.lcsService.deleteClient(this.client,this.admin);
                this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                System.out.println("Ok Button pressed");
            } else if (result.get() == ButtonType.CANCEL) {
                System.out.println("Cancel Button pressed");
            }

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
