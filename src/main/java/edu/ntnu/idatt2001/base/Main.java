package edu.ntnu.idatt2001.base;

import edu.ntnu.idatt2001.view.PathsApp;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    try {
      PathsApp pathsApp = new PathsApp();
      pathsApp.start(primaryStage);
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
