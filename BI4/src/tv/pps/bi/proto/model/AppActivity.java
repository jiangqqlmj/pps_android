package tv.pps.bi.proto.model;

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
	
	private String packageName;

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public AppActivity() {
		super();
	}

	public AppActivity(String start_timestamp, int duration) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
	}
	

	/**
	 * @param start_timestamp
	 * @param duration
	 * @param packageName
	 */
	public AppActivity(String start_timestamp, int duration, String packageName) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
		this.packageName = packageName;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppActivity [start_timestamp=" + start_timestamp
				+ ", duration=" + duration + ", packageName=" + packageName
				+ "]";
	}

}
