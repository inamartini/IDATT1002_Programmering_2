package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.view.PathsApp;
import edu.ntnu.idatt2001.view.View;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Class for controlling the different screens in the application. The class is responsible for
 * adding screens to the screenMap and viewMap, and activating the screen that is to be shown.
 * The class is also responsible for setting the current scene of the application.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class ScreenController {

  /**
   * The current scene that is shown in the application.
   */
  private Scene currentScene;

  /**
   * A map of all the screens in the application.
   */
  private HashMap<String, Pane> screenMap = new HashMap<>();

  /**
   * A map of all the views in the application.
   */
  private HashMap<String, View> viewMap = new HashMap<>();


  /**
   * Constructor for the ScreenController class.
   *
   * @param primaryScene The primary scene of the application.
   */
  public ScreenController(Scene primaryScene) {
    this.currentScene = primaryScene;
  }

  /**
   * Adds a screen to the screenMap and viewMap. The screen is added
   * with a name and a view and an exception is thrown if the name
   * or view is null.
   *
   * @param name The name of the screen.
   * @param view The view of the screen.
   */
  public void addScreen(String name, View view) {
    if (name == null || view == null) {
      throw new IllegalArgumentException("Name or view cannot be null");
    }
    screenMap.put(name, view.getPane());
    viewMap.put(name, view);
  }

  /**
   * Activates the screen with the given name. The screen is activated by setting
   * the current scene to the screen with the given name. If the name is "gameView"
   * the screen is set to full screen to start the game. An exception is thrown
   * if the name is null.
   *
   * @param name name of the screen to be activated
   */
  public void activate(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    viewMap.get(name).setUp();
    currentScene.setRoot(screenMap.get(name));

    if (name.equals("gameView")) {
      PathsApp.primaryStage.setFullScreen(true);
    }
  }
}
