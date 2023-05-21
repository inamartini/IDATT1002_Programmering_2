package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.PlayerViewController;
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


/** Class of the game view
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 *
 */
public class GameView extends View {

  private StackPane root;
  private BorderPane borderPane;
  private ScreenController screenController;
  private Text storyTitleText;
  private VBox content;
  private Text passageTitleText;
  private TextArea textArea;
  private Button btnHome;
  private Button btnRestartGame;
  private GameViewController gameViewController = GameViewController.getInstance();
  private Label playerText;
  private ImageView inventoryImage;
  private HBox inventoryBox;
  private VBox goalsBox;
  private Pane imageWrapper;
  private SoundPlayer soundPlayer = new SoundPlayer();
  private PlayerViewController playerViewController = PlayerViewController.getInstance();
  private Button didIWinButton;

  public GameView(ScreenController screenController) {
    this.root = new StackPane();
    this.borderPane = new BorderPane();
    borderPane.setCenter(root);
    this.screenController = screenController;
  }

  public Pane getPane() {
    return this.borderPane;
  }

  public void setUp() {

    borderPane.setCenter(root);
    gameViewController.initializeGame();
    try {
      soundPlayer.playOnLoop("/sound/fairytale.wav");
    } catch (Exception e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "Failed to play sound");
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

  public void generatePassagesAndButtons(Passage passage) {

    try {
      storyTitleText.setText(gameViewController.getGame().getStory().getTitle());
      gameViewController.getGame().setCurrentPassage(passage);

      passageTitleText.setText(gameViewController.getGame().getCurrentPassage().getTitle());
      textArea.setText(gameViewController.getGame().getCurrentPassage().getContent());

      content.getChildren().removeIf(node -> node instanceof Button);

      Player player = playerViewController.getPlayer();

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

  public void createEndGameButtons() {
    try {
      btnRestartGame = new Button("Restart game");
      btnRestartGame.setOnAction(e -> {
        gameViewController.resetGame();
        playerViewController.resetPlayer();
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
    catch (Exception e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
    }
  }

  public void refreshLabel() {
    gameViewController = GameViewController.getInstance();

    playerText.setText("Name: " + playerViewController.getPlayer().getName()
            + "\nHealth: " + playerViewController.getPlayer().getHealth()
            + "\nScore: " + playerViewController.getPlayer().getScore()
            + "\nGold: " + playerViewController.getPlayer().getGold());

    gameViewController.resetInventoryImages();
    gameViewController.resetInventoryList();


    for (String item : playerViewController.getPlayer().getInventory()) {
      try {
      gameViewController.updateInventoryIcon(item);
        } catch (Exception e) {
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


  protected void resetPane() {
    root.getChildren().clear();
    borderPane.setTop(null);
    borderPane.setCenter(null);
    playerViewController.resetPlayer();

    if (btnRestartGame != null) {
      content.getChildren().remove(btnRestartGame);
    }
  }
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
