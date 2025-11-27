package com.github.aoxter.thatwasgreat.ui.config;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class ThatWasGreatFxApplicationConfiguration {
    private final FxmlLoader fxmlLoader;
    private final String applicationTitle;

    public ThatWasGreatFxApplicationConfiguration(FxmlLoader fxmlLoader, @Value("${application.title}") String applicationTitle) {
        this.fxmlLoader = fxmlLoader;
        this.applicationTitle = applicationTitle;
    }

    @Bean
    @Lazy
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(fxmlLoader, stage, applicationTitle);
    }
}
