// Josiah Nethery (PID: 2551703)
// COP 3503, Fall 2013

// Treap.java
// ========
// Basic generic treap implementation that supports add() and
// remove() operations, accepting objects that implement the Comparable interface.

import java.io.*;
import java.util.*;

/**
 * @author Josiah Nethery. PID: j2551703
 */
class Node<AnyType>
{
	public AnyType data;
	public int priority;
	public Node<AnyType> left, right;
	
	public Node(AnyType data, int priority)
	{
		this.data = data;
		this.priority = priority;
	}
}

/**
 * @author Josiah Nethery. PID: j2551703
 */
public class Treap<AnyType extends Comparable<AnyType>>
{
	private int size = 0;
	private Node<AnyType> root;
	private HashSet<Integer> prioritySet; //a hash-set has ~O(1) time complexity for lookups and O(n) space complexity
	
	public Treap()
	{
		prioritySet = new HashSet<Integer>();
	}
	
	/**
	 * Adds a node to the treap
	 * @param data the data being added
	 * @return void
	 */
	public void add(AnyType data)
	{
		if (!contains(root, data))
		{
			int priority = generatePriority();
			root = add(root, data, priority);
		}
	}
	
	/**
	 * Adds a node to the treap
	 * @param data the data being added
	 * @param priority the previously set priority of the node being added 
	 * @return void
	 */
	public void add(AnyType data, int priority)
	{
		if (!contains(root, data))
			root = add(root, data, priority);
	}
	
	private Node<AnyType> add(Node<AnyType> root, AnyType data, int priority)
	{
		// instantiate a new Node with data as data if the current root has not been previously instantiated
		// increment the size field of the treap
		if (root == null)
		{
			this.size++;
			return new Node<AnyType>(data, priority);
		}
		// if the value of the data being searched for is less than the value of the current root node, then 
		// traverse to the left node of the current root, setting the current left node to whatever gets returned
		// from the insert method 
		else if (data.compareTo(root.data) < 0)
		{
			root.left = add(root.left, data, priority);
			// rotate right if the left child's priority is less than the root's priority
			if (root.left.priority < root.priority)
			{
				Node<AnyType> c = root.left;
				root.left = c.right;
				c.right = root;
				root = c;
			}
		}
		// if the value of the data being searched for is less than the value of the current root node, then 
		// traverse to the right node of the current root, setting the current right node to whatever gets returned
		// from the insert method 
		else if (data.compareTo(root.data) > 0)
		{
			root.right = add(root.right, data, priority);
			// rotate left if the left child's priority is less than the root's priority
			if (root.right.priority < root.priority)
			{
				Node<AnyType> c = root.right;
				root.right = c.left;
				c.left = root;
				root = c;
			}
		}
		return root;
	}
	
	/**
	 * Removes a node from the treap
	 * @param void
	 * @return void
	 */
	public void remove(AnyType data)
	{
		if (contains(root, data))
			root = remove(root, data);
	}
	
	private Node<AnyType> remove(Node<AnyType> root, AnyType data)
	{
		// if the root node is null, then either there's nothing to delete or no more traversal is necessary
		if (root == null)
		{
			return null;
		}
		// if the value of the data being searched for is less than the value of the current root node, then 
		// traverse to the left node of the current root, setting the current left node to whatever gets returned
		// from the delete method 
		else if (data.compareTo(root.data) < 0 )
		{
			root.left = remove(root.left, data);
		}
		// if the value of the data being searched for is greater than the value of the current root node, then 
		// traverse to the right node of the current root, setting the current right node to whatever gets returned
		// from the delete method
		else if (data.compareTo(root.data) > 0)
		{
			root.right = remove(root.right, data);
		}
		// this else statement means that the data being searched for is equal to the current root, meaning that
		// we've found the node we wish to delete
		else
		{
			// if the node has no children, then return a value of null
			// decrement the size of the treap and remove the priority of the node from the priority set
			if (root.left == null && root.right == null)
			{
				size--;
				prioritySet.remove(root.priority);
				return null;
			}
			// if the node has a left child, but no right child, then rotate right and then return the new root
			// call the remove function on the new root's right subtree
			else if (root.right == null)
			{
				Node<AnyType> c = root.left;
				root.left = c.right;
				c.right = root;
				root = c;
				root.right = remove(root.right, data);
			}
			// if the node has a right child, but no left child, then rotate left and then return the new root
			// call the remove function on the new root's left subtree
			else if (root.left == null)
			{
				Node<AnyType> c = root.right;
				root.right = c.left;
				c.left = root;
				root = c;
				root.left = remove(root.left, data);
			}
			// if the node has two children, then compare the children's priorities.
			// if the right child's priority is greater than the left child's, rotate right and then return the new root.
			// otherwise, rotate left and then return the new root.
			// call the remove function on the new root's right sub-tree or left sub-tree, respectively. 
			else
			{
				if (root.right.priority > root.left.priority)
				{
					Node<AnyType> c = root.left;
					root.left = c.right;
					c.right = root;
					root = c;
					root.right = remove(root.right, data);
				}
				else
				{
					Node<AnyType> c = root.right;
					root.right = c.left;
					c.left = root;
					root = c;
					root.left = remove(root.left, data);
				}
			}
		}
		return root;
	}
	
	/**
	 * Returns true if the element being searched for exists in the treap and false otherwise
	 * @param data the data being searched for
	 * @return true or false
	 * @see boolean
	 */
	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}
	
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		// if the root is null, then either the binary search tree is empty or the value has 
		// not been found and traversal cannot continue
		if (root == null)
		{
			return false;
		}
		// if the data being searched for is less than the value of the current root's data, 
		// check if the data exists in the current root's left sub-tree
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		// if the data being searched for is greater than the value of the current root's data, 
		// check if the data exists in the current root's right sub-tree
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		// if the data being searched for is equal to the value of the current root's data, then 
		// the search was successful and the method should return true
		else
		{
			return true;
		}
	}
	
	/**
	 * Returns the number of elements in the treap
	 * @param void
	 * @return the number of elements in the treap
	 * @see int
	 */
	public int size()
	{
		return this.size;
	}
	
	/**
	 * Returns the height of the treap
	 * @param root the root of the treap sub-tree
	 * @return the height of the treap
	 * @see int
	 */
	public int height()
	{
		return height(root);
	}
	
	private int height(Node<AnyType> root)
	{
		if (root == null)
			return -1;
		// essentially traverses through each sub-tree and finds the greatest height recursively
		else
		{
			int leftHeight = height(root.left);
			int rightHeight = height(root.right);
			
			if (leftHeight > rightHeight)
				return ++leftHeight;
			else
				return ++rightHeight;
		}
	}
	
	/**
	 * Returns the root of the treap
	 * @param void
	 * @return root
	 * @see Node
	 */
	public Node<AnyType> getRoot()
	{
		return root; 
	}
	
	/**
	 * Returns a new randomly generated priority
	 * @param void
	 * @return the new priority
	 * @see int
	 */
	private int generatePriority()
	{
		Random rand = new Random();
		int priority = rand.nextInt(Integer.MAX_VALUE) + 1;
		while (prioritySet.contains(priority))
			priority = rand.nextInt(Integer.MAX_VALUE) + 1;
		prioritySet.add(priority);
		return priority;
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
		return 2.0;
	}
	
	
	/**
	 * Returns the hours spent working on the assignment
	 * @param void
	 * @return the hours spent on the assignment
	 * @see double
	 */
	public static double hoursSpent()
	{
		return 2.65;
	}
}