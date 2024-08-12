import java.io.*;
import java.util.*;
// import java.util.stream.Collectors;

public class CS_214_Project {

  // Inner class to handle user ratings and statistics
  static class UserRating {
    List<Integer> ratings;
    double mean = 0;
    double stdDev = 0;
    boolean isUncooperative = false;

    public UserRating() {
        ratings = new ArrayList<>();
    }

    void calculateStatistics() {
        long validRatingsCount = ratings.stream().filter(rating -> rating > 0).count();
        if (validRatingsCount == 0 || new HashSet<>(ratings).size() == 1) {
            isUncooperative = true;
            return;
        }

        mean = ratings.stream().filter(rating -> rating > 0).mapToInt(Integer::intValue).average().orElse(Double.NaN);

        stdDev = Math.sqrt(ratings.stream()
                           .filter(rating -> rating > 0)
                           .mapToDouble(r -> Math.pow(r - mean, 2))
                           .sum() / validRatingsCount);
    }

    boolean isUncooperative() {
        return isUncooperative;
    }
}
  

static List<String> readSongTitles(String filename) throws IOException {
 
    List<String> songs = new ArrayList<>();
  try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
          // Check if the line is a sequence of space-separated integers (rating line format)
          if (!line.trim().isEmpty() && line.trim().matches("^\\d+(\\s+\\d+)*$")) {
              throw new IllegalArgumentException("Invalid song title, contains only numbers: " + line);
          }
          songs.add(line.trim());
      }
  }
  return songs;
}


  // Process song ratings from a file
  static void processSongRatings(String ratingsFile, String outputFile, List<String> songs) throws IOException {
    // Check if there are no songs and handle appropriately
    if (songs.isEmpty()) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            bw.write("No songs to process.");  // Write a message to the output file
        }
        return;  // Exit the method as there are no songs to process
    }

    Map<Integer, UserRating> userRatings = new HashMap<>();
    int userCount = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(ratingsFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] ratingsStr = line.trim().split("\\s+");
            userCount++;

            UserRating userRating = new UserRating();
            for (String ratingStr : ratingsStr) {
                try {
                    int rating = Integer.parseInt(ratingStr);
                    if (rating >= 1 && rating <= 5) {
                        userRating.ratings.add(rating);
                    } else {
                        userRating.ratings.add(0);  // Invalid rating
                    }
                } catch (NumberFormatException e) {
                    userRating.ratings.add(0);  // Non-numeric rating
                }
            }
            userRatings.put(userCount - 1, userRating);
        }
    }

    userRatings.values().forEach(UserRating::calculateStatistics);
    userRatings.values().removeIf(UserRating::isUncooperative);
    writeNormalizedRatings(outputFile, songs, userRatings);
}



  // Write normalized ratings to a file
  static void writeNormalizedRatings(String outputFile, List<String> songs, Map<Integer, UserRating> userRatings) throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
        for (String song : songs) {
            int songIndex = songs.indexOf(song);
            double sum = 0;
            int count = 0;
            for (UserRating rating : userRatings.values()) {
                if (songIndex < rating.ratings.size() && rating.ratings.get(songIndex) > 0) {
                    double normalizedRating = (rating.ratings.get(songIndex) - rating.mean) / rating.stdDev;
                    sum += normalizedRating;
                    count++;
                }
            }

            double normalizedMean = count > 0 ? sum / count : Double.NaN;
            double varianceSum = 0;
            for (UserRating rating : userRatings.values()) {
                if (songIndex < rating.ratings.size() && rating.ratings.get(songIndex) > 0) {
                    double normalizedRating = (rating.ratings.get(songIndex) - rating.mean) / rating.stdDev;
                    varianceSum += Math.pow(normalizedRating - normalizedMean, 2);
                }
            }

            double normalizedStdDev = count > 1 ? Math.sqrt(varianceSum / (count - 1)) : Double.NaN;
            bw.write(song + ": " + (Double.isNaN(normalizedMean) ? "UNDEFINED" : String.format("%.7f", normalizedMean)) +
                     ", " + (Double.isNaN(normalizedStdDev) ? "UNDEFINED" : String.format("%.7f", normalizedStdDev)));
            bw.newLine();
        }
    }
}


  // Main method to run the program
  public static void main(String[] args) {
    // Ensure the correct number of arguments are provided
    if (args.length != 3) {
      System.out.println("Usage: java CS_214_Project <songsFile> <ratingsFile> <outputFile>");
      return;
    }

    // Process the song titles and ratings, then write the normalized ratings to the output file
    try {
      List<String> songs = readSongTitles(args[0]);
      processSongRatings(args[1], args[2], songs);
    } catch (IOException e) {
      System.err.println("Error processing files: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Error in input data: " + e.getMessage());
    }
  }
}












/*
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java CS_214_Project <file1> <file2> <outputFile>");
            return;
        }

        try {
            List<String> songs = null;
            try {
                songs = readSongTitles(args[0]);
                processSongRatings(args[1], args[2], songs);
            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Trying to switch file order...");
                songs = readSongTitles(args[1]);
                processSongRatings(args[0], args[2], songs);
            }
        } catch (IOException e) {
            System.err.println("I/O error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input validation error: " + e.getMessage());
        }
    }
}
*/