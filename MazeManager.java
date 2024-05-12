package algorithms;

import java.io.File;
import java.util.Scanner;

public class MazeManager {
    // Class variables to handle file reading and data storage
    private static MazeFileReader mazeFileReader;
    private static boolean skipLoad;
    private static File inputFile;
    private static String inputFileName;

    // Method to initiate the loading of the maze file from the user's input
    public void initiateFileLoad(Scanner scanner) {
        skipLoad = false;
        while (true) {
            if (skipLoad) return; // Exit the method if file loading is skipped

            // Display options to the user
            System.out.println();
            System.out.println("1) To choose a file in File Explorer, press 1.");
            System.out.println("2) Enter the filename by pressing 2. (Send file to 'inputs' folder.) ");
            System.out.println("3) To go back to the main menu, press 3.");
            System.out.print("Choice: ");

            String userChoice = scanner.nextLine();
            boolean loadError = false;

            // Process user input with a switch statement
            switch (userChoice) {
                case "1":
                    try {
                        mazeFileReader = new MazeFileReader();
                        mazeFileReader.selectFile(); // Opens a file dialog to select a file
                        mazeFileReader.readAllLines(); // Read all lines from the selected file
                        mazeFileReader.loadMazeData(); // Parse the maze data from file contents
                        inputFileName = mazeFileReader.getFileName(); // Store the filename
                        inputFile = mazeFileReader.getFile(); // Store the file reference
                        printPath(); // Print the path after successful file loading
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage() + ". Please try again.");
                        loadError = true;
                    }
                    break;
                case "2":
                    try {
                        System.out.print("Input file name (e.g., 'example.txt'): ");
                        String fileName = scanner.nextLine();

                        mazeFileReader = new MazeFileReader();
                        mazeFileReader.readFile("src/inputs/" + fileName); // Read the file from a predefined directory
                        mazeFileReader.readAllLines(); // Read all lines from the file
                        mazeFileReader.loadMazeData(); // Parse the maze data
                        inputFileName = mazeFileReader.getFileName(); // Store the filename
                        inputFile = mazeFileReader.getFile(); // Store the file reference
                        printPath(); // Print the path after successful file loading
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage() + ". Please try again.");
                        loadError = true;
                    }
                    break;
                case "3":
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (!loadError) {
                skipLoad = true; // Skip subsequent loads if the current operation was successful
                return; // Exit the loop and method
            }
        }
    }

    // Method to print the shortest path through the maze after successful file load
    private void printPath() {
        // Output the number of lines read from the file
        System.out.println("Number of lines: " + mazeFileReader.getLines().size());
        System.out.println();

        // Notify the user that the shortest path calculation is starting
        System.out.println("Finding the shortest path...");
        System.out.println();

        // Retrieve the maze data from the reader
        int[][] maze = mazeFileReader.getMaze();
        int[] start = mazeFileReader.getStartLocation();
        int[] finish = mazeFileReader.getFinishLocation();

        // Create a new solver instance and print the shortest path found
        MazeSolver solver = new MazeSolver();
        System.out.println(solver.findShortestPath(maze, start, finish));
    }
}
