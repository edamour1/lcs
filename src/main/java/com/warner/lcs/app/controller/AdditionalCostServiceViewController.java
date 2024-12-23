package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.AdditionalCostService;
import com.warner.lcs.app.domain.Treatment;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AdditionalCostServiceViewController implements Initializable {
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

    private AdditionalCostService additionalCostService;

    private FxmlView TREATMENT_MENU;


    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        // Handle the back action
        this.sceneController.setScene(this.TREATMENT_MENU.getTitle(),this.TREATMENT_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.TREATMENT_MENU = FxmlView.TREATMENT_MENU;

        this.treatmentNo.setText(Integer.toString(this.additionalCostService.getId()));
        this.treatmentName.setText(this.additionalCostService.getTreatmentName());
        this.treatmentDescription.setText(this.additionalCostService.getTreatmentDescription());
        this.price.setText(Double.toString(this.additionalCostService.getPrice()));
    }

    void initData(AdditionalCostService additionalCostService)
    {
        this.additionalCostService = additionalCostService;
    }
}
