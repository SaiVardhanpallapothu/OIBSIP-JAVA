import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int rangeStart = 1;
        int rangeEnd = 100;
        int randomNumber = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
        int attempts = 0;
        boolean isCorrect = false;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Guess a number between " + rangeStart + " and " + rangeEnd);

        while (!isCorrect) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            attempts++;

            if (guess == randomNumber) {
                System.out.println("Congratulations! You guessed the correct number.");
                isCorrect = true;
            } else if (guess < randomNumber) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }

            if (attempts >= 10) {
                System.out.println("Sorry, you have reached the maximum number of attempts.");
                break;
            }
        }

        int score = 10 - attempts;
        System.out.println("Game Over!");
        System.out.println("Your score: " + score);

        scanner.close();
    }
}
