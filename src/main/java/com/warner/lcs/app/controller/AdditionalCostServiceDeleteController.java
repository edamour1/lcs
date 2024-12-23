package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.AdditionalCostService;
import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Treatment;
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

import javax.swing.text.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class AdditionalCostServiceDeleteController implements Initializable {

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    @FXML
    private Text treatmentNo;

    @FXML
    private Text treatmentName;

    @FXML
    private Text treatmentDescription;

    @FXML
    private Text price;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView treatmentTableView;

    private Admin admin;

    private AdditionalCostService additionalCostService;

    private FxmlView TREATMENT_MENU;

    @FXML
    private void handleSubmit(ActionEvent event) throws Exception
    {

        // Handle the back action
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type,"");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        StringBuilder stbr = new StringBuilder("Are you sure you want to delete ");
        stbr.append(this.additionalCostService.getTreatmentName()+" ");
        stbr.append("?");

        alert.getDialogPane().setContentText(stbr.toString());
        alert.getDialogPane().setHeaderText("Delete client");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            this.lcsService.deleteAdditionalCostService(this.additionalCostService);
            this.sceneController.setScene(this.TREATMENT_MENU.getTitle(),this.TREATMENT_MENU.getFxmlFilePath());
            this.sceneController.switchToScene(event);
            System.out.println("Ok Button pressed");
        } else if (result.get() == ButtonType.CANCEL) {
            System.out.println("Cancel Button pressed");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        // Handle the back action
        this.sceneController.setScene(this.TREATMENT_MENU.getTitle(),this.TREATMENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.TREATMENT_MENU = FxmlView.TREATMENT_MENU;

        this.treatmentNo.setText(Integer.toString(this.additionalCostService.getId()));
        this.treatmentName.setText(this.additionalCostService.getTreatmentName());
        this.treatmentDescription.setText(this.additionalCostService.getTreatmentDescription());
        this.price.setText(Double.toString(this.additionalCostService.getPrice()));
    }

    void initData(Admin admin, AdditionalCostService additionalCostService)
    {
        this.admin = admin;
        this.additionalCostService = additionalCostService;
    }
}
