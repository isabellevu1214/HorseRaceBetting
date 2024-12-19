import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HorseRace {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Character> horses = new ArrayList<>();
    horses.add('A');
    horses.add('B');
    horses.add('C');
    horses.add('D');
    horses.add('E');

    // Shuffle the horses to generate a random order
    Collections.shuffle(horses);
    System.out.println("The race has started! The order will be revealed after your bet.");

    // Ask the user for the type of bet
    System.out.println("What type of bet would you like to place?");
    System.out.println("1. Pair Bet (Specify two horses, e.g., A finishes ahead of B)");
    System.out.println("2. Subset Bet (Choose between Candidate-Subset or Position-Subset)");
    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume the newline

    double probability = 0.0;
    boolean betSucceeded = false;

    if (choice == 1) {
      // Pair Bet
      System.out.println("Enter the first horse (e.g., A):");
      char horse1 = Character.toUpperCase(scanner.nextLine().charAt(0));
      System.out.println("Enter the second horse (e.g., B):");
      char horse2 = Character.toUpperCase(scanner.nextLine().charAt(0));

      probability = calculatePairBetProbability();
      betSucceeded = checkPairBet(horses, horse1, horse2);

    } else if (choice == 2) {
      // Subset Bet: Ask for type
      System.out.println("What type of subset bet?");
      System.out.println("1. Candidate-Subset Bet (Subset of horses in a specific position)");
      System.out.println("2. Position-Subset Bet (One horse in a subset of positions)");
      int subsetType = scanner.nextInt();
      scanner.nextLine(); // Consume the newline

      if (subsetType == 1) {
        // Candidate-Subset Bet
        System.out.println("Enter the position to check (e.g., 2):");
        int position = scanner.nextInt() - 1; // Convert to 0-based index
        scanner.nextLine(); // Consume the newline
        System.out.println("Enter the subset of horses (e.g., A,B,D):");
        String[] horsesInput = scanner.nextLine().split(",");
        List<Character> subsetHorses = new ArrayList<>();
        for (String h : horsesInput) {
          subsetHorses.add(Character.toUpperCase(h.charAt(0)));
        }

        probability = calculateCandidateSubsetProbability(subsetHorses.size());
        betSucceeded = checkCandidateSubset(horses, subsetHorses, position);

      } else if (subsetType == 2) {
        // Position-Subset Bet
        System.out.println("Enter the horse (e.g., A):");
        char horse = Character.toUpperCase(scanner.nextLine().charAt(0));
        System.out.println("Enter the subset of positions (e.g., 1,2,5):");
        String[] positionsInput = scanner.nextLine().split(",");
        int[] positions = new int[positionsInput.length];
        for (int i = 0; i < positionsInput.length; i++) {
          positions[i] = Integer.parseInt(positionsInput[i]) - 1; // Convert to 0-based index
        }

        probability = calculatePositionSubsetProbability(positions.length);
        betSucceeded = checkPositionSubset(horses, horse, positions);
      } else {
        System.out.println("Invalid choice!");
        scanner.close();
        return;
      }
    } else {
      System.out.println("Invalid choice!");
      scanner.close();
      return;
    }
    scanner.close();
    // Print results
    System.out.println("The probability of your bet succeeding is: " + (probability * 100) + "%");
    System.out.println("The race results are: " + horses);
    System.out.println("Did your bet succeed? " + (betSucceeded ? "Yes!" : "No."));
  }

  // Pair Bet Probability: Fixed at 10/120 since 2 spaces can be chosen in 10
  // ways, and the arrangement is unique
  public static double calculatePairBetProbability() {
    return (10.0 * 1 * 1 * 6) / 120; // 60 successful outcomes / 5!
  }

  public static boolean checkPairBet(List<Character> horses, char horse1, char horse2) {
    return horses.indexOf(horse1) < horses.indexOf(horse2);
  }

  // Position-Subset Bet Probability
  public static double calculatePositionSubsetProbability(int subsetSize) {
    return (double) 24 * subsetSize / 120; // 24 successful outcomes per case * number of cases / total possibilities
  }

  public static boolean checkPositionSubset(List<Character> horses, char horse, int[] positions) {
    int actualPosition = horses.indexOf(horse);
    for (int pos : positions) {
      if (actualPosition == pos) {
        return true;
      }
    }
    return false;
  }

  // Candidate-Subset Bet Probability: Fixed based on subset size
  public static double calculateCandidateSubsetProbability(int subsetSize) {
    return (double) 24 * subsetSize / 120; // 24 successful outcomes per case * number of cases / total possibilities
  }

  public static boolean checkCandidateSubset(List<Character> horses, List<Character> subsetHorses, int position) {
    return subsetHorses.contains(horses.get(position));
  }
}
