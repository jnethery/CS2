// Josiah Nethery(PID: 2551703)
// COP 3503, Fall 2013

// Maze.java
// ========
// Solves an MxN maze via breadth-first search algorithm

import java.io.*;
import java.util.Queue;
import java.util.ArrayDeque;

/**
 * @author Josiah Nethery, PID: j2551703
 */
public class Maze
{
	/**
	 * @author Josiah Nethery, PID: j2551703
     * A cell object stores information regarding its own coordinates and points to its parent cell
	 */
	public static class Cell
	{
		private int x, y; //cell coordinates
		private Cell parent; //parent cell (i.e., the cell that preceeds this cell in the BFS)
		
		public Cell(int x, int y, Cell parent)
		{
			this.x = x;
			this.y = y;
			this.parent = parent;
		}
		
		public int getX()
		{
			return this.x;
		}
		
		public int getY()
		{
			return this.y;
		}
		
		public Cell getParent()
		{
			return this.parent;
		}
	}

	public static Queue<Cell> pathQueue; //a queue for cells waiting to be evaluated in the BFS
	
	/**
	 * Solves a maze and marks the shortest path from start to end
	 * @param maze the character array representing the maze
	 * @see void
	 */
	public static void solve(char [][] maze)
	{
		if(pathQueue == null) //initialize the cell queue for the BFS if it hasn't already been initialized
			pathQueue = new ArrayDeque<Cell>();
		Cell currentCell = new Cell(1, 1, null); //set the current cell to be in the top-left corner of the maze, at the start
		pathQueue.add(currentCell); //add this parent cell to the queue to be immediately evaluated 
		
		while(!pathQueue.isEmpty()) //while there are still cells waiting to be evaluated, continue to process 
		{
			currentCell = pathQueue.remove(); //pop the next cell from the queue 
			
			//get the current cell's coordinates 
			int x = currentCell.getX();
			int y = currentCell.getY();
			
			//check if the current cell is at the end of the maze, and clear the queue if so, so that the processing loop will cease
			//and so that any other mazes being solved will be able to start with a fresh queue
			if (x == maze[0].length - 2 && y == maze.length - 2)
			{
				pathQueue.clear();
			}
			else
			{
				//get the coordinates of the parent cell so that the BFS does not back-track
				int parentX = currentCell.getParent() != null ? currentCell.getParent().getX() : 0;
				int parentY = currentCell.getParent() != null ? currentCell.getParent().getY() : 0;
				
				//search for directions that the BFS can propogate towards (i.e., not through a wall and not back towards the parent) 
				if(parentX != x-1 && x > 1 && maze[y][x-1] != '#')
				{
					pathQueue.add(new Cell(x-1, y, currentCell));
				}
				if(parentX != x+1 && x < maze[0].length - 2 && maze[y][x+1] != '#')
				{
					pathQueue.add(new Cell(x+1, y, currentCell));
				}
				if(parentY != y-1 && y > 1 && maze[y-1][x] != '#')
				{
					pathQueue.add(new Cell(x, y-1, currentCell));
				}
				if(parentY != y+1 && y < maze.length - 2 && maze[y+1][x] != '#')
				{
					pathQueue.add(new Cell(x, y+1, currentCell));
				}
			}
		}
		//we check if there is more than one cell in the maze, so that we can begin marking the path with periods. 
		//if there's just one cell, then no marking need take place.
		if (maze.length > 3 || maze[0].length > 3)
			currentCell = currentCell.getParent();
		//traverse backwards through the path until getting to the start of the maze, marking the path as you go
		while (currentCell.getParent() != null)
		{
			maze[currentCell.getY()][currentCell.getX()] = '.';
			currentCell = currentCell.getParent();
		}
	}
	
	/**
	 * Returns the perceived difficulty rating of the assignment on a scale of 1 to 5, 
	 * 1 being "ridiculously easy" and 5 being "insanely difficult" 
	 * @param void
	 * @return the difficulty rating 
	 * @see double
	 */
	public static double difficultyRating()
	{
		return 1.0;
	}
	
	/**
	 * Returns the hours spent working on the assignment
	 * @param void
	 * @return the hours spent on the assignment
	 * @see double
	 */
	public static double hoursSpent()
	{
		return 2.0;
	}
}