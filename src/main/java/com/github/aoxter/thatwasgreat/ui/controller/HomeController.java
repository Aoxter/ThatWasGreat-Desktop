package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.config.ApplicationScene;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.NewCategoryRequestEvent;
import com.github.aoxter.thatwasgreat.ui.event.OpenCategoryEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.*;
import java.util.stream.Collectors;

@Component
public class HomeController extends SceneController {
    private final static double CATEGORY_TILE_PREF_SIZE = 230.0;

    @Autowired
    CategoryService categoryService;

    @FXML
    public ScrollPane categoryScrollPane;
    @FXML
    public FlowPane categoryFlowPane;
    @FXML
    public VBox mainMenuVBox;

    private List<Category> categoryList;

    private StageManager stageManager;

    @Lazy
    public HomeController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        this.stageManager = stageManager;
        super(stageManager, applicationEventPublisher);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryList = categoryService.getAll();
        categoryScrollPane.getStyleClass().add("category-scroll-pane");
        refreshCategoryTilePane();
    }

    private void refreshCategoryTilePane() {
        categoryFlowPane.getChildren().clear();
        categoryFlowPane.getChildren().addAll(categoryList.stream().map(this::createCategoryTile).collect(Collectors.toList()));
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
//        tile.setOnMousePressed(e -> tile.setStyle("-fx-opacity: 0.85;"));
//        tile.setOnMouseReleased(e -> tile.setStyle("-fx-opacity: 1.0;"));
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
        applicationEventPublisher.publishEvent(new OpenCategoryEvent(this, category.getId()));
        if(RatingForm.TIER.equals(category.getRatingForm())) {
            switchScene(ApplicationScene.CATEGORY_TIERS);
        }
        else if(RatingForm.STARS.equals(category.getRatingForm()) || RatingForm.OneToTen.equals(category.getRatingForm())) {
            switchScene(ApplicationScene.CATEGORY_TABLE);
        }
    }

    private void openNewCategoryView() {
        applicationEventPublisher.publishEvent(new NewCategoryRequestEvent(this, categoryList.stream().map(Category::getName).collect(Collectors.toSet())));
        switchScene(ApplicationScene.NEW_CATEGORY);
    }

    public void menuCategoriesOnAction(ActionEvent actionEvent) {
    }

    public void menuSettingsOnAction(ActionEvent actionEvent) {
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stageManager.switchMinimalized();
    }

    public void maximizeWindow(ActionEvent actionEvent) {
        stageManager.switchMaximized();
    }

    public void closeWindow(ActionEvent actionEvent) {
        stageManager.close();
    }

    public void hideShowMainMenu(ActionEvent actionEvent) {
        mainMenuVBox.setVisible(!mainMenuVBox.isVisible());
        mainMenuVBox.setManaged(mainMenuVBox.isVisible());
    }
}
