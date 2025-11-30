package com.github.aoxter.thatwasgreat.ui.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageManager {
    private final Stage primaryStage;
    private final FxmlLoader fxmlLoader;
    private final ApplicationEventPublisher eventPublisher;
    private final String applicationTitle;

    public StageManager(FxmlLoader fxmlLoader, ApplicationEventPublisher eventPublisher, Stage primaryStage, String applicationTitle) {
        this.primaryStage = primaryStage;
        this.fxmlLoader = fxmlLoader;
        this.eventPublisher = eventPublisher;
        this.applicationTitle = applicationTitle;
    }

    public void switchScene(final FxmlView view) {
        Parent rootNode = loadRootNode(view.getFxmlPath());
        if(primaryStage.getScene() == null) {
            primaryStage.setTitle(applicationTitle);
            primaryStage.setScene(new Scene(rootNode));
            primaryStage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            primaryStage.getScene().getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

        }
        else {
            primaryStage.getScene().setRoot(rootNode);
        }
        primaryStage.show();
        primaryStage.centerOnScreen();
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
