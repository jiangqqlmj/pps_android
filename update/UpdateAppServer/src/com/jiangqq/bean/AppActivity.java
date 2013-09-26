package com.jiangqq.bean;

import java.io.Serializable;

/**
 * APP的使用基本情况
 * 
 * @author jiangqingqing
 * @time 2013/09/03 14:36
 */
public class AppActivity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5260065781778678511L;
	private String start_timestamp; // APP打开时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	private int duration; // 使用时长，秒

	public AppActivity() {
		super();
	}

	public AppActivity(String start_timestamp, int duration) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
	}

	public String getStart_timestamp() {
		return start_timestamp;
	}

	public void setStart_timestamp(String start_timestamp) {
		this.start_timestamp = start_timestamp;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "AppActivity [start_timestamp=" + start_timestamp
				+ ", duration=" + duration + "]";
	}
}
