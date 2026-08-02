package com.github.aoxter.thatwasgreat.ui.widgets;

import com.github.aoxter.thatwasgreat.core.model.RatingForm;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

public class WidgetConstructor {
    private final static double TEXT_AREA_PREF_HEIGHT = 200.0;
    private final static double TEXT_AREA_PREF_WIDTH = 200.0;

    /**
     * Creates label for main application title
     * @param stringProperty observable object containing text value that will be displayed on the label
     * @return created Label
     */
    public static Label createAppTitleLabel(StringProperty stringProperty) {
        Label label = createDynamicStyledLabel(stringProperty, "twg-app-title-label");
        label.setMaxWidth(Double.MAX_VALUE);
        BorderPane.setAlignment(label, Pos.CENTER);
        HBox.setHgrow(label, Priority.ALWAYS);
        return label;
    }

    /**
     * Creates label for category name
     * @param stringProperty observable object containing text value that will be displayed on the label
     * @return created Label
     */
    public static Label createCategoryNameLabel(StringProperty stringProperty) {
        return createDynamicStyledLabel(stringProperty, "twg-header");
    }

    /**
     * Creates label for category description
     * @param stringProperty observable object containing text value that will be displayed on the label
     * @return created Label
     */
    public static Label createCategoryDescriptionLabel(StringProperty stringProperty) {
        return createDynamicStyledLabel(stringProperty, "twg-category-description-label");
    }

    /**
     * Creates label for entry name
     * @param stringProperty observable object containing text value that will be displayed on the label
     * @return created Label
     */
    public static Label createEntryNameLabel(ObservableValue<String> stringProperty) {
        return createDynamicStyledLabel(stringProperty, "twg-header-2");
    }

    /**
     * Creates label for error information displayed on error view
     * @param stringProperty observable object containing text value that will be displayed on the label
     * @return created Label
     */
    public static Label createErrorViewLabel(StringProperty stringProperty) {
        return createDynamicStyledLabel(stringProperty, "twg-header");
    }

    /**
     * Creates dynamic label for entry rating value that will be changing font color depending on rating value. It handles only {@link RatingForm#OneToTen}
     * @param ratingBinding IntegerBinding containing rating value to display in label
     * @return created Label
     */
    public static Label createRatingLabel(IntegerBinding ratingBinding) {
        Label label = createDynamicStyledLabel(ratingBinding.asString().concat("/10"), "twg-digits-rating-label");
        label.textFillProperty().bind(Bindings.createObjectBinding(() -> getColorForRating(ratingBinding.get()), ratingBinding));
        return label;
    }

    /**
     * Returns appropriate color for given rating. It is dedicated for {@link RatingForm#OneToTen}
     * @param rating value representing rating, values lesser than 1 will be considered as 1 and values bigger than 10 will be considered as 10
     * @return Color for given rating,
     */
    private static Color getColorForRating(int rating) {
        rating = Math.max(1, Math.min(10, rating));
        return switch (rating) {
            case 1 -> Color.rgb(180, 0, 0);
            case 2 -> Color.rgb(220, 50, 50);
            case 3 -> Color.rgb(240, 100, 40);
            case 4 -> Color.rgb(255, 160, 0);
            case 5 -> Color.rgb(255, 210, 0);
            case 6 -> Color.rgb(230, 230, 0);
            case 7 -> Color.rgb(150, 210, 0);
            case 8 -> Color.rgb(70, 180, 70);
            case 9 -> Color.rgb(20, 140, 50);
            case 10 -> Color.rgb(0, 100, 30);
            default -> Color.BLACK;
        };
    }

    /**
     * Creates regular static label
     * @param text text that will be displayed on the label
     * @return created Label
     */
    public static Label createStaticLabel(String text) {
        return createStaticStyledLabel(text);
    }

    /**
     * Creates regular dynamic label
     * @param stringProperty observable object containing text value that will be displayed on the label
     * @return created Label
     */
    public static Label createDynamicLabel(ObservableValue<String> stringProperty) {
        return createDynamicStyledLabel(stringProperty);
    }

    /**
     * Creates Label with static text
     * @param text text to display
     * @param styleClasses style classes
     * @return created Label
     */
    public static Label createStaticStyledLabel(String text, String... styleClasses) {
        Label label = createStyledLabel(styleClasses);
        label.setText(text);
        return label;
    }

    /**
     * Creates Label with dynamic text, when value or observable are null then empty string is displayed
     * @param observableValue ObservableValue of String with text to display, it will be bound to Label's text property
     * @param styleClasses style classes
     * @return created Label
     */
    public static Label createDynamicStyledLabel(ObservableValue<String> observableValue, String... styleClasses) {
        Label label = createStyledLabel(styleClasses);
        label.textProperty().bind(Bindings.createStringBinding(
                () -> observableValue == null || observableValue.getValue() == null ? "" : observableValue.getValue(),
                observableValue
            )
        );
        return label;
    }

    private static Label createStyledLabel(String... styleClasses) {
        Label label = new Label();
        label.getStyleClass().addAll(styleClasses);
        label.setWrapText(true);
        return label;
    }

    /**
     * Creates stylised button for application main top and side menu
     * @param text text to displayed on the button
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createMainMenuButton(String text, Runnable onActionRunnable) {
        return createStaticStyledButton(text, onActionRunnable, "twg-main-menu-button", "large");
    }

    /**
     * Creates stylised button for application main top and side menu with dynamic text
     * @param stringProperty observable object containing text value that will be displayed on the button
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createDynamicMainMenuButton(StringProperty stringProperty, Runnable onActionRunnable) {
        return createDynamicStyledButton(stringProperty, onActionRunnable, "twg-main-menu-button", "large");
    }

    /**
     * Creates button stylised for save/confirm action
     * @param text text to displayed on the button
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createSaveButton(String text, Runnable onActionRunnable) {
        return createSaveButton(text, null, onActionRunnable);
    }

    /**
     * Creates button stylised for save/confirm action with control of availability
     * @param text text to displayed on the button
     * @param disabledBinding binding for controlling if button is disabled or enabled
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createSaveButton(String text, BooleanBinding disabledBinding, Runnable onActionRunnable) {
        return createStaticStyledButton(text, disabledBinding, onActionRunnable, "success", "large");
    }

    /**
     * Creates button stylised for neutral actions like go back
     * @param text text to displayed on the button
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createNeutralButton(String text, Runnable onActionRunnable) {
        return createNeutralButton(text, null, onActionRunnable);
    }

    /**
     * Creates button stylised for neutral actions like go back with control of availability
     * @param text text to displayed on the button
     * @param disabledBinding binding for controlling if button is disabled or enabled
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createNeutralButton(String text, BooleanBinding disabledBinding, Runnable onActionRunnable) {
        return createStaticStyledButton(text, disabledBinding, onActionRunnable, "accent", "large");
    }

    /**
     * Creates button stylised for cancel action
     * @param text text to displayed on the button
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createCancelButton(String text, Runnable onActionRunnable) {
        return createCancelButton(text, null, onActionRunnable);
    }

    /**
     * Creates button stylised for cancel action with control of availability
     * @param text text to displayed on the button
     * @param disabledBinding binding for controlling if button is disabled or enabled
     * @param onActionRunnable runnable that will be executed whenever this button is fired
     * @return created Button
     */
    public static Button createCancelButton(String text, BooleanBinding disabledBinding, Runnable onActionRunnable) {
        return createStaticStyledButton(text, disabledBinding, onActionRunnable, "danger", "large");
    }

    private static Button createStaticStyledButton(String text, Runnable onActionRunnable, String... styleClasses) {
        return createStaticStyledButton(text, null, onActionRunnable, styleClasses);
    }

    private static Button createStaticStyledButton(String text, BooleanBinding disabledBinding, Runnable onActionRunnable, String... styleClasses) {
        Button button = createStyledButton(onActionRunnable, styleClasses);
        button.setText(text);
        if(disabledBinding != null) {
            button.disableProperty().bind(disabledBinding);
        }
        return button;
    }

    private static Button createDynamicStyledButton(StringProperty stringProperty, Runnable onActionRunnable, String... styleClasses) {
        return createDynamicStyledButton(stringProperty, null, onActionRunnable, styleClasses);
    }

    private static Button createDynamicStyledButton(StringProperty stringProperty, BooleanBinding disabledBinding, Runnable onActionRunnable, String... styleClasses) {
        Button button = createStyledButton(onActionRunnable, styleClasses);
        button.textProperty().bind(stringProperty);
        if(disabledBinding != null) {
            button.disableProperty().bind(disabledBinding);
        }
        return button;
    }

    private static Button createStyledButton(Runnable onActionRunnable, String... styleClasses) {
        Button button = new Button();
        button.getStyleClass().addAll(styleClasses);
        button.setOnAction(evt -> onActionRunnable.run());
        return button;
    }

    /**
     * Creates TextField bidirectionally bounded with given property
     * @param property property to bound with TextField widget
     * @return created widget
     */
    public static TextField createBoundTextField(StringProperty property) {
        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(property);
        return textField;
    }

    /**
     * Creates TextArea bidirectionally bounded with given property
     * @param property property to bound with TextArea widget
     * @return created widget
     */
    public static TextArea createBoundTextArea(StringProperty property) {
        TextArea textArea = new TextArea();
        textArea.prefHeight(TEXT_AREA_PREF_HEIGHT);
        textArea.prefWidth(TEXT_AREA_PREF_WIDTH);
        textArea.setWrapText(true);
        textArea.textProperty().bindBidirectional(property);
        return textArea;
    }

    /**
     * Creates vertically oriented toggle group
     * @param options list of objects to display as toggle group, for each will be created RadioButton with text from object's {@link Object#toString()} method
     * @param propertyToBind property to which object from currently selected RadioButton will be bound
     * @return created widget
     * @param <T> type of objects represented by toogle group
     */
    public static <T> Node createVerticalToggleGroup(List<T> options, ObjectProperty<T> propertyToBind) {
        VBox vBox = LayoutConstructor.createStyledVBox("twg-toggle-group-layout");
        createToggleGroup(vBox, options, propertyToBind);
        return vBox;
    }

    /**
     * Creates horizontally oriented toggle group
     * @param options list of objects to display as toggle group, for each will be created RadioButton with text from object's {@link Object#toString()} method
     * @param propertyToBind property to which object from currently selected RadioButton will be bound
     * @return created widget
     * @param <T> type of objects represented by toogle group
     */
    public static <T> Node createHorizontalToggleGroup(List<T> options, ObjectProperty<T> propertyToBind) {
        HBox hBox = LayoutConstructor.createStyledHBox("twg-toggle-group-layout");
        createToggleGroup(hBox, options, propertyToBind);
        return hBox;
    }

    private static <T> void createToggleGroup(Pane container, List<T> options, ObjectProperty<T> propertyToBind) {
        ToggleGroup toggleGroup = new ToggleGroup();
        for(T option : options) {
            RadioButton radioButton = new RadioButton(option.toString());
            radioButton.setUserData(option);
            radioButton.setToggleGroup(toggleGroup);
            container.getChildren().add(radioButton);
        }
        propertyToBind.bind(
                Bindings.createObjectBinding(
                        () -> {
                            Toggle toggle = toggleGroup.getSelectedToggle();
                            return toggle == null ? null : (T) toggle.getUserData();
                        },
                        toggleGroup.selectedToggleProperty()
                )
        );
    }

    /**
     * Enum containing available sizes for labels inside tile widget with their css styles
     */
    public enum TileLabelSize {
        NORMAL {
            @Override
            public String getStyle() {
                return "twg-tile-label-default";
            }
        },
        LARGE {
            @Override
            public String getStyle() {
                return "twg-tile-label-large";
            }
        },
        VERY_LARGE {
            @Override
            public String getStyle() {
                return "twg-tile-label-very-large";
            }
        };

        /**
         * Return css style for given size
         * @return css class name
         */
        public abstract String getStyle();
    }
}