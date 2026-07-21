package com.github.aoxter.thatwasgreat.core.config;

import java.util.List;
import java.util.stream.IntStream;

public class Consts {
    public final static List<Integer> ONE_TO_TEN_RATING_FORM_VALUES = IntStream.rangeClosed(1, 10).boxed().toList();
}
