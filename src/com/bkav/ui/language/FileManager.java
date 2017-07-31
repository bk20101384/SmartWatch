package com.bkav.ui.language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

public class FileManager {

	public static void create(Context context) {
		FileManager.context = context;
	}

	public static Xml loadXmlFromResource(int id) {
		String line;
		StringBuilder content = new StringBuilder();
		try {
			InputStream inputStream = context.getResources()
					.openRawResource(id);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));
			line = reader.readLine();
			while (line != null) {
				content.append(line);
				line = reader.readLine();
			}
			Log.i("xml", content.toString());
			Xml xml = new Xml(content.toString());
			if (xml.isValid()) {
				reader.close();
				return xml;
			} else {
				reader.close();
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	public static Xml loadXmlFromHome(String source) {
		String line;
		StringBuilder content = new StringBuilder();
		AssetManager assetManager = context.getAssets();
		try {
			InputStream inputStream = assetManager.open("home/" + source);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));
			line = reader.readLine();
			while (line != null) {
				content.append(line);
				line = reader.readLine();
			}
			Log.i("xml", content.toString());
			Xml xml = new Xml(content.toString());
			if (xml.isValid()) {
				reader.close();
				return xml;
			} else {
				reader.close();
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	public static Xml loadLanguage() {
		String source = "language.xml";
		File SDCardRoot = Environment.getExternalStorageDirectory();
		File dir = new File(SDCardRoot.getAbsolutePath() + "/WifiControl");
		if (dir.exists() == false) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File newxmlfile = new File(dir, source);
		String line;
		StringBuilder content = new StringBuilder();
		if (newxmlfile.exists() == false) {
			AssetManager assetManager = context.getAssets();
			try {
				InputStream inputStream = assetManager.open("home/" + source);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream,
								Charset.forName("UTF-8")));
				line = reader.readLine();
				while (line != null) {
					content.append(line);
					line = reader.readLine();
				}
				FileOutputStream fileOutputStream = null;
				try {
					fileOutputStream = new FileOutputStream(newxmlfile);
					fileOutputStream.write(content.toString().getBytes());
					fileOutputStream.close();
				} catch (FileNotFoundException e) {
					Log.e("FileNotFoundException", e.toString());
				}
				Xml xml = new Xml(content.toString());
				if (xml.isValid()) {
					reader.close();
					return xml;
				} else {
					reader.close();
					return null;
				}
			} catch (IOException e) {
				return null;
			}
		}
		try {
			InputStream inputStream = new FileInputStream(newxmlfile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));
			line = reader.readLine();
			while (line != null) {
				content.append(line);
				line = reader.readLine();
			}
			reader.close();
			Xml xml = new Xml(content.toString());
			if (xml.isValid()) {
				return xml;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}
	private static Context context;
}
