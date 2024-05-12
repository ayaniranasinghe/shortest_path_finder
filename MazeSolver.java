package algorithms;

import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver {
    // Inner class to represent a position within the maze
    class Position implements Comparable<Position> {
        int x; // X coordinate in the maze
        int y; // Y coordinate in the maze
        int distanceFromStart; // Distance from the start position
        String directions; // Text description of the path taken
        int stepCount; // Number of steps taken from the start

        // Constructor for Position
        Position(int x, int y, int distanceFromStart, String directions, int stepCount) {
            this.x = x;
            this.y = y;
            this.distanceFromStart = distanceFromStart;
            this.directions = directions;
            this.stepCount = stepCount;
        }

        // Define natural ordering of Position objects (for sorting, if needed)
        @Override
        public int compareTo(Position other) {
            if (this.distanceFromStart == other.distanceFromStart) {
                return this.directions.compareTo(other.directions);
            } else {
                return this.distanceFromStart - other.distanceFromStart;
            }
        }

        // Custom string representation of Position used when path is found
        @Override
        public String toString() {
            return "Total distance: " + distanceFromStart + " \nPath:\n" + directions.trim() + "\nDone!!";
        }
    }

    // Method to find the shortest path through a maze from start to goal
    public String findShortestPath(int[][] maze, int[] start, int[] goal) {
        int numRows = maze.length;
        int numCols = maze[0].length;
        boolean[][] visited = new boolean[numRows][numCols]; // Tracks visited positions

        // Initial position setup
        String initialPosition = "Start at (" + (start[1] + 1) + ", " + (start[0] + 1) + ")\n";
        Queue<Position> queue = new LinkedList<>();
        queue.offer(new Position(start[0], start[1], 0, initialPosition, 1));

        // Directions and corresponding delta movements in the maze (up, down, left, right)
        String[] directions = {"Up", "Down", "Left", "Right"};
        int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Process each position in the queue
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current.x == goal[0] && current.y == goal[1]) {
                return current.toString(); // Return the path description when goal is reached
            }

            // Explore each possible direction
            for (int i = 0; i < deltas.length; i++) {
                int x = current.x;
                int y = current.y;
                int distance = current.distanceFromStart;
                String path = current.directions;
                int stepCount = current.stepCount;

                // Move in the direction while it's within bounds and not blocked
                while (x >= 0 && x < numRows && y >= 0 && y < numCols && maze[x][y] == 0 && (x != goal[0] || y != goal[1])) {
                    x += deltas[i][0];
                    y += deltas[i][1];
                    distance += 1;
                }

                // Adjust position if it goes out of bounds or hits an obstacle
                if (x != goal[0] || y != goal[1]) {
                    x -= deltas[i][0];
                    y -= deltas[i][1];
                    distance -= 1;
                }

                // Add new position to queue if not visited
                if (!visited[x][y]) {
                    visited[current.x][current.y] = true;
                    String direction = directions[i];
                    path += String.format("%d. Move %s to (%d, %d)\n", stepCount, direction, y + 1, x + 1);
                    queue.offer(new Position(x, y, distance, path, stepCount + 1));
                }
            }
        }

        return "No path found!"; // Return this if no path is found
    }
}
