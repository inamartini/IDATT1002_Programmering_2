
package edu.ntnu.idatt2001.view;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.util.Objects;

public class HomeView extends View {

  private ScreenController screenController;
  protected StackPane root;


  public HomeView(ScreenController screenController) {
    this.root = new StackPane();
    this.screenController = screenController;
    this.setUp();
  }

  Pane getPane() {
    return this.root;
  }

  public void setUp() {
    this.resetPane();

    Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/fantasy2.jpeg")));
    ImageView backgroundImageView = new ImageView(backgroundImage);

    Text title = new Text("Welcome to the Troll Game!");
    title.getStyleClass().add("title");
    title.getStyleClass().add("homeView-title");

    Button btnStart = new Button("START NEW GAME");
    btnStart.setOnAction(e -> screenController.activate("playerView"));
    btnStart.getStyleClass().add("homeView-button");

    Button btnLoadGame = new Button("LOAD GAME");
    btnLoadGame.setOnAction(e -> screenController.activate("loadGameView"));
    btnLoadGame.getStyleClass().add("homeView-button");

    Button btnHelp = new Button("HOW TO PLAY");
    btnHelp.setOnAction(e -> screenController.activate("helpView"));
    btnHelp.getStyleClass().add("homeView-button");

    VBox content = new VBox(10, title, btnStart, btnLoadGame, btnHelp);
    content.setAlignment(Pos.CENTER);

    root.getChildren().addAll(backgroundImageView, content);
  }

  protected void resetPane() {
    root.getChildren().clear();
  }

}

