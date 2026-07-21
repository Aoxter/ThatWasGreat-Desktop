package com.github.aoxter.thatwasgreat.core.dto;

import com.github.aoxter.thatwasgreat.core.model.RatingForm;

import java.util.Set;

public class CategoryWithEntriesDTO {
    private final Long id;
    private String name;
    private String description;
    private RatingForm ratingForm;
    private Set<EntryDTO> entries;

    public CategoryWithEntriesDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RatingForm getRatingForm() {
        return ratingForm;
    }

    public void setRatingForm(RatingForm ratingForm) {
        this.ratingForm = ratingForm;
    }

    public Set<EntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(Set<EntryDTO> entries) {
        this.entries = entries;
    }
}
