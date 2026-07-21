package com.github.aoxter.thatwasgreat.ui.view;

import com.github.aoxter.thatwasgreat.ui.model.ErrorModel;
import com.github.aoxter.thatwasgreat.ui.widgets.LayoutConstructor;
import com.github.aoxter.thatwasgreat.ui.widgets.WidgetConstructor;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class ErrorViewBuilder implements Builder<Region> {
    private final ErrorModel errorModel;

    public ErrorViewBuilder(ErrorModel errorModel) {
        this.errorModel = errorModel;
    }

    @Override
    public Region build() {
        BorderPane borderPane = LayoutConstructor.createMainViewBorderPane();
        borderPane.setCenter(WidgetConstructor.createErrorViewLabel(errorModel.errorMessageProperty()));
        return borderPane;
    }
}
