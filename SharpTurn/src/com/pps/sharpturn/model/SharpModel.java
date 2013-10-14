package com.pps.sharpturn.model;

import java.io.Serializable;

public class SharpModel  implements Serializable{
	private static final long serialVersionUID = -5940846870620589040L;
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private String answer;

	public SharpModel() {
		super();
	}

	public SharpModel(String name, String answer) {
		super();
		this.name = name;
		this.answer = answer;
	}

	public SharpModel(int id, String name, String answer) {
		super();
		this.id = id;
		this.name = name;
		this.answer = answer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "SharpModel [id=" + id + ", name=" + name + ", answer=" + answer
				+ "]";
	}

	

}
