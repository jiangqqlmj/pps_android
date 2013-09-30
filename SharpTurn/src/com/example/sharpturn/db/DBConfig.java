package com.example.sharpturn.db;

public class DBConfig {
	public static final String DATEBASE_NAME = "sharp.db";
	public static final int DATEBASE_VERSION = 1;
	public static final String TABLE_NAME="sharp_model";
	public static final String CREATE_TABLE_SQL = "CREATE TABLE "+TABLE_NAME+" (id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name  TEXT(500),answer  TEXT(100))";
}
