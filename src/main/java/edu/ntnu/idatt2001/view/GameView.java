package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.GameViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;


public class GameView extends View{

  protected StackPane root;
  private ScreenController screenController;

  private Text storyTitleText;
  private VBox content;
  private Text passageTitleText;
  private TextArea textArea;
  private GameViewController gameViewController;

  public GameView(ScreenController screenController) {
    this.screenController = screenController;
    this.gameViewController = GameViewController.getInstance();
    gameViewController.setGameView(this);
    this.root = new StackPane();
    this.setUp();
  }

  public StackPane getPane() {
    return this.root;
  }

  public void setUp() {
    this.resetPane();

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

    root.getStyleClass().add("playGameView-root");
    root.getChildren().addAll(contentWithCharacter);

    updatePassage(gameViewController);
  }

  public void updatePassage(GameViewController gameViewController) {
    storyTitleText.setText(gameViewController.getStory().getTitle());
    passageTitleText.setText(gameViewController.getCurrentPassage().getTitle());
    textArea.setText(gameViewController.getCurrentPassage().getContent());

    content.getChildren().removeIf(node -> node instanceof Button);

    List<Button> buttons = gameViewController.generateButtonsForPassage();
    for (Button button : buttons) {
      content.getChildren().add(button);
      button.getStyleClass().add("gameView-link-button");
    }
  }

  protected void resetPane() {
    root.getChildren().clear();
  }
}
