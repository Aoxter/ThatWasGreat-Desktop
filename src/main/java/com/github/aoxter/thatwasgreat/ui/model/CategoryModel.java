package com.github.aoxter.thatwasgreat.ui.model;

import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoryModel {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<RatingForm> ratingForm = new SimpleObjectProperty<>();
    private final Property<ObservableList<EntryModel>> entries = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<EntryModel> selectedEntry = new SimpleObjectProperty<>();

    public Long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public void setRatingForm(RatingForm ratingForm) {
        this.ratingForm.set(ratingForm);
    }

    public ObservableList<EntryModel> getEntries() {
        return entries.getValue();
    }

    public Property<ObservableList<EntryModel>> entriesProperty() {
        return entries;
    }

    public void setEntries(List<EntryModel> entries) {
        this.entries.getValue().setAll(entries);
    }

    public EntryModel getSelectedEntry() {
        return selectedEntry.get();
    }

    public ObjectProperty<EntryModel> selectedEntryProperty() {
        return selectedEntry;
    }

    public void setSelectedEntry(EntryModel entry) {
        selectedEntry.set(entry);
    }
}
