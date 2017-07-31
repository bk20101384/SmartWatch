package com.bkav.home.common;

/**
 * Giao diện chung cho các đối tượng thực hiện trao đổi dữ liệu.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Transaction {
	/**
	 * Thực hiện trao đổi dữ liệu.
	 * 
	 * @param input Dữ liệu đầu vào nhận được.
	 * @return Dữ liệu đầu ra sau khi xử lý.
	 */
	public String transact(String input);
}
