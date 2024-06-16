package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Treatment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import javax.swing.text.TableView;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TreatmentDeleteController implements Initializable  {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView treatmentTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    void initData(Admin admin, Treatment treatment)
    {

    }
}
