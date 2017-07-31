package com.bkav.home.common;

/**
 * Giao diện truy cập các dịch vụ chung của hệ thống.
 * 
 * @author Phạm Quang Hoà
 *
 */
public interface Context {
	/**
	 * Trả về thời điểm bắt đầu của lượt xử lý hiện thời. Giá trị này không 
	 * phải là thời điểm hiện thời, vì vậy không nên dùng cho các mục đích 
	 * yêu cầu độ chính xác cao về mặt thời gian.
	 * 
	 * @return Giá trị thời gian tại thời điểm bắt đầu lượt xử lý, là số millis
	 * tính từ thời điểm 00:00:00 01/01/1970.
	 */
	public long getTime();
	
	/**
	 * Đặt lịch cho hệ thống bắt đầu một lượt xử lý tại thời điểm chỉ bởi tham
	 * số <code>time</code>. Nếu trước đó đã có một lịch xử lý trước thời điểm
	 * này, việc đặt này sẽ không có tác dụng.
	 * 
	 * @param time Thời điểm dự định cho lượt xử lý tiếp theo.
	 */
	public void reschedule(long time);
	
	/**
	 * Đặt lịch cho hệ thống bắt đầu một lượt xử lý ngay tiếp theo lượt xử lý 
	 * hiện thời. Lưu ý không gọi liên tục trong mỗi lượt xử lý vì có thể gây
	 * tăng CPU.
	 */
	public void reschedule();
}
