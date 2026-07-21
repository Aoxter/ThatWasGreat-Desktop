package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.dto.CategoryWithEntriesDTO;
import com.github.aoxter.thatwasgreat.core.dto.EntryDTO;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.model.NewEntryModel;
import com.github.aoxter.thatwasgreat.ui.model.View;
import com.github.aoxter.thatwasgreat.ui.view.NewEntryViewBuilder;
import com.github.aoxter.thatwasgreat.ui.widgets.AlertConstructor;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewEntryController extends MVCController {
    @Autowired
    private CategoryService categoryService;
    private NewEntryModel model;
    private CategoryWithEntriesDTO category;
    private final Logger logger = LoggerFactory.getLogger(NewEntryController.class);

    public Region buildView(final CategoryWithEntriesDTO category) {
        this.category = category;
        initModel();
        return new NewEntryViewBuilder(model, this::saveEntry, this::goBack).build();
    }

    private void initModel() {
        model = new NewEntryModel();
        model.setNamesAlreadyUsed(category.getEntries().stream().map(EntryDTO::getName).toList());
        model.showNameErrorProperty().bind(Bindings.createBooleanBinding(this::isNameIncorrect, model.nameProperty()));
        model.modelIsValidProperty().bind(Bindings.createBooleanBinding(this::isFormValid, model.nameProperty(), model.overallRateProperty()));
    }

    private void saveEntry() {
        EntryDTO entry = new EntryDTO();
        entry.setName(model.getName());
        entry.setDescription(model.getDescription());
        entry.setOverallRate((byte) model.getOverallRate().intValue());
        try {
            category.getEntries().add(entry);
            categoryService.update(category);
            goBack();
        } catch (Exception e) {
            category = categoryService.getWithEntries(category.getId());
            logger.error("Unable to save entry to database: {}", e.getMessage());
            AlertConstructor.showErrorAlert("Database error", "Unable to save this entry to database.");
        }
    }

    private void goBack() {
        switch (category.getRatingForm()) {
            case OneToTen -> changeView(View.CATEGORY_TABLE);
        }
    }

    private boolean isNameIncorrect() {
        boolean nameDuplicated = model.getNamesAlreadyUsed().contains(model.getName());
        model.setNameErrorMessage(nameDuplicated ? "Entry with such name already exists." : "");
        return nameDuplicated;
    }

    private boolean isFormValid() {
        return model.getName() != null && !model.getName().isEmpty() && !model.getNamesAlreadyUsed().contains(model.getName()) && model.getOverallRate() != null;
    }
}
