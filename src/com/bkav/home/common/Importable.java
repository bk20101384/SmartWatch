package com.bkav.home.common;

import com.bkav.home.data.Data;

/**
 * Bổ sung tính chất có thể nhập dữ liệu cho các đối tượng.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Importable {
	/**
	 * Nhập dữ liệu từ đối tượng dữ liệu.
	 * 
	 * @param data Đối tượng chứa dữ liệu cần nhập.
	 */
	public void importData(Data data);
}
