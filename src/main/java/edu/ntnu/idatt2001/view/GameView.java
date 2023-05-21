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
  private Button btnReturnHome;
  private GameViewController gameViewController = GameViewController.getInstance();
  private Label playerText;
  private ImageView inventoryImage;
  private HBox inventoryBox;
  private VBox goalsBox;
  private Pane imageWrapper;
  private PlayerViewController playerViewController = PlayerViewController.getInstance();

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

    btnHome = new Button("Home");
    btnHome.setVisible(true);
    btnHome.setOnAction(e ->  {
      try {
        screenController.activate("homeView");
      } catch (IllegalArgumentException ex) {
        AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
      }
      this.resetPane();
    });
    btnHome.getStyleClass().add("gameView-returnHome-button");

    VBox homeButtonBox = new VBox();
    homeButtonBox.getChildren().add(btnHome);
    btnHome.setAlignment(Pos.TOP_LEFT);
    homeButtonBox.setAlignment(Pos.TOP_LEFT);

    inventoryImage = new ImageView();

    inventoryBox = new HBox(10, inventoryImage);
    inventoryBox.setAlignment(Pos.TOP_CENTER);

    playerText = new Label();
    playerText.getStyleClass().add("gameView-player-info");
    refreshLabel();

    imageWrapper = new Pane();
    imageWrapper.getChildren().add(inventoryBox);
    imageWrapper.setPrefWidth(150);
    imageWrapper.setPrefHeight(150);

    goalsBox = gameViewController.goalStatus();

    HBox rightBox = new HBox(10, goalsBox, playerText, imageWrapper);
    rightBox.setAlignment(Pos.TOP_CENTER);

    VBox topInfo = new VBox();
    topInfo.getChildren().addAll(homeButtonBox, rightBox);
    topInfo.setAlignment(Pos.TOP_CENTER);

    borderPane.setTop(topInfo);

    generatePassagesAndButtons(gameViewController.getGame().getCurrentPassage());

    borderPane.getStyleClass().add("playGameView-root");
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
        btnHome.setVisible(false);
        createEndGameButtons();
        content.getChildren().addAll(btnReturnHome, btnRestartGame);
      } else {
        gameViewController.getGame().getCurrentPassage().getListOfLinks().forEach(link -> {
          Button btnLink = new Button(link.getText());
          Game game = gameViewController.getGame();
          if (gameViewController.getGame().getStory().getBrokenLinks().contains(link)) {
            btnLink.setDisable(true);
            btnLink.setText(link.getText() + " (BROKEN)");
          } else {
            btnLink.setOnAction(e -> {
              SoundPlayer soundPlayer = new SoundPlayer("/sound/armstrekkeren.wav");
              soundPlayer.play();

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
      GameViewController gameViewController = GameViewController.getInstance();
      btnReturnHome = new Button("Return to home");
      btnReturnHome.setOnAction(e -> {
        screenController.activate("homeView");
        playerViewController.resetPlayer();
        this.resetPane();
      });

      btnRestartGame = new Button("Restart game");
      btnRestartGame.setOnAction(e -> {
        gameViewController.resetGame();
        playerViewController.resetPlayer();
        this.resetPane();
        screenController.activate("gameView");
      });

      btnRestartGame.getStyleClass().add("gameView-returnHome-button");
      btnReturnHome.getStyleClass().add("gameView-returnHome-button");

      HBox endGameButtons = new HBox(10, btnReturnHome, btnRestartGame);
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
      content.getChildren().remove(btnReturnHome);
    }
  }
}
