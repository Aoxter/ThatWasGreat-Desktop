package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;
import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.ui.config.FxmlView;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.NewEntryRequestEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NewEntryController implements Initializable {
    private final StageManager stageManager;
    private Category parentCategory;
    private Entry createdEntry;

    @FXML
    public Label headerLabel;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextArea descriptionTextArea;
    @FXML
    public ToggleGroup ratingToggleGroup;

    @Lazy
    public NewEntryController(StageManager stageManager) {
        this.stageManager = stageManager;
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
                stageManager.switchScene(FxmlView.HOME);
            } catch (Exception e) {
                showError("Saving Error", e.getMessage());
            }
        }
    }

    private boolean isFormCorrect() {
        if(ratingToggleGroup.getSelectedToggle() == null) {
            showError("Validation Error", "No rate selected");
            return false;
        }
        if(nameTextField.getText().isBlank()) {
            showError("Validation Error", "Missing name");
            return false;
        }
        if(parentCategory.getEntries().stream().map(Entry::getName).toList().contains(nameTextField.getText())) {
            showError("Validation Error", "Entry witch such name already exists");
            return false;
        }
        return true;
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.HOME);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
