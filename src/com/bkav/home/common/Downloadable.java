package com.bkav.home.common;

/**
 * Bổ sung tính chất có thể tải về được cho một tài nguyên.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Downloadable extends Element {
	/**
	 * Trả về tính chất của tài nguyên.
	 * 
	 * @return <code>true</code> nếu tài nguyên là một thư mục, 
	 * <code>false</code> nếu ngược lại.
	 */
	public boolean isFolder();
}
