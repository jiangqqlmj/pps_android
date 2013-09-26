package com.pps.model;

public class Channel {
	private int id;
	private String url;
	private String gid;
	private String fotm;
	private String lang;
	private int fsize;
	private byte dl;
	private int ct;
	private String tag;
	private String fn;
	private String fmt;
	private String type;
	private byte pfv2mp4;
	private String def;
	private int tm;
	private int bitrate;
	private int vid;
	private byte vseg;
	private String webURL;
	private String url_android;
	private String url_key;
	private int extfs;
	private String stroy;
	public String getStroy() {
		return stroy;
	}
	public void setStroy(String stroy) {
		this.stroy = stroy;
	}
	/*参数 :  id                  每一集的ID
	参数 :  url                 播放地址
	参数 :  gid                 唯一标识ID
	参数 :  fotm               当前码率
	参数 :  lang                当前语言
	参数 :  fsize               每一集影片的大小
	参数 :  dl                 标识是否可下载
	参数 :  ct                 每一集影片的时长
	参数 :  tag                语言码率
	参数 :  fn                 综艺和新闻才有值
	参数 :  fmt                 当前视频的格式
	参数 :  type                标识是花絮预告
	参数 :  pfv2mp4            标识是否有（Highline编码）的剧集
	参数 :  def                    未知
	参数 :  tm                  每一集上映时间
	参数 :  bitrate                  未知
	参数 :  vid                  android （baseline编码） 每一集的播放ID
	参数 :  vseg                    未知
	参数 :  webURL                 第三方web每一集播放地址
	参数 :  url_android            android第三方web每一集播放地址
	参数 ： url_key               android (baseline编码) 每一集的投递ID
	参数 ： extfs                android (baseline编码) 每一集的大小
*/
	public Channel() {
		super();
	}
	public Channel(int id, String url, String gid, String fotm, String lang,
			int fsize, byte dl, int ct, String tag, String fn, String fmt,
			String type, byte pfv2mp4, String def, int tm, int bitrate, int vid,
			byte vseg, String webURL, String url_android, String url_key, int extfs,String stroy) {
		super();
		this.id = id;
		this.url = url;
		this.gid = gid;
		this.fotm = fotm;
		this.lang = lang;
		this.fsize = fsize;
		this.dl = dl;
		this.ct = ct;
		this.tag = tag;
		this.fn = fn;
		this.fmt = fmt;
		this.type = type;
		this.pfv2mp4 = pfv2mp4;
		this.def = def;
		this.tm = tm;
		this.bitrate = bitrate;
		this.vid = vid;
		this.vseg = vseg;
		this.webURL = webURL;
		this.url_android = url_android;
		this.url_key = url_key;
		this.extfs = extfs;
	    this.stroy=stroy;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getFotm() {
		return fotm;
	}
	public void setFotm(String fotm) {
		this.fotm = fotm;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public int getFsize() {
		return fsize;
	}
	public void setFsize(int fsize) {
		this.fsize = fsize;
	}
	public byte getDl() {
		return dl;
	}
	public void setDl(byte dl) {
		this.dl = dl;
	}
	public int getCt() {
		return ct;
	}
	public void setCt(int ct) {
		this.ct = ct;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getFn() {
		return fn;
	}
	public void setFn(String fn) {
		this.fn = fn;
	}
	public String getFmt() {
		return fmt;
	}
	public void setFmt(String fmt) {
		this.fmt = fmt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte getPfv2mp4() {
		return pfv2mp4;
	}
	public void setPfv2mp4(byte pfv2mp4) {
		this.pfv2mp4 = pfv2mp4;
	}
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public int getTm() {
		return tm;
	}
	public void setTm(int tm) {
		this.tm = tm;
	}
	public int getBitrate() {
		return bitrate;
	}
	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public byte getVseg() {
		return vseg;
	}
	public void setVseg(byte vseg) {
		this.vseg = vseg;
	}
	public String getWebURL() {
		return webURL;
	}
	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}
	public String getUrl_android() {
		return url_android;
	}
	public void setUrl_android(String url_android) {
		this.url_android = url_android;
	}
	public String getUrl_key() {
		return url_key;
	}
	public void setUrl_key(String url_key) {
		this.url_key = url_key;
	}
	public int getExtfs() {
		return extfs;
	}
	public void setExtfs(int extfs) {
		this.extfs = extfs;
	}
	@Override
	public String toString() {
		return "Channel [id=" + id + ", url=" + url + ", gid=" + gid
				+ ", fotm=" + fotm + ", lang=" + lang + ", fsize=" + fsize
				+ ", dl=" + dl + ", ct=" + ct + ", tag=" + tag + ", fn=" + fn
				+ ", fmt=" + fmt + ", type=" + type + ", pfv2mp4=" + pfv2mp4
				+ ", def=" + def + ", tm=" + tm + ", bitrate=" + bitrate
				+ ", vid=" + vid + ", vseg=" + vseg + ", webURL=" + webURL
				+ ", url_android=" + url_android + ", url_key=" + url_key
				+ ", extfs=" + extfs + ", stroy=" + stroy+"]";
	}
	
}
