package edu.ntnu.idatt2001.base;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Story.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 */

public class StoryTest {

  private Passage openingPassage ;
  private Link link ;
  private Story story ;
  private Map<Link, Passage> passages ;

  @BeforeEach
  public void setUp() {
    openingPassage = new Passage("New passage", "This is a test-passage.");
    link = new Link("New passage", "New passage");
    story = new Story("This is a title", openingPassage);
    passages = new HashMap<>();
    passages.put(link, openingPassage);
  }

  @Nested
  class TestGetMethods {
    @Test
    @DisplayName("Test if getTitle() returns the correct title")
    void getTitleShouldReturnCorrectTitle() {
      assertEquals("This is a title", story.getTitle());
    }

    @Test
    @DisplayName("Test if getOpeningPassage() returns the correct opening passage")
    void getOpeningPassageShouldReturnCorrectOpeningPassage() {
      assertEquals(openingPassage, story.getOpeningPassage());
    }

    @Test
    @DisplayName("Test if getPassage() returns the correct passage")
    void getPassageShouldReturnCorrectPassage() {
      story.addPassage(openingPassage);
      assertEquals(openingPassage, story.getPassage(link));
    }

    @Test
    @DisplayName("Test if getPassages() returns the correct collection of passages")
    void getPassagesTest() {
      story.addPassage(openingPassage);
      assertEquals(passages.values().toString(), story.getPassages().toString());
    }

    @Test
    @DisplayName("Test if getPassages() returns an empty collection if the story has no passages")
    void getPassagesShouldReturnEmptyCollectionIfStoryHasNoPassages() {
      Collection<Passage> actualPassages = story.getPassages();
      assertTrue(actualPassages.isEmpty());
    }

    @Test
    @DisplayName("Test if addPassage() adds a new passage")
    public void testAddPassage() {
      openingPassage.addLink(link);
      story.addPassage(openingPassage);
      assertEquals(1, story.getPassages().size());
      //assertEquals("", story.getPassages());
    }
  }

  @Nested
  class TestExceptions {

    String notEmptyString;

    @BeforeEach
    public void setUp() {
      notEmptyString = "Test";
    }

    @Test
    @DisplayName("Tests if constructor throws an IllegalArgumentException if title is null ")
    void titleThrowIllegalArgumentExceptionTest() {
      openingPassage = new Passage("New passage", "This is a test-passage.");
      assertThrows(IllegalArgumentException.class, () -> story = new Story(null, openingPassage));
    }

    @Test
    @DisplayName("Tests if constructor throws an IllegalArgumentException if openingPassage is null")
    void openingPassageThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class, () -> story = new Story(notEmptyString, null));
    }

    @Test
    @DisplayName("Tests if addPassage() throws an IllegalArgumentException if passage is null")
    void addPassageThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class, () -> story.addPassage(null));
    }

    @Test
    @DisplayName("Test if getPassage() throws an IllegalArgumentException if link is null")
    void getPassageShouldThrowIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> story.getPassage(null));
    }
  }

  @Nested
  class testDivCoreFunctionality {

    Passage passage;

    @BeforeEach
    void setUp() {
      passage = new Passage("Passage 1", "This is passage 1");
      link = new Link("Passage 1", "Passage 1");
      passage.addLink(link);
    }

    @Test
    @DisplayName("Tests if removePassage() throws an IllegalArgumentException if link is null")
    void removePassageShouldThrowIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> story.removePassage(null));
    }

    @Test
    @DisplayName("Test if removePassage() removes a passage")
    public void removePassageShouldRemovePassage() {
      story.addPassage(passage);
      story.removePassage(link);
      assertEquals(0, story.getPassages().size());
    }

    @Test
    @DisplayName("Test if removePassage() does not remove a passage")
    void removePassageShouldNotRemovePassage() {
      Link link1 = new Link("Passage 2", "Passage 2");
      passage.addLink(link1);
      story.addPassage(passage);
      story.removePassage(link);
      assertEquals(2, passage.getListOfLinks().size());
    }

    @Test
    @DisplayName("Test if getBrokenLinks() returns a list of links not connected to a passage")
    void shouldReturnListOfDeadLinks() {
      Link link1 = new Link("Passage 2", "Passage 2");
      passage.addLink(link1);
      story.addPassage(passage);
      assertEquals(1, story.getBrokenLinks().size());
    }

    @Test
    @DisplayName("Test if getBrokenLinks() returns an empty list if there are no broken links")
    void shouldReturnEmptyListIfNoDeadLinks() {
      story.addPassage(passage);
      assertEquals(0, story.getBrokenLinks().size());
    }
  }
}
