package com.joshbarta.yojob.triangle.test;

import java.util.Arrays;
import java.util.List;

import com.joshbarta.yojob.triangle.model.Node;
import com.joshbarta.yojob.triangle.model.Tree;

/**
 * Script to test the happy paths of triangle traversal and processing.
 * <br><br>
 * Since we have a known input domain, we're going to skip a lot of sad paths.
 * 
 * @author Josh
 *
 */
public class TreeOps 
{
	private Tree triangle;
	
	public TreeOps() {}
	
	public void runTreeTests()
	{
		dump(createTree() + "\t Create Tree");
		dump(firstRow() + "\tfirst two rows");
		dump(buildRestOfTriangle1() + "\tfinish triangle build");
		dump(getSum() + "\tfind sum");
		dump(findBottom() + "\tfind correct bottom row");
	}
	
	private boolean createTree()
	{
		triangle = new Tree();
		return triangle != null && triangle.isEmpty();
	}
	
	private boolean firstRow()
	{
		int[] root = {5};
		int[] nextLayer = {9, 6};
		triangle.expand(root);
		triangle.expand(nextLayer);
		
		return triangle.getRoot().getValue() == 5 
				&& triangle.getRoot().getLeft().getValue() == 9 
				&& triangle.getRoot().getRight().getValue() == 6
				;
	}
	
	private boolean buildRestOfTriangle1()
	{
		int[] layer3 = {4, 6, 8};
		int[] layer4 = {0, 7, 1, 5};
		
		triangle.expand(layer3);
		triangle.expand(layer4);
		
		//make int[] the old fashioned way, since we can't unbox to a primative
		List<Node> bottom = triangle.getBottom();
		int[] primitiveBottom = new int[bottom.size()];
		
		for (int i=0; i<bottom.size(); i++)
		{
			primitiveBottom[i] = bottom.get(i).getValue();
		}
		
		return Arrays.equals(primitiveBottom, layer4);
				
	}
	
	/**
	 * Call this after buildRestOfTriangle!
	 * @return
	 */
	private boolean findBottom()
	{
		List<Node> bottom = triangle.getBottom();
		
		return bottom.get(0).getValue() == 0
				&& bottom.get(1).getValue() == 7
				&& bottom.get(2).getValue() == 1
				&& bottom.get(3).getValue() == 5
				;
	}
	
	private boolean getSum()
	{
		return triangle.getSum() == 27;
	}
	
	private void dump(String s)
	{
		System.out.println(s);
	}
}
