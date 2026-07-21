package com.github.aoxter.thatwasgreat.core.mapper;

import com.github.aoxter.thatwasgreat.core.dto.EntryDTO;
import com.github.aoxter.thatwasgreat.core.model.Entry;

public class EntryMapper {

    public static EntryDTO toEntryDTO(Entry entry) {
        EntryDTO entryDTO = new EntryDTO(entry.getId());
        entryDTO.setName(entry.getName());
        entryDTO.setDescription(entry.getDescription());
        entryDTO.setOverallRate(entry.getOverallRate());
        return entryDTO;
    }

    public static Entry toEntry(EntryDTO entryDTO) {
        Entry entry = new Entry();
        entry.setId(entryDTO.getId());
        entry.setName(entryDTO.getName());
        entry.setDescription(entryDTO.getDescription());
        entry.setOverallRate(entryDTO.getOverallRate());
        return entry;
    }
}
