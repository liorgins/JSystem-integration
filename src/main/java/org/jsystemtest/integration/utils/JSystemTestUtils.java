package org.jsystemtest.integration.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class JSystemTestUtils {

	
	/**
	 * clear all scenarios from project except the default scenario 
	 * 
	 * @param path
	 */
	public static void cleanScenarios( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                cleanScenarios(f.getAbsolutePath());
            }
            else {
            	if(root.getName().equals("scenarios") && (!f.getName().equals("default.xml") && !f.getName().equals("default.properties"))) {
            		f.delete();
            		System.out.println( "File: " + f.getAbsoluteFile() + " has been deleted");
            	}
                
            }
        }
    }
	/**
	 * clear all properties and log files generated during jsystem run
	 * 
	 * @param path
	 */
	public static void cleanPropertiesAndLogs(String path ) { 
		File projectDir = new File(path);
		
		FilenameFilter genFilter = new  FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".properties") || name.endsWith(".properties.bu") || name.endsWith(".log");
			}
		};
		for (File file : projectDir.listFiles(genFilter)) {
			System.out.println(file.getName() + " has been deleted seccessfuly from project root");
			file.delete();
			
		}
	}
	
	public static String findValidClassDirectory(String parentPath) {
		if (!verifyClassesDirectory(parentPath)) {
			File parentFile = new File(parentPath);

			FileFilter folderFilter = new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			};

			for (File file : parentFile.listFiles(folderFilter)) {
				if (verifyClassesDirectory(file.getPath())) {
					parentPath = file.getPath();
					break;
				} else {
					parentPath = findValidClassDirectory(file.getPath());
					if (verifyClassesDirectory(parentPath)) {
						break;
					}
				}
			}

		}
		return parentPath;
	}

	public static boolean verifyClassesDirectory(String path) {
		if (path == null) {
			return false;
		}
		boolean sutFolderExists, scenariosFolderExists, isClassesDirectory;

		File selectedDirectory = new File(path);
		String[] list = selectedDirectory.list();
		isClassesDirectory = path.endsWith("classes");
		sutFolderExists = searchFor(list, "sut");
		scenariosFolderExists = searchFor(list, "scenarios");

		return sutFolderExists && scenariosFolderExists && isClassesDirectory;
	}

	private static boolean searchFor(String[] list, String item) {
		boolean result = false;
		if (list == null) {
			return false;
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(item)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
