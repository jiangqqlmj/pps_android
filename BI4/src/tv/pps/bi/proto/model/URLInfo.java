package tv.pps.bi.proto.model;

public class URLInfo {

	private String url;
	private String keywords;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	private long timestamp;

	public String toString() {
		return "URLInfo [url=" + url + ", keywords=" + keywords
				+ ", timestamp=" + timestamp + "]";
	}
	
}
