package com.github.aoxter.thatwasgreat.ui.model;

import com.github.aoxter.thatwasgreat.core.dto.CategoryDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoriesModel {
    private final ObservableList<CategoryDTO> categories = FXCollections.observableArrayList();
    private final ObjectProperty<CategoryDTO> selectedCategory = new SimpleObjectProperty<>();


    public ObservableList<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories.setAll(categories);
    }

    public CategoryDTO getSelectedCategory() {
        return selectedCategory.get();
    }

    public ObjectProperty<CategoryDTO> selectedCategoryProperty() {
        return selectedCategory;
    }

    public void setSelectedCategory(CategoryDTO category) {
        selectedCategory.set(category);
    }
}
