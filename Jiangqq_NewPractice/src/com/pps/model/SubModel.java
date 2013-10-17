package com.pps.model;

import java.io.Serializable;
/*
参数 :  id                     影片ID,用于请求详细界面数据 
参数 :  name                  影片名称
参数 :  bkid                   影片百科ID
参数 :  vm                    影片评分
参数 :  img                   影片海报地址
参数 :  multi                  用于标识影片是电影或电视剧的值
参数 :  vip                    标识影片会员等级
参数 :  vopt                   影片会员等级可见值
参数 :  popt                   可以播放的用户等级
参数 :  vlevel                  影片类型
参数 :  tp                     影片类型
参数 :  p                     无实际意义,请详细界面数据时需要加上
参数 :  nt                     0表示已到最后一层,1表示还有下一层
参数 :  sc                    视频数量
//参数  :  vlevel                在线等级
参数  :  lt                    拼音首字母
参数  :  on                   在线人数
//参数  :  on                   在线人数
参数  :  wl                    白名单
参数  :  bl                    黑名单
参数  :  plat_wl                     平台白名单
参数  :  plat_bl                     平台黑名单
Tag search
参数 :  pt                         列表下用来筛选影片的年份数据
参数 :  rg　　　　　　　　　　　　 列表下用来筛选影片的地区数据
//参数 :  tp                          列表下用来筛选影片的类型数据　
参数 :  lt                          列表下用来筛选影片的字母数据
*/

public class SubModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int bkid;
	private float vm;
	private String img;
	private byte multi;
	private int vip;
	private byte vopt;
	private int popt;
	private int vlevel;
	private String tp;
	private String p;
	private byte nt;
	private int sc;
	private String lt;
	private int on;
	private String wl;
	private String bl;
	private	String plat_wl;
	private String plat_bl;
	private String rg;
	//private String tp;
	//private Stribng lt;
	private Search search;
	private int tm;
	
	/**
	 * @return the tm
	 */
	public int getTm() {
		return tm;
	}
	/**
	 * @param tm the tm to set
	 */
	public void setTm(int tm) {
		this.tm = tm;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public SubModel() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param bkid
	 * @param vm
	 * @param img
	 * @param multi
	 * @param vip
	 * @param vopt
	 * @param popt
	 * @param vlevel
	 * @param tp
	 * @param p
	 * @param nt
	 * @param sc
	 * @param lt
	 * @param on
	 * @param wl
	 * @param bl
	 * @param plat_wl
	 * @param plat_bl
	 * @param rg
	 * @param search
	 * @param tm
	 * @param pt
	 */
	public SubModel(int id, String name, int bkid, float vm, String img,
			byte multi, int vip, byte vopt, int popt, int vlevel, String tp,
			String p, byte nt, int sc, String lt, int on, String wl, String bl,
			String plat_wl, String plat_bl, String rg, Search search, int tm) {
		super();
		this.id = id;
		this.name = name;
		this.bkid = bkid;
		this.vm = vm;
		this.img = img;
		this.multi = multi;
		this.vip = vip;
		this.vopt = vopt;
		this.popt = popt;
		this.vlevel = vlevel;
		this.tp = tp;
		this.p = p;
		this.nt = nt;
		this.sc = sc;
		this.lt = lt;
		this.on = on;
		this.wl = wl;
		this.bl = bl;
		this.plat_wl = plat_wl;
		this.plat_bl = plat_bl;
		this.rg = rg;
		this.search = search;
		this.tm = tm;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the bkid
	 */
	public int getBkid() {
		return bkid;
	}
	/**
	 * @param bkid the bkid to set
	 */
	public void setBkid(int bkid) {
		this.bkid = bkid;
	}
	/**
	 * @return the vm
	 */
	public float getVm() {
		return vm;
	}
	/**
	 * @param vm the vm to set
	 */
	public void setVm(float vm) {
		this.vm = vm;
	}
	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}
	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * @return the multi
	 */
	public byte getMulti() {
		return multi;
	}
	/**
	 * @param multi the multi to set
	 */
	public void setMulti(byte multi) {
		this.multi = multi;
	}
	/**
	 * @return the vip
	 */
	public int getVip() {
		return vip;
	}
	/**
	 * @param vip the vip to set
	 */
	public void setVip(int vip) {
		this.vip = vip;
	}
	/**
	 * @return the vopt
	 */
	public byte getVopt() {
		return vopt;
	}
	/**
	 * @param vopt the vopt to set
	 */
	public void setVopt(byte vopt) {
		this.vopt = vopt;
	}
	/**
	 * @return the popt
	 */
	public int getPopt() {
		return popt;
	}
	/**
	 * @param popt the popt to set
	 */
	public void setPopt(int popt) {
		this.popt = popt;
	}
	/**
	 * @return the vlevel
	 */
	public int getVlevel() {
		return vlevel;
	}
	/**
	 * @param vlevel the vlevel to set
	 */
	public void setVlevel(int vlevel) {
		this.vlevel = vlevel;
	}
	/**
	 * @return the tp
	 */
	public String getTp() {
		return tp;
	}
	/**
	 * @param tp the tp to set
	 */
	public void setTp(String tp) {
		this.tp = tp;
	}
	/**
	 * @return the p
	 */
	public String getP() {
		return p;
	}
	/**
	 * @param p the p to set
	 */
	public void setP(String p) {
		this.p = p;
	}
	/**
	 * @return the nt
	 */
	public byte getNt() {
		return nt;
	}
	/**
	 * @param nt the nt to set
	 */
	public void setNt(byte nt) {
		this.nt = nt;
	}
	/**
	 * @return the sc
	 */
	public int getSc() {
		return sc;
	}
	/**
	 * @param sc the sc to set
	 */
	public void setSc(int sc) {
		this.sc = sc;
	}
	/**
	 * @return the lt
	 */
	public String getLt() {
		return lt;
	}
	/**
	 * @param lt the lt to set
	 */
	public void setLt(String lt) {
		this.lt = lt;
	}
	/**
	 * @return the on
	 */
	public int getOn() {
		return on;
	}
	/**
	 * @param on the on to set
	 */
	public void setOn(int on) {
		this.on = on;
	}
	/**
	 * @return the wl
	 */
	public String getWl() {
		return wl;
	}
	/**
	 * @param wl the wl to set
	 */
	public void setWl(String wl) {
		this.wl = wl;
	}
	/**
	 * @return the bl
	 */
	public String getBl() {
		return bl;
	}
	/**
	 * @param bl the bl to set
	 */
	public void setBl(String bl) {
		this.bl = bl;
	}
	/**
	 * @return the plat_wl
	 */
	public String getPlat_wl() {
		return plat_wl;
	}
	/**
	 * @param plat_wl the plat_wl to set
	 */
	public void setPlat_wl(String plat_wl) {
		this.plat_wl = plat_wl;
	}
	/**
	 * @return the plat_bl
	 */
	public String getPlat_bl() {
		return plat_bl;
	}
	/**
	 * @param plat_bl the plat_bl to set
	 */
	public void setPlat_bl(String plat_bl) {
		this.plat_bl = plat_bl;
	}
	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}
	/**
	 * @param rg the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}
	/**
	 * @return the search
	 */
	public Search getSearch() {
		return search;
	}
	/**
	 * @param search the search to set
	 */
	public void setSearch(Search search) {
		this.search = search;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubModel [id=" + id + ", name=" + name + ", bkid=" + bkid
				+ ", vm=" + vm + ", img=" + img + ", multi=" + multi + ", vip="
				+ vip + ", vopt=" + vopt + ", popt=" + popt + ", vlevel="
				+ vlevel + ", tp=" + tp + ", p=" + p + ", nt=" + nt + ", sc="
				+ sc + ", lt=" + lt + ", on=" + on + ", wl=" + wl + ", bl="
				+ bl + ", plat_wl=" + plat_wl + ", plat_bl=" + plat_bl
				+ ", rg=" + rg + ", search=" + search + ", tm=" + tm + ", getTm()=" + getTm()
				+ ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getBkid()=" + getBkid() + ", getVm()=" + getVm()
				+ ", getImg()=" + getImg() + ", getMulti()=" + getMulti()
				+ ", getVip()=" + getVip() + ", getVopt()=" + getVopt()
				+ ", getPopt()=" + getPopt() + ", getVlevel()=" + getVlevel()
				+ ", getTp()=" + getTp() + ", getP()=" + getP() + ", getNt()="
				+ getNt() + ", getSc()=" + getSc() + ", getLt()=" + getLt()
				+ ", getOn()=" + getOn() + ", getWl()=" + getWl()
				+ ", getBl()=" + getBl() + ", getPlat_wl()=" + getPlat_wl()
				+ ", getPlat_bl()=" + getPlat_bl() + ", getRg()=" + getRg()
				+ ", getSearch()=" + getSearch() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
