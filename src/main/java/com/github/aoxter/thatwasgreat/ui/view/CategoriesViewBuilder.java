package com.github.aoxter.thatwasgreat.ui.view;

import com.github.aoxter.thatwasgreat.core.dto.CategoryDTO;
import com.github.aoxter.thatwasgreat.ui.model.CategoriesModel;
import com.github.aoxter.thatwasgreat.ui.widgets.CustomTilePaneBuilder;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class CategoriesViewBuilder implements Builder<Region> {
    private final CategoriesModel model;
    private final Runnable openNewCategoryView;
    private final Runnable openCategoryView;

    public CategoriesViewBuilder(final CategoriesModel model, Runnable openNewCategoryView, Runnable openCategoryView) {
        this.model = model;
        this.openNewCategoryView = openNewCategoryView;
        this.openCategoryView = openCategoryView;
    }

    @Override
    public Region build() {
        CustomTilePaneBuilder<CategoryDTO> customTilePane = new CustomTilePaneBuilder<>(model.getCategories(), openCategoryView, openNewCategoryView).bindSelectedElementProperty(model.selectedCategoryProperty());
        return customTilePane.build();
    }
}
