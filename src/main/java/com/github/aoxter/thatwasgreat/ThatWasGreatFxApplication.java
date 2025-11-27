package com.github.aoxter.thatwasgreat;


import com.github.aoxter.thatwasgreat.ui.config.FxmlView;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ThatWasGreatFxApplication extends Application {
    private static Stage stage;

    private ConfigurableApplicationContext applicationContext;
    private StageManager stageManager;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ThatWasGreatApplication.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stageManager = applicationContext.getBean(StageManager.class, primaryStage);
        showHomeScene();
    }

    private void showHomeScene() {
        stageManager.switchScene(FxmlView.HOME);
    }
}