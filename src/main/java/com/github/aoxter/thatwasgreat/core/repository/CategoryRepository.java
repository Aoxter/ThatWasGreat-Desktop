package com.github.aoxter.thatwasgreat.core.repository;

import com.github.aoxter.thatwasgreat.core.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(value = "graph.Category.entries")
    Category findWithEntriesById(Long id);

    @Query("select c.name from Category c")
    Set<String> findAllNames();
}
