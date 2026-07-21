package com.github.aoxter.thatwasgreat.ui.widgets;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder that creates tile pane for given list of elements. Elements are represented by plain text loaded from their {@link Object#toString()} implementation.
 * It supports adding new element and on click action for tiles.
 * @param <T> type of elements that will be represented by pane
 */
public class CustomTilePaneBuilder<T> implements Builder<Pane> {
    private final static double TILE_PREF_SIZE = 230.0;
    private final static double TILES_FLOW_PANE_PREF_WRAP_LENGTH = 400.0;
    private final ObservableList<T> observableList;
    private final ObjectProperty<T> selectedElement = new SimpleObjectProperty<>();
    private final Runnable tileOnClickAction;
    private final Runnable addTileOnClickAction;
    private final boolean addTileEnable;

    /**
     * Creates pane only with tiles for elements from list.
     * @param observableList list of elements, for each there will be created separate pane
     * @param tileOnClickAction action that will be executed when tile is clicked
     */
    public CustomTilePaneBuilder(ObservableList<T> observableList, Runnable tileOnClickAction) {
        this.observableList = observableList;
        this.tileOnClickAction = tileOnClickAction;
        this.addTileOnClickAction = null;
        this.addTileEnable = false;
    }

    /**
     * Creates pane with tiles for elements from list and additional tile for adding new elements on the end.
     * @param observableList list of elements, for each there will be created separate pane
     * @param tileOnClickAction action that will be executed when tile is clicked
     * @param addTileOnClickAction action that will be executed when tile for adding new element is clicked
     */
    public CustomTilePaneBuilder(ObservableList<T> observableList, Runnable tileOnClickAction, Runnable addTileOnClickAction) {
        this.observableList = observableList;
        this.tileOnClickAction = tileOnClickAction;
        this.addTileOnClickAction = addTileOnClickAction;
        this.addTileEnable = true;
    }

    /**
     * Returns created widget
     * @return created widget as Pane object
     */
    @Override
    public Pane build() {
        return createTilesPane();
    }

    /**
     * Binds unidirectionally given property to this widget selected element property. This property is changed on every click on tile.
     * If new element tile was clicked than this property indicates null value.
     * @param propertyToBind property that will be bound
     * @return this builder instance
     */
    public CustomTilePaneBuilder<T> bindSelectedElementProperty(ObjectProperty<T> propertyToBind) {
        propertyToBind.bind(selectedElement);
        return this;
    }

    private Pane createTilesPane() {
        FlowPane flowPane = LayoutConstructor.createStyleFlowPane("twg-categories-flow-pane");
        flowPane.setPrefWrapLength(TILES_FLOW_PANE_PREF_WRAP_LENGTH);
        flowPane.getChildren().addAll(createTiles());

        ScrollPane scrollPane = LayoutConstructor.createStyledScrollPane(flowPane, "twg-categories-scroll-pane");

        StackPane stackPane = LayoutConstructor.createStyledStackPane("twg-categories-stack-pane");
        stackPane.getChildren().add(scrollPane);
        return stackPane;
    }

    private Node[] createTiles() {
        List<Node> tiles = new ArrayList<>(observableList.stream().map(this::createTileForElement).toList());
        if(addTileEnable) {
            tiles.add(createAddTile());
        }
        return tiles.toArray(new Node[0]);
    }

    private Node createTileForElement(T element) {
        Node tile = createTile(element.toString(), WidgetConstructor.TileLabelSize.LARGE);
        tile.setOnMouseClicked(e -> {
            selectedElement.set(element);
            tileOnClickAction.run();
        });
        return tile;
    }

    private Node createAddTile() {
        Node tile = createTile("+", WidgetConstructor.TileLabelSize.VERY_LARGE);
        tile.setOnMouseClicked(e -> {
            selectedElement.set(null);
            addTileOnClickAction.run();
        });
        return tile;
    }

    private Node createTile(String text) {
        return createTile(text, WidgetConstructor.TileLabelSize.NORMAL, null);
    }

    private Node createTile(String text, Runnable onMouseClickRunnable) {
        return createTile(text, WidgetConstructor.TileLabelSize.NORMAL, onMouseClickRunnable);
    }

    private Node createTile(String text, WidgetConstructor.TileLabelSize labelSize) {
        return createTile(text, labelSize, null);
    }

    private Node createTile(String text, WidgetConstructor.TileLabelSize labelSize, Runnable onMouseClickRunnable) {
        VBox vBox = createTileLayout();
        Label label = WidgetConstructor.createStaticStyledLabel(text, labelSize.getStyle());
        vBox.getChildren().add(label);
        if(onMouseClickRunnable != null) {
            vBox.setOnMouseClicked(e -> {
                onMouseClickRunnable.run();
            });
        }
        return vBox;
    }

    private VBox createTileLayout() {
        VBox vBox = LayoutConstructor.createStyledVBox("panel", "panel-default", "twg-tile");
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(TILE_PREF_SIZE, TILE_PREF_SIZE);
        return vBox;
    }
}