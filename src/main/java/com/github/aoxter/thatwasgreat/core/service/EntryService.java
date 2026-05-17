package com.github.aoxter.thatwasgreat.core.service;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;
import com.github.aoxter.thatwasgreat.core.model.EntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EntryService {
    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Transactional(readOnly=true)
    public List<Entry> getAll(){
        return  entryRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Optional<Entry> getById(Long id) {
        return entryRepository.findById(id);
    }

    private Map<String, Byte> getRatesMapByCategory(Category category) {
        Map<String, Byte> rates = new HashMap<>();
        for(String factor : category.getFactors()) {
            rates.put(factor, (byte)0);
        }
        return rates;
    }

    @Transactional()
    public Optional<Entry> update(Long id, Entry newEntryData) {
        Optional<Entry> entryToUpdate = entryRepository.findById(id);
        if (entryToUpdate.isPresent()) {
            Entry entryUpdated = entryToUpdate.get();
            entryUpdated.setName(newEntryData.getName());
            entryUpdated.setDescription(newEntryData.getDescription());
            entryUpdated.setOverallRate(newEntryData.getOverallRate());
            entryUpdated.setRates(newEntryData.getRates());
            return Optional.of(entryRepository.save(entryUpdated));
        } else {
            return Optional.empty();
        }
    }
}
