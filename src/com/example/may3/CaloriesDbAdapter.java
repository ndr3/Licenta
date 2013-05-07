package com.example.may3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CaloriesDbAdapter extends Activity {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CALORIES = "calories_no";
	
	private static final String TAG = "CaloriesDbAdapter";
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	private static final String DATABASE_CREATE = 
			"create table calories (_id integer primary key autoincrement, "
			+ "calories_no integer not null )";
	
	private static final String DATABASE_NAME = "data";
	private static final String DATABASE_TABLE = "calories";
	private static final int DATABASE_VERSION = 2;
	
	private final Context ctx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);			
		}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
	}
	
	public CaloriesDbAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	public CaloriesDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(ctx);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long addCalories(int calories) {
		ContentValues values = new ContentValues();
		values.put(KEY_CALORIES, calories);
		
		return db.insert(DATABASE_TABLE, null, values);
	}
	
	public Cursor fetchAllCalories() {
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_CALORIES}, null, null, null, null, null);
	}

	public void deleteCaloriesTable() {
		db.execSQL("delete from calories");
	}
}
