// Sean Szumlanski, modified with additional or modified comments by Josiah Nethery (PID: 2551703)
// COP 3503, Fall 2013

// GenericBST.java
// ========
// Basic generic binary search tree (BST) implementation that supports insert() and
// delete() operations, accepting objects that implement the Comparable interface.


import java.io.*;
import java.util.*;

/**
 * @author Josiah Nethery. PID: j2551703. 
 */
class Node<T>
{
	T data;
	Node<T> left, right;

	Node(T data)
	{
		this.data = data;
	}
}

/**
 * @author Josiah Nethery. PID: j2551703. 
 */
public class GenericBST<T extends Comparable<T>>
{
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
		// note that this value does not include the time spent commenting, since I figure that 
		// the time spent commenting isn't really a factor in determining how long it took for 
		// me to solve the problem
		return 0.6333;
	}

	private Node<T> root;

	/**
	 * Adds a node to the binary search tree
	 * @param data data of type T that implements the Comparable interface
	 * @return void
	 */
	public void insert(T data)
	{
		root = insert(root, data);
	}

	private Node<T> insert(Node<T> root, T data)
	{
		// instantiate a new Node with data as data if the current root has not been previously instantiated
		if (root == null)
		{
			return new Node<T>(data);
		}
		// if the value of the data being searched for is less than the value of the current root node, then 
		// traverse to the left node of the current root, setting the current left node to whatever gets returned
		// from the insert method 
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		// if the value of the data being searched for is less than the value of the current root node, then 
		// traverse to the right node of the current root, setting the current right node to whatever gets returned
		// from the insert method 
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}
		else
		{
			// Stylistically, I have this here to explicitly state that we are
			// disallowing insertion of duplicate values.
			;
		}

		return root;
	}

	/**
	 * Removes a node from the binary search tree
	 * @param data data of type T that implements the Comparable interface
	 * @return void
	 */
	public void delete(T data)
	{
		root = delete(root, data);
	}

	private Node<T> delete(Node<T> root, T data)
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
			root.left = delete(root.left, data);
		}
		// if the value of the data being searched for is greater than the value of the current root node, then 
		// traverse to the right node of the current root, setting the current right node to whatever gets returned
		// from the delete method
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		// this else statement means that the data being searched for is equal to the current root, meaning that
		// we've found the node we wish to delete
		else
		{
			// if the node has no children, then return a value of null
			if (root.left == null && root.right == null)
			{
				return null;
			}
			// if the node has a left child, but no right child, then return the left child
			else if (root.right == null)
			{
				return root.left;
			}
			// if the node has a right child, but no left child, then return the right child
			else if (root.left == null)
			{
				return root.right;
			}
			// if the node has two children, then set the node's data to be the largest element
			// in the left sub-tree of the node, and then set the left child's data to be equal to
			// whatever data is returned when deleting the new root data from the left sub-tree
			// (i.e., the data that is currently set in the left child)
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is non-empty.
	private T findMax(Node<T> root)
	{
		// simply continue traversing to the right until you can't go no mo', and then you've found
		// the largest element 
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	/**
	 * Returns true if the value is contained in the binary search tree and false otherwise
	 * @param data the value that's being searched for
	 * @return boolean evaluation
	 * @see boolean
	 */
	public boolean contains(T data)
	{
		return contains(root, data);
	}

	private boolean contains(Node<T> root, T data)
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
	 * Prints the values in the binary search tree using in-order traversal
	 * @param void
	 * @return void
	 */
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// ORDER - Left, Root, Right
	// traverses left as far as possible until a null node is reached, then prints the root node
	// and then goes as far right as possible until a null node is reached. 
	private void inorder(Node<T> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	/**
	 * Prints the values in the binary search tree usin pre-order traversal
	 * @param void
	 * @return void
	 */
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// ORDER - Root, Left, Right
	// Prints the root node, then traverses left as far as possible until a null node is reached, 
	// and then goes as far right as possible until a null node is reached.
	private void preorder(Node<T> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	/**
	 * Prints the values in the binary search tree using post-order traversal
	 * @param void
	 * @return void
	 */
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// ORDER - Left, Right, Root
	// Traverses left as far as possible until a null node is reached, 
	// and then goes as far right as possible until a null node is reached,
	// and finally prints the root node.
	private void postorder(Node<T> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	/*public static void main(String [] args)
	{
		GenericBST<Integer> myTree = new GenericBST<Integer>();

		for (int i = 0; i < 5; i++)
		{
			int r = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + r + "...");
			myTree.insert(r);
		}

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
	}*/
}
