package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.PlayerController;
import edu.ntnu.idatt2001.model.action.Action;
import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.model.Passage;
import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.util.AlertUtil;
import edu.ntnu.idatt2001.util.SoundPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Objects;


/**
 * This class extends the View class and is responsible for the game view of the application.
 * The game view is the page where the game is played.
 * It displays the story title, passage title, passage content,
 * player image, troll image, inventory, goals, and buttons.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 *
 */
public class GameView extends View {

  /**
   * The root of the application.
   */
  private StackPane root;

  /**
   * The border pane of the application.
   */
  private BorderPane borderPane;

  /**
   * The screen controller of the application.
   */
  private ScreenController screenController;

  /**
   * The story Title of the application.
   */
  private Text storyTitleText;

  /**
   * The content of the story.
   */
  private VBox content;

  /**
   * The passage title of the application.
   */
  private Text passageTitleText;

  /**
   * The text area where the passages will be displayed.
   */
  private TextArea textArea;

  /**
   * The home button.
   */
  private Button btnHome;

  /**
   * The restart game button.
   */
  private Button btnRestartGame;

  /**
   * The game view controller of the application.
   */
  private GameViewController gameViewController = GameViewController.getInstance();

  /**
   * The player text to hold the information about the player.
   */
  private Label playerText;

  /**
   * The inventory image.
   */
  private ImageView inventoryImage;

  /**
   * The inventory box to display the inventory.
   */
  private HBox inventoryBox;

  /**
   * The goals box to display the goals.
   */
  private VBox goalsBox;

  /**
   * The pane to display the player image.
   */
  private Pane imageWrapper;

  /**
   * The sound player of the application.
   */
  private SoundPlayer soundPlayer = new SoundPlayer();

  /**
   * The player controller of the application.
   */
  private PlayerController playerController = PlayerController.getInstance();

  /**
   * The did I win button.
   */
  private Button didIWinButton;

  /**
   * The GameView constructor. It takes in the screen controller as a parameter.
   * Sets up the game view.
   *
   * @param screenController the screen controller
   */
  public GameView(ScreenController screenController) {
    this.root = new StackPane();
    this.borderPane = new BorderPane();
    borderPane.setCenter(root);
    this.screenController = screenController;
  }

  /**
   * This method gets the pane.
   *
   * @return the pane
   */
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Sets up the game view by initializing the game, playing background music, and configuring various UI elements.
   * The center of the border pane is set to the root node.
   * It initializes the game, plays the background music, and handles any exceptions that occur during the initialization process.
   * Creates and configures UI elements such as story title text, passage title text, text area, content pane, character images,
   * home button, menu bar, inventory box, player text, goals box, and the border pane.
   * Passages and buttons are generated based on the current passage of the game.
   * The pane structure and styling are set up accordingly.
   */
  public void setUp() {
    borderPane.setCenter(root);

    try {
      gameViewController.initializeGame();
      soundPlayer.playOnLoop("/sound/fairytale.wav");
    } catch (Exception e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
    }
    storyTitleText = new Text(gameViewController.getGame().getStory().getTitle());
    storyTitleText.getStyleClass().add("gameView-story-title");

    passageTitleText = new Text(gameViewController.getGame().getCurrentPassage().getTitle());
    passageTitleText.getStyleClass().add("gameView-passage-title");

    textArea = new TextArea(gameViewController.getGame().getCurrentPassage().getContent());
    textArea.setEditable(false);
    textArea.setPrefWidth(500);
    textArea.getStyleClass().add("gameView-passage-content");

    content = new VBox(10, storyTitleText, passageTitleText, textArea);
    content.setAlignment(Pos.CENTER);

    ImageView trollImage = new ImageView(new Image("images/troll.png"));

    ImageView playerImage = new ImageView(gameViewController.getPlayerImage());

    HBox contentWithCharacter = new HBox(10, playerImage, content, trollImage);
    contentWithCharacter.setAlignment(Pos.CENTER);

    Image homeIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/homebutton.png")));
    ImageView home = new ImageView(homeIcon);
    home.setFitHeight(50);
    home.setFitWidth(50);
    btnHome = new Button();
    btnHome.setGraphic(home);
    btnHome.getStyleClass().add("gameView-returnHome-button");
    btnHome.setAlignment(Pos.TOP_LEFT);
    btnHome.setOnAction(e ->  {
      try {
        soundPlayer.stopPlaying();
        screenController.activate("homeView");
      } catch (IllegalArgumentException ex) {
        AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
      }
      this.resetPane();
    });

    VBox menuBar = new VBox();
    menuBar.getChildren().addAll(btnHome, showHelpAlert());
    menuBar.setAlignment(Pos.TOP_LEFT);

    inventoryImage = new ImageView();

    inventoryBox = new HBox(10, inventoryImage);
    inventoryBox.setAlignment(Pos.TOP_CENTER);

    playerText = new Label();
    refreshLabel();

    imageWrapper = new Pane();
    imageWrapper.getChildren().add(inventoryBox);
    imageWrapper.setPrefWidth(150);
    imageWrapper.setPrefHeight(150);

    goalsBox = gameViewController.goalStatus();

    HBox rightBox = new HBox(10, goalsBox, playerText, imageWrapper);
    rightBox.setSpacing(25);
    rightBox.setAlignment(Pos.TOP_CENTER);

    VBox topInfo = new VBox();
    topInfo.getChildren().addAll(menuBar, rightBox);
    topInfo.setAlignment(Pos.TOP_CENTER);

    borderPane.setTop(topInfo);

    generatePassagesAndButtons(gameViewController.getGame().getCurrentPassage());

    borderPane.getStyleClass().add("view-background");
    root.getChildren().addAll(contentWithCharacter);
  }

  /**
   *  Generates passages and buttons based on the given Passage object.
   *  It sets the story title, current passage, passage title, and content in the respective UI elements.
   *  It removes any existing buttons from the content pane.
   *  If the current passage has no links, it stops the sound player, creates end game buttons, and adds them to the content pane.
   *  Otherwise, it generates buttons for each link in the current passage's list of links.
   *  If a link is marked as broken, the button is disabled and labeled as "(BROKEN)".
   *  Otherwise, the button is configured with an action to execute the link's actions, update the label, and generate new passages and buttons.
   *  The goal status is updated in the goalsBox.
   *  If an exception occurs during the generation process, an error alert will be displayed.
   *
   * @param passage the passage to generate passages and buttons for
   */
  public void generatePassagesAndButtons(Passage passage) {

    try {
      storyTitleText.setText(gameViewController.getGame().getStory().getTitle());
      gameViewController.getGame().setCurrentPassage(passage);

      passageTitleText.setText(gameViewController.getGame().getCurrentPassage().getTitle());
      textArea.setText(gameViewController.getGame().getCurrentPassage().getContent());

      content.getChildren().removeIf(node -> node instanceof Button);

      Player player = playerController.getPlayer();

      if (gameViewController.getGame().getCurrentPassage().getListOfLinks().isEmpty()) {
        soundPlayer.stopPlaying();
        createEndGameButtons();
        content.getChildren().addAll(btnRestartGame);
      } else {
        gameViewController.getGame().getCurrentPassage().getListOfLinks().forEach(link -> {
          Button btnLink = new Button(link.getText());
          Game game = gameViewController.getGame();
          if (gameViewController.getGame().getStory().getBrokenLinks().contains(link)) {
            btnLink.setDisable(true);
            btnLink.setText(link.getText() + " (BROKEN)");
          } else {
            btnLink.setOnAction(e -> {
              try {
                soundPlayer.playOnce("/sound/click.wav");
              } catch (Exception ex) {
                throw new RuntimeException(ex);
              }

              for (Action action : link.getActions()) {
                action.execute(player);
              }
              refreshLabel();
              Passage newPassage = game.go(link);
              generatePassagesAndButtons(gameViewController.getGame().setCurrentPassage(newPassage));
              goalsBox.getChildren().setAll(gameViewController.goalStatus().getChildren());
            });
          }
          content.getChildren().add(btnLink);
          btnLink.getStyleClass().add("gameView-link-button");
        });
      }
    } catch (Exception e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
    }
  }

  /**
   * Creates the end game buttons for restarting the game and checking if all goals are fulfilled.
   * It initializes the btnRestartGame and didIWinButton buttons with their respective actions.
   * The btnRestartGame button resets the game, player, and pane, then activates the gameView.
   * The didIWinButton button checks if all goals and inventory goals are fulfilled in the game.
   * If an exception occurs during initialization, an error alert is displayed.
   * The end game buttons are added to the content pane.
   */
  public void createEndGameButtons() {
    try {
      btnRestartGame = new Button("Restart game");
      btnRestartGame.setOnAction(e -> {
        gameViewController.resetGame();
        playerController.resetPlayer();
        this.resetPane();
        screenController.activate("gameView");
      });

      didIWinButton = new Button("Did I win?");
        didIWinButton.setOnAction(e -> {
            gameViewController.checkIfAllGoalsAreFulfilled();
            gameViewController.checkIfInventoryGoalsAreFulfilled();
      });

      btnRestartGame.getStyleClass().add("gameView-restartGame-button");

      HBox endGameButtons = new HBox(10, btnRestartGame, didIWinButton);
      endGameButtons.setAlignment(Pos.CENTER);
      content.getChildren().add(endGameButtons);
    }
    catch (IllegalArgumentException e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
    }
  }

  /**
   * Refreshes the player information label and inventory display.
   * Updates the player's name, health, score, and gold in the playerText label.
   * Resets the inventory images and inventory list in the gameViewController.
   * Updates the inventory icons and adds them to the inventoryBox.
   * If an exception occurs during the inventory icon update, an error alert is displayed.
   * If inventory images or inventory list are not null, the inventoryBox is cleared and updated accordingly.
   */
  public void refreshLabel() {
    gameViewController = GameViewController.getInstance();

    playerText.setText("Name: " + playerController.getPlayer().getName()
            + "\nHealth: " + playerController.getPlayer().getHealth()
            + "\nScore: " + playerController.getPlayer().getScore()
            + "\nGold: " + playerController.getPlayer().getGold());

    gameViewController.resetInventoryImages();
    gameViewController.resetInventoryList();


    for (String item : playerController.getPlayer().getInventory()) {
      try {
      gameViewController.updateInventoryIcon(item);
        } catch (IllegalArgumentException e) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    if (gameViewController.getInventoryImages() != null) {
        inventoryBox.getChildren().clear();

      for(Image image: gameViewController.getInventoryImages()) {
        ImageView inventoryImage = new ImageView();
        inventoryImage.setImage(image);
        inventoryImage.setFitHeight(50);
        inventoryImage.setFitWidth(50);

        inventoryBox.getChildren().add(inventoryImage);
      }
    }
    if(gameViewController.getInventoryList() != null) {
      for (String item : gameViewController.getInventoryList()) {
        Label inventoryLabel = new Label(item);
        inventoryBox.getChildren().add(inventoryLabel);
        inventoryLabel.getStyleClass().add("gameView-player-info");
      }
    }
  }

  /**
   * Resets the pane. Clears the root,
   * resets the player, and removes the restart game button.
   */
  protected void resetPane() {
    root.getChildren().clear();
    borderPane.setTop(null);
    borderPane.setCenter(null);
    playerController.resetPlayer();

    if (btnRestartGame != null) {
      content.getChildren().remove(btnRestartGame);
    }
  }

  /**
   * Shows the help alert. Includes information on how to play the game.
   *
   * @return VBox displaying the help alert.
   */
  public VBox showHelpAlert() {
    Image helpIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/helpbutton.png")));
    ImageView helpView = new ImageView(helpIcon);
    helpView.setFitHeight(50);
    helpView.setFitWidth(50);
    Button helpButton = new Button();
    helpButton.setGraphic(helpView);
    helpButton.getStyleClass().add("gameView-help-button");
    helpButton.setOnAction(e -> {
      soundPlayer.stopPlaying();
      AlertUtil.showAlert(Alert.AlertType.INFORMATION, "How to play",
              """
                      - Start by choosing your character and set your desired health and gold.
                      
                      - The next page will take you to the page where you can decide whether you want\s
                        to use the predefined goals of easy, normal and hard difficulty or if you want\s
                        to play without goals our customize your own.
                      
                      - During the game your player information will be displayed at the top,\s
                        with a progress bar that keeps track of how far you are from reaching your goals.\s
                        
                      - You will have several buttons to choose from, each button will take you further in the game.\s
                        The buttons will be displayed in the middle of the screen.\s
                        
                      - If you want to return to the home screen, click on the home button.\s
                        When the game is over you can choose to restart the game or click the\s
                        home button to return to the home screen and choose another story.""");
    });
    VBox helpBox = new VBox(helpButton);
    helpBox.setAlignment(Pos.TOP_LEFT);
    return helpBox;
  }
}
