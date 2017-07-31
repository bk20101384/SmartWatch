package com.bkav.home.common;

/**
 * Mô tả tính chất có thể thay đổi của một đối tượng.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Changable {
	/**
	 * Xuất các thay đổi trong lượt xử lý gần nhất.
	 * 
	 * @return Xâu ký tự biểu diễn dữ liệu thay đổi trong lần xử lý gần nhất.
	 */
	public String exportChange();
}
