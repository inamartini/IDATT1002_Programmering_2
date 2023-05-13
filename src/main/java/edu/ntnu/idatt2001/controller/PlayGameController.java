package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Story;
import edu.ntnu.idatt2001.util.StoryReader;
import edu.ntnu.idatt2001.view.PlayGameView;
import edu.ntnu.idatt2001.view.ViewSwitcher;
import javafx.scene.control.Button;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class PlayGameController {

  private ViewSwitcher viewSwitcher;
  private Story story;
  private Passage currentPassage;
  private PlayGameView playGameView;


  public PlayGameController(ViewSwitcher viewSwitcher, String storyPath){
    this.viewSwitcher = viewSwitcher;

    File storyFile = new File(storyPath);
    try {
      this.story = StoryReader.readStoryFromFile(storyFile);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //this.story = StoryReader.readStoryFromFile(storyFile);

    this.currentPassage = story.getOpeningPassage();
  }

  public void setPlayGameView(PlayGameView playGameView) {
    this.playGameView = playGameView;
  }

  public Story getStory() {
    return story;
  }

  public Passage getCurrentPassage() {
    return currentPassage;
  }

  public void switchToHomePageView() {
    viewSwitcher.switchToHomePageView();
  }

  public void updateSceneForCurrentPassage() {
    playGameView.updateForPassage(this, currentPassage, generateButtonsForCurrentPassage());
  }


  public List<Button> generateButtonsForCurrentPassage() {
    return currentPassage.getListOfLinks().stream().map(link -> {
      Button button = new Button(link.getText());
      button.setOnAction(e -> {
        Passage nextPassage = story.getPassage(link);
        if (nextPassage != null) {
          currentPassage = nextPassage;
          updateSceneForCurrentPassage(); // update the scene when a button is clicked
        }
      });
      return button;
    }).collect(Collectors.toList());
  }
}

