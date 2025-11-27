package com.github.aoxter.thatwasgreat.core.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Map;

@Entity
@Table(name="tbl_entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    private Long id;
    @NotNull
    private String name;
    private String description;
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JsonSerialize(using = CategorySerializer.class)
    private Category category;
    private byte overallRate;
    @ElementCollection
    @CollectionTable(name = "tbl_rates_mapping", joinColumns = {@JoinColumn(name = "entry_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "rate_name")
    @Column(name = "rating")
    private Map<String, Byte> rates;
    //TODO image for entries
    //TODO Groups: inside one category and between multi categories
    //TODO custom factors in plus and in minus which could influence overall rate if calculated automatically (example: very strong +++, strong +, weak -, very weak ---)

    public Entry() {
    }

    public Entry(String name) {
        this.name = name;
    }

    public Entry(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Entry(Category category, String name, String description, byte overallRate, Map<String, Byte> rates) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.overallRate = overallRate;
        this.rates = rates;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public byte getOverallRate() {
        return overallRate;
    }

    public void setOverallRate(byte overallRate) {
        this.overallRate = overallRate;
    }

    public Map<String, Byte> getRates() {
        return rates;
    }

    public void setRates(Map<String, Byte> rates) {
        this.rates = rates;
    }
}
