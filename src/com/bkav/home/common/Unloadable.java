package com.bkav.home.common;

/**
 * Bổ sung tính chất có thể huỷ cho các đối tượng, cho phép các đối tượng thực
 * hiện các thao tác giải phóng, ngắt kết nối khi đối tượng dừng hoạt động.
 * 
 * @author Phạm Quang Hoà
 *
 */

public interface Unloadable {
	/**
	 * Giải phóng các tài nguyên, ngắt các tham chiếu tới đối tượng từ các đối
	 * tượng khác, đảm bảo đối tượng được huỷ hoàn toàn.
	 */
	public void unload();
}
