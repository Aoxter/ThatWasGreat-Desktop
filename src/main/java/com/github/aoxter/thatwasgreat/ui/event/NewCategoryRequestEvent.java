package com.github.aoxter.thatwasgreat.ui.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashSet;
import java.util.Set;

public class NewCategoryRequestEvent extends ApplicationEvent {
    private final Set<String> existingCategoryNameSet;

    public NewCategoryRequestEvent(Object source, Set<String> existingCategoryNameSet) {
        super(source);
        this.existingCategoryNameSet = existingCategoryNameSet != null ? existingCategoryNameSet : new HashSet<>();
    }

    public Set<String> getExistingCategoryNameSet() {
        return existingCategoryNameSet;
    }
}
