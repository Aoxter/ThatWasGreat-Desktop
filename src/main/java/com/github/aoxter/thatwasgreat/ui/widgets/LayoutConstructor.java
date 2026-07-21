package com.github.aoxter.thatwasgreat.ui.widgets;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class LayoutConstructor {
    /**
     * Creates buttons bar layout
     * @return created Pane
     */
    public static Pane createButtonBar() {
        return createStyledHBox("twg-buttons-bar");
    }

    /**
     * Creates application main top pane layout
     * @return created Pane
     */
    public static Pane createTopPane() {
        return createStyledHBox("twg-top-panel-layout");
    }

    /**
     * Creates application main side navigation pane layout
     * @return created Pane
     */
    public static Pane createNavigationPane() {
        return createStyledVBox("twg-navigation-menu-layout");
    }

    /**
     * Creates layout category detail information.
     * @return created Pane
     */
    public static Pane createCategoryDetailsPane() {
        return createStyledVBox("twg-category-details-layout");
    }

    /**
     * Creates layout for category entries table
     * @return created Pane
     */
    public static Pane createCategoryTablePane() {
        return createStyledStackPane("twg-category-table-stack-pane");
    }

    /**
     * Creates stylised HBox
     * @param styleClasses style classes
     * @return created HBox
     */
    public static HBox createStyledHBox(String... styleClasses) {
        HBox hBox = new HBox();
        hBox.getStyleClass().addAll(styleClasses);
        return hBox;
    }

    /**
     * Creates stylised VBox
     * @param styleClasses style classes
     * @return created VBox
     */
    public static VBox createStyledVBox(String... styleClasses) {
        VBox vBox = new VBox();
        vBox.getStyleClass().addAll(styleClasses);
        return vBox;
    }

    /**
     * Creates stylised FlowPane
     * @param styleClasses style classes
     * @return created FlowPane
     */
    public static FlowPane createStyleFlowPane(String... styleClasses) {
        FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().addAll(styleClasses);
        return flowPane;
    }

    /**
     * Creates stylised ScrollPane
     * @param styleClasses style classes
     * @return created ScrollPane
     */
    public static ScrollPane createStyledScrollPane(Node nodeToWrap, String... styleClasses) {
        ScrollPane scrollPane = new ScrollPane(nodeToWrap);
        scrollPane.getStyleClass().addAll(styleClasses);
        return scrollPane;
    }

    /**
     * Creates stylised StackPane
     * @param styleClasses style classes
     * @return created StackPane
     */
    public static StackPane createStyledStackPane(String... styleClasses) {
        StackPane stackPane  = new StackPane();
        stackPane.getStyleClass().addAll(styleClasses);
        return stackPane;
    }

    /**
     * Creates BorderPane as root pane for central, main view of application
     * @return created BorderPane
     */
    public static BorderPane createMainViewBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().addAll("twg-main-view");
        return borderPane;
    }
}