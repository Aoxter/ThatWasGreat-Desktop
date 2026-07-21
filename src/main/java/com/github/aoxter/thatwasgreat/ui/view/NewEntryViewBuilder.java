package com.github.aoxter.thatwasgreat.ui.view;

import com.github.aoxter.thatwasgreat.core.config.Consts;
import com.github.aoxter.thatwasgreat.ui.model.NewEntryModel;
import com.github.aoxter.thatwasgreat.ui.widgets.FormBuilder;
import com.github.aoxter.thatwasgreat.ui.widgets.LayoutConstructor;
import com.github.aoxter.thatwasgreat.ui.widgets.WidgetConstructor;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class NewEntryViewBuilder implements Builder<Region> {
    private final NewEntryModel model;
    private final Runnable saveEntry;
    private final Runnable goBack;

    public NewEntryViewBuilder(NewEntryModel model, Runnable saveEntry, Runnable goBack) {
        this.model = model;
        this.saveEntry = saveEntry;
        this.goBack = goBack;
    }

    @Override
    public Region build() {
        BorderPane borderPane = LayoutConstructor.createMainViewBorderPane();
        borderPane.setCenter(createForm());
        borderPane.setBottom(createButtonsBar());
        return borderPane;
    }

    protected Node createForm() {
        return new FormBuilder()
                .addPositionWithValidation("Name", WidgetConstructor.createBoundTextField(model.nameProperty()), model.showNameErrorProperty(), model.nameErrorMessageProperty())
                .addPosition("Description (optional)", WidgetConstructor.createBoundTextArea(model.descriptionProperty()))
                .addPosition("Rating", WidgetConstructor.createHorizontalToggleGroup(Consts.ONE_TO_TEN_RATING_FORM_VALUES, model.overallRateProperty()))
                .build();
    }

    protected Node createButtonsBar() {
        Pane pane = LayoutConstructor.createButtonBar();
        pane.getChildren().add(WidgetConstructor.createSaveButton("Add", model.modelIsValidProperty().not(), saveEntry));
        pane.getChildren().add(WidgetConstructor.createCancelButton("Cancel", goBack));
        return pane;
    }
}