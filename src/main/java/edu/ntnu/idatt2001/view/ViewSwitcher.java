package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.ViewController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewSwitcher implements ViewController {

  private Stage primaryStage;
  private HomePageView homePageView;
  private ChoosePlayerView choosePlayerView;
  private PlayGameView playGameView;
  private Scene currentScene; // This stores the current scene
  private double previousWidth, previousHeight;

  public ViewSwitcher(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.previousWidth = primaryStage.getWidth();
    this.previousHeight = primaryStage.getHeight();
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  @Override
  public void switchToHomePageView() {
    saveStageDimensions();
    primaryStage.setScene(homePageView);
    this.currentScene = homePageView; // Update the current scene
    restoreStageDimensions();
  }

  @Override
  public void switchToChoosePlayerView() {
    saveStageDimensions();
    primaryStage.setScene(choosePlayerView);
    this.currentScene = choosePlayerView; // Update the current scene
    restoreStageDimensions();
  }

  @Override
  public void switchToPlayGameView() {
    saveStageDimensions();
    primaryStage.setScene(playGameView);
    this.currentScene = playGameView; // Update the current scene
    restoreStageDimensions();
  }

  private void saveStageDimensions() {
    this.previousWidth = primaryStage.getWidth();
    this.previousHeight = primaryStage.getHeight();
  }

  private void restoreStageDimensions() {
    primaryStage.setWidth(previousWidth);
    primaryStage.setHeight(previousHeight);
  }

  public void setHomePageView(HomePageView scene) {
    this.homePageView = scene;
  }

  public void setChoosePlayerView(ChoosePlayerView scene) {
    this.choosePlayerView = scene;
  }

  public void setPlayGameView(PlayGameView scene) {
    this.playGameView = scene;
  }

  public PlayGameView getPlayGameView() {
    return this.playGameView;
  }

  public ChoosePlayerView getChoosePlayerView() {
    return this.choosePlayerView;
  }

  // Method to get the current scene
  public Scene getCurrentScene() {
    return this.currentScene;
  }
}
