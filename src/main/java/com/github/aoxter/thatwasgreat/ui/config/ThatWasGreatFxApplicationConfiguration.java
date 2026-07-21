package com.github.aoxter.thatwasgreat.ui.config;

import com.github.aoxter.thatwasgreat.ui.controller.ThatWasGreatController;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class ThatWasGreatFxApplicationConfiguration {

    @Bean
    @Lazy
    public ThatWasGreatController thatWasGreatController(Stage stage) throws IOException {
        return new ThatWasGreatController(stage);
    }
}
