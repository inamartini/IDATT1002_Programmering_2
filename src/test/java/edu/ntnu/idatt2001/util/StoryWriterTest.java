package edu.ntnu.idatt2001.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2001.action.Action;
import edu.ntnu.idatt2001.action.ActionFactory;
import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Player;
import edu.ntnu.idatt2001.base.PlayerBuilder;
import edu.ntnu.idatt2001.base.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for StoryWriter
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */
public class StoryWriterTest {

    private Story story;
    private String path;

    @BeforeEach
    void setUp() {
        path = FileSystems.getDefault().getPath("src",
                "test", "resources", "test.paths").toString();
    }

    @Nested
    @DisplayName("Tests if functionality of StoryWriter works as expected")
    class TestFunctionality {
        @Test
        @DisplayName("Story is correctly written to file")
        void writeStoryToFile() throws IOException {
            //arrange
            Passage openingPassage = new Passage("Title of the opening passage", "Content in the opening passage");
            story = new Story("Test Story", openingPassage);
            Link link1 = new Link("Test Link 1", "Title of the second passage");
            Action healthAction = ActionFactory.createAction("HEALTH", 10);
            Action inventoryAction = ActionFactory.createInventoryAction("Inventory", "Sword");
            link1.addAction(healthAction);
            link1.addAction(inventoryAction);
            openingPassage.addLink(link1);
            Passage secondPassage = new Passage("Title of the second passage", "Content in the second passage");
            Link link2 = new Link("Test Link 2", "Title of the third passage");
            secondPassage.addLink(link2);
            Passage thirdPassage = new Passage("Title of the third passage", "Content in the third passage");
            Player player = new PlayerBuilder("Test Player").build();
            healthAction.execute(player);

            story.addPassage(openingPassage);
            story.addPassage(secondPassage);
            story.addPassage(thirdPassage);

            File file = new File(path);

            //act
            StoryWriter.writeStoryToFile(story, path);
            StoryReader.readStoryFromFile(file);

            //assert
            assertEquals(story.getTitle(), StoryReader.readStoryFromFile(file).getTitle());
            assertEquals(story.getOpeningPassage().getTitle(), StoryReader.readStoryFromFile(file).getOpeningPassage().getTitle());
            assertEquals(story.getOpeningPassage().getContent(), StoryReader.readStoryFromFile(file).getOpeningPassage().getContent());
            assertEquals(story.getPassages().size(), StoryReader.readStoryFromFile(file).getPassages().size());
            assertEquals(story.getOpeningPassage().getListOfLinks(), StoryReader.readStoryFromFile(file).getOpeningPassage().getListOfLinks());
            assertEquals(story.getPassage(link2).getListOfLinks(), StoryReader.readStoryFromFile(file).getPassage(link2).getListOfLinks());
            assertEquals(player.getHealth(), 10);
        }
    }

    @Nested
    @DisplayName("Tests if exceptions are thrown as expected")
    class TestExceptions {

        @Test
        @DisplayName("IllegalArgumentException is thrown if story is null")
        void shouldThrowIllegalArgumentExceptionIfStoryIsNull() {
            assertThrows(IllegalArgumentException.class, () -> StoryWriter.writeStoryToFile(null, path));
        }
        @Test
        @DisplayName("IllegalArgumentException is thrown if path is null")
        void shouldThrowIllegalArgumentExceptionIfPathIsNull() {
            assertThrows(IllegalArgumentException.class, () -> StoryWriter.writeStoryToFile(story, null));
        }

        @Test
        @DisplayName("IllegalArgumentException is thrown if file is not a test1.paths file")
        void shouldThrowIllegalArgumentExceptionIfFileIsNotAPathsFile() {
            assertThrows(IllegalArgumentException.class,
                    () -> StoryWriter.writeStoryToFile(story, "src/test/resources/test.txt"));
        }
    }
}