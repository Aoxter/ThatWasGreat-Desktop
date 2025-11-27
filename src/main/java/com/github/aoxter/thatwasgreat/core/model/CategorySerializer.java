package com.github.aoxter.thatwasgreat.core.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CategorySerializer extends StdSerializer<Category> {
    public CategorySerializer() {
        this(null);
    }

    public CategorySerializer(Class<Category> t) {
        super(t);
    }

    @Override
    public void serialize(Category category, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(category.getName());
    }
}
