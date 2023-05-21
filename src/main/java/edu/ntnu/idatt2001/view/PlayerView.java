package edu.ntnu.idatt2001.view;


import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.PlayerViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.util.AlertUtil;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Objects;


public class PlayerView extends View {

  private ScreenController screenController;
  private StackPane root;
  private BorderPane borderPane;
  private ImageView playerImageView;
  private PlayerViewController playerViewController = PlayerViewController.getInstance();
  private GameViewController gameViewController = GameViewController.getInstance();

  public PlayerView(ScreenController screenController) {
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

    Text title = new Text();
    title.setText("Choose your character");
    title.getStyleClass().add("playerView-title");

    Image princessImage = new Image("images/princess.png");

    Image princeImage = new Image("images/prince.png");

    playerImageView = new ImageView();
    playerImageView.setFitHeight(120);
    playerImageView.setFitWidth(150);

    playerImageView.setImage(princessImage);


    Image leftArrowIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/left.png")));
    ImageView leftArrow = new ImageView(leftArrowIcon);
    leftArrow.setFitHeight(30);
    leftArrow.setFitWidth(30);
    Button btnLastPlayer = new Button();
    btnLastPlayer.setGraphic(leftArrow);
    btnLastPlayer.getStyleClass().add("playerView-player-button");
    btnLastPlayer.setOnAction(e -> {
      if (playerImageView.getImage() == princessImage) {
        playerImageView.setImage(princeImage);
      } else {
        playerImageView.setImage(princessImage);
      }
    });

    Image rightArrowIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/right.png")));
    ImageView rightArrow = new ImageView(rightArrowIcon);
    rightArrow.setFitHeight(30);
    rightArrow.setFitWidth(30);
    Button btnNextPlayer = new Button();
    btnNextPlayer.setGraphic(rightArrow);
    btnNextPlayer.getStyleClass().add("playerView-player-button");
    btnNextPlayer.setOnAction(e -> {
      if (playerImageView.getImage() == princessImage) {
        playerImageView.setImage(princeImage);
      } else {
        playerImageView.setImage(princessImage);
      }
    });

    HBox characterBox = new HBox(10, btnLastPlayer, playerImageView, btnNextPlayer);
    characterBox.setAlignment(Pos.CENTER);

    Text playerName = new Text("Name:");
    TextField nameField = new TextField();
    nameField.setPrefWidth(200);

    HBox nameBox = new HBox(10, playerName, nameField);
    nameBox.setAlignment(Pos.CENTER);

    Text health = new Text("Health: ");
    health.getStyleClass().add("playerView-slider-text");
    Text playerHealth = new Text("1");

    Slider healthSlider = new Slider();
    healthSlider.setMin(1);
    healthSlider.setMax(10);
    healthSlider.setShowTickLabels(true);
    healthSlider.setShowTickMarks(true);
    healthSlider.setBlockIncrement(1);
    healthSlider.setPrefWidth(150);
    healthSlider.setValue(0);

    healthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      healthSlider.setValue(newValue.intValue());
      String valueAsString = Integer.toString(newValue.intValue());
      playerHealth.setText(valueAsString);
    });
    healthSlider.getStyleClass().add("playerView-slider");

    playerHealth.getStyleClass().add("playerView-slider-text");

    HBox healthBox = new HBox(10, health, healthSlider, playerHealth);
    healthBox.setAlignment(Pos.CENTER);

    Text gold = new Text("Gold:");
    gold.getStyleClass().add("playerView-slider-text");
    Text playerGold = new Text("0");

    Slider goldSlider = new Slider();
    goldSlider.setMin(0);
    goldSlider.setMax(200);
    goldSlider.setShowTickLabels(true);
    goldSlider.setShowTickMarks(true);
    goldSlider.setMajorTickUnit(50);
    goldSlider.setMinorTickCount(25);
    goldSlider.setBlockIncrement(5);
    goldSlider.setPrefWidth(150);
    goldSlider.setValue(0);

    goldSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      goldSlider.setValue(newValue.intValue());
      String valueAsString = Integer.toString(newValue.intValue());
      playerGold.setText(valueAsString);
    });
    goldSlider.getStyleClass().add("playerView-slider");

    playerGold.getStyleClass().add("playerView-slider-text");

    HBox goldBox = new HBox(10, gold, goldSlider, playerGold);
    goldBox.setAlignment(Pos.CENTER);

    VBox playerDetails = new VBox(10, nameBox, healthBox, goldBox);
    playerDetails.setAlignment(Pos.CENTER);

    Button btnCreatePlayer = new Button("Create player");
    btnCreatePlayer.setOnAction(e -> {
      if (nameField.getText().isEmpty()) {
        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Information",
                "Name field is empty. Please enter a name to create a player.");
      } else {
        playerViewController.setPlayerImage(playerImageView.getImage());
        playerViewController.createPlayer(nameField.getText(), (int) healthSlider.getValue(), (int) goldSlider.getValue());
        screenController.activate("goalView");
      }
    });
    btnCreatePlayer.getStyleClass().add("playerView-create-button");

    VBox createPlayerBox = new VBox(10, btnCreatePlayer);
    createPlayerBox.setAlignment(Pos.CENTER);


    Image goBackIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backbutton.png")));
    ImageView back = new ImageView(goBackIcon);
    back.setFitHeight(50);
    back.setFitWidth(50);
    Button btnGoBack = new Button();
    btnGoBack.setGraphic(back);
    btnGoBack.getStyleClass().add("goBack-button");
    btnGoBack.setOnAction(e -> screenController.activate("loadGameView"));

    VBox content = new VBox(10, title, characterBox, playerDetails, createPlayerBox);
    content.setAlignment(Pos.CENTER);

    borderPane.setTop(btnGoBack);
    borderPane.getStyleClass().add("view-background");
    root.getChildren().addAll(content);
  }

  protected void resetPane() {
    root.getChildren().clear();
  }
}
