package com.joshbarta.yojob.triangle;

import com.joshbarta.yojob.triangle.lib.TreeDAO;
import com.joshbarta.yojob.triangle.model.Tree;


/**
 * Entry Point for this app.
 * <br><br>
 * Goal: read in a file in a "triangle" format, find the quantity described here 
 * ( http://www.yodlecareers.com/puzzles/triangle.html ), and print that result out.
 * <br><br>
 * Author's assumptions and constraints:<ul><li>
 * - 100% standard JRE java 1.7 (so no junit or other outside libs) </li><li>
 * - clarity-optimized vs performance optimized (we really only need to run this once or twice) </li><li>
 * 
 * </li></ul>
 * 
 * @author Josh
 *
 */
public class RunMe {
	
	public static void main(String[] args) 
	{
		
		if (args.length < 1)
		{
			dump("I need at least one parameter: the path to triangle.txt  EX: 'c:\\stuff\\triangle.txt'");
		}
		else
		{
			findSum(args[0]);
		}

	}
	
	public static void findSum(String inFile)
	{
		Tree tree = TreeDAO.load(inFile);
		dump(tree.getSum());
	}
	
	private static void dump(Object val)
	{
		System.out.println(val.toString());
	}

}
