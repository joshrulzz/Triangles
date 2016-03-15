package com.joshbarta.yojob.triangle.test;

import java.util.Arrays;

import com.joshbarta.yojob.triangle.lib.TreeDAO;
import com.joshbarta.yojob.triangle.model.Tree;

/**
 * Again, no external libs, so we're using a custom test class
 * 
 * @author Josh
 *
 */
public class Harness {

	public static void main(String[] args) 
	{
		dump("Running tests:");
		
		dump(" ");
		dump(parseTriangleLine() + "\tParse a line from a triangle file");
		dump(readTriangle() + "\tParse full triangle");
		
		treeStuff();
			
	}
	
	private static void dump(Object o)
	{
		System.out.println(o.toString());
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	private static boolean readTriangle()
	{
		Tree retval = null;
		
		retval = TreeDAO.load("D:/setup/git_local/yojob/Triangles/src/triangle1.txt"); //triangle1.txt");
		
		return retval != null && retval.getSize() > 0;
	}
	
	private static boolean parseTriangleLine()
	{
		String sampleRow = "          3399 3350 4788 7546 ";
		final int[] CORRECT = {3399, 3350, 4788, 7546};
				
		return Arrays.equals(CORRECT, TreeDAO.parseRow(sampleRow)); 
	}
	
	private static void treeStuff()
	{
		TreeOps treeTester = new TreeOps();
		
		treeTester.runTreeTests();
	}
	
}
