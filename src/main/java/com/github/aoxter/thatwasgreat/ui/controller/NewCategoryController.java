package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.dto.NewCategoryDTO;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.model.NewCategoryModel;
import com.github.aoxter.thatwasgreat.ui.model.View;
import com.github.aoxter.thatwasgreat.ui.view.NewCategoryViewBuilder;
import com.github.aoxter.thatwasgreat.ui.widgets.AlertConstructor;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NewCategoryController extends MVCController {
    @Autowired
    private CategoryService categoryService;
    private NewCategoryModel model;
    private final Logger logger = LoggerFactory.getLogger(NewCategoryController.class);

    public Region buildView() {
        initModel();
        return new NewCategoryViewBuilder(model, this::saveCategory, this::goBack).build();
    }

    private void initModel() {
        model = new NewCategoryModel();
        model.setNamesAlreadyUsed(new ArrayList<>(categoryService.getAllNames()));
        model.showNameErrorProperty().bind(Bindings.createBooleanBinding(this::isNameIncorrect, model.nameProperty()));
        model.categoryModelIsValidProperty().bind(Bindings.createBooleanBinding(this::isFormValid, model.nameProperty(), model.ratingFormProperty()));
    }

    private void saveCategory() {
        if(categoryService.getAllNames().contains(model.getName())) {
            AlertConstructor.showWarningAlert("Incorrect data", "Category with such name already exists.");
        }
        NewCategoryDTO category = new NewCategoryDTO();
        category.setName(model.getName());
        category.setDescription(model.getDescription());
        category.setRatingForm(model.getRatingForm());
        try {
            categoryService.add(category);
            goBack();
        } catch (Exception e) {
            logger.error("Unable to save category to database: {}", e.getMessage());
            AlertConstructor.showErrorAlert("Database error", "Unable to save this category to database");
        }
    }

    private void goBack() {
        changeView(View.CATEGORIES);
    }

    private boolean isNameIncorrect() {
        boolean nameDuplicated = model.getNamesAlreadyUsed().contains(model.getName());
        model.setNameErrorMessage(nameDuplicated ? "Category with such name already exists." : "");
        return nameDuplicated;
    }

    private boolean isFormValid() {
        return model.getName() != null && !model.getName().isEmpty() && !model.getNamesAlreadyUsed().contains(model.getName()) && model.getRatingForm() != null;
    }

}
