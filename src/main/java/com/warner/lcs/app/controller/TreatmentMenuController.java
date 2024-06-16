package com.warner.lcs.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Treatment;
import com.warner.lcs.app.domain.table_ui.TreatmentTableData;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.warner.lcs.common.util.TableUtil.createTableColumn;
import static com.warner.lcs.common.util.TableUtil.getObservableList;

@Component
public class TreatmentMenuController implements Initializable {

    private FxmlView CLIENT_MENU, TREATMENT_VIEW, TREATMENT_REGISTER, TREATMENT_UPDATE, TREATMENT_DELETE;

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView treatmentTableView;

    @Autowired
    private TreatmentViewController treatmentViewController;

    @Autowired
    private TreatmentUpdateController treatmentUpdateController;

    @Autowired
    private TreatmentRegisterController treatmentRegisterController;

    private List<Treatment> treatmentsList;

    private  TreatmentTableData selectedTreatment;

    private Admin admin;

    private static final String PREF_KEY = "admin";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.treatmentsList = this.lcsService.getAllTreatments();
            this.CLIENT_MENU = FxmlView.CLIENT_MENU;
            this.TREATMENT_VIEW = FxmlView.TREATMENT_VIEW;
            this.TREATMENT_REGISTER = FxmlView.TREATMENT_REGISTER;
            this.TREATMENT_UPDATE = FxmlView.TREATMENT_UPDATE;
            this.TREATMENT_DELETE = FxmlView.TREATMENT_DELETE;

            //*********************************************invoice table**********************************************
            if(!this.treatmentsList.isEmpty())
            {
                List<TreatmentTableData> treatmentsList = new ArrayList<>();
                for(Treatment currentTreatment : this.treatmentsList)
                {
                    treatmentsList.add(new TreatmentTableData(
                            currentTreatment.getId(),
                            currentTreatment.getTreatmentName(),
                            currentTreatment.getTreatmentDescription(),
                            currentTreatment.getUnit(),
                            currentTreatment.getPrice(),
                            currentTreatment.getQty()
                            )
                    );
                }

                TableColumn<TreatmentTableData, Integer> idColumn = createTableColumn("Id", "id");
                TableColumn<TreatmentTableData, String> treatmentNameColumn = createTableColumn("Treatment Name", "treatmentName");
                TableColumn<TreatmentTableData, String> treatmentDescriptionColumn = createTableColumn("Treatment Description", "treatmentDescription");
                TableColumn<TreatmentTableData, Double> priceColumn = createTableColumn("Price", "price");

                // Converting the list of strings to an observable list of strings
                ObservableList<TreatmentTableData> observableStringList = getObservableList(treatmentsList);

                // Create a TableView with a list of invoices
                this.treatmentTableView.getColumns().addAll(idColumn,treatmentNameColumn,treatmentDescriptionColumn,priceColumn);
                this.treatmentTableView.setItems(observableStringList);

                // Add listener to handle row selection
                this.treatmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        this.selectedTreatment = (TreatmentTableData) newSelection;
                    }
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void viewTreatment(ActionEvent event) throws Exception {
        Treatment viewTreatment = new Treatment();

        for(Treatment treatment : this.treatmentsList) {
            if(treatment.getId() == this.selectedTreatment.getId())
            {
                viewTreatment = treatment;
            }
        }

        if (selectedTreatment != null) {
            this.treatmentViewController.initData(viewTreatment);
            // Add your logic to view the selected client
            this.sceneController.setScene(this.TREATMENT_VIEW.getTitle(), this.TREATMENT_VIEW.getFxmlFilePath());
            this.sceneController.switchToScene(event);
        } else {
            System.out.println("No client selected.");
        }
    }

    @FXML
    private void updateTreatment(ActionEvent event) throws Exception {
        Treatment selectedTreatment;

        for(int i = 0; i < this.treatmentsList.size(); i++)
        {
            selectedTreatment = this.treatmentsList.get(i);
            if(this.selectedTreatment.getId() == selectedTreatment.getId())
            {
                this.treatmentUpdateController.initData(this.admin,selectedTreatment);
                this.sceneController.setScene(this.TREATMENT_UPDATE.getTitle(),this.TREATMENT_UPDATE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                break;
            }
        }

    }

    @FXML
    private void deleteTreatment(ActionEvent event) throws Exception {

    }

    @FXML
    private void registerTreatment(ActionEvent event) throws Exception {
        this.treatmentRegisterController.initData(this.admin);
        // Add your logic to view the selected client
        this.sceneController.setScene(this.TREATMENT_REGISTER.getTitle(), this.TREATMENT_REGISTER.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    void initData(Admin admin)
    {
        this.admin = admin;
    }
}