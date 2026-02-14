package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;
import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.ui.config.FxmlView;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.NewEntryRequestEvent;
import com.github.aoxter.thatwasgreat.ui.event.OpenCategoryEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NewEntryController extends SceneControler {
    @FXML
    public Label headerLabel;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextArea descriptionTextArea;
    @FXML
    public ToggleGroup ratingToggleGroup;

    private Category parentCategory;
    private Entry createdEntry;

    @Lazy
    public NewEntryController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        super(stageManager, applicationEventPublisher);
        this.parentCategory = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText(String.format("Create new entry for %s category", parentCategory.getName()));
    }

    @EventListener
    public void handleNewEntryRequestEvent(NewEntryRequestEvent event) {
        parentCategory = event.getParentCategory();
    }

    public void addOnAction(ActionEvent actionEvent) {
        if(isFormCorrect()) {
            createdEntry = new Entry(parentCategory, nameTextField.getText());
            createdEntry.setDescription(descriptionTextArea.getText());
            Toggle selectedRateToggle = ratingToggleGroup.getSelectedToggle();
            RadioButton selectedRatingFormRadioButton = (RadioButton) selectedRateToggle;
            createdEntry.setOverallRate(Byte.parseByte(selectedRatingFormRadioButton.getText()));
            try {
                parentCategory.getEntries().add(createdEntry);
                goBackToCategoryView();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR,"Saving Error", e.getMessage());
            }
        }
    }

    private boolean isFormCorrect() {
        if(ratingToggleGroup.getSelectedToggle() == null) {
            showAlert(Alert.AlertType.ERROR,"Validation Error", "No rate selected");
            return false;
        }
        if(nameTextField.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Missing name");
            return false;
        }
        if(parentCategory.getEntries().stream().map(Entry::getName).toList().contains(nameTextField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Entry witch such name already exists");
            return false;
        }
        return true;
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        goBackToCategoryView();
    }

    private void goBackToCategoryView() {
        applicationEventPublisher.publishEvent(new OpenCategoryEvent(this, parentCategory));
        if(RatingForm.TIER.equals(parentCategory.getRatingForm())) {
            switchScene(FxmlView.CATEGORY_TIERS);
        }
        else if(RatingForm.STARS.equals(parentCategory.getRatingForm()) || RatingForm.OneToTen.equals(parentCategory.getRatingForm())) {
            switchScene(FxmlView.CATEGORY_TABLE);
        }
    }
}
