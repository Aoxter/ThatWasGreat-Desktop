package com.github.aoxter.thatwasgreat.ui.model;

import javafx.beans.property.*;

public class EntryModel {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty overallRate = new SimpleIntegerProperty();

    public Long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getOverallRate() {
        return overallRate.get();
    }

    public IntegerProperty overallRateProperty() {
        return overallRate;
    }

    public void setOverallRate(Integer overallRate) {
        this.overallRate.set(overallRate);
    }
}
