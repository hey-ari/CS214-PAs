import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CS_214_Project_Tester {

    private static final String SONGS_FILE = "test_songs.txt";
    private static final String RANKINGS_FILE = "test_rankings.txt";
    private static final String OUTPUT_FILE = "test_output.txt";

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public static void setUp() throws IOException {
        // Redirecting System.out to capture output for tests
        System.setOut(new PrintStream(outContent));

        // Create test files with sample data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SONGS_FILE))) {
            writer.write("Song1\nSong2\nSong3");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RANKINGS_FILE))) {
            writer.write("User1,5,0,3\nUser2,4,2,5");
        }
    }

    @AfterAll
    public static void restore() {
        // Restore System.out and delete test files
        System.setOut(originalOut);
        new File(SONGS_FILE).delete();
        new File(RANKINGS_FILE).delete();
        new File(OUTPUT_FILE).delete();
    }

   
    @Test
    public void testReadSongsNotEmpty() throws IOException {
        CS_214_Project.readSongs(SONGS_FILE);
        assertFalse(CS_214_Project.songs.isEmpty(), "Songs list should not be empty after reading.");
    }

    @Test
    public void testReadRankingsNotEmpty() throws IOException {
        CS_214_Project.readRankings(RANKINGS_FILE);
        assertFalse(CS_214_Project.userRatingsMap.isEmpty(), "User ratings map should not be empty.");
    }

    @Test
    public void testReadRankingsCorrectSize() throws IOException {
        CS_214_Project.readRankings(RANKINGS_FILE);
        assertEquals(2, CS_214_Project.userRatingsMap.size(), "There should be two users in the map.");
    }

    @Test
    public void testNormalization() {
        CS_214_Project.UserRating userRating = new CS_214_Project.UserRating();
        userRating.rawRatings = Arrays.asList(1, 2, 3, 4, 5);
        userRating.normalizeRatings();
        assertNotNull(userRating.normalizedRatings, "Normalized ratings should not be null.");
        assertFalse(userRating.normalizedRatings.isEmpty(), "Normalized ratings should not be empty.");
    }

    @Test
    public void testOutputFileExists() throws IOException {
        CS_214_Project.readSongs(SONGS_FILE);
        CS_214_Project.readRankings(RANKINGS_FILE);
        CS_214_Project.processRankings();
        CS_214_Project.writeRankings(OUTPUT_FILE);

        File outputFile = new File(OUTPUT_FILE);
        assertTrue(outputFile.exists(), "Output file should exist.");
    }

    @Test
public void testOutputFileNotEmpty() throws IOException {
    // Ensure that the songs and rankings are read before processing and writing.
    CS_214_Project.readSongs(SONGS_FILE);
    CS_214_Project.readRankings(RANKINGS_FILE);

    // Invoke the processing method to ensure data is processed before writing.
    CS_214_Project.processRankings();

    // Write the processed data to the output file.
    CS_214_Project.writeRankings(OUTPUT_FILE);

    // Check if the output file exists and is not empty.
    File outputFile = new File(OUTPUT_FILE);
    assertTrue(outputFile.exists(), "Output file should exist after writing.");
    assertTrue(outputFile.length() > 0, "Output file should not be empty after writing.");

    // Additional check to ensure the file contains expected content.
    try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
        assertNotNull(reader.readLine(), "Output file should have at least one line of content.");
    }
}


    @Test
    public void testPredictedScores() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> ratings = Arrays.asList(line.split(","));
                assertFalse(ratings.contains("0"), "There should be no zero ratings in the output file.");
            }
        }
    }

    @Test
    public void testScoreRange() throws IOException {
        // Verify that all scores are within the expected range
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Skip the user part and check only the scores
                for (int i = 1; i < parts.length; i++) {
                    int score = Integer.parseInt(parts[i]);
                    assertTrue(score >= 1 && score <= 5, "Score should be within the range 1 to 5.");
                }
            }
        }
    }
}
