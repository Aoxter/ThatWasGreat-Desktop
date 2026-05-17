package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.config.ApplicationScene;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.NewEntryRequestEvent;
import com.github.aoxter.thatwasgreat.ui.event.OpenCategoryEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

@Component
public class CategoryTableController extends SceneController {

    @Autowired
    protected CategoryService categoryService;

    @FXML
    public TableView<Entry> entryTableView;
    @FXML
    public Label categoryNameLabel;
    @FXML
    public Label categoryDescriptionLabel;

    private Category viewedCategory;

    @Lazy
    public CategoryTableController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        super(stageManager, applicationEventPublisher);
    }

    @EventListener
    public void handleOpenCategoryRequestEvent(OpenCategoryEvent event) {
        viewedCategory = categoryService.getWithEntries(event.getCategoryId()).orElse(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryNameLabel.setText(viewedCategory.getName());
        categoryDescriptionLabel.setText(viewedCategory.getDescription());
        if(viewedCategory == null) {
        }
        TableColumn<Entry, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Entry, Byte> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("overallRate"));
        refreshTableItems();
        entryTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        entryTableView.getColumns().addAll(nameColumn, ratingColumn);
    }

    protected void refreshTableItems() {
        ObservableList<Entry> entriesList = FXCollections.observableArrayList(viewedCategory.getEntries());
        entryTableView.setItems(entriesList);
        entryTableView.refresh();
    }

    public void addEntryOnAction(ActionEvent actionEvent) {
        applicationEventPublisher.publishEvent(new NewEntryRequestEvent(this, viewedCategory.getId()));
        switchScene(ApplicationScene.NEW_ENTRY);
    }

    public void removeEntriesOnAction(ActionEvent actionEvent) {
        ObservableList<Entry> selectedEntries = entryTableView.getSelectionModel().getSelectedItems();
        HashSet<Entry> entriesToRemoveSet = new HashSet<>(selectedEntries);
        try {
            viewedCategory.removeEntries(entriesToRemoveSet);
            viewedCategory = categoryService.update(viewedCategory);
        } catch (Exception e) {
            viewedCategory = categoryService.getWithEntries(viewedCategory.getId()).orElse(null);
            showAlert(Alert.AlertType.ERROR, "Database Error", "Unable to remove these entries from database");
            e.printStackTrace();
        }
        refreshTableItems();
    }

    public void removeCategoryOnAction(ActionEvent actionEvent) {
        if(ButtonType.OK.equals(showAlert(Alert.AlertType.CONFIRMATION, "Are you sure?", "This action will remove this category and all of its entries. It cannot be undone."))) {
            categoryService.delete(viewedCategory.getId());
            switchScene(ApplicationScene.HOME);
        }
    }

    public void goBackOnAction(ActionEvent actionEvent) {
        switchScene(ApplicationScene.HOME);
    }
}
