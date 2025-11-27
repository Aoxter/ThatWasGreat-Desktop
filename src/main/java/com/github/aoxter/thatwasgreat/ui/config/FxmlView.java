package com.github.aoxter.thatwasgreat.ui.config;

public enum FxmlView {
    HOME {
        @Override
        public String getFxmlPath() {
            return "/fxml/home.fxml";
        }
    };

    public abstract String getFxmlPath();

}
