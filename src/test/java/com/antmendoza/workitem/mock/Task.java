package com.antmendoza.workitem.mock;

public class Task {

	private long id;
	private String name;
	private String autor;

	public Task(long id, String name, String autor) {
		super();
		this.id = id;
		this.name = name;
		this.autor = autor;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAutor() {
		return autor;
	}

}
