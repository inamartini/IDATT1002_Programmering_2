package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Player;
import edu.ntnu.idatt2001.base.Story;
import edu.ntnu.idatt2001.util.StoryReader;
import edu.ntnu.idatt2001.view.GameView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class GameViewController {

  private static GameViewController gameViewController= new GameViewController();
  private GameView gameView;
  private Passage currentPassage;
  private Story story;
  private String storyPath = "src/test/resources/scaryStory.paths";

  private GameViewController() {
    File storyFile = new File(storyPath);
    try {
      this.story = StoryReader.readStoryFromFile(storyFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.currentPassage = story.getOpeningPassage();

  }

  public static GameViewController getInstance() {
    return gameViewController;
  }

  public void setGameView(GameView gameView) {
    this.gameView = gameView;
  }

  public Story getStory() {
    return story;
  }

  public Passage getCurrentPassage() {
    return currentPassage;
  }

  public void setOpeningPassageToCurrentPassage() {
    currentPassage = story.getOpeningPassage();
  }

  public Image getPlayerImage() {
    return PlayerViewController.getInstance().getPlayerImage();
  }

  public List<Button> generateButtonsForPassage() {
    return getCurrentPassage().getListOfLinks().stream().map(link -> {
      Button button = new Button(link.getText());
      button.setOnAction(e -> {
        Passage nextPassage = story.getPassage(link);
        if (nextPassage != null) {
          currentPassage = nextPassage;
          gameView.updatePassage(this);
        }
      });
      return button;
    }).collect(Collectors.toList());
  }

  public Player getPlayer() {
    return PlayerViewController.getInstance().getPlayer();
  }

  public void resetGame() {
    setOpeningPassageToCurrentPassage();
  }

  public void resetPlayer() {
    getPlayer();
    //PlayerViewController.getInstance().getPlayer();
  }
}
