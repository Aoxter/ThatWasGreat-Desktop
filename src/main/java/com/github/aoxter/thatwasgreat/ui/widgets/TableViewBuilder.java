package com.github.aoxter.thatwasgreat.ui.widgets;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * Builder class that creates new {@link TableView} object
 * @param <T> Type of data represented by created TableView
 */
public class TableViewBuilder<T> {
    private final TableView<T> tableView;

    /**
     * Creates new instance of TableViewBuilder, initializes TableView and populates it with objects from given property. TableView object can be accessed with {@link #build()}.
     * @param elementsListPropertyToBind Property holding objects that will be displayed inside table
     * @param multipleSelectionMode Selection mode: {@link SelectionMode#MULTIPLE} or {@link SelectionMode#SINGLE}
     */
    public TableViewBuilder(Property<ObservableList<T>> elementsListPropertyToBind, boolean multipleSelectionMode) {
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tableView.getSelectionModel().setSelectionMode(multipleSelectionMode ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
        tableView.itemsProperty().bind(elementsListPropertyToBind);
    }

    /**
     * Returns created and configured by this builder TableView widget.
     * @return new {@link TableView} object
     */
    public TableView<T> build() {
        return tableView;
    }

    /**
     * Add new column of String type.
     * @param columnName Column name
     * @param property Property of TableView data model to display in this column
     * @return this builder instance
     */
    public TableViewBuilder<T> addStringColumn(String columnName, String property) {
        TableColumn<T, String> column = new TableColumn<>(columnName);
        addColumn(column, property);
        return this;
    }

    /**
     * Add new column of Byte type.
     * @param columnName Column name
     * @param property Property of TableView data model to display in this column
     * @return this builder instance
     */
    public TableViewBuilder<T> addByteColumn(String columnName, String property) {
        TableColumn<T, Byte> column = new TableColumn<>(columnName);
        addColumn(column, property);
        return this;
    }

    private <S> void addColumn(TableColumn<T, S> tableColumn, String property) {
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(property));
        tableView.getColumns().add(tableColumn);
    }

    /**
     * Binds given property to TableView selected item property. This property is read only. If {@link SelectionMode#MULTIPLE} has been chosen, then last selected row will be indicated.
     * @param propertyToBind Property that will be bound with object represented by currently selected row
     * @return this builder instance
     */
    public TableViewBuilder<T> bindSelectedItemProperty(ObjectProperty<T> propertyToBind) {
        propertyToBind.bind(tableView.getSelectionModel().selectedItemProperty());
        return this;
    }

    /**
     * Enables row unselecting on second click. For {@link SelectionMode#SINGLE} currently selected row will be unselected but for {@link SelectionMode#MULTIPLE} every selected row will be unselected after one of them is clicked.
     * @return this builder instance
     */
    public TableViewBuilder<T> enableRowUnselectOnSecondClick() {
        tableView.setRowFactory(new Callback<TableView<T>, TableRow<T>>() {
            @Override
            public TableRow<T> call(TableView<T> tableView2) {
                final TableRow<T> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tableView.getItems().size() && tableView.getSelectionModel().isSelected(index)  ) {
                            tableView.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
        return this;
    }
}
