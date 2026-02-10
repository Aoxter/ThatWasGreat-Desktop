package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.config.FxmlView;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.OpenCategoryEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CategoryTableController implements Initializable {
    @Autowired
    CategoryService categoryService;
    @FXML
    public TableView<Entry> entryTableView;
    @FXML
    public Label categoryNameLabel;
    @FXML
    public Label categoryDescriptionLabel;

    private Category viewedCategory;
    private final StageManager stageManager;
    private ObservableList<Entry> entriesList;

    @Lazy
    public CategoryTableController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @EventListener
    public void handleNewCategoryRequestEvent(OpenCategoryEvent event) {
        viewedCategory = event.getCategoryToOpen();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryNameLabel.setText(viewedCategory.getName());
        categoryDescriptionLabel.setText(viewedCategory.getDescription());
        entriesList = FXCollections.observableArrayList(viewedCategory.getEntries());
        TableColumn<Entry, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Entry, Byte> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("overallRate"));
        entryTableView.setItems(entriesList);
        entryTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        entryTableView.getColumns().addAll(nameColumn, ratingColumn);
    }

    public void addEntryOnAction(ActionEvent actionEvent) {
        Entry newEntry = new Entry(viewedCategory, "New entry");
        entriesList.add(newEntry);
    }

    public void removeEntriesOnAction(ActionEvent actionEvent) {
        ObservableList<Entry> selectedEntries = entryTableView.getSelectionModel().getSelectedItems();
        entryTableView.getItems().removeAll(List.copyOf(selectedEntries));
    }

    public void goBackOnAction(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.HOME);
    }
}
