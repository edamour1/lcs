package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.domain.InvoiceInformation;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class InvoiceDeleteController implements Initializable {

    private Admin admin;
    private Client client;
    private InvoiceInformation invoiceInformation;

    private FxmlView CLIENT_MENU;

    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void initData(Client client, Admin admin, InvoiceInformation invoiceInformation) {
        this.admin = admin;
        this.client = client;
        this.invoiceInformation = invoiceInformation;
    }
}
