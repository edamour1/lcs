package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.AdditionalCostService;
import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Treatment;
import com.warner.lcs.app.domain.table_ui.AdditionalCostServiceTableData;
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
import static com.warner.lcs.common.util.TableUtil.createTableColumn;
import static com.warner.lcs.common.util.TableUtil.getObservableList;

@Component
public class TreatmentMenuController implements Initializable {

    private FxmlView MAIN_MENU, TREATMENT_VIEW, TREATMENT_REGISTER, TREATMENT_UPDATE, TREATMENT_DELETE, ADDITIONAL_COST_SERVICES_VIEW, ADDITIONAL_COST_SERVICES_DELETE, ADDITIONAL_COST_SERVICES_UPDATE, ADDITIONAL_COST_SERVICES_REGISTER;

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView treatmentTableView;

    @FXML
    private TableView additionalCostServiceTableView;

    @Autowired
    private TreatmentViewController treatmentViewController;

    @Autowired
    private TreatmentUpdateController treatmentUpdateController;

    @Autowired
    private TreatmentRegisterController treatmentRegisterController;

    @Autowired
    private TreatmentDeleteController treatmentDeleteController;

    @Autowired
    private AdditionalCostServiceRegisterController additionalCostServiceRegisterController;

    @Autowired
    private AdditionalCostServiceUpdateController additionalCostServiceUpdateController;

    @Autowired
    private AdditionalCostServiceDeleteController additionalCostServiceDeleteController;

    @Autowired
    private AdditionalCostServiceViewController additionalCostServiceViewController;

    private List<Treatment> treatmentsList;
    private List<AdditionalCostService> additionalCostServiceList;

    private  TreatmentTableData selectedTreatment;

    private AdditionalCostServiceTableData selectedAdditionalCostService;

    private Admin admin;

    private static final String PREF_KEY = "admin";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.treatmentsList = this.lcsService.getAllTreatments();
            this.additionalCostServiceList = this.lcsService.getAllAdditionalCostServices();

            this.MAIN_MENU = FxmlView.MAIN_MENU;
            this.TREATMENT_VIEW = FxmlView.TREATMENT_VIEW;
            this.TREATMENT_REGISTER = FxmlView.TREATMENT_REGISTER;
            this.TREATMENT_UPDATE = FxmlView.TREATMENT_UPDATE;
            this.TREATMENT_DELETE = FxmlView.TREATMENT_DELETE;
            this.ADDITIONAL_COST_SERVICES_REGISTER = FxmlView.ADDITIONAL_COST_SERVICES_REGISTER;
            this.ADDITIONAL_COST_SERVICES_UPDATE = FxmlView.ADDITIONAL_COST_SERVICES_UPDATE;
            this.ADDITIONAL_COST_SERVICES_DELETE = FxmlView.ADDITIONAL_COST_SERVICES_DELETE;
            this.ADDITIONAL_COST_SERVICES_VIEW = FxmlView.ADDITIONAL_COST_SERVICES_VIEW;

            //*********************************************treatments table**********************************************
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

                // Create a TableView with a list of treatmeants
                this.treatmentTableView.getColumns().addAll(idColumn,treatmentNameColumn,treatmentDescriptionColumn,priceColumn);
                this.treatmentTableView.setItems(observableStringList);

                // Add listener to handle row selection
                this.treatmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        this.selectedTreatment = (TreatmentTableData) newSelection;
                    }
                });
            }

            //*********************************************services table**********************************************
            if(!this.additionalCostServiceList.isEmpty()) {

                List<AdditionalCostServiceTableData> servicesList = new ArrayList<>();

                for(AdditionalCostService currentService : this.additionalCostServiceList)
                {
                    servicesList.add(new AdditionalCostServiceTableData(
                                    currentService.getId(),
                                    currentService.getTreatmentName(),
                                    currentService.getTreatmentDescription(),
                                    currentService.getUnit(),
                                    currentService.getPrice(),
                                    currentService.getQty()
                            )
                    );
                }

                TableColumn<AdditionalCostServiceTableData, Integer> idColumn = createTableColumn("Id", "id");
                TableColumn<AdditionalCostServiceTableData, String> treatmentNameColumn = createTableColumn("Service Name", "treatmentName");
                TableColumn<AdditionalCostServiceTableData, String> treatmentDescriptionColumn = createTableColumn("Service Description", "treatmentDescription");
                TableColumn<AdditionalCostServiceTableData, Double> priceColumn = createTableColumn("Price", "price");

                // Converting the list of strings to an observable list of strings
                ObservableList<AdditionalCostServiceTableData> observableStringList = getObservableList(servicesList);

                // Create a TableView with a list of additional cost services
                this.additionalCostServiceTableView.getColumns().addAll(idColumn,treatmentNameColumn,treatmentDescriptionColumn,priceColumn);
                this.additionalCostServiceTableView.setItems(observableStringList);

                // Add listener to handle row selection
                this.additionalCostServiceTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        this.selectedAdditionalCostService = (AdditionalCostServiceTableData) newSelection;
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
        Treatment selectedTreatment;

        for(int i = 0; i < this.treatmentsList.size(); i++)
        {
            selectedTreatment = this.treatmentsList.get(i);
            if(this.selectedTreatment.getId() == selectedTreatment.getId())
            {
                this.treatmentDeleteController.initData(this.admin,selectedTreatment);
                this.sceneController.setScene(this.TREATMENT_DELETE.getTitle(),this.TREATMENT_DELETE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                break;
            }
        }
    }

    @FXML
    private void registerTreatment(ActionEvent event) throws Exception {
        this.treatmentRegisterController.initData(this.admin);
        // Add your logic to view the selected client
        this.sceneController.setScene(this.TREATMENT_REGISTER.getTitle(), this.TREATMENT_REGISTER.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void registerService(ActionEvent event) throws Exception {
        this.additionalCostServiceRegisterController.initData(this.admin);
        // Add your logic to view the selected client
        this.sceneController.setScene(this.ADDITIONAL_COST_SERVICES_REGISTER.getTitle(), this.ADDITIONAL_COST_SERVICES_REGISTER.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void updateService(ActionEvent event) throws Exception {
        AdditionalCostService slectedAdditionalCostService;
        for(int i = 0; i < this.additionalCostServiceList.size(); i++)
        {
            slectedAdditionalCostService = this.additionalCostServiceList.get(i);
            if(this.selectedAdditionalCostService.getId() == slectedAdditionalCostService.getId())
            {
                this.additionalCostServiceUpdateController.initData(this.admin,slectedAdditionalCostService);
                this.sceneController.setScene(this.ADDITIONAL_COST_SERVICES_UPDATE.getTitle(),this.ADDITIONAL_COST_SERVICES_UPDATE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                break;
            }
        }

        
    }

    @FXML
    private void deleteService(ActionEvent event) throws Exception {
        AdditionalCostService selectedAdditionalCostService;

        for(int i = 0; i < this.additionalCostServiceList.size(); i++)
        {
            selectedAdditionalCostService = this.additionalCostServiceList.get(i);
            if(this.selectedAdditionalCostService.getId() == selectedAdditionalCostService.getId())
            {
                this.additionalCostServiceDeleteController.initData(this.admin,selectedAdditionalCostService);
                this.sceneController.setScene(this.ADDITIONAL_COST_SERVICES_DELETE.getTitle(),this.ADDITIONAL_COST_SERVICES_DELETE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                break;
            }
        }
    }

    @FXML
    private void viewService(ActionEvent event) throws Exception {
        AdditionalCostService viewAdditionalCostService = new AdditionalCostService();

        for(AdditionalCostService additionalCostService : this.additionalCostServiceList) {
            if(additionalCostService.getId() == this.selectedAdditionalCostService.getId())
            {
                viewAdditionalCostService = additionalCostService;
            }
        }

        if (selectedAdditionalCostService != null) {
            this.additionalCostServiceViewController.initData(viewAdditionalCostService);
            // Add your logic to view the selected client
            this.sceneController.setScene(this.ADDITIONAL_COST_SERVICES_VIEW.getTitle(), this.ADDITIONAL_COST_SERVICES_VIEW.getFxmlFilePath());
            this.sceneController.switchToScene(event);
        } else {
            System.out.println("No client selected.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        // Handle the back action
        this.sceneController.setScene(this.MAIN_MENU.getTitle(),this.MAIN_MENU.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    void initData(Admin admin)
    {
        this.admin = admin;
    }
}