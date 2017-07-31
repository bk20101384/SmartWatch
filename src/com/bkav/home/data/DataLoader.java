package com.bkav.home.data;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DataLoader {
	public static Data loadFromFile(String path) {
		Reader reader = null;
		try {
			reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(path)));
			return DataParser.parse(reader);
		} catch (Exception e) {
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					
				}
			}
		}
	}
}
