package com.example.hellonotes;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "notess";
	public static final String CONTENT = "content";
	public static final String PATH = "path";
	public static final String VIDEO = "video";
	public static final String ID = "_id";
	public static final String TIME = "time";

	public NotesDB(Context context) {
		super(context, TABLE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
				+ " TEXT NOT NULL," + PATH + " TEXT NOT NULL," + VIDEO
				+ " TEXT NOT NULL," + TIME + " TEXT NOT NULL)";
//		db.execSQL("DROP TABLE notes");
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
