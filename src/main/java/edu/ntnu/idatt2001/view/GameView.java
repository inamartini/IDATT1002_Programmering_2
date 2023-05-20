package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.action.Action;
import edu.ntnu.idatt2001.base.Game;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Player;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


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
  private VBox inventoryBox;

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

    ImageView trollImage = new ImageView(new Image("images/troll.jpeg"));

    ImageView playerImage = new ImageView(gameViewController.getPlayerImage());

    HBox contentWithCharacter = new HBox(10, playerImage, content, trollImage);
    contentWithCharacter.setAlignment(Pos.CENTER);

    btnHome = new Button("Home");
    btnHome.setVisible(true);
    btnHome.setOnAction(e ->  {
      screenController.activate("homeView");
      this.resetPane();
    });
    btnHome.getStyleClass().add("gameView-returnHome-button");

    HBox leftBox = new HBox(10, btnHome);
    leftBox.setAlignment(Pos.CENTER_LEFT);

    inventoryImage = new ImageView();

    inventoryBox = new VBox(10, inventoryImage);
    inventoryBox.setAlignment(Pos.CENTER_RIGHT);

    playerText = new Label();
    playerText.getStyleClass().add("gameView-player-info");
    refreshLabel();

    HBox rightBox = new HBox(10, playerText, inventoryBox);
    rightBox.setAlignment(Pos.CENTER_RIGHT);

    HBox hBoxes = new HBox(10, leftBox, rightBox);
    hBoxes.setHgrow(leftBox, Priority.ALWAYS);

    borderPane.setTop(hBoxes);

    generatePassagesAndButtons(gameViewController.getGame().getCurrentPassage());

    borderPane.getStyleClass().add("playGameView-root");
    root.getChildren().addAll(contentWithCharacter);

  }

  public void generatePassagesAndButtons(Passage passage) {

    storyTitleText.setText(gameViewController.getGame().getStory().getTitle());
    gameViewController.getGame().setCurrentPassage(passage);

    passageTitleText.setText(gameViewController.getGame().getCurrentPassage().getTitle());
    textArea.setText(gameViewController.getGame().getCurrentPassage().getContent());

    content.getChildren().removeIf(node -> node instanceof Button);

    Player player = gameViewController.getGame().getPlayer();

    if(gameViewController.getGame().getCurrentPassage().getListOfLinks().isEmpty()) {
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
            for (Action action : link.getActions()) {
              action.execute(player);
            }
            refreshLabel();
            Passage newPassage = game.go(link);
            generatePassagesAndButtons(gameViewController.getGame().setCurrentPassage(newPassage));
          });
        }
        content.getChildren().add(btnLink);
        btnLink.getStyleClass().add("gameView-link-button");
      });
    }
  }

  public void createEndGameButtons() {
    GameViewController gameViewController = GameViewController.getInstance();
    btnReturnHome = new Button("Return to home");
    btnReturnHome.setOnAction(e ->  {
      screenController.activate("homeView");
      gameViewController.resetPlayer();
      this.resetPane();
    });

    btnRestartGame = new Button("Restart game");
    btnRestartGame.setOnAction(e ->  {
      this.resetPane();
      //player = gameViewController.getPlayer();
      screenController.activate("gameView");
      //screenController.activate("gameView");
      //gameViewController.resetGame();
    });

    btnRestartGame.getStyleClass().add("gameView-returnHome-button");
    btnReturnHome.getStyleClass().add("gameView-returnHome-button");

    HBox endGameButtons = new HBox(10, btnReturnHome, btnRestartGame);
    endGameButtons.setAlignment(Pos.CENTER);
    content.getChildren().add(endGameButtons);
  }

  public void refreshLabel() {
    gameViewController = GameViewController.getInstance();

    playerText.setText("Name: " + gameViewController.getGame().getPlayer().getName()
            + "\nHealth: " + gameViewController.getGame().getPlayer().getHealth()
            + "\nGold: " + gameViewController.getGame().getPlayer().getGold());

    gameViewController.resetInventoryImages();


    for (String item : gameViewController.getGame().getPlayer().getInventory()) {
      gameViewController.updateInventoryIcon(item);
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
    GameViewController gameViewController = GameViewController.getInstance();
    root.getChildren().clear();
    borderPane.setTop(null);
    borderPane.setCenter(null);
    //gameViewController.resetGame();
    gameViewController.resetPlayer();

    if (btnRestartGame != null) {
      content.getChildren().remove(btnRestartGame);
      content.getChildren().remove(btnReturnHome);
    }
  }
}
