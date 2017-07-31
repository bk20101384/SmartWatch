package com.bkav.home.common;

/**
 * Giao diện chung cho các đầu ra, cho phép một đối tượng gửi một giá trị tới
 * một hoặc nhiều đối tượng khác.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Output {
	/**
	 * Gửi giá trị ra đầu ra, giá trị sẽ được nhận tại các đầu vào có kết nối
	 * với đầu ra này.
	 * 
	 * @param value Giá trị gửi đi.
	 */
	public void send(int value);
}
