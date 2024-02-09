import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestPA2 {

    @TempDir
    Path tempDir;

    File titlesFile;
    File ratingsFile;
    File outputFile;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize temporary files for song titles, ratings, and output before each test
        titlesFile = tempDir.resolve("titles.txt").toFile();
        ratingsFile = tempDir.resolve("ratings.txt").toFile();
        outputFile = tempDir.resolve("output.txt").toFile();
    }

    @AfterEach
    void tearDown() {
        // No need for explicit cleanup thanks to @TempDir
    }

    @Test
    void testWriteOutputWithValidData() throws Exception {
        // Prepare test data
        try (PrintWriter writer = new PrintWriter(titlesFile)) {
            writer.println("Song One");
            writer.println("Song Two");
        }
        try (PrintWriter writer = new PrintWriter(ratingsFile)) {
            writer.println("5 4 3 2 1"); // Ratings for Song One
            writer.println("1 2 3 4 5"); // Ratings for Song Two
        }

        // This assumes a static method in SongStatistics that can be called directly.
        // Adjust accordingly if your implementation differs.
        PA2.main(new String[]{titlesFile.getAbsolutePath(), ratingsFile.getAbsolutePath(), outputFile.getAbsolutePath()});

        // Verify the output
        try (Scanner scanner = new Scanner(outputFile)) {
            assertTrue(scanner.hasNextLine(), "The output file should have content.");
            String songOneOutput = scanner.nextLine().trim();
            String songTwoOutput = scanner.nextLine().trim();
            
            // Adjust these assertions based on the expected output format and values.
            assertEquals("Expected output for Song One", songOneOutput, "Song One 3.00 1.41");
            assertEquals("Expected output for Song Two", songTwoOutput, "Song Two 3.00 1.41");
        }
    }

    // Test for Empty Song Titles File
    @Test
    void testEmptySongTitlesFile() throws IOException {
      // Create an empty titles file
      PrintWriter writer = new PrintWriter(titlesFile);
      writer.close();
    
      // Populate ratings file with some data
      writer = new PrintWriter(ratingsFile);
      writer.println("5 4 3");
      writer.close();
    
      // Run the program and expect an exception or specific behavior
      Exception exception = assertThrows(Exception.class, () -> {
        PA2.main(new String[]{titlesFile.getAbsolutePath(), ratingsFile.getAbsolutePath(), outputFile.getAbsolutePath()});
      });
      // Verify that the exception message is correct
      assertTrue(exception.getMessage().contains("Error: The song titles file is empty."));
    }

    // Test for Empty Ratings File
    @Test
    void testEmptyRatingsFile() throws IOException {
      // Populate the titles file with at least one song title
      PrintWriter writer = new PrintWriter(titlesFile);
      writer.println("Song One");
      writer.close();
    
      // Create an empty ratings file
      writer = new PrintWriter(ratingsFile);
      writer.close();
    
      // Run the program and expect an exception or specific error handling
      // Adjust the following line based on how your program reports errors
      Exception exception = assertThrows(Exception.class, () -> {
        PA2.main(new String[]{titlesFile.getAbsolutePath(), ratingsFile.getAbsolutePath(), outputFile.getAbsolutePath()});
    });
    
      // Verify that the exception or error message indicates the problem with the ratings file
      // Adjust the assertion based on the actual exception message or error handling in your program
      assertTrue(exception.getMessage().contains("Error: The ratings file is empty"), "The program should indicate the ratings file is empty.");
    }


    // Test for Ratings File with Invalid Numbers
    @Test
    void testRatingsFileWithInvalidNumbers() throws IOException {
      // Populate titles file
      PrintWriter writer = new PrintWriter(titlesFile);
      writer.println("Song One");
      writer.close();
    
      // Populate ratings file with invalid ratings
      writer = new PrintWriter(ratingsFile);
      writer.println("0 6 -3"); // Invalid ratings
      writer.close();
    
      // Run the program and expect an exception or specific behavior indicating invalid input
      Exception exception = assertThrows(Exception.class, () -> {
        PA2.main(new String[]{titlesFile.getAbsolutePath(), ratingsFile.getAbsolutePath(), outputFile.getAbsolutePath()});
      });
    
      // Check that the exception message indicates invalid ratings
      assertTrue(exception.getMessage().contains("Error: Invalid rating found"));
    }

    // Test for Mismatched Number of Songs and Ratings
    @Test
    void testMismatchNumberOfSongsAndRatings() throws IOException {
      // Populate titles file with two songs
      PrintWriter writer = new PrintWriter(titlesFile);
     writer.println("Song One");
      writer.println("Song Two");
      writer.close();
    
      // Populate ratings file with ratings for only one song
      writer = new PrintWriter(ratingsFile);
      writer.println("5 4 3 2 1");
      writer.close();
    
      // Run the program and expect an exception or specific behavior
      Exception exception = assertThrows(Exception.class, () -> {
        PA2.main(new String[]{titlesFile.getAbsolutePath(), ratingsFile.getAbsolutePath(), outputFile.getAbsolutePath()});
      });
    
      // Verify that the program correctly identified the mismatch
      assertTrue(exception.getMessage().contains("Error: Mismatch in number of songs and ratings."));
    }








    
}
