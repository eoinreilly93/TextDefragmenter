package com.oddschecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TextDefrag {

	public static String main(String[] args) {
		String reassembledText = "";
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        	//BufferedReader reader = new BufferedReader(new FileReader("Resources/SampleFile2.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
            	reassembledText = reassemble(line);
                System.out.println(reassembledText);
            }
            reader.close();

        } 
        catch (IOException e) {
            e.printStackTrace();
        }
		return reassembledText;
    }
	
	private static String reassemble(String line) {
		
		//Create list of each text fragment
		List<String> fragments = new ArrayList<>(Arrays.asList(line.split(";")));
		
		if (fragments.isEmpty()) {
            return "";
        }
        
        //Get first element to start and remove it as it's not needed
        String recombinedString = fragments.get(0);
        fragments.remove(0);
        
        //Loop through rest of the string fragments and check for overlaps.
        int bestOverlap = 0;
        String overlapping = "";
        int numberOfLoops = 0;
        int maxLoops = fragments.size();
        
        while (fragments.size() > 0 && numberOfLoops < maxLoops) {
            for (String fragment : fragments) {
            	
            	System.out.println(recombinedString);
            	System.out.println(fragment);

                // Check if the recombined string overlaps the fragment
                int overlap = overlap(recombinedString, fragment);
                if (overlap > bestOverlap) {
                    bestOverlap = overlap;
                    overlapping = fragment;
                }
                
                // Check if the fragment string overlaps the recombined string
                overlap = overlap(fragment, recombinedString);
                if (overlap > bestOverlap) {
                    bestOverlap = overlap;
                    overlapping = fragment;
                }
                
                //Check whether the fragment is already contained entirely in the recombined string
                if (recombinedString.contains(fragment)) {
                    bestOverlap = fragment.length();
                    overlapping = fragment;
                } 
                //Check whether the fragment contains the recombined string
                else if (fragment.contains(recombinedString)) {
                    bestOverlap = recombinedString.length();
                    recombinedString = fragment;
                    overlapping = fragment;
                }
            }
            
            recombinedString = recombine(recombinedString, overlapping, bestOverlap);
            fragments.remove(overlapping);
            bestOverlap = 0;
            overlapping = "";
            numberOfLoops++;
        }
        
        
		return recombinedString;
		
	}
	
	/**
     * Calculates the max length of overlapping regions between two strings
     *
     * @param str1 - String to be compared
     * @param str2 - String to be compared
     * @return maxOverlap - length of maximum overlap
     */
    private static int overlap(final String str1, final String str2) {

        // Start at the longest possible length and work down
        int maxOverlap = str2.length();
        while (!str1.regionMatches(str1.length() - maxOverlap, str2, 0, maxOverlap)) {
            maxOverlap--;
        }
        return maxOverlap;
    }
    
    private static String recombine(String str1, String str2, int overlap) {

    	//If the end of str1 matches the start of st2, subset str2 and add str1 to the start
        if (str1.substring((str1.length() - overlap), str1.length())
        		.equals(str2.substring(0, overlap))) {
            // end of 1 matches start of 2
            str2 = str2.substring(overlap, str2.length());
            return str1 + str2;
        } 
        //If the end of str2 matches the start of str1, subset str1 and add str2 to the start
        else if (str2.substring((str2.length() - overlap), str2.length())
                .equals(str1.substring(0, overlap))) {
            str1 = str1.substring(overlap, str1.length());

            return str2 + str1;
        } 
        else {
        	
            // There appears to be no overlap
            return str1;
        }
    }
}
