package edu.ntnu.idatt2001.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * SoundPlayer class for playing sounds in the application. The class has two methods, one for playing a sound once
 * and one for playing a sound on loop. The class also has a method for stopping the sound that is playing.
 * The class is used to play sounds when the user clicks on buttons and during the game.
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class SoundPlayer {
  /**
   * Clip object for playing the sound.
   */
  private Clip clip;

  /**
   * Plays a sound once. Takes in the filename of the sound file and plays it once.
   * @param filename the filename of the sound file
   * @throws Exception if the sound file is not found
   */
  public void playOnce(String filename) throws Exception {
    try (InputStream is = getClass().getResourceAsStream(filename)) {
      assert is != null;
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      throw new Exception("Failed to play sound");
    }
  }

  /**
   * Plays a sound on loop. Takes in the filename of the sound file and plays it on loop.
   * The method also sets the volume of the sound to -10 decibels.
   * @param filename the filename of the sound file
   * @throws Exception if the sound file is not found
   */
  public void playOnLoop(String filename) throws Exception {
    try (InputStream is = getClass().getResourceAsStream(filename)) {
      assert is != null;
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-10.0f);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      throw new Exception("Failed to play sound");
    }
  }

  /**
   * Stops the sound that is playing. Checks if the clip is running and stops it.
   */
  public void stopPlaying() {
    if(clip != null && clip.isRunning()) {
      clip.flush();
      clip.stop();
    }
  }
}

