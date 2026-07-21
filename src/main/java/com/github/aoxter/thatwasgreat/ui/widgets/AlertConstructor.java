package com.github.aoxter.thatwasgreat.ui.widgets;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertConstructor {

    /**
     * Displays confirmation popup window with {@link ButtonType#OK} and {@link ButtonType#CANCEL} buttons.
     * @param title window title
     * @param contentText message text
     * @return button used to exit window or null if none was used.
     */
    public static ButtonType showConfirmationAlert(String title, String contentText) {
        return showAlert(Alert.AlertType.CONFIRMATION, title, null, contentText);
    }

    /**
     * Displays warning popup window.
     * @param title window title
     * @param contentText message text
     */
    public static void showWarningAlert(String title, String contentText) {
        showAlert(Alert.AlertType.WARNING, title, null, contentText);
    }

    /**
     * Displays error popup window.
     * @param title window title
     * @param contentText message text
     */
    public static void showErrorAlert(String title, String contentText) {
        showAlert(Alert.AlertType.ERROR, title, null, contentText);
    }

    /**
     * Displays error popup window.
     * @param title window title
     * @param headerText text of header message
     * @param contentText message text
     */
    public static void showErrorAlert(String title, String headerText, String contentText) {
        showAlert(Alert.AlertType.ERROR, title, headerText, contentText);
    }

    private static ButtonType showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }
}
