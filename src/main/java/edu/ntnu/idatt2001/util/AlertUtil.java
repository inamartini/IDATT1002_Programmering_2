package edu.ntnu.idatt2001.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class for showing alerts. The class has one method for showing alerts with different alert types.
 * Used in the views to display alerts to the user.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class AlertUtil {

  /**
   * Method for showing alerts with different alert types.
   * The method takes in the alert type, title and message.
   * The method shows the alert and waits for the user to close it.
   *
   * @param alertType the type of alert
   * @param title     the title of the alert
   * @param message   the message of the alert
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
