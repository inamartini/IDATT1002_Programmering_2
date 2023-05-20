package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.view.PathsApp;
import edu.ntnu.idatt2001.view.View;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {

  private Scene currentScene;
  private HashMap<String, Pane> screenMap = new HashMap<>();
  private HashMap<String, View> viewMap = new HashMap<>();

  public ScreenController(Scene primaryScene) {
    this.currentScene = primaryScene;
  }

  public void addScreen(String name, View view) {
    screenMap.put(name, view.getPane());
    viewMap.put(name, view);
  }

  public void activate(String name) {
    viewMap.get(name).setUp();
    currentScene.setRoot(screenMap.get(name));

    if (name.equals("gameView")) {
      PathsApp.primaryStage.setFullScreen(true);
    }
  }
}
