package com.github.aoxter.thatwasgreat.ui.controller;

import com.github.aoxter.thatwasgreat.core.model.Category;
import com.github.aoxter.thatwasgreat.core.model.Entry;
import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import com.github.aoxter.thatwasgreat.core.service.CategoryService;
import com.github.aoxter.thatwasgreat.ui.config.ApplicationScene;
import com.github.aoxter.thatwasgreat.ui.config.StageManager;
import com.github.aoxter.thatwasgreat.ui.event.NewCategoryRequestEvent;
import com.github.aoxter.thatwasgreat.ui.event.OpenCategoryEvent;
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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HomeController extends SceneControler {
    private final static double CATEGORY_TILE_PREF_SIZE = 230.0;

    @Autowired
    CategoryService categoryService;

    @FXML
    public ScrollPane categoryScrollPane;
    @FXML
    public FlowPane categoryFlowPane;

    private Set<Category> categorySet;

    @Lazy
    public HomeController(StageManager stageManager, ApplicationEventPublisher applicationEventPublisher) {
        super(stageManager, applicationEventPublisher);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(categorySet == null) {
            populateWithTestData();
        }
        categoryScrollPane.getStyleClass().add("category-scroll-pane");
        refreshCategoryTilePane();
    }

    private void populateWithTestData() {
        categorySet = new LinkedHashSet<>(categoryService.getAll());
        Category category1 = new Category("Test 1", RatingForm.OneToTen);
        category1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu felis venenatis, tincidunt mauris sed, pharetra justo. Ut congue lectus dolor, et sollicitudin ipsum fermentum ut. Nam imperdiet tempor augue tristique facilisis. Mauris maximus augue id velit aliquet tempus. Sed a facilisis ligula, nec condimentum risus. Suspendisse potenti. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Quisque eget felis ut dui pretium mollis at id risus.");
        category1.getEntries().add(new Entry(category1, "Lorem Entrum", "Lorem ipsum dolor sit amet", (byte) 6, new HashMap <>()));
        categorySet.add(category1);
        categorySet.add(new Category("Test 2", RatingForm.TIER));
        categorySet.add(new Category("Test 3", RatingForm.STARS));
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
        applicationEventPublisher.publishEvent(new OpenCategoryEvent(this, category));
        if(RatingForm.TIER.equals(category.getRatingForm())) {
            switchScene(ApplicationScene.CATEGORY_TIERS);
        }
        else if(RatingForm.STARS.equals(category.getRatingForm()) || RatingForm.OneToTen.equals(category.getRatingForm())) {
            switchScene(ApplicationScene.CATEGORY_TABLE);
        }
    }

    private void openNewCategoryView() {
        applicationEventPublisher.publishEvent(new NewCategoryRequestEvent(this, categorySet.stream().map(Category::getName).collect(Collectors.toSet())));
        switchScene(ApplicationScene.NEW_CATEGORY);
    }
}
