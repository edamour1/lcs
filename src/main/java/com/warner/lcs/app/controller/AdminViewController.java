package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.AdditionalCostService;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import com.warner.lcs.app.domain.Admin;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AdminViewController implements Initializable  {

    private FxmlView ADMIN_MENU;

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Text id;

    @FXML
    private Text username;

    @FXML
    private Text password;

    @FXML
    private Text role;

    @FXML
    private Text hint;

    private Admin admin, selectedAdmin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ADMIN_MENU = FxmlView.ADMIN_MENU;
        this.id.setText(Integer.toString(this.selectedAdmin.getId()));
        this.username.setText(this.selectedAdmin.getUsername());
        this.password.setText(this.selectedAdmin.getPassword());
        this.role.setText(this.selectedAdmin.getRole());
        this.hint.setText(this.selectedAdmin.getHint());
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        // Handle the back action
        this.sceneController.setScene(this.ADMIN_MENU.getTitle(),this.ADMIN_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    void initData(Admin admin, Admin selectedAdmin)
    {
        this.admin = admin;
        this.selectedAdmin = selectedAdmin;
    }

}
