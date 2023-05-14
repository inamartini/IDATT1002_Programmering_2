package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.controller.PlayGameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

public class PlayGameView extends Scene {

  private VBox content;
  private Text storyTitleText;
  private Text passageTitleText;
  private TextArea textArea;
  private ImageView characterImageView;

  public PlayGameView(PlayGameController controller) {
    super(new StackPane());

    characterImageView = new ImageView();

    String cssPath = Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm();
    getStylesheets().add(cssPath);

    storyTitleText = new Text(controller.getStory().getTitle()); // Story title
    storyTitleText.getStyleClass().add("story-title");

    passageTitleText = new Text(controller.getCurrentPassage().getTitle()); // Passage title
    passageTitleText.getStyleClass().add("passage-title");

    textArea = new TextArea(controller.getCurrentPassage().getContent()); // Passage content
    textArea.setEditable(false);
    textArea.setMaxWidth(500);
    textArea.getStyleClass().add("passage-content");

    content = new VBox(10, storyTitleText, passageTitleText, textArea);
    content.setAlignment(Pos.CENTER);

    ImageView trollImage = new ImageView(new Image("images/troll.jpeg"));

    HBox contentWithCharacter = new HBox(10, characterImageView, content, trollImage);
    contentWithCharacter.setAlignment(Pos.CENTER);

    StackPane root = (StackPane) getRoot();
    root.getStyleClass().add("playGameView-root");
    root.getChildren().addAll(contentWithCharacter);

    controller.setPlayGameView(this);
    controller.updateSceneForCurrentPassage();
  }

  public void setCharacterImageView(ImageView characterImageView) {
    this.characterImageView.setImage(characterImageView.getImage());
  }

  public void updateForPassage(PlayGameController controller, Passage passage, List<Button> buttons) {
    // update the text area and titles with the new passage content
    storyTitleText.setText(controller.getStory().getTitle());
    passageTitleText.setText(passage.getTitle());
    textArea.setText(passage.getContent());

    // remove the old buttons from the layout
    content.getChildren().removeIf(node -> node instanceof Button);

    // add the new buttons to the layout
    for (Button button : buttons) {
      content.getChildren().add(button);
      button.getStyleClass().add("passage-link");
    }
  }
}
