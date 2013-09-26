package tv.pps.bi.proto.model;

public class SMS {

	private String smstime;
	public String getSmstime() {
		return smstime;
	}
	public void setSmstime(String smstime) {
		this.smstime = smstime;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	private long timestamp;

	public String toString() {
		return "SMS [smstime=" + smstime + ", timestamp=" + timestamp + "]";
	}
	
	
}
