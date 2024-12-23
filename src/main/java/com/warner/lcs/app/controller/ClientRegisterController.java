package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    @FXML
    private Label firstNameErrorLabel;

    @FXML
    private Label lastNameErrorLabel;

    @FXML
    private Label middleNameErrorLabel;

    @FXML
    private Label phoneNumberErrorLabel;

    @FXML
    private Label emailErrorLabel;

    private Client saveClient;

    private  Admin admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize anything if needed
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.saveClient = new Client();
    }

    @FXML
    private void submitForm(ActionEvent event) throws  Exception{
        // Perform actions with the collected data
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String middleName = middleNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();

        // Validate first name
//        if (firstName.isEmpty()) {
//            showError(firstNameTextField, "First name cannot be empty",firstNameErrorLabel);
//            return;
//        } else { clearError(firstNameTextField,firstNameErrorLabel); }
//
//        // Validate last name
//        if (lastName.isEmpty()) {
//            showError(lastNameTextField, "Last name cannot be empty",lastNameErrorLabel);
//            return;
//        } else { clearError(lastNameTextField,lastNameErrorLabel); }

        // Validate email
//        if (!isValidEmail(email)) {
//            showError(emailTextField, "Invalid email format",emailErrorLabel);
//            return;
//        } else { clearError(emailTextField,emailErrorLabel); }

        // Validate phone number
//        if (!isValidPhoneNumber(phoneNumber)) {
//            showError(phoneNumberTextField, "Invalid phone number format",phoneNumberErrorLabel);
//            return;
//        } else { clearError(phoneNumberTextField,phoneNumberErrorLabel); }
//
        this.saveClient.setActive(true);

        this.lcsService.saveClient(this.saveClient,this.admin);
        this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void goBack(ActionEvent event) throws  Exception {
        this.sceneController.setScene(this.CLIENT_MENU.getTitle(),this.CLIENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    private boolean isValidEmail(String email) {
        // Basic email validation: Check if it contains '@'
        return email.contains("@");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Phone number validation: Format "467-777-9000"
        return phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}");
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

    private void clearError(TextField textField,Label errorLabel)
    {
        // Clear CSS style to indicate no error
        textField.getStyleClass().remove("invalid-text-field");
        textField.setPromptText(null);
        // Clear error message
        errorLabel.setText("");
    }

    private void clearErrorMessages()
    {
        // Remove CSS style and clear prompt text for all text fields
        firstNameTextField.getStyleClass().remove("invalid-text-field");
        firstNameTextField.setPromptText(null);
        lastNameTextField.getStyleClass().remove("invalid-text-field");
        lastNameTextField.setPromptText(null);
        emailTextField.getStyleClass().remove("invalid-text-field");
        emailTextField.setPromptText(null);
        phoneNumberTextField.getStyleClass().remove("invalid-text-field");
        phoneNumberTextField.setPromptText(null);
    }

    void initData(Admin admin) {
        this.admin = admin;
    }
}
