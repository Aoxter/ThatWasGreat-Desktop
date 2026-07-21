package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.ui.model.ThatWasGreatModel;
import com.github.aoxter.thatwasgreat.ui.model.View;

public abstract class MVCController {
    protected void changeView(View view) {
        ThatWasGreatModel.getInstance().currentViewProperty().setValue(view);
    }
}
