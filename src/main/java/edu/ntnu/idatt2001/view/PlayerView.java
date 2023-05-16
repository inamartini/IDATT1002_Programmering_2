package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.PlayerViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class PlayerView extends View {

  private ScreenController screenController;
  private StackPane root;
  private ImageView playerImageView;
  private PlayerViewController playerViewController;

  public PlayerView(ScreenController screenController) {
    this.screenController = screenController;
    this.playerViewController = PlayerViewController.getInstance();
    this.root = new StackPane();
    this.setUp();
  }

  public StackPane getPane() {
    return this.root;
  }

  public void setUp() {
    this.resetPane();

    Text title = new Text();
    title.setText("Choose your character");
    title.getStyleClass().add("playerView-title");

    Image princessImage = new Image("images/princess.png");
    ImageView princessImageView = new ImageView(princessImage);

    Image princeImage = new Image("images/prince.png");
    ImageView princeImageView = new ImageView(princeImage);

    playerImageView = new ImageView();

    playerImageView.setImage(princessImage);

    Button btnLastPlayer = new Button("back");
    btnLastPlayer.setOnAction(e -> {
      if (playerImageView.getImage() == princessImage) {
        playerImageView.setImage(princeImage);
      } else {
        playerImageView.setImage(princessImage);
      }
    });

    btnLastPlayer.getStyleClass().add("playerView-player-button");

    Button btnNextPlayer = new Button("next");
    btnNextPlayer.setOnAction(e -> {
      if (playerImageView.getImage() == princessImage) {
        playerImageView.setImage(princeImage);
      } else {
        playerImageView.setImage(princessImage);
      }
    });

    btnNextPlayer.getStyleClass().add("playerView-player-button");

    HBox characterBox = new HBox(10, btnLastPlayer, playerImageView, btnNextPlayer);
    characterBox.setAlignment(Pos.CENTER);

    Text playerName = new Text("Name:");
    TextField nameField = new TextField();
    nameField.setPrefWidth(200);

    HBox nameBox = new HBox(10, playerName, nameField);
    nameBox.setAlignment(Pos.CENTER);

    Text playerHealth = new Text("Health:");
    TextField healthField = new TextField();
    healthField.setPrefWidth(200);

    HBox healthBox = new HBox(10, playerHealth, healthField);
    healthBox.setAlignment(Pos.CENTER);

    Text playerGold = new Text("Gold:   ");
    TextField goldField = new TextField();
    goldField.setPrefWidth(200);

    HBox goldBox = new HBox(10, playerGold, goldField);
    goldBox.setAlignment(Pos.CENTER);

    VBox playerDetails = new VBox(10, nameBox, healthBox, goldBox);
    playerDetails.setAlignment(Pos.CENTER);

    Button btnCreatePlayer = new Button("Create Player");
    btnCreatePlayer.setOnAction(e -> {
      playerViewController.setPlayerImage(playerImageView.getImage());
      screenController.activate("gameView");
    });
    btnCreatePlayer.getStyleClass().add("playerView-create-button");

    VBox createPlayerBox = new VBox(10, btnCreatePlayer);
    createPlayerBox.setAlignment(Pos.CENTER);

    Button btnGoBack = new Button("GO BACK");
    btnGoBack.getStyleClass().add("playerView-goBack-button");
    btnGoBack.setOnAction(e -> screenController.activate("homeView"));

    VBox content = new VBox(10, title, characterBox, playerDetails, createPlayerBox);
    content.setAlignment(Pos.CENTER);

    BorderPane borderPane = new BorderPane();
    borderPane.setTop(btnGoBack);
    borderPane.setCenter(content);

    root.getChildren().addAll(borderPane);
    root.setAlignment(Pos.CENTER);
  }

  protected void resetPane() {
    root.getChildren().clear();
  }
}
