/*
* Copyright 2018 Infosys Ltd.
* Version: 1.0.0
*Use of this source code is governed by MIT license that can be found in the LICENSE file or at https://opensource.org/licenses/MIT.
*/

/*
* Date: 06-May-2019
* @version 1.0.0
* Description: 
*/

package com.infy.openjdk.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import com.infy.openjdk.ui.View;

public class ReadGradleBuildFile {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("D:\\DemoJDK\\spring4-mvc-gradle-annotation-hello-world-master\\build.gradle");
		//D:\DemoJDK\gradledemo
		ReadGradleBuildFile rgf = new ReadGradleBuildFile();
		try {
			GradleBuild gb = rgf.readGradleBuildFile(file);
			gb.getDependencies().forEach(value->System.out.println(value.getArtifactId()+":"+value.getGroupId()+":"+value.getVersion()));
			gb.getDef().forEach((k,v)->System.out.println(k+"="+v));
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			View.logger.error(e.getMessage());
		}
	}

	/**
	 * @param gradlebuildfile
	 * @return
	 * @throws FileNotFoundException
	 */
	public GradleBuild readGradleBuildFile(File gradlebuildfile) throws FileNotFoundException {
		GradleBuild gb= new GradleBuild();
		Scanner scan = new Scanner(gradlebuildfile);
		HashMap<String,String> def = new HashMap<>();
		List<Dependency> dependencies = new ArrayList<>();
		while(scan.hasNext()) {
			String line = scan.nextLine();
			if(!line.startsWith("//")) {
				if(line.startsWith("def")) {
					System.out.println(line);
					line = line.replace("def", "");
					String[] defArray = line.split("=");
					def.put(defArray[0].trim(), defArray[1].replace("\"", "").trim());
				}
				else if(line.startsWith("dependencies")) {					
					dependencyReader(scan, dependencies, line);
				}				
			}

		}
		scan.close();
		gb.setDef(def);
		dependencies.forEach((dep)->{
			if(def.containsKey(dep.getVersion())) {
				dep.setVersion(def.get(dep.getVersion()));	
			}
		});
		gb.setDependencies(dependencies);
		return gb;
	}

	/**
	 * @param scan
	 * @param dependencies
	 * @param line
	 */
	private void dependencyReader(Scanner scan, List<Dependency> dependencies, String line) {
		while(!line.endsWith("}")) {
			line = scan.nextLine().trim();
			System.out.println(line);
			if(line.endsWith("}")) {
				break;
			}
			if(line.contains("exclude") || line.startsWith("//")) {
				continue;
			}
			if(line.contains("{")) {
				line = line.substring(line.indexOf("(")+1, line.lastIndexOf(")"));
				dependencyReader(scan, dependencies, line);
			}
			if(!line.equals("") && !line.contains("+")) {
			line = line.substring(line.indexOf("'")+1, line.lastIndexOf("'"));
			String[] dep = line.split(":");
			if(dep.length==2) {
				dependencies.add(new Dependency(dep[0],dep[1],null));
			}
			else if(dep.length==3){
				dependencies.add(new Dependency(dep[0],dep[1],dep[2]));
			}
				
			}
			else if(line.contains("+")){
				String[] dep = line.split("\\+");
				String[] groupartifact = dep[0].substring(dep[0].indexOf("'")+1, line.lastIndexOf(":")).split(":");
				dependencies.add(new Dependency(groupartifact[0], groupartifact[1], dep[1].trim()));
			}
			else {
				System.out.println("Line>>"+line);
			}
		}
	}
		
}