package com.joshbarta.yojob.triangle.model;

/**
 * POJO for the B-tree structure
 * 
 * 
 * @author Josh
 *
 */
public class Node 
{
	private int value;
	private Node left = null;
	private Node right = null;

	public Node(int init)
	{
		value = init;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * 
	 * @return true IFF both nodes != null
	 */
	public boolean hasKids()
	{
		return getLeft() != null && getRight() != null;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
	
	
}
