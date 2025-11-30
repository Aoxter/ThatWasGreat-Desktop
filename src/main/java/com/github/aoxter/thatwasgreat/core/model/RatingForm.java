package com.github.aoxter.thatwasgreat.core.model;

public enum RatingForm {
    TIER("Tier list"),
    STARS("Stars"),
    OneToTen("Rating from 1 to 10");

    private final String name;

    private RatingForm(String name){
        this.name = name;
    }

    public static RatingForm getDefault(){
        return STARS;
    }

    public static RatingForm getByName(String searchingName) {
        for(RatingForm ratingForm : values()) {
            if(ratingForm.name.equals(searchingName)) {
                return ratingForm;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
