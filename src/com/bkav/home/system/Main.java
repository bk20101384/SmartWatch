package com.bkav.home.system;

import com.bkav.home.lib.FileSystem;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Watch Client started.");
		FileSystem.init(args[0]);
		Platform.start();
	}
}
