package com.oddschecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextDefrag {

	public static String main(String[] args) {
		
        String result = "";
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = reader.readLine()) != null) {
                result = reassemble(line);
            }
            reader.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	/**
	 * Reassembles the fragments into their original format
	 * @param line		- All the fragments separated by ";"
	 * @return			- Recombined fragments 
	 */
	private static String reassemble(String line) {
		
		//Create list of each text fragment
		List<String> fragments = new ArrayList<>(Arrays.asList(line.split(";")));
		
		if (fragments.isEmpty() || fragments.size() == 0) {
            return "";
        }
        
        //Get first element to start and remove it as it's not needed
        String recombinedString = fragments.get(0);
        fragments.remove(0);
        
        //Loop through rest of the string fragments and check for overlaps.
        int largestOverlap = 0;
        String overlappingString = "";
        int numberOfLoops = 0;
        int maxLoops = fragments.size();
        
        while (numberOfLoops < maxLoops) {
            for (String fragment : fragments) {
            	
                //Check if the recombined string overlaps the fragment
                int overlap = overlap(recombinedString, fragment);
                if (overlap > largestOverlap) {
                	largestOverlap = overlap;
                    overlappingString = fragment;
                }
                
                //Check if the fragment string overlaps the recombined string
                overlap = overlap(fragment, recombinedString);
                if (overlap > largestOverlap) {
                	largestOverlap = overlap;
                    overlappingString = fragment;
                }
            }
            
            recombinedString = combineStrings(recombinedString, overlappingString, largestOverlap);
            fragments.remove(overlappingString);
            largestOverlap = 0;
            overlappingString = "";
            numberOfLoops++;
        }       
        
		return recombinedString;		
	}
	
	/**
     * Calculates the max length of overlapping regions between two strings
     *
     * @param str1 			- String to be compared
     * @param str2			- String to be compared
     * @return maxOverlap 	- length of maximum overlap between the two strings
     */
    private static int overlap(final String str1, final String str2) {

        //Start at the longest possible length and work down
        int maxOverlap = str2.length();
        while (!str1.regionMatches(str1.length() - maxOverlap, str2, 0, maxOverlap)) {
            maxOverlap--;
        }
        return maxOverlap;
    }
    
    /**
     * Combines the current fragment with the recombined string
     * If not possible, it returns the curent recombined string
     * 
     * @param recombinedString		- Current recombined string
     * @param overlappingStr		- The current fragment 
     * @param overlap				- The largest overlap between the two strings
     * @return						- Newly combined string
     */
    private static String combineStrings(String recombinedString, String overlappingStr, int overlap) {

    	//If the end of recombinedString matches the start of overlappingStr, 
    	//subset overlappingStr and add recombinedString to the start
        if (recombinedString.substring((recombinedString.length() - overlap), recombinedString.length())
        		.equals(overlappingStr.substring(0, overlap))) {
        	
        	overlappingStr = overlappingStr.substring(overlap, overlappingStr.length());
            return recombinedString + overlappingStr;
        } 
        //If the end of overlappingStr matches the start of recombinedString, 
        //subset recombinedString and add overlappingStr to the start
        else if (overlappingStr.substring((overlappingStr.length() - overlap), overlappingStr.length())
                .equals(recombinedString.substring(0, overlap))) {
        	
        	recombinedString = recombinedString.substring(overlap, recombinedString.length());
            return overlappingStr + recombinedString;
        } 
       	
         //No overlap occurs
         return recombinedString;
    }
}
