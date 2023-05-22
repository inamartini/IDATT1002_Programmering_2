package edu.ntnu.idatt2001.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Passage.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */


public class PassageTest {

  private Passage passage;
  private Link links;
  private String notEmptyString;

  @Nested
  @DisplayName("Tests that exceptions are thrown")
  class ExceptionsAreThrown {

    @BeforeEach
    public void setUp() {
      notEmptyString = "Test";
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if title is null")
    void throwsIllegalArgumentExceptionIfTitleIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> passage = new Passage(null, notEmptyString));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if content is null")
    void throwsIllegalArgumentExceptionIfContentIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> passage = new Passage(notEmptyString, null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if link is null")
    void throwsIllegalArgumentExceptionIfLinkIsNull() {
      passage = new Passage(notEmptyString, notEmptyString);
      assertThrows(IllegalArgumentException.class,
          () -> passage.addLink(null));
    }
  }

  @Nested
  @DisplayName("Tests that get methods work as expected")
  class GetMethodsWorkAsExpected {
    @BeforeEach
    void setUp() {
      passage = new Passage("Title", "Content");
    }

    @Test
    @DisplayName("The correct title is returned")
    void getTitleShouldReturnCorrectTitle() {
      assertEquals("Title", passage.getTitle());
    }

    @Test
    @DisplayName("The correct content is returned")
    void getContentShouldReturnCorrectContent() {
      assertEquals("Content", passage.getContent());
    }

    @Test
    @DisplayName("The correct list of links is returned")
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
    @DisplayName("Returns the correct size of list of links")
    void addLinkTestSize() {
      passage.addLink(links);
      links = new Link("new text", "new reference2");
      passage.addLink(links);
      assertEquals(2, passage.getListOfLinks().size());
    }

    @Test
    @DisplayName("Returns the the correct link by index")
    void addLinkTestIndex() {
      passage.addLink(links);
      links = new Link("new text", "new reference2");
      passage.addLink(links);
      assertEquals(links = new Link("Text", "Reference"), passage.getListOfLinks().get(0));
    }

    @Test
    @DisplayName("Returns true if list of links is not empty")
    void hasLinksIsNotEmpty() {
      passage.addLink(links);
      assertTrue(passage.hasLinks());
    }

    @Test
    @DisplayName("Returns false if list of links is empty")
    void hasLinksIsEmpty() {
      assertFalse(passage.hasLinks());
    }
  }
}
