package com.pps.sharpturn.db;

public class DBConfig {
	public static final String DATEBASE_NAME = "sharp.db";
	public static final int DATEBASE_VERSION = 1;
	public static final String TABLE_NAME="sharp_model";
	public static final String TABLE_BOOK_NAME="sharp_book_model";
	public static final String CREATE_TABLE_SQL = "CREATE TABLE "+TABLE_NAME+" (id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name  TEXT(500),answer  TEXT(100))";
    public static final String CREATE_BOOK_TABLE_SQL="CREATE TABLE "+TABLE_BOOK_NAME+" (id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name  TEXT(500),answer  TEXT(100))";
}
