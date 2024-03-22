package com.warner.lcs.common.util;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String sceneTitle;
    private File file;
    private URL url;
    private FXMLLoader fxmlLoader;

    public SceneController() throws MalformedURLException {}

    public void switchToScene(ActionEvent event) throws IOException {
        root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(this.sceneTitle);
        stage.show();
    }

    public void setScene(String sceneTitle,String filePath){
        this.sceneTitle = sceneTitle;
        this.file = new File(filePath);
        URL url = null;
        try {
            url = file.toURI().toURL();
            fxmlLoader = new FXMLLoader(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}

