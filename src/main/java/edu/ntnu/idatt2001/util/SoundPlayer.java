package edu.ntnu.idatt2001.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
  private Clip clip;

  public SoundPlayer(String path) {
    try {
      URL soundFile = getClass().getResource(path);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
      clip = AudioSystem.getClip();
      clip.open(audioStream);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      //TODO handle exception
    }
  }

  public void play() {
    if (clip != null) {
      clip.start();
    }
  }
}

