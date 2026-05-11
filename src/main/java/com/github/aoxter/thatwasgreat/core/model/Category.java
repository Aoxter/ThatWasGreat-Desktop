package com.github.aoxter.thatwasgreat.core.model;

import com.github.aoxter.thatwasgreat.core.service.exception.EntryAlreadyExistsException;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="tbl_category")
@NamedEntityGraph(name = "graph.Category.entries",
        attributeNodes = @NamedAttributeNode("entries"))
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
    private Set<Entry> entries;
    //TODO icon for category

    public Category() {

    }

    public Category(String name) {
        this.name = name;
        this.ratingForm = RatingForm.getDefault();
        this.factors = new HashSet<>();
        this.entries = new HashSet<>();
    }

    public Category(String name, RatingForm ratingForm) {
        this.name = name;
        this.ratingForm = ratingForm;
        this.factors = new HashSet<>();
        this.entries = new HashSet<>();
    }

    public Category(String name, RatingForm ratingForm, Set<String> factors) {
        this.name = name;
        this.ratingForm = ratingForm;
        this.factors = factors;
        this.entries = new HashSet<>();
    }

    public Category(String name, String description, RatingForm ratingForm, Set<String> factors) {
        this.name = name;
        this.description = description;
        this.ratingForm = ratingForm;
        this.factors = factors;
        this.entries = new HashSet<>();
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

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

    public void addEntry(Entry entry) {
        if(entries.stream().map(Entry::getName).collect(Collectors.toSet()).contains(entry.getName())){
            throw new EntryAlreadyExistsException("Entry with that name already exists in the given category.");
        }
        entries.add(entry);
        entry.setCategory(this);
    }

    public void removeEntry(Entry entry) {
        entries.remove(entry);
        entry.setCategory(null);
    }

    public void removeEntries(Set<Entry> entries) {
        for(Entry entry : entries) {
            removeEntry(entry);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
