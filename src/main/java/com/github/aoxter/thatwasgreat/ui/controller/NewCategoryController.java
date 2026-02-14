package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.config.ApplicationScene;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.NewCategoryRequestEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class NewCategoryController extends SceneControler {
    @Autowired
    CategoryService categoryService;

    @FXML
    public VBox newCategoryFormVBox;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextArea descriptionTextArea;

    final private ToggleGroup ratingFormToggleGroup;
    private Category createdCategory;
    private Set<String> existingCategoryNameSet;

    @Lazy
    public NewCategoryController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        super(stageManager, applicationEventPublisher);
        this.createdCategory = null;
        this.ratingFormToggleGroup = new ToggleGroup();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBox toggleBox = new VBox();
        toggleBox.setSpacing(10);
        toggleBox.setAlignment(Pos.CENTER_LEFT);
        for(RatingForm ratingForm : RatingForm.values()) {
            RadioButton ratingFormRadioButton = new RadioButton(ratingForm.toString());
            ratingFormRadioButton.setToggleGroup(ratingFormToggleGroup);
            toggleBox.getChildren().add(ratingFormRadioButton);
        }
        newCategoryFormVBox.getChildren().add(toggleBox);
    }

    @EventListener
    public void handleNewCategoryRequestEvent(NewCategoryRequestEvent event) {
        existingCategoryNameSet = event.getExistingCategoryNameSet();
    }

    public void addCategoryOnAction(ActionEvent actionEvent) {
        if(isFormCorrect()) {
            createdCategory = new Category(nameTextField.getText());
            createdCategory.setDescription(descriptionTextArea.getText());
            Toggle selectedRatingFormToggle = ratingFormToggleGroup.getSelectedToggle();
            RadioButton selectedRatingFormRadioButton = (RadioButton) selectedRatingFormToggle;
            createdCategory.setRatingForm(RatingForm.getByName(selectedRatingFormRadioButton.getText()));
            try {
                switchScene(ApplicationScene.HOME);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Saving Error", e.getMessage());
            }
        }
    }

    private boolean isFormCorrect() {
        if(ratingFormToggleGroup.getSelectedToggle() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "No rating form selected");
            return false;
        }
        if(nameTextField.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Missing category name");
            return false;
        }
        if(existingCategoryNameSet.contains(nameTextField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Category witch such name already exists");
            return false;
        }
        return true;
    }

    public void goToHomeOnAction(ActionEvent actionEvent) {
        switchScene(ApplicationScene.HOME);
    }
}
