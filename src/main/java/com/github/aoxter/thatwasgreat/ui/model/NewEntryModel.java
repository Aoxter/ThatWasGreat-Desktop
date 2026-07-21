package com.github.aoxter.thatwasgreat.ui.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NewEntryModel {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty nameErrorMessage = new SimpleStringProperty();
    private final BooleanProperty showNameError = new SimpleBooleanProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<Integer> overallRate = new SimpleObjectProperty<>();
    private final Property<ObservableList<String>> namesAlreadyUsed = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private final BooleanProperty modelIsValid = new SimpleBooleanProperty(false);

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNameErrorMessage() {
        return nameErrorMessage.get();
    }

    public StringProperty nameErrorMessageProperty() {
        return nameErrorMessage;
    }

    public void setNameErrorMessage(String nameErrorMessage) {
        this.nameErrorMessage.set(nameErrorMessage);
    }

    public boolean isShowNameError() {
        return showNameError.get();
    }

    public BooleanProperty showNameErrorProperty() {
        return showNameError;
    }

    public void setShowNameError(boolean showNameError) {
        this.showNameError.set(showNameError);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Integer getOverallRate() {
        return overallRate.get();
    }

    public ObjectProperty<Integer> overallRateProperty() {
        return overallRate;
    }

    public void setOverallRate(Integer overallRate) {
        this.overallRate.set(overallRate);
    }

    public ObservableList<String> getNamesAlreadyUsed() {
        return namesAlreadyUsed.getValue();
    }

    public Property<ObservableList<String>> namesAlreadyUsedProperty() {
        return namesAlreadyUsed;
    }

    public void setNamesAlreadyUsed(List<String> namesAlreadyUsed) {
        this.namesAlreadyUsed.getValue().setAll(namesAlreadyUsed);
    }

    public boolean getModelIsValid() {
        return modelIsValid.get();
    }

    public BooleanProperty modelIsValidProperty() {
        return modelIsValid;
    }

    public void setModelIsValid(boolean modelIsValid) {
        this.modelIsValid.set(modelIsValid);
    }
}
