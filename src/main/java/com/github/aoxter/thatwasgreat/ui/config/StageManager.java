package com.github.aoxter.thatwasgreat.ui.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageManager {
    private final Stage primaryStage;
    private final FxmlLoader fxmlLoader;
    private final ApplicationEventPublisher eventPublisher;
    private final String applicationTitle;
    private double x, y = 0;

    public StageManager(FxmlLoader fxmlLoader, ApplicationEventPublisher eventPublisher, Stage primaryStage, String applicationTitle) {
        this.primaryStage = primaryStage;
        this.primaryStage.initStyle(StageStyle.TRANSPARENT);
        this.fxmlLoader = fxmlLoader;
        this.eventPublisher = eventPublisher;
        this.applicationTitle = applicationTitle;
    }

    public void switchScene(final ApplicationScene view) {
        Parent rootNode = loadRootNode(view.getFxmlPath());

        //TODO temporary demo version - will be removed after transition from fxml based to java code base scenes
        if(view.equals(ApplicationScene.HOME)) {
            rootNode.setOnMousePressed(mouseEvent -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });
        }
        rootNode.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - x);
            primaryStage.setY(mouseEvent.getScreenY() - y);
        });

        if(primaryStage.getScene() == null) {
            primaryStage.setTitle(applicationTitle);
            primaryStage.setScene(new Scene(rootNode));
            primaryStage.getScene().getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

        }
        else {
            primaryStage.getScene().setRoot(rootNode);
        }
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public void switchMinimalized() {
        primaryStage.setIconified(true);;
    }

    public void switchMaximized() {
        primaryStage.setMaximized(!primaryStage.isMaximized());
    }

    public void close() {
        primaryStage.close();
    }

    private Parent loadRootNode(String fxmlPath) {
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(fxmlPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootNode;
    }
}
