package com.pps.myrenren.model;

/**
 * 设置列表中item的实体类
 * 
 * @author jiangqingqing
 * 
 */
public class ItemSettingModel {

	private Integer id; // 设置item图标的id
	private String name; // 该项item的名字

	public ItemSettingModel() {
		super();
	}

	public ItemSettingModel(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ItemSettingModel [id=" + id + ", name=" + name + "]";
	}

}
