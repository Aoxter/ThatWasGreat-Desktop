package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.ui.config.ApplicationScene;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationEventPublisher;

public abstract class SceneControler implements Initializable {
    private final StageManager stageManager;
    protected final ApplicationEventPublisher applicationEventPublisher;

    public SceneControler(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        this.stageManager = stageManager;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    protected void switchScene(ApplicationScene newScene) {
        stageManager.switchScene(newScene);
    }

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        showAlert(alertType, title, null, message);
    }

    protected void showAlert(Alert.AlertType alertType, String title, String headerText, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
