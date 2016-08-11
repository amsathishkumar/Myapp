package com.sat.info;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper{
    final static String DBNAME="sathish.db";
    final static String TABLENAME="sat";
    final static String COL1="ID";
    final static String COL2="NAME";
    final static String COL3="AGE";
    final static String COL4="EMAIL";
	@SuppressLint("NewApi")
	public DBhelper(Context context) {
		super(context, DBNAME, null,1);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table "+TABLENAME +"("+COL1+" INTEGER, "+COL2+" TEXT, "+COL3+" INTEGER,"+ COL4+" TEXT)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS sat");
		// TODO Auto-generated method stub
		onCreate(db);
	}
	public boolean insertData(int id, String name,int age,String email){
		SQLiteDatabase db=this.getWritableDatabase();	
		ContentValues cv = new ContentValues();
		cv.put("id", id);
		cv.put("name", name);
		cv.put("age", age);
		cv.put("email", email);
		
		long result=db.insert(TABLENAME, null,cv);
		if (result==-1)
			return false;
		else
			return true;
	}
	
	public Cursor getallData()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res = db.rawQuery("select * from "+TABLENAME, null);
		return res;
	}
	
	public boolean updateData(int id, String name,int age, String email){
		SQLiteDatabase db=this.getWritableDatabase();	
		ContentValues cv = new ContentValues();
		cv.put("id", id);
		cv.put("name", name);
		cv.put("age", age);
		cv.put("email", email);
		long result=db.update(TABLENAME,cv,"ID = ?", new String[] {String.valueOf(id)});
		if (result==-1)
			return false;
		else
			return true;
	}
	
	public boolean deleteData(int id){
		SQLiteDatabase db=this.getWritableDatabase();	
		ContentValues cv = new ContentValues();
		cv.put("id", id);		
		long result=db.delete(TABLENAME,"ID = ?", new String[] {String.valueOf(id)});
		if (result==-1)
			return false;
		else
			return true;
	}

}
