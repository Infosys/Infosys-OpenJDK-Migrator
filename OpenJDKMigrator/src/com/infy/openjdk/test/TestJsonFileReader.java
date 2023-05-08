package com.infy.openjdk.test;

import java.io.IOException;
import org.junit.Test;
import com.infy.openjdk.configuration.JsonFileReader;

public class TestJsonFileReader {

	@Test
	public void testLoadPattern() {
		JsonFileReader jfr = new JsonFileReader();
		try {
			jfr.loadPattern("JDK11");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
}