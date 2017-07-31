package com.bkav.home.common;

/**
 * Giao diện chung cho các đối tượng <i>có thể chạy được</i>, cho phép các đối
 * tượng thực hiện xử lý khi có sự kiện.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Process {
	/**
	 * Xử lý các sự kiện, thực hiện các công việc giúp đối tượng có thể hoạt 
	 * động được. Phương thức <code>process</code> không được gọi liên tục, chỉ
	 * được gọi bởi hệ thống khi có sự kiện xảy ra.
	 */
	public void process();
}
