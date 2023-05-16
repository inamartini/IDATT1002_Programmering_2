package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.view.PlayerView;
import edu.ntnu.idatt2001.view.ScreenController;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class PathsApp extends Application {

  private StackPane firstPage = new StackPane();
  private Scene frontPageScene = new Scene(firstPage, 300, 300);
  private ScreenController screenController = new ScreenController(frontPageScene);
  private HelpView helpView = new HelpView(screenController);
  private HomeView homeView = new HomeView(screenController);
  private PlayerView playerView = new PlayerView(screenController);
  private GameView gameView = new GameView(screenController);
  //private GoalsAndStoryView goalsAndStoryView = new GoalsAndStoryView(screenController);
  public static Stage primaryStage;

  @Override
  public void start(Stage primaryStage) {
    PathsApp.primaryStage = primaryStage;

    screenController.addScreen("helpView", helpView);
    screenController.addScreen("homeView", homeView);
    screenController.addScreen("playerView", playerView);
    screenController.addScreen("gameView", gameView);
    //screenController.addScreen("goalsAndStoryView", goalsAndStoryView);

    primaryStage.setWidth(800);
    primaryStage.setHeight(500);

    primaryStage.setTitle("Paths");
    primaryStage.setScene(frontPageScene);
    primaryStage.show();

    frontPageScene.getStylesheets().add("css/styles.css");

    screenController.activate("homeView");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
