package com.github.aoxter.thatwasgreat.ui.event;

import com.github.aoxter.thatwasgreat.core.model.Category;
import org.springframework.context.ApplicationEvent;

public class NewEntryRequestEvent extends ApplicationEvent {
    private final Category parentCategory;

    public NewEntryRequestEvent(Object source, Category parentCategory) {
        super(source);
        this.parentCategory = parentCategory;
    }

    public Category getParentCategory() {
        return parentCategory;
    }
}
