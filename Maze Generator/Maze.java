// Josiah Nethery, with modified code samples from Sean Szumlanski (PID: 2551703)
// COP 3503, Fall 2013

// Maze.java
// ========
// Creates and returns an "m x n"-cell maze using disjoint sets.

import java.io.*;
import java.util.*;

/**
 * @author Josiah Nethery, PID: j2551703
 */
public class Maze
{
	private static int[][] set; // [index, [parent, rank]]
	private static List<Integer> unvisitedWalls; //stores the unvisited walls in the maze
	private static Set<Integer> setParents; //stores all the set parents
	
	/**
	 * Creates a maze with the given width and height
	 * @param width the width of the map, in cells
	 * @param height the height of the map, in cells
	 * @return the generated map, represented as an array of characters
	 * @see char[][] 
	 */
	public static char[][] create(int width, int height)
	{
		char[][] maze = initMaze(width, height); //initialize the maze
		initSet(width, height); //initialize the collection of disjoint sets
		while (setParents.size() > 1) //loop through the program while there is at least one disjoint set
		{
			removeWall(maze, selectWall(maze)); //remove a wall
		}
		return maze;
	}
	
	//intialize the maze to have "m x n" disjoint cells. Example of a 2x2 initialized maze:
	// #####
	// #s# #
	// #####
	// # #e#
	// #####
	private static char[][] initMaze(int width, int height)
	{
		char[][] maze = new char[height*2 + 1][width*2 + 1];
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				if (i == maze.length - 2 && j == maze[i].length - 2)
					maze[i][j] = 'e';
				else if (i == 1 && j == 1)
					maze[i][j] = 's';
				else if (j % 2 == 0 || i % 2 == 0)
					maze[i][j] = '#';
				else if (i == 0 || i == maze.length - 1)
					maze[i][j] = '#';
				else
					maze[i][j] = ' ';
			}
		}
		return maze;
	}
	
	//initialize the collection of disjoint sets, such that each element is its own parent and every parent has a rank of 0
	//initialize the unvisited walls such that every inner wall in the maze is unvisited 
	private static void initSet(int width, int height)
	{
		set = new int[width*height][2];
		unvisitedWalls = new ArrayList<Integer>();
		setParents = new HashSet<Integer>();
		for (int i = 0; i < width*height; i++)
		{
			set[i][0] = i;
			set[i][1] = 0;
			setParents.add(i);
		}
		for (int i = 0; i < (width*(height-1)) + (height*(width-1)); i++)
		{
			unvisitedWalls.add(i);
		}
	}
	
	//try to remove a wall from the maze if its bordering cells are disjoint
	private static void removeWall(char[][] maze, int wallIndex)
	{
		//first, get the coordinates of the wall and get the neighboring cells of the wall 
		int[] wallCoordsAndCells = getWallCoordsAndCells(maze, wallIndex);
		int i = wallCoordsAndCells[0];
		int j = wallCoordsAndCells[1];
		int cellX = wallCoordsAndCells[2];
		int cellY = wallCoordsAndCells[3];
		
		//if the cells aren't part of the same set, then remove the wall from the maze and unionize the cells
		if (!sameSet(cellX, cellY))
		{
			maze[j][i] = ' ';
			union(cellX, cellY);
		}
		else
		{
			removeWall(maze, selectWall(maze));
		}
	}
	
	//select a wall to visit (returns the index of the wall)
	private static int selectWall(char[][] maze)
	{
		int maxIndex = unvisitedWalls.size();
		Random rand = new Random();
		int randIndex = rand.nextInt(maxIndex);
		int wallIndex = unvisitedWalls.get(randIndex);
		unvisitedWalls.remove(new Integer(wallIndex));
		return wallIndex;
	}
	
	//get the (i, j) coordinates of the specified wall index and get the indices of the bordering cells
	private static int[] getWallCoordsAndCells(char[][] maze, int wallIndex)
	{
		//note: this is what took up the majority of my 7 hours working on this thing.
		//for the life of me, I couldn't figure out a solid programmatic method of getting 
		//wall coordinates. After a lot of sketching and one-too-many cups of java (see my pun? har har...), 
		//I was able to arrive at the answer. The rest of this was cake. 
		int w = maze[0].length - 2;
		int h = maze.length - 2;
		int i = (((wallIndex)*2 + w + 1) % w) + 1;
		int j = ((int)Math.floor(((float)(wallIndex*2 + 1)/w))) + 1;
		int cellX = 0, cellY = 0;
		
		//if the cell above the wall isn't another wall, then it's an empty cell.
		//otherwise, we know that the cells are to the left and right of the wall. 
		if ((i % 2) == 1)
		{
			cellX = getCellIndex(maze, i, j+1);
			cellY = getCellIndex(maze, i, j-1);
		}
		else
		{
			cellX = getCellIndex(maze, i+1, j);
			cellY = getCellIndex(maze, i-1, j);
		}
		
		int[] coordsAndCells = {i, j, cellX, cellY};
		return coordsAndCells;
	}
	
	//gets the index of the cell at the coordinates (i, j)
	private static int getCellIndex(char[][]maze, int i, int j)
	{
		int w = maze[0].length - 2;
		int h = maze.length - 2;
		
		//determines how many cells to the right and how many down the cell is.
		//this way, I can easily determine the index, by simply multiplying how far
		//down the cell is by the width of the maze (in cells) and adding that to
		//how many cells to the right the cell is. 
		int cellsRight = (i+1)/2 - 1;
		int cellsDown = (j+1)/2 - 1;
		return cellsRight + cellsDown*(w - w/2);
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//// Most of the section below is modified code written by Sean Szumlanski ////
	///////////////////////////////////////////////////////////////////////////////
	
	//returns the parent of the set containing the element x
	private static int findset(int x)
	{
		if (set[x][0] == x)
			return x;
		else
		{
			set[x][0] = findset(set[x][0]);
			return set[x][0];
		}
	}
	
	//returns whether or not two elements are in the same set 
	private static boolean sameSet(int x, int y)
	{
		if (findset(x) == findset(y))
			return true;
		else
			return false;
	}
	
	//unionizes the two elements, using path compression and union-by-rank optimizations
	private static void union(int x, int y)
	{
		int setX = findset(x);
		int setY = findset(y);
		if (set[setX][1] < set[setY][1])
		{
			setParents.remove(new Integer(set[setX][0]));
			set[setX][0] = setY;
		}
		else if (set[setX][1] > set[setY][1])
		{
			setParents.remove(new Integer(set[setY][0]));
			set[setY][0] = setX;
		}
		else
		{
			setParents.remove(new Integer(set[setY][0]));
			set[setY][0] = setX;
			set[setX][1]++;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////
	////							 End Section    						   ////
	///////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the hours spent working on the assignment
	 * @param void
	 * @return the hours spent on the assignment
	 * @see double
	 */
	public static double hoursSpent()
	{
		return 7.0;
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
		return 3.0;
	}
}