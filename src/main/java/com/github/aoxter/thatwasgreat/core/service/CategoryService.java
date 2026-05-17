package com.github.aoxter.thatwasgreat.core.service;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.CategoryRepository;
import com.github.aoxter.thatwasgreat.core.service.exception.CategoryNotFoundException;
import com.github.aoxter.thatwasgreat.core.service.exception.FactorAlreadyExistsException;
import com.github.aoxter.thatwasgreat.core.service.exception.FactorNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final FactorService factorService;

    public CategoryService(CategoryRepository categoryRepository, FactorService factorService) {
        this.categoryRepository = categoryRepository;
        this.factorService = factorService;
    }

    @Transactional(readOnly=true)
    public List<Category> getAll(){
        return  categoryRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly=true)
    public Optional<Category> getWithEntries(Long id) {
        return Optional.of(categoryRepository.findWithEntriesById(id));
    }

    @Transactional()
    public Category add(Category category) {
        return categoryRepository.save(new Category(category.getName(), category.getDescription(),
                category.getRatingForm(), category.getFactors()));
    }

    @Transactional()
    public Category update(Category categoryToUpdate) {
        return categoryRepository.save(categoryToUpdate);
    }

    @Transactional()
    public Category addFactor(Long id, String factor) throws CategoryNotFoundException, FactorAlreadyExistsException {
        Optional<Category> categoryToUpdateRaw = categoryRepository.findById(id);
        if (categoryToUpdateRaw.isPresent()) {
            Category categoryToUpdate = categoryToUpdateRaw.get();
            if(categoryToUpdate.getFactors() == null) {
                categoryToUpdate.setFactors(new HashSet<>(Arrays.asList(factor)));
            }
            if(!categoryToUpdate.getFactors().add(factor)) {
                throw new FactorAlreadyExistsException("This factor already exists in the category of the given ID");
            }
            try {
                categoryToUpdate.getEntries().forEach(entry -> factorService.addFactor(entry.getId(), factor));
            }
            catch (Exception e) {
                throw e;
            }
            return categoryRepository.save(categoryToUpdate);
        } else {
            throw new CategoryNotFoundException("Can not add new factor because category of the given ID doesn't exists.");
        }
    }

    @Transactional()
    public Category deleteFactor(Long id, String factor) throws CategoryNotFoundException, FactorAlreadyExistsException {
        Optional<Category> categoryToUpdateRaw = categoryRepository.findById(id);
        if (categoryToUpdateRaw.isPresent()) {
            Category categoryToUpdate = categoryToUpdateRaw.get();
            if(!categoryToUpdate.getFactors().contains(factor)) {
                throw new FactorNotFoundException("This factor doesn't exists in the category of the given ID");
            }
            else {
                try {
                    categoryToUpdate.getFactors().remove(factor);
                    categoryToUpdate.getEntries().forEach(entry -> factorService.deleteFactor(entry.getId(), factor));
                }
                catch(Exception e) {
                    throw e;
                }
            }
            return categoryRepository.save(categoryToUpdate);
        } else {
            throw new CategoryNotFoundException("Can not delete factor because category of the given ID doesn't exists.");
        }
    }

    @Transactional()
    public Category renameFactor(Long id, String oldFactor, String newFactor) {
        Optional<Category> categoryToUpdateRaw = categoryRepository.findById(id);
        if (categoryToUpdateRaw.isPresent()) {
            Category categoryToUpdate = categoryToUpdateRaw.get();
            if(!categoryToUpdate.getFactors().contains(oldFactor)) {
                throw new FactorNotFoundException("This factor doesn't exists in the category of the given ID");
            }
            else {
                try {
                    categoryToUpdate.getFactors().remove(oldFactor);
                    categoryToUpdate.getFactors().add(newFactor);
                    categoryToUpdate.getEntries().forEach(entry -> {
                        Byte rating = entry.getRates().get(oldFactor);
                        factorService.deleteFactor(entry.getId(), oldFactor);
                        factorService.addFactor(entry.getId(), newFactor, rating);
                    });
                }
                catch(Exception e) {
                    throw e;
                }
            }
            return categoryRepository.save(categoryToUpdate);
        }
        else {
            throw new CategoryNotFoundException("Can not rename factor because category of the given ID doesn't exists.");
        }
    }

    @Transactional()
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}