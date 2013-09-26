package tv.pps.bi.proto.model;

import java.io.Serializable;

/**
 * 通话记录 打电话的时间戳与电话的时长
 * @author jiangqingqing
 * @time 2013/09/03 16:43
 */
public class PhoneActivity implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1511698170778903832L;
	private String start_timestamp ;  //拨通电话时间戳，格式：“20130601130122”（YYYYmmddhhMMss）
	private int duration;  // 使用时长，秒
	
	private long timestamp;
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 
	 */
	public PhoneActivity() {
		super();
	}
	/**
	 * @param start_timestamp
	 * @param duration
	 */
	public PhoneActivity(String start_timestamp, int duration) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
	}
	
	
	/**
	 * @param start_timestamp
	 * @param duration
	 * @param timestamp
	 */
	public PhoneActivity(String start_timestamp, int duration, long timestamp) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
		this.timestamp = timestamp;
	}
	/**
	 * @return the start_timestamp
	 */
	public String getStart_timestamp() {
		return start_timestamp;
	}
	/**
	 * @param start_timestamp the start_timestamp to set
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
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PhoneActivity [start_timestamp=" + start_timestamp
				+ ", duration=" + duration + ", timestamp=" + timestamp + "]";
	}
    
	
	
	
}
