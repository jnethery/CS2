// Josiah Nethery(PID: 2551703)
// COP 3503, Fall 2013

// RunLikeHell.java
// ========
// Finds the maximum sum of elements in an array from 0 to n where
// at least one index is skipped each jump (e.g., blocks[0] + blocks[2] + blocks[4]...).
// Solved via dynammic programming. 

import java.io.*;

/**
 * @author Josiah Nethery, PID: j2551703
 */
class RunLikeHell
{
	/**
	 * Gets the maximum gain attainable from jumping through an array
	 * @param nlocks the array
	 * @return the maximum gain
	 * @see int 
	 */
	public static int maxGain(int [] blocks)
	{
		// if the array is empty, then return 0
		if (blocks == null || blocks.length == 0)
			return 0;
		// if the array has one element, then return that element
		else if (blocks.length == 1)
			return blocks[0];
		else
		{
			//initialize an O(n) space complexity array to store current max gains
			int [] maxes = new int[blocks.length]; 
			maxes[0] = blocks[0];
			maxes[1] = blocks[1];
			
			//loop through the array if there are more than 2 elements 
			if (blocks.length > 2)
			{
				maxes[2] = blocks[2] + maxes[0];
				for (int i = 3; i < blocks.length; i++)
				{
					//see which is the maximum value from the previous maxes before the previous element.
					//this will indicate the current max attainable before hitting this spot in the array.
					maxes[i] = blocks[i] + Math.max(maxes[i-2], maxes[i-3]);
				}
			}
			//return the maximum of the last two elements of the maxes array. this is the maximum gain attainable.
			return Math.max(maxes[blocks.length-1], maxes[blocks.length-2]);
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
		return 1.0;
	}
}