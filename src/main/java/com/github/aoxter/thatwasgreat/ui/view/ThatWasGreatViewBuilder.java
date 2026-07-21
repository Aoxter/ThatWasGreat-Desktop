package com.github.aoxter.thatwasgreat.ui.view;

import com.github.aoxter.thatwasgreat.ui.model.ThatWasGreatModel;
import com.github.aoxter.thatwasgreat.ui.model.View;
import com.github.aoxter.thatwasgreat.ui.widgets.LayoutConstructor;
import com.github.aoxter.thatwasgreat.ui.widgets.WidgetConstructor;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class ThatWasGreatViewBuilder implements Builder<Region> {
    private final BorderPane mainLayoutBorderPane = new BorderPane();
    private final ThatWasGreatModel model;
    private final Runnable minimizeWindowRunnable;
    private final Runnable maximizeWindowRunnable;
    private final Runnable closeWindowRunnable;
    private final Runnable switchNavigationMenuVisibility;
    private final Callable<Region> changeViewCallable;
    private final Logger logger = LoggerFactory.getLogger(ThatWasGreatViewBuilder.class);


    public ThatWasGreatViewBuilder(final ThatWasGreatModel model, Runnable minimizeWindowRunnable, Runnable maximizeWindowRunnable, Runnable closeWindowRunnable, Runnable switchNavigationMenuVisibility, Callable<Region> changeViewCallable) {
        this.model = model;
        this.minimizeWindowRunnable = minimizeWindowRunnable;
        this.maximizeWindowRunnable = maximizeWindowRunnable;
        this.closeWindowRunnable = closeWindowRunnable;
        this.switchNavigationMenuVisibility = switchNavigationMenuVisibility;
        this.changeViewCallable = changeViewCallable;
    }

    @Override
    public Region build() {
        mainLayoutBorderPane.setPrefWidth(model.getAppWindowWidth());
        mainLayoutBorderPane.setPrefHeight(model.getAppWindowHeight());
        mainLayoutBorderPane.setTop(createTopPanel());
        mainLayoutBorderPane.setLeft(createNavigationMenu());
        updateCenter();
        model.currentViewProperty().addListener((ob, oldValue, newValue) -> updateCenter());
        return mainLayoutBorderPane;
    }

    private Node createTopPanel() {
        Pane topPane = LayoutConstructor.createTopPane();
        topPane.getChildren().add(createNavMenuVisibilityButton(switchNavigationMenuVisibility));
        topPane.getChildren().add(createAppTitleLabel(model.appTitleProperty()));
        topPane.getChildren().add(createMinimizeButton(minimizeWindowRunnable));
        topPane.getChildren().add(createMaximizeButton(maximizeWindowRunnable));
        topPane.getChildren().add(createCloseButton(closeWindowRunnable));
        return topPane;
    }

    private Node createNavMenuVisibilityButton(Runnable toggleNavigationMenu) {
        return WidgetConstructor.createMainMenuButton("☰", toggleNavigationMenu);
    }

    private Node createAppTitleLabel(StringProperty stringProperty) {
        return WidgetConstructor.createAppTitleLabel(stringProperty);
    }

    private Node createMinimizeButton(Runnable minimizeWindow) {
        return WidgetConstructor.createMainMenuButton("—", minimizeWindow);
    }

    private Node createMaximizeButton(Runnable maximizeWindow) {
        return WidgetConstructor.createDynamicMainMenuButton(model.windowMaximizedButtonTextProperty(), maximizeWindow);
    }

    private Node createCloseButton(Runnable closeWindow) {
        return WidgetConstructor.createMainMenuButton("✕", closeWindow);
    }

    private Node createNavigationMenu() {
        Pane navigationPane = LayoutConstructor.createNavigationPane();
        navigationPane.visibleProperty().bind(model.navigationMenuShownProperty());
        navigationPane.managedProperty().bind(model.navigationMenuShownProperty());
        navigationPane.getChildren().add(createOpenCategoriesButton(() -> model.setCurrentView(View.CATEGORIES)));
        navigationPane.getChildren().add(createOpenSettingsButton(() -> model.setCurrentView(View.SETTINGS)));
        return navigationPane;
    }

    private Node createOpenCategoriesButton(Runnable onAction) {
        return WidgetConstructor.createMainMenuButton("Categories", onAction);
    }

    private Node createOpenSettingsButton(Runnable onAction) {
        return WidgetConstructor.createMainMenuButton("Settings", onAction);
    }

    private void updateCenter() {
        try {
            mainLayoutBorderPane.setCenter(changeViewCallable.call());
        } catch (Exception e) {
            logger.error("Unable to load new view: {}", e.getMessage());
            model.setCurrentView(View.ERROR);
            model.setErrorMessage("Something went wrong");
            throw new RuntimeException(e);
        }
    }
}
