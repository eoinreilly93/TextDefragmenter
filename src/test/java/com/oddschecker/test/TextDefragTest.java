package com.oddschecker.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oddschecker.TextDefrag;

public class TextDefragTest {

	/*@Test
	public void SmallFileTest() {
		String[] file = {"src/main/resources/SampleFile.txt"};
		String result = TextDefrag.main(file);
		
		assertEquals("O draconian devil! Oh lame saint!", result);
	}
	
	@Test
	public void LargeFileTest() {
		String[] file = {"src/main/resources/SampleFile2.txt"};
		String result = TextDefrag.main(file);
		
		assertEquals("Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, "
				+ "consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt"
				+ " ut labore et dolore magnam aliquam quaerat voluptatem.", result);
	}*/
	
	@Test
	public void MultipleTestsPerFile() {
		String[] file = {"src/main/resources/SampleFile3.txt"};
		TextDefrag.main(file);
	}
}
