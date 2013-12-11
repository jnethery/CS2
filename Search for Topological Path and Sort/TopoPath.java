import java.io.*;
import java.util.*;

//Written by Josiah Nethery - PID: j2551703
public class TopoPath
{
	public static boolean hasTopoPath(String filename)
	{
		try
		{
			Scanner s = new Scanner(new BufferedReader(new FileReader(filename)));
			return hasTopoPath(s);
		}
		catch(IOException e)
		{
			return false;
		}
	}
	
	private static boolean hasTopoPath(Scanner s) //O(n) + O(n) + O(n^2) = O(n^2)
	{
		int numVerts = s.nextInt();
		
		int nullTag = -2; //Integer value that represents "null"
		int tailIndex = nullTag;
		
		//This ArrayList of ArrayList's stores the nodes that point to each node in the topology
		//For example, if 1 -> 2, 3 && 2 -> 3, 4, then the list would look like this: [null],[1],[1,2],[2]
		//This will be used in the recursive algorithm 'getPathLen' to determine if the graph contains a topoPath
		ArrayList<ArrayList<Integer>> relationsList = new ArrayList<ArrayList<Integer>>(numVerts);
		for (int i = 0; i < numVerts; i++) //O(n)
			relationsList.add(i, new ArrayList<Integer>());
			
		for (int i = 0; i < numVerts; i++) //[O(m*n) | m < n] = O(n)
		{
			int numEdges = s.nextInt();
			if (numEdges == numVerts) //This would ensure a cycle, so we will return false
				return false;
			else if (numEdges > 0)
			{
				for (int j = 0; j < numEdges; j++)
				{
					int relationIndex = s.nextInt() - 1;
					if (relationIndex == i) //This would ensure a cycle, so we will return false
						return false;
					else
						relationsList.get(relationIndex).add(i);
				}
			}
			else //If the vertex does not point to another vertex, it is the tail of the graph
			{
				tailIndex = i;
			}
		}
		
		if (tailIndex == nullTag) //This would ensure a cycle, so we will return false
			return false;
		else
		{	
			//If the maximum path length from the tail vertex is equal to the number of vertices in the graph minus one,
			//then we know we have a valid topoPath. Otherwise, we don't. 
			if (getPathLen(relationsList, tailIndex) + 1 == numVerts) 
				return true;
			else
				return false;
		}
	}
	
	//This returns the path length to from the node index "tailIndex"
	//For example, in the path 1 -> 2 -> 3, the value returned from '3' would be 2
	private static int getPathLen(ArrayList<ArrayList<Integer>> relationsList, int tailIndex)
	{
		if (relationsList.get(tailIndex).size() == 0)
			return 0;
		else
		{
			int maxPathLen = 0;
			//[O(m_0*m_1*m_2*...*m_(n-1)) | 1 <= m_i <= (n-1)] = O(n^2)
			for (int i = 0; i < relationsList.get(tailIndex).size(); i++)
			{
				int tempMaxPathLen = 1;
				tempMaxPathLen += getPathLen(relationsList, relationsList.get(tailIndex).get(i));
				if (tempMaxPathLen > maxPathLen)
					maxPathLen = tempMaxPathLen;
			}
			return maxPathLen;
		}
	}

	public static double hoursSpent()
	{
		return 9.5;
	}
	
	public static double difficultyRating()
	{
		return 3.5;
	}
}