package com.github.aoxter.thatwasgreat.core.mapper;

import com.github.aoxter.thatwasgreat.core.dto.CategoryDTO;
import com.github.aoxter.thatwasgreat.core.dto.CategoryWithEntriesDTO;
import com.github.aoxter.thatwasgreat.core.dto.EntryDTO;
import com.github.aoxter.thatwasgreat.core.dto.NewCategoryDTO;
import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryMapper {

     public static CategoryDTO toCategoryDTO(Category category) {
         CategoryDTO categoryDTO = new CategoryDTO(category.getId());
         categoryDTO.setName(category.getName());
         categoryDTO.setDescription(category.getDescription());
         categoryDTO.setRatingForm(category.getRatingForm());
         return categoryDTO;
    }

    public static Category toCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setRatingForm(categoryDTO.getRatingForm());
        return category;
    }

    public static Category toCategory(NewCategoryDTO newCategoryDTO) {
         Category category = new Category();
         category.setName(newCategoryDTO.getName());
         category.setDescription(newCategoryDTO.getDescription());
         category.setRatingForm(newCategoryDTO.getRatingForm());
         return category;
    }

    public static CategoryWithEntriesDTO toCategoryWithEntriesDTO(Category category) {
         CategoryWithEntriesDTO categoryWithEntriesDTO = new CategoryWithEntriesDTO(category.getId());
         categoryWithEntriesDTO.setName(category.getName());
         categoryWithEntriesDTO.setDescription(category.getDescription());
         categoryWithEntriesDTO.setRatingForm(category.getRatingForm());
         Set<EntryDTO> entries = new HashSet<>();
         for(Entry entry : category.getEntries()) {
             entries.add(EntryMapper.toEntryDTO(entry));
         }
         categoryWithEntriesDTO.setEntries(entries);
         return categoryWithEntriesDTO;
    }

    public static Category toCategory(CategoryWithEntriesDTO categoryWithEntriesDTO) {
        Category category = new Category();
        category.setId(categoryWithEntriesDTO.getId());
        category.setName(categoryWithEntriesDTO.getName());
        category.setDescription(categoryWithEntriesDTO.getDescription());
        category.setRatingForm(categoryWithEntriesDTO.getRatingForm());
        Set<Entry> entries = categoryWithEntriesDTO.getEntries().stream().map(EntryMapper::toEntry).collect(Collectors.toSet());
        entries.forEach(entry -> entry.setCategory(category));
        category.setEntries(entries);
        return category;
    }
}
