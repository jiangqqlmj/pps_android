package tv.pps.bi.proto.model;

public class Bootup {

	private String boottime ;
	public String getBoottime() {
		return boottime;
	}
	public void setBoottime(String boottime) {
		this.boottime = boottime;
	}
	private long timestamp;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String toString() {
		return "Bootup [boottime=" + boottime + ", timestamp=" + timestamp
				+ "]";
	}
	
	
	
}
