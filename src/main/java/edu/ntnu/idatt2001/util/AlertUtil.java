package edu.ntnu.idatt2001.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Utility class for showing alerts.
 *
 * @author Ina Martini
 * @author Malin Haugland Høli
 * @version 1.0 2023.05.22
 */
public class AlertUtil {

    /**
     * Shows an alert with the given parameters.
     * @param alertType The type of alert to show.
     * @param title The title of the alert.
     * @param message The message of the alert.
     */
    public static void showAlert(AlertType alertType, String title, String message) {
        Alert alertUtil = new Alert(alertType);
        alertUtil.setTitle(title);
        alertUtil.setHeaderText(null);
        alertUtil.setContentText(message);
        alertUtil.showAndWait();
    }

    /**
     * Private constructor to hide the implicit public one.
     */
    private AlertUtil() {
    }

}
