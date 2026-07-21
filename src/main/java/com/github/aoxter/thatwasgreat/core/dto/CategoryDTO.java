package com.github.aoxter.thatwasgreat.core.dto;

import com.github.aoxter.thatwasgreat.core.model.RatingForm;

public class CategoryDTO {
    private final Long id;
    private String name;
    private String description;
    private RatingForm ratingForm;

    public CategoryDTO(Long id) {
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

    @Override
    public String toString() {
        return name;
    }
}
