package algorithms;

import java.util.Scanner;

public class Maze {
    // Scanner object to read user input from the console
    private static final Scanner scanner = new Scanner(System.in);
    // MazeManager instance to manage maze file loading and solving
    private static MazeManager mazeManager = new MazeManager();

    public static void main(String[] args) {
        // Start of the program
        System.out.println();
        System.out.println("Shortest Path Finder");

        // Infinite loop to keep the program running until the user decides to exit
        while (true) {
            // Display menu options to the user
            System.out.println();
            System.out.println("1) To Load New Input Press L");
            System.out.println("2) To Exit the Application Press E");
            System.out.println();

            // Prompt the user for a choice
            System.out.print("Enter Your choice: ");
            String userChoice = scanner.nextLine().trim(); // Read and trim user input

            // Handle user input for exiting the program
            if (userChoice.equalsIgnoreCase("E")) {
                System.out.println("Exiting the application...");
                System.exit(0); // Exit the program
            }
            // Handle user input for loading a new maze
            else if (userChoice.equalsIgnoreCase("L")) {
                mazeManager.initiateFileLoad(scanner); // Call method to load and process a new maze file
                // After returning from initiateFileLoad, the loop will prompt again
            }
            // Handle invalid user input
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
