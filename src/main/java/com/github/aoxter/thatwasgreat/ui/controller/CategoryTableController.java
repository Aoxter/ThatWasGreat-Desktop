package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.dto.CategoryWithEntriesDTO;
import com.github.aoxter.thatwasgreat.core.dto.EntryDTO;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.model.CategoryModel;
import com.github.aoxter.thatwasgreat.ui.model.EntryModel;
import com.github.aoxter.thatwasgreat.ui.model.View;
import com.github.aoxter.thatwasgreat.ui.view.CategoryTableViewBuilder;
import com.github.aoxter.thatwasgreat.ui.widgets.AlertConstructor;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryTableController extends MVCController {
    @Autowired
    private CategoryService categoryService;
    private CategoryModel model;
    private final Logger logger = LoggerFactory.getLogger(CategoryTableController.class);


    public Region buildView(final CategoryWithEntriesDTO category) {
        initModel(category);
        return new CategoryTableViewBuilder(model, this::addEntry, this::removeEntry, this::removeCategory, this::goBack).build();
    }

    private void initModel(final CategoryWithEntriesDTO category) {
        model = new CategoryModel();
        model.setId(category.getId());
        model.setName(category.getName());
        model.setDescription(category.getDescription());
        model.setRatingForm(category.getRatingForm());
        model.setEntries(category.getEntries().stream().map(this::castEntryDTOToEntryModel).collect(Collectors.toList()));
    }

    private void addEntry() {
        changeView(View.NEW_ENTRY);
    }

    private void removeEntry() {
        if(ButtonType.OK.equals(AlertConstructor.showConfirmationAlert("Are you sure?", "This action will remove currently selected entry. It cannot be undone."))) {
            List<EntryModel> entriesBackup = model.getEntries().stream().toList();
            try {
                model.getEntries().remove(model.getSelectedEntry());
                categoryService.update(castModelToCategoryWithEntriesDTO());
            } catch (Exception e) {
                model.setEntries(entriesBackup);
                logger.error("Unable to remove entry from database: {}", e.getMessage());
                AlertConstructor.showErrorAlert("Database Error", "Unable to remove these entries from database");
            }
        }
    }

    private void removeCategory() {
        if(ButtonType.OK.equals(AlertConstructor.showConfirmationAlert("Are you sure?", "This action will remove this category and all of its entries. It cannot be undone."))) {
            try {
                categoryService.delete(model.getId());
                goBack();
            } catch (Exception e) {
                logger.error("Unable to remove category from database: {}", e.getMessage());
                AlertConstructor.showErrorAlert("Database error", "Unable to remove category from database.");
            }
        }
    }

    private void goBack() {
        changeView(View.CATEGORIES);
    }

    private CategoryWithEntriesDTO castModelToCategoryWithEntriesDTO() {
        CategoryWithEntriesDTO categoryToUpdate = new CategoryWithEntriesDTO(model.getId());
        categoryToUpdate.setName(model.getName());
        categoryToUpdate.setDescription(model.getDescription());
        categoryToUpdate.setRatingForm(model.getRatingForm());
        categoryToUpdate.setEntries(model.getEntries().stream().map(this::castEntryModelToEntryDTOModel).collect(Collectors.toSet()));
        return categoryToUpdate;
    }

    private EntryModel castEntryDTOToEntryModel(final EntryDTO entryDTO) {
        EntryModel entryModel = new EntryModel();
        entryModel.setId(entryDTO.getId());
        entryModel.setName(entryDTO.getName());
        entryModel.setDescription(entryDTO.getDescription());
        entryModel.setOverallRate((int) entryDTO.getOverallRate());
        return entryModel;
    }

    private EntryDTO castEntryModelToEntryDTOModel(final EntryModel entryModel) {
        EntryDTO entryDTO = new EntryDTO(entryModel.getId());
        entryDTO.setName(entryModel.getName());
        entryDTO.setDescription(entryModel.getDescription());
        entryDTO.setOverallRate((byte) entryModel.getOverallRate().intValue());
        return entryDTO;
    }
}
