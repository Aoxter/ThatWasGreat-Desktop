package com.github.aoxter.thatwasgreat.core.dto;

import com.github.aoxter.thatwasgreat.core.model.RatingForm;

public class NewCategoryDTO {
    private String name;
    private String description;
    private RatingForm ratingForm;

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
}
