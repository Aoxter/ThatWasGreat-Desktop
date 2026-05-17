package com.github.aoxter.thatwasgreat.core.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(value = "graph.Category.entries")
    Category findWithEntriesById(Long id);

}
