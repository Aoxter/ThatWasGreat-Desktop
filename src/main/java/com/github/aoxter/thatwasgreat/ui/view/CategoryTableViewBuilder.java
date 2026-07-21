package com.github.aoxter.thatwasgreat.ui.view;

import com.github.aoxter.thatwasgreat.ui.model.CategoryModel;
import com.github.aoxter.thatwasgreat.ui.model.EntryModel;
import com.github.aoxter.thatwasgreat.ui.widgets.LayoutConstructor;
import com.github.aoxter.thatwasgreat.ui.widgets.TableViewBuilder;
import com.github.aoxter.thatwasgreat.ui.widgets.WidgetConstructor;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class CategoryTableViewBuilder implements Builder<Region> {
    private final CategoryModel model;
    private final Runnable addEntry;
    private final Runnable removeEntry;
    private final Runnable removeCategory;
    private final Runnable goBack;
    private TableView<EntryModel> tableView;

    public CategoryTableViewBuilder(CategoryModel model, Runnable addEntry, Runnable removeEntry, Runnable removeCategory, Runnable goBack) {
        this.model = model;
        this.addEntry = addEntry;
        this.removeEntry = removeEntry;
        this.removeCategory = removeCategory;
        this.goBack = goBack;
    }

    @Override
    public Region build() {
        BorderPane borderPane = LayoutConstructor.createMainViewBorderPane();
        borderPane.setTop(createCategoryInfo());
        borderPane.setCenter(createTable());
        borderPane.setBottom(createButtonsBar());
        return borderPane;
    }

    protected Node createCategoryInfo() {
        Pane pane = LayoutConstructor.createCategoryDetailsPane();
        pane.getChildren().add(WidgetConstructor.createCategoryNameLabel(model.nameProperty()));
        pane.getChildren().add(WidgetConstructor.createCategoryDescriptionLabel(model.descriptionProperty()));
        return pane;
    }

    protected Node createTable() {
        Pane tableViewPane = LayoutConstructor.createCategoryTablePane();
        tableView = new TableViewBuilder<>(model.entriesProperty(),false).addStringColumn("Name", "name").addByteColumn("Rating", "overallRate").bindSelectedItemProperty(model.selectedEntryProperty()).build();
        tableViewPane.getChildren().add(tableView);
        return tableViewPane;
    }

    protected Node createButtonsBar() {
        Pane pane = LayoutConstructor.createButtonBar();
        pane.getChildren().add(WidgetConstructor.createSaveButton("Add entry", addEntry));
        pane.getChildren().add(WidgetConstructor.createCancelButton("Remove entry", tableView.getSelectionModel().selectedItemProperty().isNull(), this::prepareAndRunRemoveEntryRunnable));
        pane.getChildren().add(WidgetConstructor.createCancelButton("Remove category", removeCategory));
        pane.getChildren().add(WidgetConstructor.createNeutralButton("Go back", goBack));
        return pane;
    }

    private void prepareAndRunRemoveEntryRunnable() {
        if(tableView.getSelectionModel().getSelectedItems() != null) {
            removeEntry.run();
            tableView.getSelectionModel().clearSelection();
        }
    }
}