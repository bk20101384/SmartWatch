package com.bkav.home.lib;

import java.io.File;

public class FileSystem {
	public static void init(String rootDirectory) {
		FileSystem.rootDirectory = rootDirectory;
	}
	
	public static String getRootDirectory() {
		return FileSystem.rootDirectory;
	}
	
	public static String getDataDirectory() {
		return FileSystem.rootDirectory + "/data";
	}
	
	public static String getAppDirectory() {
		return FileSystem.rootDirectory + "/app";
	}
	
	public static String getLogDirectory() {
		return FileSystem.rootDirectory + "/log";		
	}
	
	public static String getTempDirectory() {
		return FileSystem.rootDirectory + "/temp";
	}
	
	public static void createDirectory(String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (!file.mkdirs())
				return;
		}
	}
	
	public static void deleteDirectory(String path) {
		deleteDirectory(new File(path));
	}
	
	public static void deleteDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file: files) {
				if (file.isDirectory())
					deleteDirectory(file);
				else
					if (!file.delete())
						return;
			}
		}
		if (!directory.delete())
			return;
	}
	
	private static String rootDirectory;
}
