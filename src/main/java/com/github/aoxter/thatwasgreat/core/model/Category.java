package com.github.aoxter.thatwasgreat.core.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tbl_category")
public class Category {
    //TODO Configure generator for sequence generator incremented by 1
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    private Long id;
    @NotNull
    @Column(unique=true)
    private String name;
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RatingForm ratingForm;
    @ElementCollection
    @CollectionTable(name="tbl_category_factors", joinColumns=@JoinColumn(name="category_id"))
    @Column(name = "factor")
    private Set<String> factors;
    @OneToMany(mappedBy="category", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonSerialize(using = EntryListSerializer.class)
    private List<Entry> entries;
    //TODO icon for category

    public Category(String name) {
        this.name = name;
        this.ratingForm = RatingForm.getDefault();
        this.factors = new HashSet<>();
        this.entries = new ArrayList<>();
    }

    public Category(String name, RatingForm ratingForm) {
        this.name = name;
        this.ratingForm = ratingForm;
        this.factors = new HashSet<>();
        this.entries = new ArrayList<>();
    }

    public Category(String name, RatingForm ratingForm, Set<String> factors) {
        this.name = name;
        this.ratingForm = ratingForm;
        this.factors = factors;
        this.entries = new ArrayList<>();
    }

    public Category(String name, String description, RatingForm ratingForm, Set<String> factors) {
        this.name = name;
        this.description = description;
        this.ratingForm = ratingForm;
        this.factors = factors;
        this.entries = new ArrayList<>();
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

    public Set<String> getFactors() {
        return factors;
    }

    public void setFactors(Set<String> factors) {
        this.factors = factors;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append(" (Category):\n");
        stringBuilder.append(getDescription());
        stringBuilder.append("\n");
        stringBuilder.append("Rating form: ");
        stringBuilder.append(ratingForm);
        stringBuilder.append("\nRated aspects:");
        String prefix = " ";
        for (String factor: factors){
            stringBuilder.append(prefix);
            stringBuilder.append(factor);
            prefix = ", ";
        }
        return stringBuilder.toString();
    }
}
