package tv.pps.bi.proto.model;

public class Shutdown {

	private String shutdowntime;
	private long timestamp;
	public String getShutdowntime() {
		return shutdowntime;
	}
	public void setShutdowntime(String shutdowntime) {
		this.shutdowntime = shutdowntime;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return "Shutdown [shutdowntime=" + shutdowntime + ", timestamp="
				+ timestamp + "]";
	}
	
	
}
