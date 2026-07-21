package com.github.aoxter.thatwasgreat.ui.widgets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class FormBuilder implements Builder<Pane>{
    private final Pane formPane;

    /**
     * Builder that creates form.
     * It provides methods to create positions inside the form with or without validation.
     * Validation is resolved by red font label with given text under the position.
     */
    public FormBuilder() {
        formPane = LayoutConstructor.createStyledVBox("twg-form-pane");
    }

    /**
     * Returns created Form widget
     * @return created form as Pane object
     */
    @Override
    public Pane build() {
        return formPane;
    }

    /**
     * Add new position without validation to the form.
     * @param labelText text explaining this position
     * @param widget widget for user input
     * @return this builder instance
     */
    public FormBuilder addPosition(String labelText, Node widget) {
        VBox vBox = LayoutConstructor.createStyledVBox("twg-form-position");
        vBox.getChildren().add(new Label(labelText));
        vBox.getChildren().add(widget);
        formPane.getChildren().add(vBox);
        return this;
    }

    /**
     * Add new position with validation to the form.
     * @param labelText text explaining this position
     * @param widget widget for user input
     * @param showMessageProperty property indicating when validation is failed and error label should be displayed
     * @param messageProperty property containing message for failed validation
     * @return this builder instance
     */
    public FormBuilder addPositionWithValidation(String labelText, Node widget, BooleanProperty showMessageProperty, StringProperty messageProperty) {
        VBox vBox = LayoutConstructor.createStyledVBox("twg-form-position");
        vBox.getChildren().add(new Label(labelText));
        vBox.getChildren().add(widget);
        formPane.getChildren().add(vBox);
        formPane.getChildren().add(createFormValidationLabel(showMessageProperty, messageProperty));
        return this;
    }

    private Label createFormValidationLabel(BooleanProperty showProperty, StringProperty messageProperty) {
        Label validationLabel = WidgetConstructor.createDynamicStyledLabel(messageProperty, "twg-form-validation-message");
        validationLabel.visibleProperty().bind(showProperty);
        validationLabel.managedProperty().bind(showProperty);
        return validationLabel;
    }
}