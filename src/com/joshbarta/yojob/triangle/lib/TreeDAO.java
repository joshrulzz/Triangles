package com.joshbarta.yojob.triangle.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.joshbarta.yojob.triangle.model.Tree;

public class TreeDAO 
{

	/**
	 * Given path to a file, read and build a B-tree from the 
	 * contents.
	 * <br><br>
	 * Rather than use fancy logging frameworks, we'll just dump
	 * problems to the console.  If this weren't a boxed, pure
	 * JRE example, we would want to keep lib stuff focused on 
	 * a purpose, logging using our enterprise framework, and 
	 * bubble up a Throwable at alert clients of trouble. 
	 * 
	 * @param fromFile path to source file. For the sake of time, I'm trusting you to give me a good one, client.
	 * @return populated 
	 */
	public static Tree load(String fromFile)
	{
		Tree retval = new Tree();
		String nextLine = null;
		int linesRead = 0;
		BufferedReader stream = null;
		File source = new File(fromFile);
		
		try {
			
			stream = new BufferedReader(new FileReader(source));
			nextLine = stream.readLine();
			
			while(nextLine != null)
			{
				retval.expand(parseRow(nextLine));
				nextLine = null;
				nextLine = stream.readLine();
			}
			
		} catch (FileNotFoundException e) {
			dump("can't read this file:_" + fromFile);
			dump(e.getMessage());
		} catch (IOException e) {
			dump("on line read " + linesRead + " we can't read:");
			dump(e.getMessage());
		}
		
		
		
		return retval;
	}

	/**
	 * Chop up one read line from your input file into pieces of int,
	 * then return those for the next row of a tree
	 * 
	 * @param readLine
	 * @return
	 */
	public static int[] parseRow(String readLine)
	{
		int[] retval = {};
		String[] split = null;
		Integer convert = null;
		
		if (readLine != null && !readLine.isEmpty())
		{
			split = readLine.trim().split(" ");
			
			if (split.length < 1)
			{
				dump("found no whitespace for_" + readLine);
			}
			else
			{
				retval = new int[split.length];
				
				for (int i=0; i<split.length; i++)
				{
					try {
						convert = new Integer(split[i]);
						retval[i] = convert.intValue();
						convert = null;
					} catch (NumberFormatException e) {
						dump("in loop " + i + " of parseRow, |" + split[i] +"| was an invalid integer");
					}
					
				}
			}
				
		}
		
		return retval;
	}
	
	private static void dump(String s)
	{
		System.out.println(s);
	}
	
}
