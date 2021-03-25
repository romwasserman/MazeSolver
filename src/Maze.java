//Rom Wasserman
// Dylan Widecki
//Bryan Soto
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.io.FileReader;
//import java.io.IOException;


public class Maze implements TextMaze{
	public static char maze[][];
	public static int width;
	public static int height;
	
	// Maze constructor
	public Maze(int width,int height) {
		Maze.width = width;
		Maze.height = height;
		Maze.maze = new char[height][width];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j <maze[i].length; j++) {
				maze[i][j] = TextMaze.EMPTY;
			}
		}
	}
	// setting a coordinates of the point p equals to c
	public void set(Point p, char c) {
		if (p.x > Maze.width - 1 || p.y > Maze.height - 1 || p.x < 0 || p.y < 0) {
			throw new PointOutOfBoundsException(p.toString());
		}
		maze[p.y][p.x] = c;	
	}
	public char get(Point p) {		
		if (p.x > Maze.width - 1 || p.y > Maze.height - 1 || p.x < 0 || p.y < 0) {
			throw new PointOutOfBoundsException(p.toString());
		}
		else {
			char a = maze[p.y][p.x];
			return a;	
		}
	}
	public int width() {
		return width;
	}
	public int height() {
		return height;
	}
	
	public String toString() {
		String mazeString = "";
		for (int i = maze.length - 1; i >= 0; i--) {
			for (int j = 0; j <maze[i].length; j++) {
				mazeString += maze[i][j];
			}
			mazeString += "\n";
		}	
		return mazeString.trim();
	}

	public boolean inBounds(Point p) {
		//System.out.println(p);
		if (p.x >= 0 && p.x < Maze.width && p.y >= 0 && p.y < Maze.height) {			
			return true;
		}
		else {
			return false;
		}	
	}	

	public static Maze loadMaze (String filename){
		Scanner myScanner = new Scanner(System.in);
		try {
			File newFile = new File(filename);
			Scanner fileReader = new Scanner(newFile);
			String readline = fileReader.nextLine();
			String[] newline = readline.split("\\s+");
			int width = Integer.parseInt(newline[0]);
			int height = Integer.parseInt(newline[1]);
			int k = height - 1;
			Maze newMaze = new Maze(width,height);
			while (k >= 0) {								
				char[] ch = fileReader.nextLine().toCharArray();
				for(int j = 0;j < ch.length;j++){
					newMaze.maze[k][j] = ch[j];
				}
				k--;
			}
			fileReader.close();
			myScanner.close();
			//	System.out.println(newMaze);
			return newMaze;

		}			
		catch (FileNotFoundException e) {
			myScanner.close();
			return null;
		}
	}
public static void saveMaze(String filename, Maze maze) {

		try {
			FileWriter fileWriter = new FileWriter(filename);
			PrintWriter out = new PrintWriter(new FileWriter(filename));
			out.println("" + maze.width + " " + maze.height);

			for (int i = maze.maze.length - 1; i >= 0; i--) {
				for (int j = 0; j <maze.maze[i].length; j++) {
					out.print(maze.maze[i][j]);
				}
				out.print("\n");
			}	
			out.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	
}