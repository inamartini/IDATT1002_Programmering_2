package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.PlayerViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.model.goal.Goal;
import edu.ntnu.idatt2001.model.goal.ScoreGoal;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


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
    //borderPane.setCenter(root);
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
    //ImageView princessImageView = new ImageView(princessImage);

    Image princeImage = new Image("images/prince.png");
    //ImageView princeImageView = new ImageView(princeImage);

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

    Text health = new Text("Health: ");
    health.getStyleClass().add("playerView-slider-text");
    Text playerHealth = new Text("0");

    Slider healthSlider = new Slider();
    healthSlider.setMin(0);
    healthSlider.setMax(10);
    healthSlider.setShowTickLabels(true);
    healthSlider.setShowTickMarks(true);
    healthSlider.setMajorTickUnit(5);
    healthSlider.setBlockIncrement(1);
    healthSlider.setPrefWidth(150);
    healthSlider.setValue(0);

    healthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      healthSlider.setValue(newValue.intValue());
      String valueAsString = Integer.toString(newValue.intValue());
      playerHealth.setText(valueAsString);
    });
    healthSlider.getStyleClass().add("playerView-slider");

    //playerHealth.textProperty().bind(healthSlider.valueProperty().asString());
    playerHealth.getStyleClass().add("playerView-slider-text");

    //TextField healthField = new TextField();
    //healthField.setPrefWidth(200);


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

    //playerGold.textProperty().bind(goldSlider.valueProperty().asString());
    playerGold.getStyleClass().add("playerView-slider-text");


    //TextField goldField = new TextField();
    //goldField.setPrefWidth(200);

    HBox goldBox = new HBox(10, gold, goldSlider, playerGold);
    goldBox.setAlignment(Pos.CENTER);

    VBox playerDetails = new VBox(10, nameBox, healthBox, goldBox);
    playerDetails.setAlignment(Pos.CENTER);

    Button btnCreatePlayer = new Button("Create player");
    btnCreatePlayer.setOnAction(e -> {
      if (nameField.getText().isEmpty()) {
        createAlert("Invalid input", "Name field is empty",
                "Please enter a name to create a player");
      } else {
        playerViewController.setPlayerImage(playerImageView.getImage());
        playerViewController.createPlayer(nameField.getText(), (int) healthSlider.getValue(), (int) goldSlider.getValue());
        Story story = gameViewController.getStory();
        List<Goal> goals = new ArrayList<>();
        goals.add(new ScoreGoal(100));
        //gameViewController.setGame(new Game(playerViewController.getPlayer(), story, goals));
        screenController.activate("goalView");
      }
    });
    btnCreatePlayer.getStyleClass().add("playerView-create-button");

    VBox createPlayerBox = new VBox(10, btnCreatePlayer);
    createPlayerBox.setAlignment(Pos.CENTER);

    Button btnGoBack = new Button("GO BACK");
    btnGoBack.getStyleClass().add("playerView-goBack-button");
    btnGoBack.setOnAction(e -> screenController.activate("homeView"));

    VBox content = new VBox(10, title, characterBox, playerDetails, createPlayerBox);
    content.setAlignment(Pos.CENTER);

    root.getChildren().addAll(content);

    borderPane.setTop(btnGoBack);
    borderPane.setCenter(root);

    //root.getChildren().addAll(borderPane);
    //root.setAlignment(Pos.CENTER);
  }

  public void createAlert(String title, String headerText, String contentText) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
  }

  protected void resetPane() {
    root.getChildren().clear();
  }
}
