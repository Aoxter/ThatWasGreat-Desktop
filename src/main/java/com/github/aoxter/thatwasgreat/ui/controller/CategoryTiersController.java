package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.ui.config.ApplicationScene;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.OpenCategoryEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class CategoryTiersController extends SceneControler {

    @FXML
    public Label categoryNameLabel;
    @FXML
    public Label categoryDescriptionLabel;

    private Category viewedCategory;

    @Lazy
    public CategoryTiersController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        super(stageManager, applicationEventPublisher);
    }

    @EventListener
    public void handleNewCategoryRequestEvent(OpenCategoryEvent event) {
        viewedCategory = event.getCategoryToOpen();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryNameLabel.setText(viewedCategory.getName());
        categoryDescriptionLabel.setText(viewedCategory.getDescription());
    }

    public void goBackOnAction(ActionEvent actionEvent) {
        switchScene(ApplicationScene.HOME);
    }
}
