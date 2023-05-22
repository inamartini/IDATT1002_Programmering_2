package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.PlayerController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Passage;
import edu.ntnu.idatt2001.model.action.Action;
import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.util.AlertUtil;
import edu.ntnu.idatt2001.util.SoundPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 * This class extends the View class and is responsible for the game view of the application.
 * The game view is the page where the game is played.
 * It displays the story title, passage title, passage content,
 * player image, troll image, inventory, goals, and buttons.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
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
   * Error message.
   */
  private static final String ERROR = "Error";

  /**
   * The sound player of the application.
   */
  private SoundPlayer soundPlayer = new SoundPlayer();

  /**
   * The player controller of the application.
   */
  private PlayerController playerController = PlayerController.getInstance();

  /**
   * The end game title.
   */
  private Text endGameTitle = new Text();

  /**
   * The restart game button.
   */
  private Button btnRestartGame = new Button();

  /**
   * The see results button.
   */
  private Button btnSeeResults = new Button();

  /**
   * The end game buttons box.
   */
  private HBox endGameButtons = new HBox();


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
   * Sets up the game view by initializing the game,
   * playing background music, and configuring various UI elements.
   * The center of the border pane is set to the root node.
   * It initializes the game, plays the background music,
   * and handles any exceptions that occur during the initialization process.
   * Creates and configures UI elements such as story title text,
   * passage title text, text area, content pane, character images,
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
      AlertUtil.showAlert(Alert.AlertType.ERROR, ERROR, e.getMessage());
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

    Image homeIcon =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/homebutton.png")));
    ImageView home = new ImageView(homeIcon);
    home.setFitHeight(50);
    home.setFitWidth(50);
    Button btnHome = new Button();
    btnHome.setGraphic(home);
    btnHome.getStyleClass().add("gameView-returnHome-button");
    btnHome.setAlignment(Pos.TOP_LEFT);
    btnHome.setOnAction(e -> {
      try {
        soundPlayer.stopPlaying();
        this.resetPane();
        screenController.activate("homeView");
      } catch (IllegalArgumentException ex) {
        AlertUtil.showAlert(Alert.AlertType.ERROR, ERROR, ex.getMessage());
      }
      this.resetPane();
    });

    VBox menuBar = new VBox();
    menuBar.getChildren().addAll(btnHome, showHelpAlert());
    menuBar.setAlignment(Pos.TOP_LEFT);

    ImageView inventoryImage;
    inventoryImage = new ImageView();

    inventoryBox = new HBox(10, inventoryImage);
    inventoryBox.setAlignment(Pos.TOP_CENTER);

    playerText = new Label();
    refreshLabel();


    Pane imageWrapper = new Pane();
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
   * Generates passages and buttons based on the given Passage object.
   * It sets the story title, current passage, passage title,
   * and content in the respective UI elements.
   * It removes any existing buttons from the content pane.
   * If the current passage has no links, it stops the sound player,
   * creates end game buttons, and adds them to the content pane.
   * Otherwise, it generates buttons for each link in the current passage's list of links.
   * If a link is marked as broken, the button is disabled and labeled as "(BROKEN)".
   * Otherwise, the button is configured with an action to
   * execute the link's actions, update the label, and generate new passages and buttons.
   * The goal status is updated in the goalsBox.
   * If an exception occurs during the generation process, an error alert will be displayed.
   *
   * @param passage the passage to generate passages and buttons for
   */
  public void generatePassagesAndButtons(Passage passage) {
    try {
      setupPassageAndTitle(passage);

      content.getChildren().removeIf(Button.class::isInstance);
      Player player = playerController.getPlayer();

      if (gameViewController.getGame().getCurrentPassage().getListOfLinks().isEmpty()) {
        handleEmptyLinks();
      } else {
        processLinks(player);
      }
    } catch (Exception e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, ERROR, e.getMessage());
    }
  }

  /**
   * Sets the story title, current passage,
   * passage title, and content in the respective UI elements.
   *
   * @param passage the passage to set up
   */
  private void setupPassageAndTitle(Passage passage) {
    storyTitleText.setText(gameViewController.getGame().getStory().getTitle());
    gameViewController.getGame().setCurrentPassage(passage);

    passageTitleText.setText(gameViewController.getGame().getCurrentPassage().getTitle());
    textArea.setText(gameViewController.getGame().getCurrentPassage().getContent());
  }

  /**
   * Method to handle the case where the current passage has no links.
   * It stops the sound player, creates end game buttons, and adds them to the content pane.
   */
  private void handleEmptyLinks() {
    soundPlayer.stopPlaying();
    createEndGameButtons();
    content.getChildren().addAll(btnRestartGame, btnSeeResults);
  }

  /**
   * Creates a button for the given link.
   * Removes buttons if the player has a health of 0 or below.
   *
   * @param player the player to execute the link's actions on
   */
  private void processLinks(Player player) {
    List<Button> buttons = new ArrayList<>();
    gameViewController.getGame().getCurrentPassage().getListOfLinks().forEach(link -> {
      Button btnLink = createLinkButton(link);

      if (gameViewController.getGame().getStory().getBrokenLinks().contains(link)) {
        disableBrokenLink(btnLink, link);
      } else {
        setLinkButtonAction(player, link, btnLink);
      }
      content.getChildren().add(btnLink);
      btnLink.getStyleClass().add("gameView-link-button");
      buttons.add(btnLink);
    });
    if (player.getHealth() <= 0) {
      buttons.forEach(button -> content.getChildren().remove(button));
    }
  }

  /**
   * Sets the action of the given button to execute
   * the link's actions, update the label, and generate new passages and buttons.
   *
   * @param player  the player to execute the link's actions on
   * @param link    the link to execute actions for
   * @param btnLink the button to set the action for
   */
  private void setLinkButtonAction(Player player, Link link, Button btnLink) {
    Game game = gameViewController.getGame();
    btnLink.setOnAction(e -> {
      try {
        soundPlayer.playOnce("/sound/click.wav");
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }

      executeLinkActions(player, link.getActions());
      refreshLabel();

      Passage newPassage = game.go(link);
      generatePassagesAndButtons(gameViewController.getGame().setCurrentPassage(newPassage));
      goalsBox.getChildren().setAll(gameViewController.goalStatus().getChildren());
    });
  }

  /**
   * Creates the buttons that links the player from one passage to another.
   *
   * @param link the link to create a button for
   * @return the button that links the player from one passage to another
   */
  private Button createLinkButton(Link link) {
    return new Button(link.getText());
  }

  /**
   * Disables a button if it refers to a passage that doesn't exist.
   *
   * @param btnLink the button to disable
   * @param link    the link that is broken
   */
  private void disableBrokenLink(Button btnLink, Link link) {
    btnLink.setDisable(true);
    btnLink.setText(link.getText() + " (BROKEN)");
  }

  /**
   * Executes the actions of the given list of actions on the given player.
   *
   * @param player  the player to execute the actions on
   * @param actions the list of actions to execute
   */
  private void executeLinkActions(Player player, List<Action> actions) {
    try {
      for (Action action : actions) {
        action.execute(player);
        if (player.getHealth() <= 0) {
          endGameTitle.setText("You died");
          soundPlayer.stopPlaying();
          gameResults();
          return;
        }
      }
    } catch (Exception ex) {
      if (player.getHealth() <= 0) {
        endGameTitle.setText("You died");
        soundPlayer.stopPlaying();
        gameResults();
      }
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
      btnRestartGame.setText("Restart game");
      btnRestartGame.setOnAction(e -> {
        gameViewController.resetGame();
        this.resetPane();
        screenController.activate("gameView");
      });

      btnSeeResults.setText("See results");
      btnSeeResults.setOnAction(e -> {
        if (gameViewController.checkIfAllGoalsAreFulfilled()) {
          endGameTitle.setText("Congratulations! You fulfilled all the goals!");
        } else {
          endGameTitle.setText("Well played! Try again to fulfill all the goals!");
        }
        gameResults();
      });

      btnRestartGame.getStyleClass().add("gameView-restartGame-button");
      btnSeeResults.getStyleClass().add("gameView-seeResults-button");

      endGameButtons = new HBox(10, btnRestartGame, btnSeeResults);
      endGameButtons.setAlignment(Pos.CENTER);
      content.getChildren().add(endGameButtons);
    } catch (IllegalArgumentException e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, ERROR, e.getMessage());
    }
  }

  /**
   * Refreshes the player information label and inventory display.
   * Updates the player's name, health, score, and gold in the playerText label.
   * Resets the inventory images and inventory list in the gameViewController.
   * Updates the inventory icons and adds them to the inventoryBox.
   * If an exception occurs during the inventory icon update,
   * an error alert is displayed.
   * If inventory images or inventory list are not null,
   * the inventoryBox is cleared and updated accordingly.
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
        AlertUtil.showAlert(Alert.AlertType.ERROR, ERROR, e.getMessage());
      }
    }
    if (gameViewController.getInventoryImages() != null) {
      inventoryBox.getChildren().clear();

      for (Image image : gameViewController.getInventoryImages()) {
        inventoryImage = new ImageView();
        inventoryImage.setImage(image);
        inventoryImage.setFitHeight(50);
        inventoryImage.setFitWidth(50);

        inventoryBox.getChildren().add(inventoryImage);
      }
    }
    if (gameViewController.getInventoryList() != null) {
      for (String item : gameViewController.getInventoryList()) {
        Label inventoryLabel = new Label(item);
        inventoryBox.getChildren().add(inventoryLabel);
        inventoryLabel.getStyleClass().add("gameView-player-info");
      }
    }
  }


  /**
   * Creates the game results screen.
   * The game results screen displays the end game title, game info, and game summary.
   * The game summary displays the goals fulfilled and the goals not fulfilled.
   */
  public void gameResults() {
    content.getChildren().remove(endGameButtons);

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setMaxWidth(600);
    scrollPane.setMaxHeight(500);
    scrollPane.getStyleClass().add("gameResults-scrollpane");

    VBox container = new VBox();
    container.getStyleClass().add("gameResults-container");
    container.setAlignment(Pos.CENTER);

    endGameTitle.getStyleClass().add("endGameView-title");

    VBox titleBox = new VBox();
    titleBox.setAlignment(Pos.CENTER);
    titleBox.getChildren().add(endGameTitle);

    VBox gameInfo = new VBox();
    gameInfo.setAlignment(Pos.CENTER);

    List<Pair<String, String>> goalSummaries = gameViewController.getGoalSummary();

    for (Pair<String, String> summary : goalSummaries) {
      Text goalNameText = new Text(summary.getKey() + ": ");
      Text statusText = new Text(summary.getValue());

      if (summary.getValue().equals("Fulfilled")) {
        statusText.getStyleClass().add("fulfilled-class");
      } else {
        statusText.getStyleClass().add("not-fulfilled-class");
      }

      HBox goalSummaryBox = new HBox(goalNameText, statusText);
      goalSummaryBox.setAlignment(Pos.CENTER);
      gameInfo.getChildren().add(goalSummaryBox);
    }

    Button btnRestartGame2 = new Button("RESTART GAME");
    btnRestartGame2.setOnAction(e -> {
      gameViewController.resetGame();
      this.resetPane();
      screenController.activate("gameView");
    });
    btnRestartGame2.getStyleClass().add("gameResults-button");

    Button btnGoToHome = new Button("GO TO HOME");
    btnGoToHome.setOnAction(e -> {
      this.resetPane();
      screenController.activate("homeView");
    });
    btnGoToHome.getStyleClass().add("gameResults-button");

    HBox buttons = new HBox();
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(20);
    buttons.getChildren().addAll(btnRestartGame2, btnGoToHome);

    content.getChildren().clear();
    content = new VBox();
    content.setAlignment(Pos.CENTER);
    content.getStyleClass().add("gameResults-content");
    content.getChildren().addAll(titleBox, gameInfo, buttons);

    container.getChildren().add(content);
    scrollPane.setContent(container);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    VBox centeringPane = new VBox();
    centeringPane.setAlignment(Pos.CENTER);
    centeringPane.getChildren().add(scrollPane);

    borderPane.setCenter(centeringPane);
  }

  /**
   * Resets the pane. Clears the root,
   * resets the player, and removes the restart game button.
   */
  protected void resetPane() {
    root.getChildren().clear();
    borderPane.getChildren().clear();
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
    Image helpIcon =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/helpbutton.png")));
    ImageView helpView = new ImageView(helpIcon);
    helpView.setFitHeight(50);
    helpView.setFitWidth(50);
    Button helpButton = new Button();
    helpButton.setGraphic(helpView);
    helpButton.getStyleClass().add("gameView-help-button");
    String howToPlayMessage = """
        - Start by choosing your character and set your desired health and gold.
          
        - The next page will take you to the page where you can decide whether you want
          to use the predefined goals of easy, normal and hard difficulty or if you want
          to play without goals our customize your own.
          
        - During the game your player information will be displayed at the top,
          with a progress bar that keeps track of how far you are from reaching your goals.
          
        - You will have several buttons to choose from, each button will\s
          take you further in the game. The buttons will be displayed\s
          in the middle of the screen.
          
        - If you want to return to the home screen, click on the home button.
          When the game is over you can choose to restart the game or click the
          home button to return to the home screen and choose another story.
        """;
    helpButton.setOnAction(e -> {
      soundPlayer.stopPlaying();
      AlertUtil.showAlert(Alert.AlertType.INFORMATION, "How to play", howToPlayMessage);
    });

    VBox helpBox = new VBox(helpButton);
    helpBox.setAlignment(Pos.TOP_LEFT);
    return helpBox;
  }
}
