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
    },
    CATEGORY_TABLE {
        @Override
        public String getFxmlPath() {
            return "/fxml/CategoryTable.fxml";
        }
    },
    CATEGORY_TIERS {
        @Override
        public String getFxmlPath() {
            return "/fxml/CategoryTiers.fxml";
        }
    };

    public abstract String getFxmlPath();

}
