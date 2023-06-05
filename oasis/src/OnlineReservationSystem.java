import java.time.LocalDate;
import java.util.*;

class ReservationSystem {
    public String trainNumber;
    public String trainName;
    public String classType;
    public LocalDate dateOfJourney;
    private final String fromPlace;
    private final String toDestination;
    public String pnrNumber;

    public ReservationSystem(String trainNumber, String trainName, String classType, LocalDate dateOfJourney, String fromPlace, String toDestination, String pnrNumber) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromPlace = fromPlace;
        this.toDestination = toDestination;
        this.pnrNumber = pnrNumber;
    }

    public String getPNRNumber() {
        return getPnrNumber();
    }

    public LocalDate getDateOfJourney() {
        return dateOfJourney;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getClassType() {
        return classType;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public String getToDestination() {
        return toDestination;
    }

    public void displayReservationDetails() {
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Train Name: " + trainName);
        System.out.println("Class Type: " + classType);
        System.out.println("Date of Journey: " + dateOfJourney);
        System.out.println("From: " + fromPlace);
        System.out.println("To: " + toDestination);
        System.out.println("PNR Number: " + getPnrNumber());
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

}

public class OnlineReservationSystem {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String FILE_NAME = "reservations.txt";

    private static Scanner scanner;
    private static List<ReservationSystem> reservations;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to the Online Reservation System!");

        // Login Form
        if (login()) {
            System.out.println("Login successful!");

            reservations = new ArrayList<>();

            while (true) {
                System.out.println("\nSelect an option:");
                System.out.println("1. Make a reservation");
                System.out.println("2. View reservations");
                System.out.println("3. Cancel a reservation");
                System.out.println("4. Exit");

                int choice = getIntInput();

                switch (choice) {
                    case 1 -> makeReservation();
                    case 2 -> viewReservations();
                    case 3 -> cancelReservation();
                    case 4 -> {
                        System.out.println("Thank you for using the Online Reservation System!");
                        saveReservationsToFile();
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid login credentials. Please try again!");
        }
    }

    public static boolean login() {
        System.out.print("Enter your login id: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        return loginId.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASSWORD);
    }
    public static void makeReservation() {
        System.out.println("=== Reservation Form ===");
        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter Train Name: ");
        String trainName = scanner.nextLine();
        System.out.print("Enter Class Type: ");
        String classType = scanner.nextLine();
        LocalDate dateOfJourney = getDateInput("Enter Date of Journey (YYYY-MM-DD): ");
        System.out.print("Enter From (Place): ");
        String fromPlace = scanner.nextLine();
        System.out.print("Enter To (Destination): ");
        String toDestination = scanner.nextLine();
        System.out.print("Enter PNR Number: ");
        String pnrNumber = scanner.nextLine();

        ReservationSystem reservation = new ReservationSystem(trainNumber, trainName, classType, dateOfJourney, fromPlace, toDestination, pnrNumber);
        reservations.add(reservation);
        System.out.println("Reservation created successfully!");
    }

    public static void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("=== Reservations ===");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println("\nReservation " + (i + 1));
            reservations.get(i).displayReservationDetails();
        }
    }

    public static void cancelReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.print("Enter PNR Number: ");
        String pnrNumber = scanner.nextLine();

        for (ReservationSystem reservation : reservations) {
            if (reservation.getPNRNumber().equals(pnrNumber)) {
                reservations.remove(reservation);
                System.out.println("Reservation with PNR Number " + pnrNumber + " cancelled successfully!");
                return;
            }
        }

        System.out.println("No reservation found with PNR Number " + pnrNumber);
    }

    public static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static LocalDate getDateInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid date in the format YYYY-MM-DD.");
            }
        }
    }

    public static void saveReservationsToFile() {
        try (Formatter formatter = new Formatter(FILE_NAME)) {
            for (ReservationSystem reservation : reservations) {
                formatter.format("%s,%s,%s,%s,%s,%s%n", reservation.getTrainNumber(), reservation.getTrainName(),
                        reservation.getClassType(), reservation.getDateOfJourney(), reservation.getFromPlace(),
                        reservation.getToDestination());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while saving reservations to file.");
        }
    }
}
