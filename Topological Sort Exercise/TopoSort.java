import java.io.*;
import java.util.*;

//Written by Josiah Nethery - PID: j2551703
public class TopoSort
{
	private boolean matrix[][];
	public TopoSort(String filename)
	{
		try
		{
			Scanner s = new Scanner(new BufferedReader(new FileReader(filename)));
			this.matrix = generateMatrix(s);
		}
		catch(IOException e)
		{
			this.matrix = null;
		}
	}
	
	//Modified version of topoSort() algorithm by Sean Szumlanski
	public boolean hasTopoSort(int x, int y)
	{
		//Keeps track of the path level of x and y (i.e., the maximum amount of edges it takes to reach x and y)
		//The default is -1, which indicates that the value does not exist in the topological sort
		int[] incoming = new int[matrix.length];
		int cnt = 0;
		
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++)
				incoming[j] += (matrix[i][j] ? 1 : 0);
				
		Queue<Integer> q = new ArrayDeque<Integer>();
		
		for (int i = 0; i < matrix.length; i++)
		{
			if (incoming[i] == 0)
				q.add(i);
		}
			
		while (!q.isEmpty())
		{
			int node = q.remove();
			++cnt;
			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] && --incoming[i] == 0)
					q.add(i);
		}
		
		if (cnt != matrix.length) //If there is not valid topoSort, then of course x cannot come before y in a topoSort
			return false;
		else if (1) //If this evaluates as false, then x or y are not in the topoSort
			return true;
		else
			return false;
	}
	
	//Generates an adjacency matrix from the input file 
	private boolean[][] generateMatrix(Scanner s)
	{
		int numVerts = s.nextInt();
		boolean[][] adjacencyMatrix = new boolean[numVerts][numVerts];
		
		for (int i = 0; i < numVerts; i++)
		{
			int numEdges = s.nextInt();
			for (int j = 0; j < numEdges; j++)
			{	
				adjacencyMatrix[i][s.nextInt() - 1] = true;
			}
			for (int j = 0; j < numVerts; j++)
				adjacencyMatrix[i][j] = adjacencyMatrix[i][j] ? true : false;
		}
		s.close();		
		return adjacencyMatrix;
	}
	
	public static double hoursSpent()
	{
		return 0.5;
	}
	
	public static double difficultyRating()
	{
		return 1.0;
	}
	
	public static void main(String args[])
	{
		TopoSort t2 = new TopoSort(args[0]);

		System.out.print("Test Case #1: ");
		System.out.println((t2.hasTopoSort(4, 1) == false) ? "PASS" : "FAIL");

		System.out.print("Test Case #2: ");
		System.out.println((t2.hasTopoSort(8, 3) == true) ? "PASS" : "FAIL");

		System.out.print("Test Case #3: ");
		System.out.println((t2.hasTopoSort(14, 11) == true) ? "PASS" : "FAIL");

		System.out.print("Test Case #4: ");
		System.out.println((t2.hasTopoSort(6, 12) == true) ? "PASS" : "FAIL");

		System.out.print("Test Case #5: ");
		System.out.println((t2.hasTopoSort(12, 6) == false) ? "PASS" : "FAIL");

		System.out.print("Test Case #6: ");
		System.out.println((t2.hasTopoSort(12, 4) == true) ? "PASS" : "FAIL");
	}
}