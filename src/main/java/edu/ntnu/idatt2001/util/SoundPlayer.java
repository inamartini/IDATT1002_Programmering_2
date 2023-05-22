package edu.ntnu.idatt2001.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {
  private Clip clip;

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

  public void stopPlaying() {
    if(clip != null && clip.isRunning()) {
      clip.flush();
      clip.stop();
    }
  }
}

