package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.domain.table_ui.AdminTableData;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.warner.lcs.common.util.TableUtil.createTableColumn;
import static com.warner.lcs.common.util.TableUtil.getObservableList;

@Component
public class AdminMenuController implements Initializable {

    private FxmlView MAIN_MENU, ADMIN_VIEW, ADMIN_DELETE, ADMIN_UPDATE, ADMIN_REGISTER;

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView tableView;

    @Autowired
    private AdminViewController adminViewController;

    @Autowired
    private AdminUpdateController adminUpdateController;

    @Autowired
    private AdminRegisterController adminRegisterController;

    @Autowired
    private AdminDeleteController adminDeleteController;

    private List<Admin> adminList;

    private AdminTableData selectedAdmin;

    private Admin admin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.adminList = this.lcsService.getAllAdmin();
            this.MAIN_MENU = FxmlView.MAIN_MENU;
            this.ADMIN_REGISTER = FxmlView.ADMIN_REGISTER;
            this.ADMIN_UPDATE = FxmlView.ADMIN_UPDATE;
            this.ADMIN_DELETE = FxmlView.ADMIN_DELETE;
            this.ADMIN_VIEW = FxmlView.ADMIN_VIEW;

            List<Admin> list = null;
            try {
                list = lcsService.getAllAdmin();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //*********************************************admin table**********************************************
            if(!this.adminList.isEmpty())
            {
                List<AdminTableData> adminList = new ArrayList<>();
                for(Admin admin : this.adminList)
                {
                    adminList.add(new AdminTableData(
                                    admin.getId(),
                                    admin.getUsername(),
                                    admin.getPassword(),
                                    admin.getRole(),
                                    admin.getHint())
                    );
                }
                TableColumn<AdminTableData, Integer> idColumn = createTableColumn("Id", "id");
                TableColumn<AdminTableData, String> usernameColumn = createTableColumn("Username", "username");
                TableColumn<AdminTableData, String> roleColumn = createTableColumn("Role", "role");

                // Converting the list of strings to an observable list of strings
                ObservableList<AdminTableData> observableStringList = getObservableList(adminList);
                this.tableView.setItems(observableStringList);
                this.tableView.getColumns().addAll(idColumn,usernameColumn,roleColumn);

                this.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        this.selectedAdmin = (AdminTableData) newSelection;
                    }
                });


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void viewAdmin(ActionEvent event)throws Exception {
        Admin viewAdmin = new Admin();

        for(Admin admin : this.adminList) {
            if(admin.getId() == this.selectedAdmin.getId())
            {
                viewAdmin = admin;
            }
        }


        if (selectedAdmin != null) {
            this.adminViewController.initData(this.admin,viewAdmin);
            // Add your logic to view the selected client
            this.sceneController.setScene(this.ADMIN_VIEW.getTitle(), this.ADMIN_VIEW.getFxmlFilePath());
            this.sceneController.switchToScene(event);
        } else {
            System.out.println("No client selected.");
        }
    }

    @FXML
    private void updateAdmin(ActionEvent event) throws Exception {
        Admin selectedAdmin;

        for(int i = 0; i < this.adminList.size(); i++)
        {
            selectedAdmin = this.adminList.get(i);
            if(this.selectedAdmin.getId() == selectedAdmin.getId())
            {
                this.adminUpdateController.initData(this.admin,selectedAdmin);
                this.sceneController.setScene(this.ADMIN_UPDATE.getTitle(),this.ADMIN_UPDATE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                break;
            }
        }

    }

    @FXML
    private void deleteAdmin(ActionEvent event) throws Exception {
        Admin selectedAdmin;

        for(int i = 0; i < this.adminList.size(); i++)
        {
            selectedAdmin = this.adminList.get(i);
            if(this.selectedAdmin.getId() == selectedAdmin.getId())
            {
                this.adminDeleteController.initData(this.admin,selectedAdmin);
                this.sceneController.setScene(this.ADMIN_DELETE.getTitle(),this.ADMIN_DELETE.getFxmlFilePath());
                this.sceneController.switchToScene(event);
                break;
            }
        }
    }

    @FXML
    private void registerAdmin(ActionEvent event) throws Exception {
        this.adminRegisterController.initData(this.admin);
        // Add your logic to view the selected client
        this.sceneController.setScene(this.ADMIN_REGISTER.getTitle(), this.ADMIN_REGISTER.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    void initData(Admin admin) {
        this.admin = admin;
    }

}
