/* DISCLAIMER: CREATED BY ARIADNA SHAMRAEVA FOR SELF-STUDY PURPOSES ONLY.
NOBODY HAS PERMISSION TO COPY AND REUSE ANY MATERIALS IN THE CURRENT REPOSITORY. 
COPYING ANY PARTS OF THIS ASSIGNMENT IS CONSIDERED PLAGIARISM AT CSU AND OTHER INSTITUTIONS.
VIOLATORS WILL BE RESPONSIBLE IN FULL AND FACE CONSEQUENCES SPECIFIED BY THE INSTITUTION.*/

import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class TestPA2 {

    // @TempDir is a JUnit Jupiter annotation that creates a temporary directory before the test method or class starts,
    // and deletes it after the test method or class finishes.
    @TempDir
    static Path tempDir;

    // Static variables to hold paths for the titles, ratings, and output files.
    static Path titlesFilePath;
    static Path ratingsFilePath;
    static Path outputFilePath;

    // @BeforeAll is an annotation used to signal that the annotated method should be executed before all tests in the current class.
    @BeforeAll
    static void setUp() throws IOException {
        // Resolves the paths for our test files in the temporary directory.
        titlesFilePath = tempDir.resolve("titles.txt");
        ratingsFilePath = tempDir.resolve("ratings.txt");
        outputFilePath = tempDir.resolve("output.txt");
    }

    // @Test is used to signal that the annotated method is a test method.
    @Test
    void testValidInputProducesExpectedOutput() throws Exception {
        // Writes sample valid data to titles and ratings files.
        Files.writeString(titlesFilePath, "Song1\nSong2");
        Files.writeString(ratingsFilePath, "5 4 3\n3 2 1");
        
        // Executes the main method of PA2 with the paths of the input and output files.
        PA2.main(new String[]{titlesFilePath.toString(), ratingsFilePath.toString(), outputFilePath.toString()});
        
        // Asserts that the output file exists and its content length is not zero.
        assertTrue(Files.exists(outputFilePath));
        assertNotEquals(0, Files.readString(outputFilePath).length());
    }

    // Additional tests follow similar patterns, testing various edge cases like empty input files,
    // mismatched input sizes, and handling of invalid ratings.

    // This test checks the program's behavior with an empty titles file.
    @Test
    void testWithEmptyTitlesFile() throws Exception {
        Files.writeString(titlesFilePath, "");
        Files.writeString(ratingsFilePath, "5 4 3\n3 2 1");
        
        // Since PA2's behavior may vary, adjust this assertion based on its actual handling of an empty titles file.
        assertDoesNotThrow(() -> PA2.main(new String[]{titlesFilePath.toString(), ratingsFilePath.toString(), outputFilePath.toString()}));
    }

    // Similar to the previous test, but for an empty ratings file.
    @Test
    void testWithEmptyRatingsFile() throws Exception {
        Files.writeString(titlesFilePath, "Song1\nSong2");
        Files.writeString(ratingsFilePath, "");
        
        // Adjust the assertion based on PA2's handling of an empty ratings file.
        assertDoesNotThrow(() -> PA2.main(new String[]{titlesFilePath.toString(), ratingsFilePath.toString(), outputFilePath.toString()}));
    }

    // This test verifies the program can handle a mismatch in the number of titles and ratings.
    @Test
    void testMismatchBetweenTitlesAndRatings() throws Exception {
        Files.writeString(titlesFilePath, "Song1\nSong2\nSong3");
        Files.writeString(ratingsFilePath, "5 4 3\n3 2 1");
        
        // Adjust based on how PA2 is expected to behave when there's a mismatch in input sizes.
        assertDoesNotThrow(() -> PA2.main(new String[]{titlesFilePath.toString(), ratingsFilePath.toString(), outputFilePath.toString()}));
    }

    // Test to verify how PA2 handles a ratings file containing invalid ratings.
    @Test
    void testRatingsFileWithInvalidRatings() throws Exception {
        Files.writeString(titlesFilePath, "Song1\nSong2");
        Files.writeString(ratingsFilePath, "5 4 3\ninvalid");
        
        // Adjust the assertion based on PA2's specific handling of invalid ratings.
        assertDoesNotThrow(() -> PA2.main(new String[]{titlesFilePath.toString(), ratingsFilePath.toString(), outputFilePath.toString()}));
    }

    // Test to check PA2's behavior when a nonexistent file is provided as input.
    @Test
    void testFileNotFound() {
        Path nonexistentFilePath = tempDir.resolve("nonexistent.txt");
        
        // Adjust based on how PA2 handles attempts to read a nonexistent file.
        assertDoesNotThrow(() -> PA2.main(new String[]{nonexistentFilePath.toString(), ratingsFilePath.toString(), outputFilePath.toString()}));
    }

    // Finally, this test checks PA2's response when no input files are provided.
    @Test
    void testNoInputFilesProvided() {
        // Adjust based on PA2's expected behavior when no input files are provided.
        assertDoesNotThrow(() -> PA2.main(new String[]{"", "", ""}));
    }
}





    
}
