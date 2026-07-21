package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.dto.CategoryDTO;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.model.CategoriesModel;
import com.github.aoxter.thatwasgreat.ui.model.View;
import com.github.aoxter.thatwasgreat.ui.view.CategoriesViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriesController extends MVCController {
    @Autowired
    private CategoryService categoryService;
    private final CategoriesModel model = new CategoriesModel();


    public Region buildView() {
        initModel();
        return new CategoriesViewBuilder(model, this::showNewCategoryView, this::showCategoryView).build();
    }

    protected void initModel() {
        model.setCategories(categoryService.getAll());
    }

    private void showNewCategoryView() {
        changeView(View.NEW_CATEGORY);
    }

    private void showCategoryView() {
        if(model.getSelectedCategory() != null) {
            switch (model.getSelectedCategory().getRatingForm()) {
                case OneToTen -> changeView(View.CATEGORY_TABLE);
                case STARS -> changeView(View.CATEGORY_STARS);
                case TIER -> changeView(View.CATEGORY_TIERS);
            }
        }
    }

    public CategoryDTO getCurrentCategory() {
        return model.getSelectedCategory();
    }
}
