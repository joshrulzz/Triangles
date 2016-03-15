package com.joshbarta.yojob.triangle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * B-tree for representing a read-in data set in a visually equal "triangle" structure
 * 
 * @author Josh
 *
 */
public class Tree 
{

	private Node root = null;
	private int size = 0;
	private int depth = 0;
	
	public Tree() {}
	

	/**
	 * Add an entire row to the bottom of the triangle structure.
	 * <br><br>
	 * We do this to keep the top-down, left-to-right *visual* structure of the read file. 
	 * (Also, we know how's coming in to us.)
	 * 
	 * @param nextRow
	 */
	public void expand(int[] nextRow)
	{
		List<Node> bottom = null;
		List<Node> toAdd = null;
		int bottomSize;
		
		/*
		 * Here's the trick.  You can't do a plain B-tree, because you'll fill up your lefts,
		 * even with a breadth-first search.  The search is a visual one, not a topological 
		 * one.  So you have to have make sure all nodes have a left and right child, even if
		 * they overlap. Bonus: this also simplifies your search (see getBottom()).
		 * 
		 * So we get the deepest row already there, and for each n-1 node, map to the one-node
		 * bigger list that's arriving with every read-in.
		 * 
		 * Steps:
		 * 
		 * 1) getBottom()
		 * 
		 * 2) create a set of Nodes with values that match the ints of nextRow[], as you
		 * 		will want to reuse the same object as L of one and R of another 
		 * 
		 * 3) for each node of bottom, take the matching node and node+1 of the input set 
		 * 		as your left and right child nodes
		 * 
		 * 4) Update your internal size and depth counts
		 * 
		 * Later, when you getSum(), you always know the bigger of your L and R child node
		 * will have a successful, full-depth path.
		 * 
		 * No, this isn't a general solution for a tree, but again: this is a *visual* problem,
		 * not a topological problem.
		 * 
		 */
		
		if (nextRow.length < 1)
		{
			dump("bro, you're not adding anything");
			return;
		}
		
		// step 1
		bottom = getBottom();
		bottomSize = bottom.size();
		
		if (bottomSize < 1)
		{
			//then we need to create root
			root = new Node(nextRow[0]);
			size = 1;
			depth = 1;
		}
		else
		{
		
			if (bottomSize > nextRow.length - 1)
			{
				dump("for the sake of time, this ain't a general solution.  Expansion must be bigger than given");
				//we could do some better sanitation, but you get the idea
			}
	
			// step 2
			toAdd = int2node(nextRow);
			
			// step 3
			for (int i=0; i<bottomSize; i++)
			{
				bottom.get(i).setLeft(toAdd.get(i));
				bottom.get(i).setRight(toAdd.get(i+1));
			}
			
			// step 4
			depth++;
			size += toAdd.size();
		}
		
	}
	
	/**
	 * Just separate this out for clarity
	 * <br><br>
	 * In hindsight, I should have just made List<Node> for the DAO builder return
	 * <br><br>
	 * No validation on this private
	 * 
	 * @param nextRow
	 * @return
	 */
	private List<Node> int2node(int[] nextRow)
	{
		ArrayList<Node> retval = new ArrayList<>();
		
		for (int i=0; i<nextRow.length; i++)
		{
			retval.add(new Node(nextRow[i]));
		}
		
		return retval;
	}
	
	/**
	 * Using the current depth, walks the tree to return the bottom (deepest) row. 
	 * <br><br>
	 * We want an ArrayList<> because it has an index that we can count off of
	 *  
	 * 
	 * @return a left-to-right ArrayList<> of the deepest elements, or empty list if none found 
	 */
	public List<Node> getBottom()
	{
		ArrayList<Node> retval = new ArrayList<>();
		
		collectBottom(retval, 1, getRoot());
		
		return retval;
	}
	
	
	private void collectBottom(List<Node> buffer, int currentDepth, Node startAt)
	{
		/*
		 * Again, here's the trick.  We recurse down to the depth-1, because we want the
		 * penultimate row.  From there, we build our return list of the Left and Right 
		 * children of the first match, then the right node of each one after that.
		 * 
		 * Alg: 
		 * 
		 * 1) if startAt is null, then there's nothing to collect; give empty set back (signal client to create root
		 * 
		 * 2) if there's only one row, just spit out root
		 * 
		 * 3) walk down tree until you get to penultimate row.
		 * 
		 * 4) grab left for the first node
		 * 
		 * 5) grab right for the rest
		 * 
		 * 
		 * 
		 */
		
		// 1 
		if (startAt == null)
		{
			return;
		}
		
		// 2
		if (getDepth() < 2)
		{
			buffer.add(getRoot());
			return;
		}
		
		// 3
		if ( (getDepth()- 1) > currentDepth )
		{
			// (remember, as we go back up tree, calling right will always
			//		be safe because we double-linked
			if (buffer.isEmpty())
				collectBottom(buffer, currentDepth+1, startAt.getLeft());
			
			collectBottom(buffer, currentDepth+1, startAt.getRight());
		}
		else
		{
			// 4
			if (buffer.isEmpty())
				buffer.add(startAt.getLeft());
			
			// 5
			buffer.add(startAt.getRight());
		}
		
		return;
	}
	
	/**
	 * Calculate the sum of a dull-depth path through the triangle, picking the larger 
	 * of each child node starting from the root node.
	 * 
	 * @return 
	 */
	public int getSum()
	{
		return walkSum(getRoot());
	}
	
	private int walkSum(Node from)
	{
		
		int retval = 0;
		
		if (from != null)
		{
			retval = from.getValue();
			
			if (from.hasKids())
			{
				if (from.getLeft().getValue() > from.getRight().getValue())
				{
					retval += walkSum(from.getLeft());
				}
				else
				{
					retval += walkSum(from.getRight());
				}
			}
		}
		
		return retval;
	}
	
	public Node getRoot() {
		return root;
	}

	public int getSize() {
		return size;
	}

	public int getDepth() {
		return depth;
	}
	
	public boolean isEmpty()
	{
		return getSize() < 1;
	}
	
	private void dump(String s)
	{
		System.out.println(s);
	}
}
