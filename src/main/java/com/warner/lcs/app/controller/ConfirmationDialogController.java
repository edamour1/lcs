package com.warner.lcs.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ConfirmationDialogController {

    private boolean confirmed = false;

    @FXML
    private void confirmDelete(ActionEvent event) {
        confirmed = true;
        closeDialog(event);
    }

    @FXML
    private void cancelDelete(ActionEvent event) {
        closeDialog(event);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private void closeDialog(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
