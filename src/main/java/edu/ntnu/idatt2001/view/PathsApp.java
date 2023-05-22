package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.ScreenController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the application.
 * This class extends the Application class and is the entry point of the application.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class PathsApp extends Application {

  /**
   * The first page of the application.
   */
  private StackPane firstPage = new StackPane();

  /**
   * The scene of the first page.
   */
  private Scene frontPageScene = new Scene(firstPage, 400, 400);

  /**
   * The screen controller of the application.
   */
  private ScreenController screenController = new ScreenController(frontPageScene);


  /**
   * The HomeView of the application.
   */
  private HomeView homeView = new HomeView(screenController);

  /**
   * The LoadGameView of the application.
   */
  private LoadGameView loadGameView = new LoadGameView(screenController);

  /**
   * The PlayerView of the application.
   */
  private PlayerView playerView = new PlayerView(screenController);

  /**
   * The GameView of the application.
   */
  private GameView gameView = new GameView(screenController);

  /**
   * The GoalsView of the application.
   */
  private GoalsView goalsView = new GoalsView(screenController);

  /**
   * The primary stage of the application.
   */
  public static Stage primaryStage;


  /**
   * Starts the application.
   * @param primaryStage The primary stage of the application.
   */
  @Override
  public void start(Stage primaryStage) {
    PathsApp.primaryStage = primaryStage;

    screenController.addScreen("homeView", homeView);
    screenController.addScreen("loadGameView", loadGameView);
    screenController.addScreen("playerView", playerView);
    screenController.addScreen("gameView", gameView);
    screenController.addScreen("goalView", goalsView);

    primaryStage.setWidth(800);
    primaryStage.setHeight(500);

    primaryStage.setTitle("Paths");
    primaryStage.setScene(frontPageScene);
    primaryStage.getIcons().add(new Image("images/princess.png"));
    primaryStage.show();

    frontPageScene.getStylesheets().add("css/styles.css");

    screenController.activate("homeView");
  }

  /**
   * The main method of the application.
   * @param args The arguments of the application.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
