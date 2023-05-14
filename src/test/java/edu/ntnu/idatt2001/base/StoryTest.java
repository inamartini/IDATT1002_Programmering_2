package edu.ntnu.idatt2001.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Story.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.05.14
 */

public class StoryTest {

  private Passage openingPassage;
  private Link link;
  private Story story;
  private String notEmptyString;

  @BeforeEach
  public void setUp() {
    openingPassage = new Passage("Opening passage", "Content of the opening passage");
    link = new Link("Link", "Passage 2");
    story = new Story("This is a title", openingPassage);
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
      Passage newPassage = new Passage("newPassage", "Content of passage 2");
      Link newLink = new Link("newLink", "newPassage");
      story.addPassage(newPassage);
      assertEquals(newPassage, story.getPassage(newLink));
    }

    @Test
    @DisplayName("The correct passages are returned")
    void returnsTheCorrectPassages() {
      story.addPassage(openingPassage);
      Passage passage2 = new Passage("Passage 2", "Content of passage 2");
      story.addPassage(passage2);
      Collection<Passage> actualPassages = story.getPassages();
      assertTrue(actualPassages.contains(openingPassage));
      assertTrue(actualPassages.contains(passage2));
    }

    @Test
    @DisplayName("Test that the a passage is added")
    void passageIsAdded() {
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

    @Test
    @DisplayName("IllegalArgumentException is thrown if link is null")
    void removePassageShouldThrowIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> story.removePassage(null));
    }

    @Test
    @DisplayName("Passage is removed if it exists and does not have any links")
    void removePassageShouldRemovePassage() {
      Passage passage = new Passage("Passage 1", "Content of passage 1");
      Link link = new Link("Link 1", "Passage 1");
      story.addPassage(passage);
      story.removePassage(link);
      assertFalse(story.getPassages().contains(passage));
    }

    @Test
    @DisplayName("Passage is not removed if it is referenced by another passage")
    void removePassageShouldNotRemoveReferencedPassage() {
      Passage passage1 = new Passage("Passage 1", "Content of passage 1");
      Passage passage2 = new Passage("Passage 2", "Content of passage 2");
      Link link2 = new Link("Link 2", "Passage 2");
      Link link3 = new Link("Link 3", "Passage 3");
      passage1.addLink(link2);
      passage2.addLink(link3);
      story.addPassage(passage1);
      story.addPassage(passage2);
      story.removePassage(link);
      assertTrue(story.getPassages().contains(passage2));
    }

    @Test
    @DisplayName("Returns a list of links not connected to a passage")
    void getBrokenLinksShouldReturnBrokenLinks() {
      Passage passage1 = new Passage("Passage 1", "Content of passage 1");
      Passage passage2 = new Passage("Passage 2", "Content of passage 2");
      Link link2 = new Link("Link 2", "Passage 2");
      Link link3 = new Link("Link 3", "Passage 3");
      passage1.addLink(link2);
      passage1.addLink(link3);
      story.addPassage(passage1);
      story.addPassage(passage2);
      List<Link> brokenLinks = story.getBrokenLinks();
      assertEquals(1, brokenLinks.size());
      assertTrue(brokenLinks.contains(link3));
    }

    @Test
    @DisplayName("returns an empty list if there are no broken links")
    void getBrokenLinksShouldReturnEmptyList() {
      Passage passage1 = new Passage("Passage 1", "Content of passage 1");
      Passage passage2 = new Passage("Passage 2", "Content of passage 2");
      Link link2 = new Link("Link 2", "Passage 2");
      passage1.addLink(link2);
      story.addPassage(passage1);
      story.addPassage(passage2);
      List<Link> brokenLinks = story.getBrokenLinks();
      assertTrue(brokenLinks.isEmpty());
    }
  }
}
