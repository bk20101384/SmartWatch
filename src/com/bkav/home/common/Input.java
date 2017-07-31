package com.bkav.home.common;

/**
 * Giao diện chung cho các đầu vào, cho phép một đối tượng nhận giá trị từ một
 * hoặc nhiều đối tượng khác.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Input {
	/**
	 * Nhận giá trị từ các đầu ra có kết nối với đầu vào này.
	 * @param value Giá trị nhận được, override để xử lý giá trị nhận được.
	 */
	public void receive(int value);
}
