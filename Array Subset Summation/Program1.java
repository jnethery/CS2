import java.io.*;

/**
 * @author Josiah Nethery. PID: j2551703. 
 */
public class Program1 
{
	
	/**
	 * Returns the smallest integer p >= 1 such that no subset of elements
	 * from a sorted array of positive integers sum to p. The array will be sorted 
	 * in non-decreasing order and may contain duplicates. Worst case O(n).
	 * @param array the array of positive integers sorted in non-decreasing order.
	 * @return the smallest integer p >= 1 such that no subset of elements from array sum to p.
	 * @see int
	 */
	public static int CantHasSum(int [] array) 
	{
		//we know that if the first array element isn't 1, then
		//p is 1, so we'll return 1. Best case runtime for this algorithm
		//is then O(1)
		if (1 < array[0])
			return 1;
	
		int p = 1, sum = 0;
		for (int i = 0; i < array.length; i++)
		{
			//add up the current elements so far
			sum += array[i];
			
			//if the current value of p is not less than both 
			//the current element of the array and the sum of all 
			//previous elements in the array, then the value must be set 
			//to the sum + 1. If, however, it IS less than both the current
			//element and the current sum, that means that no other addition
			//of elements will be able to sum up to the current value of p,
			//meaning we've found our integer.
			if (!(p < array[i] && p < sum))
				p = sum + 1;
			else 
				i = array.length;
		}
		System.out.println(p);
		return p;
	}
	
	/**
	 * Returns the perceived difficulty rating of the assignment on a scale of 1 to 5, 
	 * 1 being "ridiculously easy" and 5 being "insanely difficult" 
	 * @param void
	 * @return the difficulty rating 
	 * @see int
	 */
	public static int difficultyRating() 
	{
		//the example with the set {1, 3, 9} made this one easier for me to figure out
		return 2;
	}

}