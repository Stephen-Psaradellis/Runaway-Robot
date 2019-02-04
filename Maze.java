
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Maze {
    private static final int ROAD = 0;
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int EXIT = 3;
    private static final int PATH = 4;
    

	

    private boolean[][] visited;
    private Coordinate start;
    private Coordinate end;
    
    Maze(int[][] maze) {
    	
    }
//down
    public ArrayList<Coordinate> validMoves(int[][] maze, Coordinate coordinate) {
    	ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
    	Coordinate testY = new Coordinate(coordinate.getX(), coordinate.getY()+1);
    	Coordinate testX = new Coordinate(coordinate.getX()+1, coordinate.getY());
		if(!isWall(maze[testY.getY()][testY.getX()]) && !isExplored(testY.getY(),testY.getX()) && isValidLocation(maze, testY.getY(), testY.getX())) 
				validMoves.add(testY);
				
    	return null;
    	
    }

    public int getHeight(int[][] maze) {
        return maze.length;
    }

    public int getWidth(int[][] maze) {
        return maze[0].length;
    }

    public boolean isFinalState(int x, int y) {
        return x == EXIT && y == EXIT;
    }

    public boolean isStart(int x, int y) {
        return x == START && y == START;
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col, int[][] maze) {
        return maze[row][col] == WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int[][] maze, int row, int col) {
        if (row < 0 || row >= getHeight(maze) || col < 0 || col >= getWidth(maze)) {
            return false;
        }
        return true;
    }

    public void printPath(List<Coordinate> path) {
        int[][] tempMaze = Arrays.stream(maze)
            .map(int[]::clone)
            .toArray(int[][]::new);
        for (Coordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isExit(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempMaze[coordinate.getX()][coordinate.getY()] = PATH;
        }
        System.out.println(toString(tempMaze));
    }

    public String toString(int[][] maze) {
        StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (maze[row][col] == ROAD) {
                    result.append(' ');
                } else if (maze[row][col] == WALL) {
                    result.append('#');
                } else if (maze[row][col] == START) {
                    result.append('S');
                } else if (maze[row][col] == EXIT) {
                    result.append('E');
                } else {
                    result.append('.');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public void reset() {
        for (int i = 0; i < visited.length; i++)
            Arrays.fill(visited[i], false);
    }
}