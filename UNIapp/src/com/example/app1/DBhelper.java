package com.example.app1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper{
    final static String DBNAME="sathish.db";
	
	@SuppressLint("NewApi")
	public DBhelper(Context context) {
		super(context, DBNAME, null,1);
SQLiteDatabase db=this.getWritableDatabase();		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table sat (ID INTEGER, NAME TEXT)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS sat");
		// TODO Auto-generated method stub
		onCreate(db);
	}

}
