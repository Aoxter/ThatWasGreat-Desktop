package com.github.aoxter.thatwasgreat.ui.model;

import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NewCategoryModel {
    private final StringProperty name= new SimpleStringProperty();
    private final StringProperty nameErrorMessage = new SimpleStringProperty();
    private final BooleanProperty showNameError = new SimpleBooleanProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<RatingForm> ratingForm = new SimpleObjectProperty<>();
    private final Property<ObservableList<String>> namesAlreadyUsed = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private final BooleanProperty categoryModelIsValid = new SimpleBooleanProperty(false);

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

    public RatingForm getRatingForm() {
        return ratingForm.get();
    }

    public ObjectProperty<RatingForm> ratingFormProperty() {
        return ratingForm;
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

    public boolean getCategoryModelIsValid() {
        return categoryModelIsValid.get();
    }

    public BooleanProperty categoryModelIsValidProperty() {
        return categoryModelIsValid;
    }

    public void setCategoryModelIsValid(boolean categoryModelIsValid) {
        this.categoryModelIsValid.set(categoryModelIsValid);
    }
}
