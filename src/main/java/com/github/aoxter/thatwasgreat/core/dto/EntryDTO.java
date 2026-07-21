package com.github.aoxter.thatwasgreat.core.dto;

public class EntryDTO {
    private final Long id;
    private String name;
    private String description;
    private byte overallRate;

    public EntryDTO() {
        this.id = null;
    }

    public EntryDTO(Long id) {
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

    public byte getOverallRate() {
        return overallRate;
    }

    public void setOverallRate(byte overallRate) {
        this.overallRate = overallRate;
    }
}
