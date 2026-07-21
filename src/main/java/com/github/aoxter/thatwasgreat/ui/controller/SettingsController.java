package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.ui.view.SettingsViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class SettingsController extends MVCController {
    public Region buildView() {
        return new SettingsViewBuilder().build();
    }
}
