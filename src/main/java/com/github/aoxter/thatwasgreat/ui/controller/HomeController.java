package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.config.FxmlView;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.NewCategoryRequestEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HomeController implements Initializable {
    private final static double CATEGORY_TILE_PREF_SIZE = 230.0;
    private final StageManager stageManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    CategoryService categoryService;
    @FXML
    public ScrollPane categoryScrollPane;
    @FXML
    public FlowPane categoryFlowPane;

    private Set<Category> categorySet;

    @Lazy
    public HomeController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateWithTestData();
        categoryScrollPane.getStyleClass().add("category-scroll-pane");
        refreshCategoryTilePane();
    }

    private void populateWithTestData() {
        categorySet = new LinkedHashSet<>(categoryService.getAll());
        categorySet.add(new Category("Test 1", RatingForm.STARS));
        categorySet.add(new Category("Test 2", RatingForm.TIER));
        categorySet.add(new Category("Test 3", RatingForm.OneToTen));
    }

    private void refreshCategoryTilePane() {
        categoryFlowPane.getChildren().clear();
        categoryFlowPane.getChildren().addAll(categorySet.stream().map(this::createCategoryTile).collect(Collectors.toList()));
        categoryFlowPane.getChildren().add(createAddTile(this::openNewCategoryView));
    }

    private VBox createCategoryTile(Category category) {
        VBox tile = new VBox();
        tile.setAlignment(Pos.CENTER);
        tile.setPrefSize(CATEGORY_TILE_PREF_SIZE, CATEGORY_TILE_PREF_SIZE);
        tile.getStyleClass().addAll("panel", "panel-default", "category-tile");
        Label titleLabel = new Label(category.getName());
        titleLabel.getStyleClass().add("panel-title");
        tile.getChildren().add(titleLabel);
        tile.setOnMouseClicked(e -> {
            openCategoryView(category);
        });
        return tile;
    }

    private VBox createAddTile(Runnable onAdd) {
        VBox tile = new VBox();
        tile.setAlignment(Pos.CENTER);
        tile.setPrefSize(CATEGORY_TILE_PREF_SIZE, CATEGORY_TILE_PREF_SIZE);
        tile.getStyleClass().addAll("btn", "btn-success", "category-tile");
        Label plus = new Label("+");
        plus.getStyleClass().addAll("category-add-tile-label");
        tile.getChildren().add(plus);
        tile.setOnMouseClicked(e -> onAdd.run());
        return tile;
    }

    private void openCategoryView(Category category) {
    }

    private void openNewCategoryView() {
        applicationEventPublisher.publishEvent(new NewCategoryRequestEvent(this, categorySet.stream().map(Category::getName).collect(Collectors.toSet())));
        stageManager.switchScene(FxmlView.NEW_CATEGORY);
    }
}
