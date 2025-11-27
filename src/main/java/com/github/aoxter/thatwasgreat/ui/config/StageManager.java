package com.github.aoxter.thatwasgreat.ui.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageManager {
    private final Stage primaryStage;
    private final FxmlLoader fxmlLoader;
    private final String applicationTitle;

    public StageManager(FxmlLoader fxmlLoader, Stage primaryStage, String applicationTitle) {
        this.primaryStage = primaryStage;
        this.fxmlLoader = fxmlLoader;
        this.applicationTitle = applicationTitle;
    }

    public void switchScene(final FxmlView view) {
        primaryStage.setTitle(applicationTitle);

        Parent rootNode = loadRootNode(view.getFxmlPath());

        Scene scene = new Scene(rootNode);
//        String stylesheet = Objects.requireNonNull(getClass()
//                        .getResource("/styles/styles.css"))
//                .toExternalForm();
//
//        scene.getStylesheets().add(stylesheet);

        primaryStage.setScene(scene);
        primaryStage.show();
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

    public void switchToNextScene(final FxmlView view) {
        Parent rootNode = loadRootNode(view.getFxmlPath());
        primaryStage.getScene().setRoot(rootNode);

        primaryStage.show();
    }
}
