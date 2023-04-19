package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for Passage.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 */


public class PassageTest {

  private Passage passage;
  private Link links;

  @Nested
  @DisplayName("Tests if expected exceptions are thrown")
  class ExceptionsAreThrown {

    String notEmptyString;

    @BeforeEach
    public void setUp() {
      notEmptyString = "Test";
    }

    @Test
    @DisplayName("Tests if constructor throws an IllegalArgumentException if title is null")
    void titleThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class,
              () -> passage = new Passage(null, notEmptyString));
    }

    @Test
    @DisplayName("Tests if constructor throws an IllegalArgumentException if content is null")
    void contentThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class,
              () -> passage = new Passage(notEmptyString, null));
    }

    @Test
    @DisplayName("Tests if addLink throws an IllegalArgumentException if link is null")
    void addLinkThrowIllegalArgumentExceptionTest() {
      passage = new Passage(notEmptyString, notEmptyString);
      assertThrows(IllegalArgumentException.class,
              () -> passage.addLink(null));
    }
  }

  @Nested
  @DisplayName("Tests if getMethods are returning the right values")
  class getMethodsAreReturningCorrectValues {
    @BeforeEach
    void setUp() {
      passage = new Passage("Title", "Content");
    }

    @Test
    @DisplayName("getTitle() should return the correct title")
    void getTitleShouldReturnCorrectTitle() {
      assertEquals("Title", passage.getTitle());
    }

    @Test
    @DisplayName("getContent() should return the correct content")
    void getContentShouldReturnCorrectContent() {
      assertEquals("Content", passage.getContent());
    }

    @Test
    @DisplayName("getListOfLinks() should return a list of links")
    void getListOfLinksTest() {
      links = new Link("Text", "Reference");
      passage.addLink(links);
      assertEquals(links, passage.getListOfLinks().get(0));
      assertEquals(1, passage.getListOfLinks().size());
    }
  }

  @Nested
  @DisplayName("Tests if addLink and hasLinks are returning correct values")
  class linkMethodsAreReturningCorrectValue {

    @BeforeEach
    void setUp() {
      passage = new Passage("Title", "Content");
      links = new Link("Text", "Reference");
    }

    @Test
    void addLinkTestSize() {
      passage.addLink(links);
      links = new Link("new text", "new reference2");
      passage.addLink(links);
      assertEquals(2, passage.getListOfLinks().size());
    }

    @Test
    void addLinkTestIndex() {
      passage.addLink(links);
      links = new Link("new text", "new reference2");
      passage.addLink(links);
      assertEquals(links = new Link("Text", "Reference"), passage.getListOfLinks().get(0));
    }

    @Test
    @DisplayName("hasLinks() should return true")
    void hasLinksIsNotEmptyTest() {
      passage.addLink(links);
      assertTrue(passage.hasLinks());
    }

    @Test
    @DisplayName("hasLinks() should return false")
    void hasLinksIsEmptyTest() {
      assertFalse(passage.hasLinks());
    }
  }
}
