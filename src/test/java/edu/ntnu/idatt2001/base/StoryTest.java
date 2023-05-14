package edu.ntnu.idatt2001.base;

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

  private Passage openingPassage;
  private Link link;
  private Story story;
  private Map<Link, Passage> passages;
  private String notEmptyString;

  @BeforeEach
  public void setUp() {
    openingPassage = new Passage("Opening passage", "Content of the opening passage");
    link = new Link("Link", "Passage 2");
    story = new Story("This is a title", openingPassage);
    //passages = new HashMap<>();
    //passages.put(link, openingPassage);
  }

  @Nested
  @DisplayName("Tests that get methods work as expected")
  class TestGetMethods {
    @Test
    @DisplayName("The correct title is returned")
    void getTitleShouldReturnCorrectTitle() {
      assertEquals("This is a title", story.getTitle());
    }

    @Test
    @DisplayName("The correct opening passage is returned")
    void getOpeningPassageShouldReturnCorrectOpeningPassage() {
      assertEquals(openingPassage, story.getOpeningPassage());
    }

    @Test
    @DisplayName("Test that the correct passage is returned")
    void returnsTheCorrectPassage() {
      openingPassage.addLink(link);
      story.addPassage(openingPassage);
      assertEquals(openingPassage, story.getPassage(link));
    }

    @Test
    @DisplayName("The correct passages are returned")
    void returnsTheCorrectPassages() {
      story.addPassage(openingPassage);
      assertEquals(passages.values().toString(), story.getPassages().toString());
    }

    @Test
    @DisplayName("Empty collection is returned if story has no passages")
    void emptyCollectionISReturnedIfStoryHasNoPassages() {
      openingPassage.addLink(link);
      story.addPassage(openingPassage);
      story.removePassage(link);
      Collection<Passage> actualPassages = story.getPassages();
      assertTrue(actualPassages.isEmpty());
    }

    @Test
    @DisplayName("Test that the a passage is added")
    public void passageIsAdded() {
      openingPassage.addLink(link);
      story.addPassage(openingPassage);
      assertEquals(1, story.getPassages().size());
    }
  }

  @Nested
  @DisplayName("Tests that exceptions are thrown")
  class ExceptionsAreThrown {

    @BeforeEach
    public void setUp() {
      notEmptyString = "Test";
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if title is null ")
    void titleThrowIllegalArgumentExceptionTest() {
      openingPassage = new Passage("New passage", "This is a test-passage.");
      assertThrows(IllegalArgumentException.class, () -> story = new Story(null, openingPassage));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if openingPassage is null")
    void openingPassageThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class, () -> story = new Story(notEmptyString, null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if passage is null")
    void addPassageThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class, () -> story.addPassage(null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if link is null")
    void getPassageShouldThrowIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> story.getPassage(null));
    }
  }

  @Nested
  @DisplayName("Tests that the core functionality of the class works as expected")
  class testDivCoreFunctionality {

    /*@BeforeEach
    void setUp() {
      passage = new Passage("Passage 1", "This is passage 1");
      link = new Link("Passage 1", "Passage 1");
      passage.addLink(link);
    }*/

    @Test
    @DisplayName("IllegalArgumentException is thrown if link is null")
    void removePassageShouldThrowIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> story.removePassage(null));
    }

    @Test
    @DisplayName("Passage is removed if it exists and does not have any links")
    public void removePassageShouldRemovePassage() {
      openingPassage.addLink(link);
      story.addPassage(openingPassage);
      story.removePassage(link);
      assertFalse(story.getPassages().contains(openingPassage));
    }

    @Test
    @DisplayName("Passage is not removed if other passages links to it")
    void removePassageShouldNotRemovePassage() {
        Passage passage1 = new Passage("Passage 2", "Content of passage 2");
        Link link1 = new Link("Link 1", "Passage 3");
        passage1.addLink(link1);
        Passage passage2 = new Passage("Passage 3", "Content of passage 3");
        story.addPassage(passage1);
        story.addPassage(passage2);
        assertThrows(IllegalArgumentException.class, () -> story.removePassage(link1));
    }

    @Test
    @DisplayName("Returns a list of links not connected to a passage")
    void shouldReturnListOfDeadLinks() {
      Link link1 = new Link("Passage 2", "Passage 2");
      openingPassage.addLink(link1);
      story.addPassage(openingPassage);
      assertEquals(1, story.getBrokenLinks().size());
    }

    @Test
    @DisplayName("Returns an empty list if there are no broken links")
    void shouldReturnEmptyListIfNoDeadLinks() {
      story.addPassage(openingPassage);
      assertEquals(0, story.getBrokenLinks().size());
    }
  }
}
