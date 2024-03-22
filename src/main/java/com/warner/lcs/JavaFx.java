package com.warner.lcs;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class JavaFx extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception{
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                ac -> {
                    ac.registerBean(Application.class, () -> JavaFx.this);
                    ac.registerBean(Parameters.class, this::getParameters);
                    ac.registerBean(HostServices.class, () -> getHostServices());
                };

        this.context = new SpringApplicationBuilder()
                .sources(LcsApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.context.publishEvent(new StageReadyEvent(stage));
    }

}

class StageReadyEvent extends ApplicationEvent {

    public Stage getStage() {
        return Stage.class.cast(getSource());
    }

    public StageReadyEvent(Object source) {
        super(source);
    }
}


