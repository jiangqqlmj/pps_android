package com.pps.myrenren.model;

/**
 * 进行显示的item的实体类
 * 
 * @author jiangqingqing
 * 
 */
public class ItemComOrMoreModel {
	private Integer id; // 需要进行显示item的icon的id
	private String name; // item的名字
	private int num; // 未读消息的数量

	public ItemComOrMoreModel() {
		super();
	}

	public ItemComOrMoreModel(Integer id, String name, int num) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "ItemModel [id=" + id + ", name=" + name + ", num=" + num + "]";
	}

}
