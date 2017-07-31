package com.bkav.home.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.bkav.home.common.Changable;
import com.bkav.home.common.Deletable;
import com.bkav.home.common.Downloadable;
import com.bkav.home.common.Element;
import com.bkav.home.common.Exportable;
import com.bkav.home.common.Importable;
import com.bkav.home.common.Monitor;
import com.bkav.home.common.Process;
import com.bkav.home.data.Data;
import com.bkav.home.data.DataLoader;
import com.bkav.home.lib.FileSystem;

/**
 * Lá»›p cÆ¡ sá»Ÿ cho cÃ¡c Ä‘á»‘i tÆ°á»£ng thÃ nh pháº§n há»‡ thá»‘ng, vá»›i cÃ¡c tÃ­nh cháº¥t cÃ³ tÃªn,
 * cÃ³ kháº£ nÄƒng xá»­ lÃ½, cung cáº¥p cÃ¡c Ä‘áº§u vÃ o, Ä‘áº§u ra cho phÃ©p 
 * káº¿t ná»‘i tá»›i cÃ¡c Ä‘á»‘i tÆ°á»£ng khÃ¡c, hoáº·c cho phÃ©p cÃ¡c Ä‘á»‘i tÆ°á»£ng khÃ¡c káº¿t ná»‘i tá»›i.
 * <p>
 * Má»—i thÃ nh pháº§n gáº¯n vá»›i má»™t thÆ° má»¥c trÃªn há»‡ thá»‘ng, qua Ä‘Ã³ thÃ nh pháº§n cÃ³ thá»ƒ 
 * táº£i cÃ¡c thuá»™c tÃ­nh Ä‘Æ°á»£c thiáº¿t káº¿ trÆ°á»›c, Ä‘Æ°á»£c lÆ°u trong thÆ° má»¥c cá»§a thÃ nh 
 * pháº§n, cÃ³ thá»ƒ lÆ°u cÃ¡c tráº¡ng thÃ¡i hoáº¡t Ä‘á»™ng vÃ  khÃ´i phá»¥c khi component táº£i láº¡i.
 * 
 * @author Pháº¡m Quang HoÃ 
 *
 */
public abstract class Component extends Container implements Element, Process, 
	Importable, Exportable, Changable, Deletable, Downloadable {
	
	/**
	 * Táº¡o má»™t thÃ nh pháº§n má»›i.
	 * @param parent ThÃ nh pháº§n quáº£n lÃ½.
	 * @param name TÃªn thÃ nh pháº§n Ä‘Æ°á»£c táº¡o.
	 */
	public Component(Component parent, String name) {
		this.parent = parent;
		this.name = name;		
		this.data = DataLoader.loadFromFile(getDirectory() + "/" + "attributes");
		this.modified = false;
		load();
	}
	
	/**
	 * Tráº£ vá»� thÃ nh pháº§n quáº£n lÃ½.
	 * @return ThÃ nh pháº§n quáº£n lÃ½.
	 */
	public Component getParent() {
		return this.parent;
	}
	
	/**
	 * Tráº£ vá»� tÃªn cá»§a thÃ nh pháº§n.
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Tráº£ vá»� Ä‘Æ°á»�ng dáº«n Ä‘áº§y Ä‘á»§ cá»§a Ä‘á»‘i tÆ°á»£ng. Ä�Æ°á»�ng dáº«n bao gá»“m cÃ¡c tÃªn phÃ¢n 
	 * tÃ¡ch báº±ng dáº¥u <code>/</code>, báº¯t Ä‘áº§u tá»« Ä‘á»‘i tÆ°á»£ng gá»‘c.
	 * 
	 * @return Ä�Æ°á»�ng dáº«n Ä‘áº§y Ä‘á»§ cá»§a Ä‘á»‘i tÆ°á»£ng.
	 */
	public String getPath() {
		return this.parent.getPath() + "/" + this.name;
	}
	
	/**
	 * Tráº£ vá»� Ä‘Æ°á»�ng dáº«n tuyá»‡t Ä‘á»‘i tá»›i thÆ° má»¥c cá»§a Ä‘á»‘i tÆ°á»£ng.
	 * 
	 * @return ThÆ° má»¥c cá»§a Ä‘á»‘i tÆ°á»£ng.
	 */
	public String getDirectory() {
		return this.parent.getDirectory() + "/" + this.name;
	}
	
	@Override
	public boolean isFolder() {
		return true;
	}		

	@Override
	public void delete() {
		FileSystem.deleteDirectory(getDirectory());
	}
	
	@Override
	public String exportChange() {
		String change = super.exportChange();
		if (change != null) {
			for (Object object: this) {
				if (object instanceof Monitor)
					((Monitor)object).update(change);
			}
		}
		return change;
	}
	
	@Override
	public String exportData() {
		String data = super.exportData();
		if (data == null)
			return "{}";
		else
			return data;
	}
	
	@Override
	public String exportAttributes() {
		String data = super.exportAttributes();
		if (data == null)
			return "{}";
		else
			return data;
	}
	
	@Override
	public void importData(Data data) {
		super.importData(data);
		if (this.modified) {
			saveAttributes();
		}
	}
	
	public void setModified() {
		this.modified = true;
	}
	
	/**
	 * LÆ°u cÃ¡c thuá»™c tÃ­nh ra file.
	 */
	public void saveAttributes() {
		try {
			File directory = new File(getDirectory());
			if (!directory.exists()) {
				boolean success = directory.mkdirs();
				if (!success) {
					return;
				}
			}
			
			String path = getDirectory() + "/attributes";			
			File file = new File(path);
			FileWriter writer = new FileWriter(file);
			writer.write(exportAttributes());
			writer.close();
			this.modified = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ä�á»�c giÃ¡ trá»‹ má»™t thuá»™c tÃ­nh kiá»ƒu xÃ¢u kÃ½ tá»± Ä‘Æ°á»£c thiáº¿t káº¿ trÆ°á»›c.
	 * 
	 * @param name TÃªn thuá»™c tÃ­nh cáº§n Ä‘á»�c.
	 * @return GiÃ¡ trá»‹ thuá»™c tÃ­nh, hoáº·c xÃ¢u rá»—ng náº¿u thuá»™c tÃ­nh khÃ´ng tá»“n táº¡i.
	 */
	protected String loadStringAttribute(String name) {
		if (this.data != null)
			return this.data.getString(name);
		else
			return "";
	}
	
	/**
	 * Ä�á»�c giÃ¡ trá»‹ má»™t thuá»™c tÃ­nh kiá»ƒu xÃ¢u kÃ½ tá»± Ä‘Æ°á»£c thiáº¿t káº¿ trÆ°á»›c.
	 * 
	 * @param name TÃªn thuá»™c tÃ­nh cáº§n Ä‘á»�c.
	 * @param e Ngoáº¡i lá»‡ Ä‘Æ°á»£c quÄƒng ra trong trÆ°á»�ng há»£p thuá»™c tÃ­nh khÃ´ng 
	 * tá»“n táº¡i.
	 * @return GiÃ¡ trá»‹ thuá»™c tÃ­nh.
	 * @throws ComponentException
	 */
	protected String loadStringAttribute(String name, ComponentException e) throws ComponentException {
		if (this.data == null)
			throw e;
		else {
			Data data = this.data.getObject(name);
			if (data == null)
				throw e;
			return data.toString();
		}
	}
	
	/**
	 * Ä�á»�c giÃ¡ trá»‹ má»™t thuá»™c tÃ­nh kiá»ƒu nguyÃªn Ä‘Æ°á»£c thiáº¿t káº¿ trÆ°á»›c.
	 * @param name TÃªn thuá»™c tÃ­nh cáº§n Ä‘á»�c.
	 * @param defaultValue GiÃ¡ trá»‹ ngáº§m Ä‘á»‹nh, Ä‘Æ°á»£c tráº£ vá»� trong trÆ°á»�ng há»£p 
	 * thuá»™c tÃ­nh khÃ´ng tá»“n táº¡i.
	 * @return GiÃ¡ trá»‹ thuá»™c tÃ­nh, hoáº·c giÃ¡ trá»‹ ngáº§m Ä‘á»‹nh náº¿u thuá»™c tÃ­nh khÃ´ng
	 * tá»“n táº¡i.
	 */
	protected Integer loadIntegerAttribute(String name, int defaultValue) {
		String string = loadStringAttribute(name);
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * Ä�á»�c giÃ¡ trá»‹ má»™t thuá»™c tÃ­nh kiá»ƒu nguyÃªn Ä‘Æ°á»£c thiáº¿t káº¿ trÆ°á»›c.
	 * @param name TÃªn thuá»™c tÃ­nh cáº§n Ä‘á»�c.
	 * @param e Ngoáº¡i lá»‡ Ä‘Æ°á»£c quÄƒng ra trong trÆ°á»�ng há»£p thuá»™c tÃ­nh khÃ´ng 
	 * tá»“n táº¡i.
	 * @return GiÃ¡ trá»‹ thuá»™c tÃ­nh.
	 * @throws ComponentException
	 */
	protected Integer loadIntegerAttribute(String name, ComponentException e) throws ComponentException {
		String string = loadStringAttribute(name);
		try {
			return Integer.parseInt(string);
		} catch (Exception ex) {
			throw e;
		}
	}
	
	/**
	 * Ä�á»�c giÃ¡ trá»‹ má»™t thuá»™c tÃ­nh kiá»ƒu <code>Data</code> Ä‘Æ°á»£c thiáº¿t káº¿ trÆ°á»›c.
	 * @param name TÃªn thuá»™c tÃ­nh cáº§n Ä‘á»�c.
	 * @return GiÃ¡ trá»‹ thuá»™c tÃ­nh, hoáº·c <code>null</code> náº¿u thuá»™c tÃ­nh khÃ´ng
	 * tá»“n táº¡i.
	 */
	protected Data loadDataAttribute(String name) {
		if (this.data == null)
			return null;
		else
			return this.data.getObject(name);
	}
	
	/**
	 * Ä�á»�c giÃ¡ trá»‹ má»™t thuá»™c tÃ­nh kiá»ƒu <code>Data</code> Ä‘Æ°á»£c thiáº¿t káº¿ trÆ°á»›c.
	 * @param name TÃªn thuá»™c tÃ­nh cáº§n Ä‘á»�c.
	 * @param e Ngoáº¡i lá»‡ Ä‘Æ°á»£c quÄƒng ra trong trÆ°á»�ng há»£p thuá»™c tÃ­nh khÃ´ng 
	 * tá»“n táº¡i.
	 * @return GiÃ¡ trá»‹ thuá»™c tÃ­nh Ä‘Ã£ Ä‘áº£m báº£o khÃ¡c <code>null</code>.
	 * @throws ComponentException
	 */
	protected Data loadDataAttribute(String name, ComponentException e) throws ComponentException {
		Data data = loadDataAttribute(name);
		if (data == null)
			throw e;
		return data;
	}
	
	/**
	 * Táº£i giÃ¡ trá»‹ tráº¡ng thÃ¡i kiá»ƒu sá»‘ nguyÃªn Ä‘Æ°á»£c lÆ°u láº¡i trÆ°á»›c Ä‘Ã³.
	 * 
	 * @param name TÃªn tráº¡ng thÃ¡i cáº§n Ä‘á»�c.
	 * @param defaultValue GiÃ¡ trá»‹ ngáº§m Ä‘á»‹nh.
	 * @return GiÃ¡ trá»‹ tráº¡ng thÃ¡i, hoáº·c giÃ¡ trá»‹ ngáº§m Ä‘á»‹nh náº¿u khÃ´ng Ä‘á»�c Ä‘Æ°á»£c.
	 */
	protected int loadIntegerProperty(String name, int defaultValue) {
		try {
			String path = getDirectory() + "/" + name + ".sta";
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String stringValue = reader.readLine();
			reader.close();
			if (stringValue != null) {
				return Integer.parseInt(stringValue);
			} else {
				return defaultValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;			
		}
	}
	
	/**
	 * Táº£i giÃ¡ trá»‹ tráº¡ng thÃ¡i kiá»ƒu xÃ¢u kÃ½ tá»± Ä‘Æ°á»£c lÆ°u láº¡i trÆ°á»›c Ä‘Ã³.
	 * 
	 * @param name TÃªn tráº¡ng thÃ¡i cáº§n Ä‘á»�c.
	 * @param defaultValue GiÃ¡ trá»‹ ngáº§m Ä‘á»‹nh.
	 * @return GiÃ¡ trá»‹ tráº¡ng thÃ¡i, hoáº·c giÃ¡ trá»‹ ngáº§m Ä‘á»‹nh náº¿u khÃ´ng Ä‘á»�c Ä‘Æ°á»£c.
	 */
	protected String loadStringProperty(String name, String defaultValue) {
		try {
			String path = getDirectory() + "/" + name + ".sta";
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String stringValue = reader.readLine();
			reader.close();
			if (stringValue == null)
				return defaultValue;
			else
				return stringValue;
		} catch (IOException e) {
			return defaultValue;			
		}
	}
	
	/**
	 * LÆ°u giÃ¡ trá»‹ tráº¡ng thÃ¡i kiá»ƒu sá»‘ nguyÃªn.
	 * 
	 * @param name TÃªn tráº¡ng thÃ¡i cáº§n lÆ°u.
	 * @param value GiÃ¡ trá»‹ tráº¡ng thÃ¡i.
	 */
	protected void saveIntegerProperty(String name, int value) {
		try {
			String path = getDirectory() + "/" + name + ".sta";			
			File file = new File(path);
			FileWriter writer = new FileWriter(file);
			writer.write(String.valueOf(value));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * LÆ°u giÃ¡ trá»‹ tráº¡ng thÃ¡i kiá»ƒu xÃ¢u kÃ½ tá»±
	 * 
	 * @param name TÃªn tráº¡ng thÃ¡i cáº§n lÆ°u.
	 * @param value GiÃ¡ trá»‹ tráº¡ng thÃ¡i.
	 */
	protected void saveStringProperty(String name, String value) {
		try {
			String path = getDirectory() + "/" + name + ".sta";			
			File file = new File(path);
			FileWriter writer = new FileWriter(file);
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ä�áº£m báº£o má»™t thÃ nh pháº§n Ä‘Æ°á»£c Ä‘á»‹nh nghÄ©a trÆ°á»›c pháº£i Ä‘Æ°á»£c táº¡o ra ngay cáº£
	 * khi chÆ°a cÃ³ thÃ´ng tin lÆ°u trÃªn há»‡ thá»‘ng.
	 * 
	 * @param name TÃªn thÃ nh pháº§n cáº§n Ä‘áº£m báº£o.
	 */
	protected void ensureComponent(String name) {
		if (getElement(name) == null) {
			Component component = createComponent(name);
			if (component != null) {
				component.saveAttributes();
				add(component);
			}
		}
	}

	/**
	 * Táº¡o má»™t Ä‘á»‘i tÆ°á»£ng thÃ nh pháº§n theo tÃªn.
	 * 
	 * @param name TÃªn thÃ nh pháº§n cáº§n táº¡o.
	 * @return ThÃ nh pháº§n Ä‘Æ°á»£c táº¡o, hoáº·c <code>null</code> náº¿u khÃ´ng táº¡o Ä‘Æ°á»£c.
	 */
	protected Component createComponent(String name) {
		return null;
	}
	
	@Override
	protected Element createElement(String name, Data data) {
		Component component = createComponent(name);
		if (component != null) {
			component.importData(data);
			component.saveAttributes();
		}
		
		return component;
	}
	
	protected String createContent() {
		StringBuilder builder = new StringBuilder();
		for (Object element: this) {
			if (element instanceof Downloadable) {
				builder.append(((Downloadable)element).getName());
				if (((Downloadable)element).isFolder())
					builder.append("/\r\n");
				else
					builder.append("\r\n");
			}
		}
		return builder.toString();
	}

	private Component parent;
	private String name;
	private Data data;
	private boolean modified;
		
	private void load() {
		File directory = new File(getDirectory());
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file: files) {
				if (file.isDirectory()) {
					String name = file.getName();
					Component component = createComponent(name);
					if (component != null)
						add(component);
				}
			}
		}
	}
}
