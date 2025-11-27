package com.github.aoxter.thatwasgreat.core.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntryListSerializer extends StdSerializer<List<Entry>> {

    public EntryListSerializer() {
        this(null);
    }

    public EntryListSerializer(Class<List<Entry>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Entry> entries, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<String> names = new ArrayList<>();
        for (Entry entry : entries) {
            names.add(entry.getName());
        }
        jsonGenerator.writeObject(names);
    }
}
