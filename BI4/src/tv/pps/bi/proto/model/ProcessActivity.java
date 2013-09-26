package tv.pps.bi.proto.model;

import java.io.Serializable;

public class ProcessActivity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2574080942135590971L;
	private String start_timestamp;
	private int duration;

	public ProcessActivity() {
		super();
	}

	public ProcessActivity(String start_timestamp, int duration) {
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
		return "ProcessActivity [start_timestamp=" + start_timestamp
				+ ", duration=" + duration + "]";
	}
 
}
