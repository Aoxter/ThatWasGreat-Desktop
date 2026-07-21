package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.model.ThatWasGreatModel;
import com.github.aoxter.thatwasgreat.ui.view.ThatWasGreatViewBuilder;
import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class ThatWasGreatController extends MVCController {
    @Autowired
    Environment environment;
    @Autowired
    CategoriesController categoriesController;
    @Autowired
    NewCategoryController newCategoryController;
    @Autowired
    CategoryTableController categoryTableController;
    @Autowired
    NewEntryController newEntryController;
    @Autowired
    SettingsController settingsController;
    @Autowired
    ErrorController errorController;
    @Autowired
    CategoryService categoryService;

    private final ThatWasGreatModel model;
    private final Stage primaryStage;

    public ThatWasGreatController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.model = ThatWasGreatModel.getInstance();
    }

    public Region buildView() {
        initModel();
        Builder<Region> viewBuilder = new ThatWasGreatViewBuilder(model, this::switchMinimized, this::switchMaximized, this::stop, this::switchNavigationMenuVisibility, this::changeView);
        Region region = viewBuilder.build();
        region.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());
        return region;
    }

    private void initModel() {
        primaryStage.maximizedProperty().addListener((obs, oldVal, maximized) -> {
            model.setWindowMaximizedButtonText(maximized ? "❐" : "☐");
        });
        model.setAppTitle(Objects.requireNonNull(environment.getProperty("application.title")));
        model.setAppWindowWidth(Integer.parseInt(Objects.requireNonNull(environment.getProperty("application.window.width"))));
        model.setAppWindowHeight(Integer.parseInt(Objects.requireNonNull(environment.getProperty("application.window.height"))));
    }

    public Region changeView() {
        switch(model.getCurrentView()) {
            case CATEGORIES -> {
                return categoriesController.buildView();
            }
            case NEW_CATEGORY -> {
                return newCategoryController.buildView();
            }
            case CATEGORY_TABLE -> {
                return categoryTableController.buildView(categoryService.getWithEntries(categoriesController.getCurrentCategory().getId()));
            }
            case NEW_ENTRY -> {
                return newEntryController.buildView(categoryService.getWithEntries(categoriesController.getCurrentCategory().getId()));
            }
            case SETTINGS -> {
                return settingsController.buildView();
            }
            case ERROR -> {
                return errorController.buildView(model.errorMessageProperty());
            }
            default -> {
                return null;
            }
        }
    }

    public void switchNavigationMenuVisibility() {
        model.setNavigationMenuShown(!model.isNavigationMenuShown());
    }

    public void stop() {
        primaryStage.close();
        Platform.exit();
    }

    public void switchMinimized() {
        primaryStage.setIconified(true);
    }

    public void switchMaximized() {
        primaryStage.setMaximized(!primaryStage.isMaximized());
    }
}
