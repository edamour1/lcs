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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AdditionalCostServiceRegisterController implements Initializable  {
    private Admin admin;

    private FxmlView CLIENT_MENU, TREATMENT_MENU;

    private AdditionalCostService additionalCostService;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView treatmentTableView;

    @FXML
    private TextField serviceName;

    @FXML
    private TextField serviceDescription;

    @FXML
    private TextField price;

    @FXML
    private Label serviceNameErrorLabel;

    @FXML
    private Label serviceDescriptionErrorLabel;

    @FXML
    private Label priceErrorLabel;

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    public Admin getAdmin() {
        return admin;
    }

    void initData(Admin admin)
    {
        this.admin = admin;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.TREATMENT_MENU = FxmlView.TREATMENT_MENU;
        this.serviceName.setText("");
        this.serviceDescription.setText("");
        this.price.setText("");
        this.serviceNameErrorLabel.setText("");
        this.serviceDescriptionErrorLabel.setText("");
        this.priceErrorLabel.setText("");
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        // Handle the back action
        this.sceneController.setScene(this.TREATMENT_MENU.getTitle(),this.TREATMENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Back button clicked");

        // Add your code to go back to the previous scene or close the form
    }

    public static boolean isDouble(String s) {
        try {//if there is no error then return ture becuase no error mean it's in the correct format
            //i.e. "8.79" correct
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {//if there is an error means s is in incorrect format
            //i.e. "9rU65.0ty"
            return false;
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) throws Exception {
        // Handle the submit action
        String name = serviceName.getText();
        String description = serviceDescription.getText();
        String priceValue = price.getText();


        if(name.equals("") || name.equals(" ") || name == null)
        {
            showError(this.serviceName, "Please enter a treatment name.",this.serviceNameErrorLabel);
        }
        else
        {
            clearError(this.serviceName,this.serviceNameErrorLabel);
        }

        if(description.equals("") || description.equals(" ") || description == null)
        {
            showError(this.serviceDescription,"Please enter a description for the treatment.",this.serviceDescriptionErrorLabel );
        }
        else
        {
            clearError(this.serviceDescription,this.serviceDescriptionErrorLabel);
        }

        if(priceValue.equals("") || priceValue.equals(" ") || priceValue == null || !isDouble(priceValue))
        {
            showError(this.price,"Please enter a price for the treatment. example: 89.87",this.priceErrorLabel );
        }
        else
        {
            clearError(this.price,this.priceErrorLabel);
        }

        AdditionalCostService additionalCostService = new AdditionalCostService();

        additionalCostService.setTreatmentName(name);
        additionalCostService.setTreatmentDescription(description);
        additionalCostService.setPrice(Double.valueOf(priceValue));

        this.lcsService.saveAdditionalCostService(additionalCostService,this.admin);

        this.sceneController.setScene(this.TREATMENT_MENU.getTitle(),this.TREATMENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
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
}
