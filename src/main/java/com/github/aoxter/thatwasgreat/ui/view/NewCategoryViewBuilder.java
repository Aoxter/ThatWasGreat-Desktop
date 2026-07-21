package com.github.aoxter.thatwasgreat.ui.view;

import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.ui.model.NewCategoryModel;
import com.github.aoxter.thatwasgreat.ui.widgets.FormBuilder;
import com.github.aoxter.thatwasgreat.ui.widgets.LayoutConstructor;
import com.github.aoxter.thatwasgreat.ui.widgets.WidgetConstructor;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

import java.util.Arrays;

public class NewCategoryViewBuilder implements Builder<Region> {
    private final NewCategoryModel categoryModel;
    private final Runnable saveCategory;
    private final Runnable goBack;

    public NewCategoryViewBuilder(NewCategoryModel model, Runnable saveCategory, Runnable goBack) {
        this.saveCategory = saveCategory;
        this.goBack = goBack;
        this.categoryModel = model;
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
                .addPositionWithValidation("Name", WidgetConstructor.createBoundTextField(categoryModel.nameProperty()), categoryModel.showNameErrorProperty(), categoryModel.nameErrorMessageProperty())
                .addPosition("Description (optional)", WidgetConstructor.createBoundTextArea(categoryModel.descriptionProperty()))
                .addPosition("Rating form", WidgetConstructor.createVerticalToggleGroup(Arrays.asList(RatingForm.values()), categoryModel.ratingFormProperty()))
                .build();
    }

    protected Node createButtonsBar() {
        Pane pane = LayoutConstructor.createButtonBar();
        pane.getChildren().add(WidgetConstructor.createSaveButton("Add", categoryModel.categoryModelIsValidProperty().not(), saveCategory));
        pane.getChildren().add(WidgetConstructor.createCancelButton("Cancel", goBack));
        return pane;
    }
}