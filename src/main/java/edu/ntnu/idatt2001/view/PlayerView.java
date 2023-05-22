package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.PlayerController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.util.AlertUtil;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * This class extends the View class and is responsible for the player view of the application.
 * The player view is the page where the user can choose a character.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class PlayerView extends View {

  /**
   * The screen controller is used to switch between screens.
   */
  private ScreenController screenController;

  /***
   * The root is the main layout of the player view.
   */
  private StackPane root;
  /**
   * The border pane is the layout of the player view.
   */
  private BorderPane borderPane;

  /**
   * The player controller is used to create a player.
   */
  private PlayerController playerController = PlayerController.getInstance();

  /**
   * The player view slider text styling class.
   */
  private static final String PLAYERVIEW_SLIDER_TEXT = "playerView-slider-text";

  /**
   * The PlayerView constructor. It takes in a screen controller as a parameter.
   *
   * @param screenController the screen controller
   */
  public PlayerView(ScreenController screenController) {
    this.root = new StackPane();
    this.borderPane = new BorderPane();
    borderPane.setCenter(root);
    this.screenController = screenController;
  }

  /**
   * Resets the pane.
   *
   * @return the pane
   */
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   *  This method configures the layout and components of the player view.
   *  It allows the player to choose their character, enter their name, set health and gold values using sliders,
   *  and create a player profile.
   * <p>
   *  This method performs the following steps:
   *  1. Reset the pane to its initial state.
   *  2. Create and configure the title text.
   *  3. Set up the character selection area with previous and next player buttons.
   *  4. Set up the name input field.
   *  5. Set up the health and gold slider and corresponding text.
   *  6. Set up the create player button, go back button
   *  7. Set up the main content layout and add all the components to it.
   */
  public void setUp() {
    ImageView playerImageView;
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
    health.getStyleClass().add(PLAYERVIEW_SLIDER_TEXT);
    Text playerHealth = new Text("1");

    Slider healthSlider = new Slider();
    healthSlider.setMin(1);
    healthSlider.setMax(10);
    healthSlider.setShowTickLabels(true);
    healthSlider.setShowTickMarks(true);
    healthSlider.setBlockIncrement(1);
    healthSlider.setPrefWidth(150);
    healthSlider.setValue(1);

    healthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      healthSlider.setValue(newValue.intValue());
      String valueAsString = Integer.toString(newValue.intValue());
      playerHealth.setText(valueAsString);
    });
    healthSlider.getStyleClass().add(PLAYERVIEW_SLIDER_TEXT);

    playerHealth.getStyleClass().add(PLAYERVIEW_SLIDER_TEXT);

    HBox healthBox = new HBox(10, health, healthSlider, playerHealth);
    healthBox.setAlignment(Pos.CENTER);

    Text gold = new Text("Gold:");
    gold.getStyleClass().add(PLAYERVIEW_SLIDER_TEXT);
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

    playerGold.getStyleClass().add(PLAYERVIEW_SLIDER_TEXT);

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
        playerController.setPlayerImage(playerImageView.getImage());
        playerController.createPlayer(nameField.getText(), (int) healthSlider.getValue(), (int) goldSlider.getValue());
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

  /**
   * Resets the pane. Clears the pane of all elements.
   */
  protected void resetPane() {
    root.getChildren().clear();
  }
}
