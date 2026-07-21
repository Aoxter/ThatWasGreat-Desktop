package com.github.aoxter.thatwasgreat.ui.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SettingsViewBuilder {

    public Region build() {
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("SETTINGS TMP VIEW"));
        return vBox;
    }
}
