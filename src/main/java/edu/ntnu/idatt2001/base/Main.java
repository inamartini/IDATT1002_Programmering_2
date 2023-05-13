package edu.ntnu.idatt2001.base;

import edu.ntnu.idatt2001.controller.HomePageController;
import edu.ntnu.idatt2001.controller.ChoosePlayerController;
import edu.ntnu.idatt2001.controller.PlayGameController;
import edu.ntnu.idatt2001.view.ChoosePlayerView;
import edu.ntnu.idatt2001.view.HomePageView;
import edu.ntnu.idatt2001.view.PlayGameView;
import edu.ntnu.idatt2001.view.ViewSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {

    ViewSwitcher viewSwitcher = new ViewSwitcher(primaryStage);
    viewSwitcher.setPrimaryStage(primaryStage);

    HomePageController homePageController = new HomePageController(viewSwitcher);
    ChoosePlayerController choosePlayerController = new ChoosePlayerController(viewSwitcher);
    PlayGameController playGameController = new PlayGameController(viewSwitcher, "src/test/resources/scaryStory.paths");

    HomePageView homePageView = new HomePageView(homePageController);
    ChoosePlayerView choosePlayerView = new ChoosePlayerView(choosePlayerController);
    PlayGameView playGameView = new PlayGameView(playGameController);

    viewSwitcher.setHomePageView(homePageView);
    viewSwitcher.setChoosePlayerView(choosePlayerView);
    viewSwitcher.setPlayGameView(playGameView);

    primaryStage.setTitle("Troll Game");

    primaryStage.setScene(viewSwitcher.getCurrentScene());

    viewSwitcher.switchToHomePageView();

    primaryStage.setFullScreen(true);

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
