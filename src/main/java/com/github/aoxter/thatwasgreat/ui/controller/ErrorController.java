package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.ui.model.ErrorModel;
import com.github.aoxter.thatwasgreat.ui.view.ErrorViewBuilder;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class ErrorController extends MVCController {
    private ErrorModel errorModel;

    public Region buildView(StringProperty errorMessage) {
        initModel(errorMessage);
        return new ErrorViewBuilder(errorModel).build();
    }

    private void initModel(StringProperty errorMessage) {
        errorModel = new ErrorModel();
        errorModel.errorMessageProperty().bind(errorMessage);
    }
}
