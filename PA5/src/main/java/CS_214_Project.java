import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CS_214_Project {

    static class UserRating {
        List<Integer> rawRatings = new ArrayList<>();
        List<Double> normalizedRatings = new ArrayList<>();
        double mean = 0;
        double stdDev = 0;

        void normalizeRatings() {
            mean = rawRatings.stream().mapToInt(Integer::intValue).average().orElse(0);
            stdDev = Math.sqrt(rawRatings.stream()
                    .mapToDouble(rating -> Math.pow(rating - mean, 2))
                    .average().orElse(0));
            for (int rating : rawRatings) {
                normalizedRatings.add(stdDev == 0 ? 0.0 : (rating - mean) / stdDev);
            }
        }
    }

    public static Map<String, UserRating> userRatingsMap = new HashMap<>();
    public static List<String> songs = new ArrayList<>();

    // Method to read song names
    public static void readSongs(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                songs.add(line.trim());
            }
        }
    }

    // Method to read rankings
    public static void readRankings(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String user = parts[0].trim();
                List<Integer> rankings = Arrays.stream(parts)
                                               .skip(1) // Skip the username
                                               .map(String::trim)
                                               .map(Integer::parseInt)
                                               .collect(Collectors.toList());

                UserRating userRating = new UserRating();
                userRating.rawRatings = rankings;
                userRating.normalizeRatings();
                userRatingsMap.put(user, userRating);
            }
        }
    }

    // Method to write rankings
    public static void writeRankings(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, UserRating> entry : userRatingsMap.entrySet()) {
                writer.write(entry.getKey());
                for (double score : entry.getValue().normalizedRatings) {
                    // Convert normalized score back to raw score, round it and ensure it's within 1-5
                    int rawScore = (int) Math.round(score * entry.getValue().stdDev + entry.getValue().mean);
                    rawScore = Math.max(1, Math.min(rawScore, 5)); // Ensuring the score is between 1 and 5
                    writer.write("," + rawScore);
                }
                writer.newLine();
            }
        }
    }

    
    public static void processRankings() {
      for (Map.Entry<String, UserRating> entry : userRatingsMap.entrySet()) {
          UserRating currentUserRating = entry.getValue();
          for (int i = 0; i < currentUserRating.rawRatings.size(); i++) {
              if (currentUserRating.rawRatings.get(i) == 0) {  // Unrated song
                  UserRating mostSimilarUserRating = findMostSimilarUser(entry.getKey(), i);
                  if (mostSimilarUserRating != null) {
                      double predictedNormalizedRating = mostSimilarUserRating.normalizedRatings.get(i);
                      double predictedRating = predictedNormalizedRating * currentUserRating.stdDev + currentUserRating.mean;
                      currentUserRating.rawRatings.set(i, (int) Math.round(predictedRating));
                      currentUserRating.normalizedRatings.set(i, predictedNormalizedRating);
                  }
              }
          }
      }
  }
  
  public static UserRating findMostSimilarUser(String currentUser, int songIndex) {
      UserRating mostSimilarUser = null;
      double minDistance = Double.MAX_VALUE;
  
      for (Map.Entry<String, UserRating> entry : userRatingsMap.entrySet()) {
          if (!entry.getKey().equals(currentUser) && entry.getValue().rawRatings.get(songIndex) != 0) {
              double distance = calculateEuclideanDistance(userRatingsMap.get(currentUser), entry.getValue());
              if (distance < minDistance) {
                  minDistance = distance;
                  mostSimilarUser = entry.getValue();
              }
          }
      }
      return mostSimilarUser;
  }
  
  public static double calculateEuclideanDistance(UserRating user1, UserRating user2) {
      double sum = 0;
      for (int i = 0; i < user1.normalizedRatings.size(); i++) {
          sum += Math.pow(user1.normalizedRatings.get(i) - user2.normalizedRatings.get(i), 2);
      }
      return Math.sqrt(sum);
  }
  

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java CS_214_Project <songs_file> <rankings_file> <output_file>");
            return;
        }
        try {
            readSongs(args[0]);
            readRankings(args[1]);
            processRankings();
            writeRankings(args[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
