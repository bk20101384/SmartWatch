package com.bkav.home.data;

/**
 * Cung cấp công cụ đóng gói dữ liệu đồng bộ.
 * 
 * @author Phạm Quang Hoà
 *
 */
public class DataBuilder {
	/**
	 * Tạo một đối tượng <code>DataBuilder</code> rỗng.
	 */
	public DataBuilder() {
		this.builder = new StringBuilder();
		this.first = true;
	}
	
	/**
	 * Bắt đầu đóng gói một đối tượng.
	 */
	public void beginObject() {
		if (!this.first)
			this.builder.append(',');
		builder.append('{');
		this.first = true;
	}
	
	public void beginArray() {
		if (!this.first)
			this.builder.append(',');
		builder.append('[');
		this.first = true;
	}
	
	public void addName(String name) {
		if (!this.first)
			this.builder.append(',');
		this.builder.append(name);
		this.builder.append(':');
		this.first = true;
	}
	
	public void addValue(String value) {
		if (!this.first)
			this.builder.append(',');
		this.builder.append(value);
		this.first = false;
	}
	
	/**
	 * Thêm một cặp name/value vào đối tượng đóng gói.
	 * 
	 * @param name Tên phần tử.
	 * @param value Giá trị phần tử.
	 */
	public void addPair(String name, String value) {
		addName(name);
		addValue(value);
	}
	
	/**
	 * Thêm một cặp name/value vào đối tượng đóng gói.
	 * @param name Tên phần tử.
	 * @param value Giá trị phần tử.
	 */
	public void addPair(String name, int value) {
		addName(name);
		addValue(String.valueOf(value));
	}

	/**
	 * Kết thúc đóng gói một đối tượng.
	 */
	public void endObject() {
		builder.append('}');
		this.first = false;
	}
	
	public void endArray() {
		this.builder.append(']');
		this.first = false;
	}
	
	@Override
	public String toString() {
		return this.builder.toString();
	}
	
	private StringBuilder builder;
	private boolean first;
}
