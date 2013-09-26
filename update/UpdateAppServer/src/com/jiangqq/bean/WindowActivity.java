package com.jiangqq.bean;

import java.io.Serializable;

/**
 * 单个活动窗口打开的信息
 * 
 * @author jiangqingqing
 * @time 2013/09/03 15:03
 */
public class WindowActivity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8886932432660343209L;
	private String start_timestamp; // 进程打开时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	private int duration; // 使用时长，秒

	/**
	 * 
	 */
	public WindowActivity() {
		super();
	}

	/**
	 * @param start_timestamp
	 * @param duration
	 */
	public WindowActivity(String start_timestamp, int duration) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
	}

	/**
	 * @return the start_timestamp
	 */
	public String getStart_timestamp() {
		return start_timestamp;
	}

	/**
	 * @param start_timestamp
	 *            the start_timestamp to set
	 */
	public void setStart_timestamp(String start_timestamp) {
		this.start_timestamp = start_timestamp;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WindowActivity [start_timestamp=" + start_timestamp
				+ ", duration=" + duration + "]";
	}
}
