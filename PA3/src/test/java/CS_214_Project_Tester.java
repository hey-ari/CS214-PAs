import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CS_214_Project_Tester {
  private static Path tempDir;
  
  @BeforeAll
  static void setup() throws IOException {
    tempDir = Files.createTempDirectory("testCS214Project");
  }
  
  @AfterAll
  static void tearDown() throws IOException {
    Files.walk(tempDir)
    .sorted(Comparator.reverseOrder())
    .map(Path::toFile)
    .forEach(File::delete);
  }

  private String createFile(String name, List<String> lines) throws IOException {
    Path file = tempDir.resolve(name);
    Files.write(file, lines);
    return file.toString();
  }

  @Test
  public void testReadSongTitles() throws IOException {
    List<String> songs = Arrays.asList("Song1", "Song2", "Song3");
    String songsFile = createFile("songs.txt", songs);

    List<String> readSongs = CS_214_Project.readSongTitles(songsFile);
    assertEquals(songs, readSongs, "Read song titles should match the input file.");
  }

  @Test
  public void testProcessSongRatings() throws IOException {
    List<String> songs = Arrays.asList("Song1", "Song2", "Song3");
    //String songsFile = createFile("songs.txt", songs);
    List<String> ratings = Arrays.asList("5 0 3", "4 0 0", "3 2 1");
    String ratingsFile = createFile("ratings.txt", ratings);
    String outputFile = tempDir.resolve("output.txt").toString();
    
    CS_214_Project.processSongRatings(ratingsFile, outputFile, songs);
    if (songs.isEmpty()) {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
          bw.write("No songs to process.");
          return;
      }
    }
    assertTrue(Files.exists(Path.of(outputFile)), "Output file should be created.");
  }

  @Test
  public void testWriteNormalizedRatings() throws IOException {
    List<String> songs = Arrays.asList("Song1", "Song2", "Song3");
    String outputFile = tempDir.resolve("normalized_output.txt").toString();
    Map<Integer, CS_214_Project.UserRating> userRatings = new HashMap<>();
    CS_214_Project.UserRating userRating = new CS_214_Project.UserRating();
    userRating.ratings.addAll(Arrays.asList(5, 4, 3));
    userRating.calculateStatistics();
    userRatings.put(0, userRating);

    CS_214_Project.writeNormalizedRatings(outputFile, songs, userRatings);

    assertTrue(Files.exists(Path.of(outputFile)), "Normalized ratings file should be created.");
  }

  @Test
  public void testInvalidRatingsFile() {
    //List<String> songs = Arrays.asList("Song1", "Song2", "Song3");
    String songsFile = tempDir.resolve("invalid_songs.txt").toString();
    Assertions.assertThrows(IOException.class, () -> {
      CS_214_Project.readSongTitles(songsFile);
    }, "Should throw IOException for non-existing songs file.");
  }

  @Test
public void testMainWithInvalidArguments() {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    CS_214_Project.main(new String[]{});
    String expectedOutput = "Usage: java CS_214_Project <songsFile> <ratingsFile> <outputFile>\n";

    assertEquals(expectedOutput, outContent.toString(), "Main method should guide for incorrect usage.");

    System.setOut(originalOut);
}


  @Test
  public void testEmptySongsFile() throws IOException {
    // Setup - Create an empty songs file and a dummy ratings file
    Path songsPath = tempDir.resolve("emptySongs.txt");
    Files.createFile(songsPath); // Ensure the file is empty
    
    Path ratingsPath = tempDir.resolve("ratings.txt");
    Files.writeString(ratingsPath, "1 2 3\n4 5 6"); // Content is irrelevant for this test
    
    Path outputPath = tempDir.resolve("output.txt");
    
    // Act - Run the method under test
    CS_214_Project.processSongRatings(ratingsPath.toString(), outputPath.toString(), new ArrayList<>());
    
    // Assert - Verify the output file contains the expected message
    String outputContent = Files.readString(outputPath);
    assertEquals("No songs to process.", outputContent.trim(), "The output file should contain the 'No songs to process.' message.");
  }
    


  @Test
  public void testExceptionForInvalidRatings() throws IOException {
    // Create a file with invalid ratings
    Path invalidRatingsFile = Files.createTempFile(tempDir, "invalid_ratings", ".txt");
    Files.write(invalidRatingsFile, Arrays.asList("NotANumber 5 3", "2 NotANumber 4"));

    // Define paths for the required files
    String ratingsFile = invalidRatingsFile.toString();
    String outputFile = tempDir.resolve("output.txt").toString();
    List<String> songs = Arrays.asList("Song1", "Song2", "Song3");

    // Execute the method expected to handle the invalid ratings
    assertDoesNotThrow(() -> CS_214_Project.processSongRatings(ratingsFile, outputFile, songs),
    "Method should handle invalid ratings format without throwing NumberFormatException.");
  }
}