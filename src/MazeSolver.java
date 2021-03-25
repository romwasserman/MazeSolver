import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MazeSolver {

	public static void main(String[] args) {
		Scanner _Scanner = new Scanner(System.in);
		System.out.println("Please enter the name of the maze file.");
		String fileName = _Scanner.nextLine();
		// add while the file is not valid ask again
		Maze newMaze = Maze.loadMaze(fileName);
		while (newMaze == null) {
			System.out.println("Error: file not found. Please enter the name of the file again");
			fileName = _Scanner.nextLine();
			newMaze = Maze.loadMaze(fileName);
		}
		System.out.println("Maze:");
		print(newMaze);
		System.out.println("Please enter the starting point of the maze.");
		String[] start = _Scanner.nextLine().split("\\s+");
		Point startPoint = new Point(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
		while ((!newMaze.inBounds(startPoint)) || (newMaze.get(startPoint) != TextMaze.EMPTY)) {
			System.out.println(
					"Error: the point is not in bounds or is not an empty space. Please choose another starting point.");
			start = _Scanner.nextLine().split("\\s+");
			startPoint = new Point(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
		}
		System.out.println("Please enter the end point of the maze.");
		String[] end = _Scanner.nextLine().split("\\s+");
		Point endPoint = new Point(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
		while ((!newMaze.inBounds(endPoint)) || (newMaze.get(endPoint) != TextMaze.EMPTY)) {
			System.out.println(
					"Error: the point is not in bounds or is not an empty space. Please choose another ending point.");
			end = _Scanner.nextLine().split("\\s+");
			endPoint = new Point(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
		}
		boolean isSolved = solveMaze(newMaze, startPoint, endPoint);
		if (!isSolved) {
			System.out.println("Unsolvable Maze:");
		} else {
			System.out.println("Solved Maze:");
		}
		print(newMaze);
		if (isSolved == true) {
			String newFileName = ("" + fileName + ".solved");
			Maze.saveMaze(newFileName, newMaze);
		}
		_Scanner.close();
	}

	public static void print(Maze maze) {
		System.out.println(maze.toString());
	}
	public static boolean solveMaze(Maze maze, Point startPoint, Point endPoint) {
		maze.set(endPoint, TextMaze.GOAL);
		boolean isGoalFound = findGoal(maze, startPoint);
		maze.set(startPoint, TextMaze.START);
		if (!maze.inBounds(startPoint)) {
			System.out.println("That's not an empty space in the maze, try again.");
			return false;
		}
		return isGoalFound;

	}

	public static boolean findGoal(Maze maze, Point p) {
		// maze.set(p,TextMaze.START);
		if (maze.get(p) == TextMaze.GOAL) {
			return true;
		}
		if (maze.get(p) != TextMaze.EMPTY) {
			return false;
		}
		maze.set(p, TextMaze.PATH);
		Point[] adjecPoints = p.getAdjacentPoints();
		for (int i = 0; i < adjecPoints.length; i++) {
			if (maze.inBounds(adjecPoints[i]) == true) {
				boolean isSolved = findGoal(maze, adjecPoints[i]);
				if (isSolved == true) {
					return true;
				}
			}
		}
		maze.set(p, TextMaze.VISITED);
		return false;

	}

}
