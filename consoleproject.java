import java.util.ArrayList;
import java.util.Scanner;

public class Chatb {
    private static final String[] HOTEL_NAMES = {"Hotel A", "Hotel B", "Hotel C", "Hotel D"};
    private static final double AC_ROOM_RATE = 100.0;
    private static final double NON_AC_ROOM_RATE = 80.0;
    private static String LOGIN_USERNAME = "user";
    private static String LOGIN_PASSWORD = "password";

    private static ArrayList<String> history = new ArrayList<>();
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Chatbot!");

        String userInput;
        do {
            if (!loggedIn) {
                System.out.print("You: ");
                userInput = scanner.nextLine().toLowerCase();
                if (userInput.equals("login")) {
                    loggedIn = login(scanner);
                    continue;
                } else if (userInput.equals("signup")) {
                    loggedIn = signup(scanner);
                    continue;
                } else {
                    System.out.println("You need to login or signup before viewing the history.");
                    continue;
                }
            }

            System.out.print("You: ");
            userInput = scanner.nextLine().toLowerCase();
            history.add(userInput);

            String botResponse = respondTo(userInput, scanner);
            System.out.println("Chatbot: " + botResponse);
        } while (!userInput.equalsIgnoreCase("exit"));

        scanner.close();
    }

    public static String respondTo(String input, Scanner scanner) {
        String response;
        switch (input) {
            case "hi":
            case "hello":
                response = "Hello there!";
                break;
            case "how are you?":
                response = "I am doing well!!,Thanks for asking";
                break;
            case "what is your name?":
                response = "I am a chatbot.";
                break;
            case "can you help me!":
                response = "Sure, How can I help you?";
                break;
            case "book hotel":
                response = bookHotel(scanner);
                break;
            case "show history":
                if (loggedIn)
                    response = showHistory();
                else
                    response = "You need to login or signup before viewing the history.";
                break;
            case "exit":
                response = "Goodbye!";
                break;
            default:
                response = "I'm not sure how to respond to that.";
                break;
        }
        return response;
    }

    private static String bookHotel(Scanner scanner) {
        System.out.println("\nAvailable hotels:");
        for (int i = 0; i < HOTEL_NAMES.length; i++) {
            System.out.println((i + 1) + ". " + HOTEL_NAMES[i]);
        }

        System.out.print("Select a hotel (enter the number): ");
        int hotelChoice = scanner.nextInt();
        scanner.nextLine(); 

        if (hotelChoice < 1 || hotelChoice > HOTEL_NAMES.length) {
            return "Invalid choice!";
        }

        System.out.print("Number of persons: ");
        int numPersons = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Number of nights: ");
        int numNights = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("AC room (Y/N): ");
        boolean acRoom = scanner.nextLine().equalsIgnoreCase("Y");

        double roomRate = acRoom ? AC_ROOM_RATE : NON_AC_ROOM_RATE;
        double totalCost = roomRate * numNights * numPersons;

        String bookingDetails = "\nBooking details:\n" +
                                "Hotel: " + HOTEL_NAMES[hotelChoice - 1] + "\n" +
                                "Number of persons: " + numPersons + "\n" +
                                "Number of nights: " + numNights + "\n" +
                                "Room type: " + (acRoom ? "AC" : "Non-AC") + "\n" +
                                "Total cost: $" + totalCost;

        history.add(bookingDetails);
        return bookingDetails;
    }

    private static String showHistory() {
        StringBuilder historyString = new StringBuilder("Chat History:\n");
        for (int i = 0; i < history.size(); i++) {
            historyString.append((i + 1)).append(". ").append(history.get(i)).append("\n");
        }
        return historyString.toString();
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (username.equals(LOGIN_USERNAME) && password.equals(LOGIN_PASSWORD)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }
    private static boolean signup(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine().trim(); 
        if (!username.matches("[a-zA-Z]+")) {
            System.out.println("Username must contain only alphabetic characters.");
            return false;
        }
        System.out.print("Enter a password: ");
        String password = scanner.nextLine().trim(); 
        LOGIN_USERNAME = username;
        LOGIN_PASSWORD = password;

        System.out.println("Signup successful! You can now login.");
        return false; // Signup finished, user still needs to login
    }
}
