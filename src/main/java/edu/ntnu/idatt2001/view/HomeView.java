
package edu.ntnu.idatt2001.view;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.util.Objects;

public class HomeView extends View {

  private ScreenController screenController;
  private StackPane root;
  private BorderPane borderPane;


  public HomeView(ScreenController screenController) {
    this.root = new StackPane();
    this.borderPane = new BorderPane();
    borderPane.setCenter(root);
    this.screenController = screenController;
  }

  public Pane getPane() {
    return this.borderPane;
  }

  public void setUp() {
    this.resetPane();

    Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/background5.png")));
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            new BackgroundSize(1.0 , 1.0 , true, true, false, true));

    root.setBackground(new Background(background));

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

    root.getChildren().addAll(content);
  }

  protected void resetPane() {
    root.getChildren().clear();
  }

}

