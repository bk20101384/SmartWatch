package com.bkav.home.common;

/**
 * Mô tả tính chất có thể xoá của một đối tượng.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Deletable {
	/**
	 * Được gọi bởi danh sách khi phần tử được xoá khỏi danh sách.
	 * 
	 */
	public void delete();
}
