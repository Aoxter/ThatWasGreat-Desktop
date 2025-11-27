package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HomeController implements Initializable {
    private final StageManager stageManager;
    @Autowired
    CategoryService categoryService;
    @FXML
    private TilePane categoryTilePane;

    private Set<Category> categorySet;

    @Lazy
    public HomeController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateWithTestData();
        refreshCategoryTilePane();
    }

    private void populateWithTestData() {
        categorySet = new HashSet<>(categoryService.getAll());
        categorySet.add(new Category("Test 1", RatingForm.STARS));
        categorySet.add(new Category("Test 2", RatingForm.TIER));
        categorySet.add(new Category("Test 3", RatingForm.OneToTen));
    }

    private void refreshCategoryTilePane() {
        categoryTilePane.getChildren().clear();
        categoryTilePane.getChildren().addAll(categorySet.stream().map(this::createCategoryTile).collect(Collectors.toList()));
        categoryTilePane.getChildren().add(createAddTile(this::openNewCategoryView));
    }

    private VBox createCategoryTile(Category category) {
        VBox tile = new VBox();
        tile.setPadding(new Insets(10));
        tile.setAlignment(Pos.CENTER);
        tile.setPrefSize(120, 120);
        tile.setStyle("-fx-border-color: #999; -fx-border-radius: 5; -fx-background-color: #eee;");
        tile.getChildren().add(new Label(category.getName()));
        tile.setOnMouseClicked(e -> {
            openCategoryView(category);
        });
        return tile;
    }

    private VBox createAddTile(Runnable onAdd) {
        VBox tile = new VBox();
        tile.setPadding(new Insets(10));
        tile.setAlignment(Pos.CENTER);
        tile.setPrefSize(120, 120);
        tile.setStyle("-fx-border-color: #999; -fx-border-radius: 5; -fx-background-color: #ddd;");
        Label plus = new Label("+");
        plus.setStyle("-fx-font-size: 40; -fx-text-fill: #666;");
        tile.getChildren().add(plus);
        tile.setOnMouseClicked(e -> onAdd.run());
        return tile;
    }

    private void openCategoryView(Category category) {
    }

    private void openNewCategoryView() {
        categorySet.add(new Category("Test " + String.valueOf(categorySet.size()+1), RatingForm.STARS));
        refreshCategoryTilePane();
    }
}
