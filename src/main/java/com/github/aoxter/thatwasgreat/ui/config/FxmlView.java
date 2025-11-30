package com.github.aoxter.thatwasgreat.ui.config;

public enum FxmlView {
    HOME {
        @Override
        public String getFxmlPath() {
            return "/fxml/Home.fxml";
        }
    },
    NEW_CATEGORY {
        @Override
        public String getFxmlPath() {
            return "/fxml/NewCategory.fxml";
        }
    };

    public abstract String getFxmlPath();

}
