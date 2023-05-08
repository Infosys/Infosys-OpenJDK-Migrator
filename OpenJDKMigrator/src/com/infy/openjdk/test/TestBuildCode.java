package com.infy.openjdk.test;

import java.io.IOException;
import org.junit.Test;
import com.infy.openjdk.business.BuildCode;

public class TestBuildCode {
	@Test
	public void testBuild() {
		BuildCode buildCode = new BuildCode();
		try {
			buildCode.buildMavenProject("D:\\Demo\\test\\");
		} 
		catch (IOException e) {
			e.getMessage();
		}
	}
	
}