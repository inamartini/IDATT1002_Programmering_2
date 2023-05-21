package edu.ntnu.idatt2001.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {

    public static void showAlert(AlertType alertType, String title, String message) {
        Alert alertUtil = new Alert(alertType);
        alertUtil.setTitle(title);
        alertUtil.setHeaderText(null);
        alertUtil.setContentText(message);
        alertUtil.showAndWait();
    }

}
