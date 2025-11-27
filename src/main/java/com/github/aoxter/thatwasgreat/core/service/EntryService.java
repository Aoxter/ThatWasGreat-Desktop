package com.github.aoxter.thatwasgreat.core.service;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;
import com.github.aoxter.thatwasgreat.core.model.EntryRepository;
import com.github.aoxter.thatwasgreat.core.service.exception.CategoryNotFoundException;
import com.github.aoxter.thatwasgreat.core.service.exception.EntryAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntryService {
    private final EntryRepository entryRepository;
    private final CategoryService categoryService;

    public EntryService(EntryRepository entryRepository, CategoryService categoryService) {
        this.entryRepository = entryRepository;
        this.categoryService = categoryService;
    }

    public List<Entry> getAll(){
        return  entryRepository.findAll();
    }

    public List<Entry> getAll(long id){
        return  entryRepository.findByCategoryId(id);
    }

    public Optional<Entry> getById(Long id) {
        return entryRepository.findById(id);
    }

    public Entry add(long categoryId, Entry entry) throws EntryAlreadyExistsException, CategoryNotFoundException {
        if(getAll(categoryId).stream().map(Entry::getName).collect(Collectors.toList()).contains(entry.getName())){
            throw new EntryAlreadyExistsException("Entry with that name already exists in the given category.");
        }
        Optional<Category> category = categoryService.getById(categoryId);
        if(category.isEmpty()){
            throw new CategoryNotFoundException("Can not add new entry because category of the given ID doesn't exists.");
        }
        return entryRepository.save(new Entry(category.get(), entry.getName(), entry.getDescription(), (byte)0, getRatesMapByCategory(category.get())));
    }

    private Map<String, Byte> getRatesMapByCategory(Category category) {
        Map<String, Byte> rates = new HashMap<>();
        for(String factor : category.getFactors()) {
            rates.put(factor, (byte)0);
        }
        return rates;
    }

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

    public void delete(Long id) throws Exception {
        entryRepository.deleteById(id);
    }
}
