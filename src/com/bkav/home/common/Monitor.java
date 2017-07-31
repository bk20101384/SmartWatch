package com.bkav.home.common;

/**
 * Giao diện chung cho phép nhận các thay đổi trên thành phần.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Monitor {
	/**
	 * Được gọi bởi thành phần khi có sự thay đổi trên thành phần.
	 * 
	 * @param change Xâu ký tự biểu diễn thay đổi.
	 */
	public void update(String change);
}
