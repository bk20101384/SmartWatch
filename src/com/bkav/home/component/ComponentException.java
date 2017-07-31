package com.bkav.home.component;

/**
 * Lớp chung biểu diễn các ngoại lệ liên quan tới thành phần.
 * 
 * @author Phạm Quang Hoà
 *
 */
public class ComponentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Khởi tạo một đối tượng ngoại lệ.
	 * 
	 * @param message Thông điệp của ngoại lệ.
	 */
	public ComponentException(String message) {
		super(message);
	}
}
