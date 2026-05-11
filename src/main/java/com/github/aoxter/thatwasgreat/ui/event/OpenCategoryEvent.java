package com.github.aoxter.thatwasgreat.ui.event;

import org.springframework.context.ApplicationEvent;

public class OpenCategoryEvent extends ApplicationEvent {
    private final Long categoryId;

    public OpenCategoryEvent(Object source, Long categoryId) {
        super(source);
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
