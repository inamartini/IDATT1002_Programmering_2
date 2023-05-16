package edu.ntnu.idatt2001.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2001.action.Action;
import edu.ntnu.idatt2001.action.ActionFactory;
import edu.ntnu.idatt2001.action.GoldAction;
import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Player;
import edu.ntnu.idatt2001.base.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

/**
 * Test class for StoryReader
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */

public class StoryReaderTest {

    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = FileSystems.getDefault().getPath("src", "test",
                "resources", "precomputedvalues.paths").toString();
    }

    @Nested
    class TestExceptions {
        @Test
        @DisplayName("IllegalArgumentException is thrown if filename does not end with .paths")
        void shouldThrowIllegalArgumentExceptionIfFilenameDoesNotEndWithPaths() {
            File testFile = new File("src/test/resources/test.txt");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }

        @Test
        @DisplayName("Test1 file throws IllegalArgumentException")
        void shouldThrowIllegalArgumentExceptionIfTest1FileIsRead() {
            File testFile = new File("src/test/resources/test1.paths");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }

        @Test
        @DisplayName("Test2 file throws IllegalArgumentException")
        void shouldThrowIllegalArgumentExceptionIfTest2FileIsRead() {
            File testFile = new File("src/test/resources/test2.paths");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }
        @Test
        @DisplayName("Test3 file throws IllegalArgumentException")
        void shouldThrowIllegalArgumentExceptionIfTest3FileIsRead() {
            File testFile = new File("src/test/resources/test3.paths");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }
    }

    @Nested
    @DisplayName("Test if functionality works as intended")
    class TestFunctionality {

        @Test
        @DisplayName("File is read correctly")
        void readStoryFromFile1() throws FileNotFoundException {
            //arrange
            File testFile = new File(testFilePath);
            Passage openingPassage = new Passage("Title of the opening passage", "Content in the opening passage");
            Story expectedStory = new Story("Test Story", openingPassage);
            Link link1 = new Link("Test Link 1", "Title of the second passage");
            openingPassage.addLink(link1);
            Action inventoryAction = ActionFactory.createInventoryAction("inventory", "Sword");
            Action healthAction = ActionFactory.createAction("health", 100);
            link1.addAction(inventoryAction);
            link1.addAction(healthAction);
            Passage secondPassage = new Passage("Title of the second passage", "Content in the second passage");
            Link link2 = new Link("Test Link 2", "Title of the third passage");
            secondPassage.addLink(link2);
            Passage thirdPassage = new Passage("Title of the third passage", "Content in the third passage");
            expectedStory.addPassage(secondPassage);
            expectedStory.addPassage(thirdPassage);

            //act
            Story actualStory = StoryReader.readStoryFromFile(testFile);

            //assert
            assertEquals(expectedStory.getTitle(), actualStory.getTitle());
            assertEquals(expectedStory.getOpeningPassage().getTitle(), actualStory.getOpeningPassage().getTitle());
            assertEquals(expectedStory.getOpeningPassage().getContent(), actualStory.getOpeningPassage().getContent());
            assertEquals(expectedStory.getPassages().size(), actualStory.getPassages().size());

            Collection<Passage> expectedPassages = expectedStory.getPassages();
            Collection<Passage> actualPassages = actualStory.getPassages();
            assertEquals(expectedPassages.size(), actualPassages.size());

            List<Link> expectedLinks = new ArrayList<>();
            List<Link> actualLinks = new ArrayList<>();
            expectedPassages.forEach(p -> expectedLinks.addAll(p.getListOfLinks()));
            actualPassages.forEach(p -> actualLinks.addAll(p.getListOfLinks()));
            assertEquals(expectedLinks.size(), actualLinks.size());

            List<Action> expectedActions = new ArrayList<>();
            List<Action> actualActions = new ArrayList<>();
            expectedLinks.forEach(l -> expectedActions.addAll(l.getActions()));
            actualLinks.forEach(l -> actualActions.addAll(l.getActions()));
            assertEquals(expectedActions.size(), actualActions.size());
            }
        }
    }







