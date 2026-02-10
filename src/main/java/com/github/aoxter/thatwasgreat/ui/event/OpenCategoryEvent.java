package com.github.aoxter.thatwasgreat.ui.event;

import com.github.aoxter.thatwasgreat.core.model.Category;
import org.springframework.context.ApplicationEvent;

public class OpenCategoryEvent extends ApplicationEvent {
    private final Category categoryToOpen;

    public OpenCategoryEvent(Object source, Category category) {
        super(source);
        this.categoryToOpen = category;
    }

    public Category getCategoryToOpen() {
        return categoryToOpen;
    }
}
