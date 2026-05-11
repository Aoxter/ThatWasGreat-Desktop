package com.github.aoxter.thatwasgreat.ui.event;

import com.github.aoxter.thatwasgreat.core.model.Category;
import org.springframework.context.ApplicationEvent;

public class NewEntryRequestEvent extends ApplicationEvent {
    private final Long categoryId;

    public NewEntryRequestEvent(Object source, Long categoryId) {
        super(source);
        this.categoryId = categoryId;
    }

    public Long getParentCategoryId() {
        return categoryId;
    }
}
