package com.pps.model;

import java.util.List;

public class DetailModel {
	private int tm;
	private int id;
	private String inton;
	private String type;
	private String region;
	private String dirt;
	public String getDirt() {
		return dirt;
	}
	public void setDirt(String dirt) {
		this.dirt = dirt;
	}
	private String actor;
	private int vote_count;
	private String block;
	private String wlock;
	private String name;
	private float vote;
	
	public int getBkid() {
		return bkid;
	}
	public void setBkid(int bkid) {
		this.bkid = bkid;
	}
	public byte getMulti() {
		return multi;
	}
	public void setMulti(byte multi) {
		this.multi = multi;
	}
	public int getVip() {
		return vip;
	}
	public void setVip(int vip) {
		this.vip = vip;
	}
	private int bkid;
	private byte multi;
	private int vip;
	
	
	private int vopt;
	private byte popt;
	private int vlevel;
	private int ct;
	private String img;
	private byte followable;
	private byte fn;
	private int partType;
	private String partTitle;
	private String partImage;
	private int total;
	private List<Channel> channels;
 /**
  * 参数 :  tm                   xml文件创建时间
参数 :  id                    影片ID
参数 :  inton                 剧情简介 
参数 :  type                 影片类型
参数 :  region               影片所属地区信息
参数 :  dirt                 导演信息
参数 :  region               影片所属地区信息
参数 :  actor                演员信息
参数 :  vote_count           评分人数
参数 :  block                黑名单数据
参数 :  wlock               白名单数据
参数 :  name                影片名称
参数 :  vote                 影片评分
参数 :  bkid                 影片百科ID
参数 :  multi                影片标识是电影或电视剧的值
参数 :  vip                  标识影片会员等级
参数 :  vopt                 影片会员等级可见值
参数 :  popt                 可以播放的用户等级
参数 :  vlevel                影片类型
参数 :  ct                   影片时长
参数 :  img                  海报地址
参数 :  followable            标识可以追剧
参数 :  fn                   用来区分综艺和其他影片的值
参数 :  partType             第三方视频的id值    
参数 :  partTitle              第三方视频的名称
参数 :  partImage             第三方视频的图标地址
参数 :  fn                   用来区分综艺和其他影片的值
 参数 : type           标识当前播放方式,如启动系统 播放器
 参数 :  Total                总集数
  */
	public DetailModel() {
		super();
	}
public DetailModel(int tm, int id, String inton, String type, String region,
		String actor, int vote_count, String block, String wlock, String name,
		float vote, int vopt, byte popt, int vlevel, int ct, String img,
		byte followable, byte fn, int partType, String partTitle,
		String partImage, int total, List<Channel> channels) {
	super();
	this.tm = tm;
	this.id = id;
	this.inton = inton;
	this.type = type;
	this.region = region;
	this.actor = actor;
	this.vote_count = vote_count;
	this.block = block;
	this.wlock = wlock;
	this.name = name;
	this.vote = vote;
	this.vopt = vopt;
	this.popt = popt;
	this.vlevel = vlevel;
	this.ct = ct;
	this.img = img;
	this.followable = followable;
	this.fn = fn;
	this.partType = partType;
	this.partTitle = partTitle;
	this.partImage = partImage;
	this.total = total;
	this.channels = channels;
}
public int getTm() {
	return tm;
}
public void setTm(int tm) {
	this.tm = tm;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getInton() {
	return inton;
}
public void setInton(String inton) {
	this.inton = inton;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getRegion() {
	return region;
}
public void setRegion(String region) {
	this.region = region;
}
public String getActor() {
	return actor;
}
public void setActor(String actor) {
	this.actor = actor;
}
public int getVote_count() {
	return vote_count;
}
public void setVote_count(int vote_count) {
	this.vote_count = vote_count;
}
public String getBlock() {
	return block;
}
public void setBlock(String block) {
	this.block = block;
}
public String getWlock() {
	return wlock;
}
public void setWlock(String wlock) {
	this.wlock = wlock;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public float getVote() {
	return vote;
}
public void setVote(float vote) {
	this.vote = vote;
}
public int getVopt() {
	return vopt;
}
public void setVopt(int vopt) {
	this.vopt = vopt;
}
public byte getPopt() {
	return popt;
}
public void setPopt(byte popt) {
	this.popt = popt;
}
public int getVlevel() {
	return vlevel;
}
public void setVlevel(int vlevel) {
	this.vlevel = vlevel;
}
public int getCt() {
	return ct;
}
public void setCt(int ct) {
	this.ct = ct;
}
public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}
public byte getFollowable() {
	return followable;
}
public void setFollowable(byte followable) {
	this.followable = followable;
}
public byte getFn() {
	return fn;
}
public void setFn(byte fn) {
	this.fn = fn;
}
public int getPartType() {
	return partType;
}
public void setPartType(int partType) {
	this.partType = partType;
}
public String getPartTitle() {
	return partTitle;
}
public void setPartTitle(String partTitle) {
	this.partTitle = partTitle;
}
public String getPartImage() {
	return partImage;
}
public void setPartImage(String partImage) {
	this.partImage = partImage;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
public List<Channel> getChannels() {
	return channels;
}
public void setChannels(List<Channel> channels) {
	this.channels = channels;
}
@Override
public String toString() {
	return "DetailModel [tm=" + tm + ", id=" + id + ", inton=" + inton
			+ ", type=" + type + ", region=" + region + ", actor=" + actor
			+ ", vote_count=" + vote_count + ", block=" + block + ", wlock="
			+ wlock + ", name=" + name + ", vote=" + vote + ", vopt=" + vopt
			+ ", popt=" + popt + ", vlevel=" + vlevel + ", ct=" + ct + ", img="
			+ img + ", followable=" + followable + ", fn=" + fn + ", partType="
			+ partType + ", partTitle=" + partTitle + ", partImage="
			+ partImage + ", total=" + total + ", channels=" + channels + "]";
}
	
}
