package ToolsPackage;

import DataBasePackage.*;
public class GeneratingUMLDocumentation {
	public static void main(String[] args) {
		System.out.println("Yes");
		Class<?> test = DB_API.class;
		Tools.generateUMLInfo(test);
	}

}
