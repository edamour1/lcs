package com.warner.lcs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

    private final String applicationTitle;
    private File file;
    private String filePath = "src\\main\\resources\\fxml\\FXMLDocument.fxml";
    private ApplicationContext ac;

    public StageListener(@Value("${spring.application.ui.title}") String applicationTitle,
                         ApplicationContext ac)
    {
        this.applicationTitle = applicationTitle;

        this.file = new File(filePath);
        this.ac = ac;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try{
            Stage stage = stageReadyEvent.getStage();
            URL url = file.toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(ac::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,800,800);
            stage.setScene(scene);
            stage.setTitle(this.applicationTitle);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
