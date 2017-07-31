package com.bkav.home.common;


/**
 * Bổ sung tính chất có thể xuất dữ liệu cho các đối tượng.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Exportable {
	/**
	 * Xuất toàn bộ dữ liệu của đối tượng.
	 *  
	 * @return Xâu ký tự biểu diễn toàn bộ dữ liệu đối tượng.
	 */
	public String exportData();
}