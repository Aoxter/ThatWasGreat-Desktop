package com.github.aoxter.thatwasgreat.ui.model;

import javafx.beans.property.*;


public class ThatWasGreatModel {
    private final StringProperty appTitle = new SimpleStringProperty("");
    private final IntegerProperty appWindowWidth = new SimpleIntegerProperty();
    private final IntegerProperty appWindowHeight = new SimpleIntegerProperty();
    private final StringProperty windowMaximizedButtonText = new SimpleStringProperty("☐");
    private final BooleanProperty navigationMenuShown = new SimpleBooleanProperty(true);
    private final ObjectProperty<View> currentView = new SimpleObjectProperty<>(View.CATEGORIES);
    private final StringProperty errorMessage = new SimpleStringProperty();
    private static ThatWasGreatModel INSTANCE;

    private ThatWasGreatModel() {
    }

    public static synchronized ThatWasGreatModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThatWasGreatModel();
        }
        return INSTANCE;
    }

    public String getAppTitle() {
        return appTitle.get();
    }

    public StringProperty appTitleProperty() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle.set(appTitle);
    }

    public int getAppWindowWidth() {
        return appWindowWidth.get();
    }

    public IntegerProperty appWindowWidthProperty() {
        return appWindowWidth;
    }

    public void setAppWindowWidth(int appWindowWidth) {
        this.appWindowWidth.set(appWindowWidth);
    }

    public int getAppWindowHeight() {
        return appWindowHeight.get();
    }

    public IntegerProperty appWindowHeightProperty() {
        return appWindowHeight;
    }

    public void setAppWindowHeight(int appWindowHeight) {
        this.appWindowHeight.set(appWindowHeight);
    }

    public String getWindowMaximizedButtonText() {
        return windowMaximizedButtonText.get();
    }

    public StringProperty windowMaximizedButtonTextProperty() {
        return windowMaximizedButtonText;
    }

    public void setWindowMaximizedButtonText(String windowMaximizedButtonText) {
        this.windowMaximizedButtonText.set(windowMaximizedButtonText);
    }

    public boolean isNavigationMenuShown() {
        return navigationMenuShown.get();
    }

    public BooleanProperty navigationMenuShownProperty() {
        return navigationMenuShown;
    }

    public void setNavigationMenuShown(boolean navigationMenuShown) {
        this.navigationMenuShown.set(navigationMenuShown);
    }

    public View getCurrentView() {
        return currentView.get();
    }

    public ObjectProperty<View> currentViewProperty() {
        return currentView;
    }

    public void setCurrentView(View view) {
        currentView.set(view);
    }

    public String getErrorMessage() {
        return errorMessage.get();
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.set(errorMessage);
    }
}
