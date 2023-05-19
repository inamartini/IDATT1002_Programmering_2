package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.base.Player;
import edu.ntnu.idatt2001.controller.GameViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;


public class GameView extends View{

  private StackPane root;
  private BorderPane borderPane;
  private ScreenController screenController;
  private Text storyTitleText;
  private VBox content;
  private Text passageTitleText;
  private TextArea textArea;
  private GameViewController gameViewController = GameViewController.getInstance();
  private Player player;
  private Button btnHome;
  private Button btnRestartGame;
  private Button btnReturnHome;

  public GameView(ScreenController screenController) {
    this.root = new StackPane();
    this.borderPane = new BorderPane();
    borderPane.setCenter(root);
    this.screenController = screenController;
    gameViewController.setGameView(this);
  }

  public Pane getPane() {
    return this.borderPane;
  }

  public void setUp() {

    borderPane.setCenter(root);

    //player = gameViewController.getPlayer();

    storyTitleText = new Text(gameViewController.getStory().getTitle());
    storyTitleText.getStyleClass().add("gameView-story-title");

    passageTitleText = new Text(gameViewController.getCurrentPassage().getTitle());
    passageTitleText.getStyleClass().add("gameView-passage-title");

    textArea = new TextArea(gameViewController.getCurrentPassage().getContent());
    textArea.setEditable(false);
    textArea.setPrefWidth(500);
    textArea.getStyleClass().add("gameView-passage-content");

    content = new VBox(10, storyTitleText, passageTitleText, textArea);
    content.setAlignment(Pos.CENTER);

    ImageView trollImage = new ImageView(new Image("images/troll.jpeg"));

    ImageView playerImage = new ImageView(gameViewController.getPlayerImage());

    HBox contentWithCharacter = new HBox(10, playerImage, content, trollImage);
    contentWithCharacter.setAlignment(Pos.CENTER);

    player = gameViewController.getPlayer();

    Label playerText = new Label("Name: " + player.getName() + "\nHealth: " + player.getHealth()
            + "\nGold: " + player.getGold() + "\nInventory: " + player.getInventory());
    playerText.getStyleClass().add("gameView-player-info");

    btnHome = new Button("Home");
    btnHome.setVisible(true);
    btnHome.setOnAction(e ->  {
      screenController.activate("homeView");
      this.resetPane();
    });
    btnHome.getStyleClass().add("gameView-returnHome-button");

    HBox leftBox = new HBox(10, btnHome);
    leftBox.setAlignment(Pos.CENTER_LEFT);

    HBox rightBox = new HBox(10, playerText);
    rightBox.setAlignment(Pos.CENTER_RIGHT);

    HBox hBoxes = new HBox(10, leftBox, rightBox);
    hBoxes.setHgrow(leftBox, Priority.ALWAYS);

    borderPane.setTop(hBoxes);

    borderPane.getStyleClass().add("playGameView-root");
    root.getChildren().addAll(contentWithCharacter);

    updatePassage(gameViewController);
  }

  public void updatePassage(GameViewController gameViewController) {
    storyTitleText.setText(gameViewController.getStory().getTitle());
    passageTitleText.setText(gameViewController.getCurrentPassage().getTitle());
    textArea.setText(gameViewController.getCurrentPassage().getContent());

    content.getChildren().removeIf(node -> node instanceof Button);

    List<Button> buttons = gameViewController.generateButtonsForPassage();
    if (buttons.isEmpty()) {
      btnHome.setVisible(false);
      createEndGameButtons();
    } else {
      for (Button button : buttons) {
        content.getChildren().add(button);
        button.getStyleClass().add("gameView-link-button");
      }
    }
  }

  public void createEndGameButtons() {
    btnReturnHome = new Button("Return to home");
    btnReturnHome.setOnAction(e ->  {
      screenController.activate("homeView");
      gameViewController.resetGame();
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

  protected void resetPane() {
    root.getChildren().clear();
    borderPane.setTop(null);
    borderPane.setCenter(null);
    gameViewController.resetGame();
    gameViewController.resetPlayer();

    if (btnRestartGame != null) {
      content.getChildren().remove(btnRestartGame);
      content.getChildren().remove(btnReturnHome);
    }
  }
}
