package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.AdditionalCostService;
import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AdminUpdateController implements Initializable {

    private FxmlView ADMIN_MENU;

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField role;

    @FXML
    private TextField hint;

    @FXML
    private Label usernameErrorLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Label roleErrorLabel;

    @FXML
    private Label hintErrorLabel;

    private Admin admin;
    private Admin adminClient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ADMIN_MENU = FxmlView.ADMIN_MENU;
        this.usernameErrorLabel.setText("");
        this.passwordErrorLabel.setText("");
        this.roleErrorLabel.setText("");
        this.hintErrorLabel.setText("");
        this.username.setText(this.admin.getUsername());
        this.password.setText(this.admin.getPassword());
        this.role.setText(this.admin.getRole());
        this.hint.setText(this.admin.getHint());
    }

    void initData(Admin adminClient, Admin admin)
    {
        this.adminClient = adminClient;
        this.admin = admin;
    }

    private void showError(TextField textField, String errorMessage, Label errorLabel) {
        // Apply CSS style to indicate error
        textField.getStyleClass().add("invalid-text-field");

        // Display error message
        // You can choose how to display the error message (e.g., tooltip, label)
        // Here, we'll set the prompt text to the error message temporarily
        textField.setPromptText(errorMessage);

        // Show error message
        errorLabel.setText(errorMessage);
    }

    private void clearError(TextField textField, Label errorLabel) {
        // Clear CSS style to indicate no error
        textField.getStyleClass().remove("invalid-text-field");
        textField.setPromptText(null);
        // Clear error message
        errorLabel.setText("");
    }

    @FXML
    private void handleSubmit(ActionEvent event) throws Exception {
        // Handle the submit action
        String username = this.username.getText();
        String password = this.password.getText();
        String role = this.role.getText();
        String hint = this.hint.getText();
        int id = this.admin.getId();

        if(username.equals("") || username.equals(" ") || username == null)
        {
            showError(this.username, "Please enter a  username.",this.usernameErrorLabel);
            return;
        }
        else
        {
            clearError(this.username,this.usernameErrorLabel);
        }

        if(password.equals("") || password.equals(" ") || password == null)
        {
            showError(this.password,"Please enter a password.",this.passwordErrorLabel );
            return;
        }
        else
        {
            clearError(this.password,this.passwordErrorLabel);
        }

        if(role.equals("") || role.equals(" ") || role == null)
        {
            showError(this.role,"Please enter a role.",this.roleErrorLabel );
            return;
        }
        else
        {
            clearError(this.role,this.roleErrorLabel);
        }

        if(hint.equals("") || hint.equals(" ") || hint == null)
        {
            showError(this.hint,"Please enter a hint.",this.hintErrorLabel );
            return;
        }
        else
        {
            clearError(this.hint,this.hintErrorLabel);
        }


        Admin admin = new Admin();
        admin.setId(id);
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRole(role);
        admin.setHint(hint);

        this.lcsService.updateAdmin(admin);

        this.sceneController.setScene(this.ADMIN_MENU.getTitle(),this.ADMIN_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        // Handle the back action
        this.sceneController.setScene(this.ADMIN_MENU.getTitle(),this.ADMIN_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }
}
