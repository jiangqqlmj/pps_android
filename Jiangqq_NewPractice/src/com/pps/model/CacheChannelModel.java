package com.pps.model;

/**
 * 
 * 剧情列表缓存信息实体类
 * @author jiangqingqing
 * @time 2013/08/22 14:02
 * 
 */
public class CacheChannelModel {
	private int id;
	private String img_url; // 图片网址
	private String list_name; // 节目名字
	private String list_tp; // 剧情简介
	private String list_on; // 在线人数
	private String list_vm; // 评分
	private String sub_id; // 请求详情的id

	public CacheChannelModel() {
		super();
	}

	public CacheChannelModel(String img_url, String list_name, String list_tp,
			String list_on, String list_vm, String sub_id) {
		super();
		this.img_url = img_url;
		this.list_name = list_name;
		this.list_tp = list_tp;
		this.list_on = list_on;
		this.list_vm = list_vm;
		this.sub_id = sub_id;
	}

	public CacheChannelModel(int id, String img_url, String list_name,
			String list_tp, String list_on, String list_vm, String sub_id) {
		super();
		this.id = id;
		this.img_url = img_url;
		this.list_name = list_name;
		this.list_tp = list_tp;
		this.list_on = list_on;
		this.list_vm = list_vm;
		this.sub_id = sub_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public String getList_tp() {
		return list_tp;
	}

	public void setList_tp(String list_tp) {
		this.list_tp = list_tp;
	}

	public String getList_on() {
		return list_on;
	}

	public void setList_on(String list_on) {
		this.list_on = list_on;
	}

	public String getList_vm() {
		return list_vm;
	}

	public void setList_vm(String list_vm) {
		this.list_vm = list_vm;
	}

	public String getSub_id() {
		return sub_id;
	}

	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}

	@Override
	public String toString() {
		return "CacheChannelModel [id=" + id + ", img_url=" + img_url
				+ ", list_name=" + list_name + ", list_tp=" + list_tp
				+ ", list_on=" + list_on + ", list_vm=" + list_vm + ", sub_id="
				+ sub_id + "]";
	}

}
