package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.service.LcsService;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ClientViewController implements Initializable {
    @Autowired
    private LcsService lcsService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void initData(Client client) {
        System.out.println(client.getId());
    }
}
