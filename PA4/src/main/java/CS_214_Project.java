


/** */
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CS_214_Project {

    static class UserRating {
        List<Integer> rawRatings = new ArrayList<>();
        List<Double> normalizedRatings = new ArrayList<>();
        double mean = 0;
        double stdDev = 0;

        // Normalize the ratings for each user
        void normalizeRatings() {
            // Calculate the mean of the ratings
            mean = rawRatings.stream().mapToInt(Integer::intValue).average().orElse(0);

            // Calculate the standard deviation
            stdDev = Math.sqrt(rawRatings.stream()
                    .mapToDouble(rating -> Math.pow(rating - mean, 2))
                    .average().orElse(0));

            // Normalize the ratings
            for (int rating : rawRatings) {
                if (stdDev == 0) {
                    normalizedRatings.add(0.0);
                } else {
                    normalizedRatings.add((rating - mean) / stdDev);
                }
            }
        }
    }

    // Reads the song titles from a file
    static List<String> readSongTitles(String filename) throws IOException {
      List<String> songs = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
          String line;
          while ((line = br.readLine()) != null) {
              songs.add(line.trim());
          }
      }
      return songs;
  }

  static void processSongRatings(String ratingsFile, List<List<Integer>> userRatings) throws IOException {
    List<List<Integer>> songRatings = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(ratingsFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            List<Integer> ratings = Arrays.stream(line.trim().split("\\s+"))
                                          .map(Integer::parseInt)
                                          .peek(rating -> {
                                              if (rating < 0 || rating > 5) {
                                                  throw new IllegalArgumentException("Rating values should be between 0 and 5.");
                                              }
                                          })
                                          .collect(Collectors.toList());

            songRatings.add(ratings);
        }
    }
  

    // Check if all songs have ratings from the same number of users
    if (!songRatings.isEmpty()) {
        int numberOfUsers = songRatings.get(0).size();
        for (List<Integer> ratings : songRatings) {
            if (ratings.size() != numberOfUsers) {
                throw new IllegalArgumentException("All songs must have ratings from the same number of users.");
            }
        }
    }

    // Transpose the matrix to align with the users
    if (!songRatings.isEmpty()) {
        int numberOfUsers = songRatings.get(0).size();
        for (int i = 0; i < numberOfUsers; i++) {
            List<Integer> userRating = new ArrayList<>();
            for (List<Integer> songRating : songRatings) {
                userRating.add(songRating.get(i));
            }
            userRatings.add(userRating);
        }
    }
}

static List<UserRating> convertToUserRatings(List<List<Integer>> allSongRatings) {
    List<UserRating> userRatings = new ArrayList<>();
    if (!allSongRatings.isEmpty()) {
        int numberOfUsers = allSongRatings.get(0).size();
        for (int i = 0; i < numberOfUsers; i++) {
            UserRating userRating = new UserRating();
            for (List<Integer> songRatings : allSongRatings) {
                userRating.rawRatings.add(songRatings.get(i));
            }
            userRating.normalizeRatings();
            userRatings.add(userRating);
        }
    }
    return userRatings;
}

    // Calculates the similarity matrix for songs or users
    static double[][] calculateSimilarity(List<UserRating> userRatings, int count, boolean isSong) {
        double[][] similarityMatrix = new double[count][count];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                double sum = 0.0;
                for (int k = 0; k < userRatings.size(); k++) {
                    double valueI = isSong ? userRatings.get(k).normalizedRatings.get(i) : userRatings.get(i).normalizedRatings.get(k);
                    double valueJ = isSong ? userRatings.get(k).normalizedRatings.get(j) : userRatings.get(j).normalizedRatings.get(k);
                    sum += valueI * valueJ;
                }
                similarityMatrix[i][j] = sum;
            }
        }

        return similarityMatrix;
    }

    // Writes the similarity scores to the specified output file
    static void writeSimilarityScores(String outputFile, double[][] similarityMatrix) throws IOException {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
          for (double[] row : similarityMatrix) {
              for (int i = 0; i < row.length; i++) {
                  bw.write(String.valueOf(row[i]) + (i < row.length - 1 ? " " : ""));
              }
              bw.newLine();
          }
      }
  }


  public static void main(String[] args) {
    if (args.length != 4) {
        System.out.println("Usage: java CS_214_Project <songsFile> <ratingsFile> <songsSimilarityFile> <usersSimilarityFile>");
        return;
    }

    try {
        List<String> songs = readSongTitles(args[0]);
        List<List<Integer>> allUserRatings = new ArrayList<>();
        processSongRatings(args[1], allUserRatings);

        List<UserRating> userRatings = convertToUserRatings(allUserRatings);
        double[][] songSimilarity = calculateSimilarity(userRatings, songs.size(), true);
        double[][] userSimilarity = calculateSimilarity(userRatings, userRatings.size(), false);

        writeSimilarityScores(args[2], songSimilarity);
        writeSimilarityScores(args[3], userSimilarity);
    } catch (IOException e) {
        System.err.println("Error processing files: " + e.getMessage());
        return; // Exit the program after displaying the error message
    } catch (IllegalArgumentException e) {
        System.err.println("Error: " + e.getMessage());
        return; // Exit the program after displaying the error message
    } catch (Exception e) {
        System.err.println("An unexpected error occurred: " + e.getMessage());
        return; // Exit the program after displaying the error message
    }
    }
}