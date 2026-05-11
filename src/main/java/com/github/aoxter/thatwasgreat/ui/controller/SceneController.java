package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.ui.config.ApplicationScene;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

public abstract class SceneController implements Initializable {
    private final StageManager stageManager;
    protected final ApplicationEventPublisher applicationEventPublisher;

    public SceneController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        this.stageManager = stageManager;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    protected void switchScene(ApplicationScene newScene) {
        stageManager.switchScene(newScene);
    }

    protected ButtonType showAlert(Alert.AlertType alertType, String title, String message) {
        return showAlert(alertType, title, null, message);
    }

    protected ButtonType showAlert(Alert.AlertType alertType, String title, String headerText, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }
}
