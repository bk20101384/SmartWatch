package com.bkav.ui.language;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.bkav.ui.language.FileManager;
import com.bkav.ui.language.Xml;
import com.bkav.ui.watchactivity.WatchActivity;

public class LanguageManager {
	public LanguageManager(Context context) {
		this.languages = getLanguageInfo();
		Xml labelXml = null;
		String source = WatchActivity.sysManager.getPreferences().getString("language", languages
				.get(0).getSource());
		for (int index = 0; index < this.languages.size(); index++) {
			Language language = languages.get(index);
			language.setIsDefault("0");
			if (language.getSource().equalsIgnoreCase(source)) {
				language.setIsDefault("1");
			}
		}
		labelXml = FileManager.loadXmlFromHome(source);
		Xml child = labelXml.getChild("item");
		while (child != null) {
			LabelItem labelItem = new LabelItem(child);
			labelMap.put(labelItem.getKey(), labelItem);
			child = child.getNext("item");
		}
	}

	public LabelItem getLabel(String key) {
		return labelMap.get(key);
	}

	public void updateLanguage(Language language) {
		labelMap = new HashMap<String, LabelItem>();
		for (int index = 0; index < languages.size(); index++) {
			languages.get(index).setIsDefault("0");
		}
		language.setIsDefault("1");
		Editor editor = WatchActivity.sysManager.getPreferences().edit();
		editor.putString("language", language.getSource());
		editor.commit();
		Xml labelXml = FileManager.loadXmlFromHome(language.getSource());
		Xml child = labelXml.getChild("item");
		while (child != null) {
			LabelItem labelItem = new LabelItem(child);
			labelMap.put(labelItem.getKey(), labelItem);
			child = child.getNext("item");
		}
	}

	private ArrayList<Language> getLanguageInfo() {
		ArrayList<Language> country = new ArrayList<Language>();
		Xml xml = FileManager.loadLanguage();
		Xml child = xml.getChild("language");
		while (child != null) {
			Language language = new Language();
			language.setValue(child.getAttrib("value"));
			language.setSource(child.getAttrib("source"));
			language.setIsDefault(child.getAttrib("default"));
			country.add(language);
			child = child.getNext("language");
		}
		return country;
	}

	public ArrayList<Language> getLanguages() {
		return languages;
	}

	private HashMap<String, LabelItem> labelMap = new HashMap<String, LabelItem>();
	private ArrayList<Language> languages = new ArrayList<Language>();
}
