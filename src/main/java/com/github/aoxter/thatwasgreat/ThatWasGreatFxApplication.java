package com.github.aoxter.thatwasgreat;


import atlantafx.base.theme.PrimerLight;
import com.github.aoxter.thatwasgreat.ui.controller.ThatWasGreatController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class ThatWasGreatFxApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private double x, y = 0;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ThatWasGreatApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        ThatWasGreatController thatWasGreatController = applicationContext.getBean(ThatWasGreatController.class, primaryStage);
        Scene rootScene = new Scene(thatWasGreatController.buildView());
        rootScene.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        rootScene.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - x);
            primaryStage.setY(mouseEvent.getScreenY() - y);
        });
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}