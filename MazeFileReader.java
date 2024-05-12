package algorithms;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeFileReader {
    private final ArrayList<String> lines = new ArrayList<>();
    private boolean fileRead = false; // Indicates if the file has been successfully read
    private int[] startLocation; // Starting position in the maze
    private int[] finishLocation; // Finishing position in the maze
    private int[][] maze; // Array representation of the maze
    private boolean fileLoaded = false; // Indicates if the file data is fully loaded into memory
    private File currentFile; // Current file being processed

    // Checks if the file has been read
    public boolean isFileRead() {
        return this.fileRead;
    }

    // Retrieves the start location from the maze
    public int[] getStartLocation() {
        if (isFileLoaded()) {
            return this.startLocation;
        }
        return null;
    }

    // Retrieves the finish location from the maze
    public int[] getFinishLocation() {
        if (isFileLoaded()) {
            return this.finishLocation;
        }
        return null;
    }

    // Returns the maze data as a 2D integer array if the file has been loaded
    public int[][] getMaze() {
        if (isFileLoaded()) {
            return this.maze;
        }
        return null;
    }

    // Reads all lines from the file into the 'lines' ArrayList
    public void readAllLines() throws IOException {
        if (this.fileRead) {
            lines.addAll(Files.readAllLines(currentFile.toPath(), Charset.defaultCharset()));
            this.fileLoaded = true;
        }
    }

    // Opens a file and sets the fileRead flag if successful
    public void readFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);

        if (file.length() == 0) {
            throw new FileNotFoundException("File " + filePath + "cannot be found");
        }

        this.currentFile = file;
        this.fileRead = true;
    }
    // Returns the file name if the file has been loaded
    public String getFileName() {
        if (fileLoaded) {
            return currentFile.getName();
        }
        return null;
    }

    // Returns the list of all lines read from the file
    public ArrayList<String> getLines() {
        if (this.fileRead) {
            return this.lines;
        }
        return null;
    }

    // Returns the File object if the file has been read
    public File getFile() {
        if (this.fileRead) {
            return currentFile;
        }
        return null;
    }

    public void printLines() {
        if (this.fileRead) {
            lines.forEach(System.out::println);
        }
    }

    public void selectFile() throws Exception {
        // Informs the user that the file explorer will be opened
        System.out.println("Opening file explorer. Check the taskbar to select a file.");
        // Creates a FileDialog window in load mode to allow the user to select a file
        FileDialog fileDialog = new FileDialog((Frame) null, "Select Input File");
        fileDialog.setMode(FileDialog.LOAD);
        // Sets the initial directory of the file dialog to the user's current working directory
        fileDialog.setDirectory(System.getProperty("user.dir"));
        // Restricts the file selection to text files only
        fileDialog.setFile("*.txt");
        // Displays the file dialog to the user
        fileDialog.setVisible(true);
        // Retrieves the name of the file selected by the user
        String fileName = fileDialog.getFile();
       // Checks if the file name does not end with ".txt", indicating it's not a text file
        if (!fileName.endsWith(".txt")) {
            throw new Exception("File extension must be .txt");
        }

        File file = new File(fileDialog.getDirectory(), fileName);
        if (file.length() == 0) {
            throw new Exception("No file selected");
        }
     // Updates the currentFile field with the selected file
        this.currentFile = file;
        // Sets the fileRead flag to true, indicating a file has been successfully read
        this.fileRead = true;
    }

    public boolean isFileLoaded() {
        // Returns true if both fileRead and fileLoaded flags are true
        return this.fileRead && this.fileLoaded;
    }

    public boolean loadMazeData() {
        // Get the lines of text that have been read from the file
        ArrayList<String> lines = getLines();

        // Calculate the length of the first line after trimming whitespace to determine the width of the maze
        int floorSize = lines.get(0).trim().length();

        // Initialize the maze data structure based on the number of lines (height) and floorSize (width)
        this.maze = new int[lines.size()][floorSize];
        int lineCount = 0; // Variable to keep track of the current line number

        // Iterate through each line in the list of lines
        for (String line : lines) {
            // Create an array to hold the integer representation of each character in the current line
            int[] row = new int[floorSize];
            // Use a Scanner to read the current line
            Scanner lineScanner = new Scanner(line);
            String currentLine = lineScanner.nextLine(); // Retrieve the actual line content

            // Replace all '0' characters with '1', and '.' characters with '0'
            // This is part of converting the maze from a textual representation to a numerical one,
            // where '1' typically stands for walls and '0' for open paths
            currentLine = currentLine.replace('0', '1').replace('.', '0');

            // Check if the current line contains the start location 'S'
            if (currentLine.contains("S")) {
                // Set the start location using the current line index and character index of 'S'
                this.startLocation = new int[] {lineCount, currentLine.indexOf("S")};
                // Replace 'S' with '0' to normalize the maze representation (start is treated as a walkable path)
                currentLine = currentLine.replace("S", "0");
            }

            // Check if the current line contains the finish location 'F'
            if (currentLine.contains("F")) {
                // Set the finish location using the current line index and character index of 'F'
                this.finishLocation = new int[] {lineCount, currentLine.indexOf("F")};
                // Replace 'F' with '0' to normalize the maze representation (finish is treated as a walkable path)
                currentLine = currentLine.replace("F", "0");
            }

            // Convert each character in the modified current line to its numeric value and store it in the row array
            for (int j = 0; j < floorSize; j++) {
                row[j] = Character.getNumericValue(currentLine.charAt(j));
            }

            // Assign the row array to the corresponding position in the maze array
            maze[lineCount] = row;
            lineCount++; // Increment the line count for the next iteration
            lineScanner.close(); // Close the Scanner object
        }
        return true; // Return true to indicate that the maze data has been successfully loaded
    }
}