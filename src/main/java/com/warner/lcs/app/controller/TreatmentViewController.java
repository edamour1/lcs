package com.warner.lcs.app.controller;

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
public class TreatmentViewController implements Initializable {

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

    private Treatment treatment;

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

        this.treatmentNo.setText(Integer.toString(this.treatment.getId()));
        this.treatmentName.setText(this.treatment.getTreatmentName());
        this.treatmentDescription.setText(this.treatment.getTreatmentDescription());
        this.price.setText(Double.toString(this.treatment.getPrice()));
    }

    void initData(Treatment treatment)
    {
        this.treatment = treatment;
    }
}
