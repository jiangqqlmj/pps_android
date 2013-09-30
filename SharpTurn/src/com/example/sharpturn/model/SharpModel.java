package com.example.sharpturn.model;

public class SharpModel {

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
		return "SharpModel [name=" + name + ", answer=" + answer + "]";
	}

}
