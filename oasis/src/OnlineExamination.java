import java.util.*;
class User {
    public String username;
    private String password;
    private final String profile;
    private final String email;

    public User(String username,
                String password,
                String profile,
                String email) {
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public String getEmail() {
        return email;
    }
}

class MCQQuestion {
    public String question;
    private final String[] options;
    public int correctOption;

    public MCQQuestion(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}


class Examination {
    private final Map<String, Integer> scores = new HashMap<>();
    private final Map<String, MCQQuestion> questions;
    private final int timer;

    public Examination() {
        this.questions = new HashMap<>();
        this.timer = 60; // Timer set to 60 seconds by default
        initializeQuestions();
    }
    public void initializeQuestions() {
        MCQQuestion question1 = new MCQQuestion("What is the capital of France?",
                new String[]{"A. London", "B. Paris", "C. Rome", "D. Berlin"}, 1);
        questions.put("Q1", question1);

        MCQQuestion question2 = new MCQQuestion("Which is the largest planet in our solar system?",
                new String[]{"A. Jupiter", "B. Earth", "C. Saturn", "D. Mars"}, 0);
        questions.put("Q2", question2);

        // Add more questions here as needed
    }

    public void startExam(User user, Scanner scanner) {
        System.out.println("Welcome, " + user.getUsername() + "!");
        System.out.println("You have " + timer + " seconds to complete the exam.");
        System.out.println("------------------------------------------------");

        int score = 0;

        for (String questionId : questions.keySet()) {
            MCQQuestion question = questions.get(questionId);

            System.out.println(question.getQuestion());
            for (String option : question.getOptions()) {
                System.out.println(option);
            }

            System.out.print("Enter your answer (A, B, C, D): ");
            String answer = scanner.nextLine().toUpperCase();

            if (answer.equals(question.getOptions()[question.getCorrectOption()].substring(0, 1))) {
                score++;
            }

            System.out.println("------------------------------------------------");
        }

        scores.put(user.getUsername(), score);
        System.out.println("Thank you for completing the exam, " + user.getUsername() + "!");
        System.out.println("Your score: " + score + " out of " + questions.size());
    }

    public static void setTimer(Scanner scanner) {
        System.out.print("Enter the timer in seconds: ");
        try {
            int timerSeconds = Integer.parseInt(scanner.nextLine());
            // Do something with the timerSeconds value
            System.out.println("Timer set to " + timerSeconds + " seconds.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer value for the timer.");
        }
    }

    public void showLeaderboard() {
        if (scores.isEmpty()) {
            System.out.println("No scores available.");
        } else {
            System.out.println("Leaderboard:");
            List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
            sortedScores.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            for (Map.Entry<String, Integer> entry : sortedScores) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}
public class OnlineExamination {
    private static Scanner scanner;
    private static User currentUser;
    private static Examination examination;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        currentUser = null;
        examination = new Examination();

        boolean quit = false;
        while (!quit) {
            System.out.println("1. Login");
            System.out.println("2. Update Profile and Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Timer and Auto-Submit");
            System.out.println("5. Closing Session and Logout");
            System.out.println("6. Show Leaderboard");
            System.out.println("7. Quit");
            System.out.print("Enter option: ");
            int option;
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (option) {
                case 1 -> login();
                case 2 -> updateProfileAndPassword();
                case 3 -> startExam();
                case 4 -> setTimer();
                case 5 -> logout();
                case 6 -> examination.showLeaderboard();
                case 7 -> {
                    quit = true;
                    System.out.println("Thank you for using the Online Examination system. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    public static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Perform login validation here
        // Example: Retrieve user from database and validate credentials
        User user = getUserFromDatabase(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public static User getUserFromDatabase(String username) {
        // Simulated database retrieval
        // Replace with actual database access code
        Map<String, User> userDatabase = new HashMap<>();
        userDatabase.put("user1", new User("user1", "password1", "Student", "user1@example.com"));
        userDatabase.put("user2", new User("user2", "password2", "Student", "user2@example.com"));

        return userDatabase.get(username);
    }

    public static void updateProfileAndPassword() {
        if (currentUser != null) {
            System.out.println("Current Profile:");
            System.out.println("Username: " + currentUser.getUsername());
            System.out.println("Email: " + currentUser.getEmail());
            System.out.println("Profile Type: " + currentUser.getProfile());

            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            currentUser.setPassword(newPassword);

            System.out.println("Profile and password updated successfully!");
        } else {
            System.out.println("You need to login first.");
        }
    }

    public static void startExam() {
        if (currentUser != null) {
            examination.startExam(currentUser, scanner);
        } else {
            System.out.println("You need to login first.");
        }
    }
    public static void setTimer() {
        System.out.print("Enter the timer in seconds: ");
        try {
            int timerSeconds = scanner.nextInt();
            Examination.setTimer(scanner); // Pass the scanner object as an argument
            System.out.println("Timer set to " + timerSeconds + " seconds.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer value for the timer.");
        } finally {
            scanner.nextLine(); // Consume the newline character
        }
    }


    public static void logout() {
        currentUser = null;
        System.out.println("Session closed and logged out.");
    }
}
