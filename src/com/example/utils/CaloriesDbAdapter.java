package com.example.utils;

import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class CaloriesDbAdapter extends Activity {
	private static CaloriesDbAdapter instance = null;
	
	private static final String KEY_ROWID = "_id";
	private static final String KEY_CALORIES = "calories_no";
	private static final String KEY_CALORIC_NEED = "caloric_need";
	private static final String KEY_EATEN_DATE = "eaten_date";
	
	private static final String TAG = "CaloriesDbAdapter";
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	private static final String CREATE_CALORIES_TABLE = "CREATE TABLE IF NOT EXISTS calories (_id integer primary key autoincrement, calories_no integer not null, eaten_date long )";
	private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (_id integer primary key autoincrement,  caloric_need integer)";
	
	private static final String DATABASE_NAME = "CaloriesDB";
	private static final String DATABASE_CALORIES_TABLE = "calories";
	private static final String DATABASE_USERS_TABLE = "users";
	private static final int DATABASE_VERSION = 2;
	
	private final Context ctx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static DatabaseHelper mInstance = null;
		
		public static DatabaseHelper getInstance(Context ctx) {
			if (mInstance == null) {
				mInstance = new DatabaseHelper(ctx.getApplicationContext());
			}		
			return mInstance;
		}
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.beginTransaction();
			try {
				db.execSQL(CREATE_CALORIES_TABLE);
				db.execSQL(CREATE_USERS_TABLE);
				
				db.setTransactionSuccessful();
			}finally {
				db.endTransaction();
			}
		}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS calories");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
	}
	
	public static CaloriesDbAdapter getInstance(Context ctx) {
		if ((instance == null) && (ctx != null)) {
			instance = new CaloriesDbAdapter(ctx);
			instance.open();
		}
		
		return instance;
	}
	
	public CaloriesDbAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	
	public CaloriesDbAdapter open() throws SQLException {
		try {
			dbHelper = DatabaseHelper.getInstance(ctx);					
			db = dbHelper.getWritableDatabase();
			
			if (db == null) {
				System.out.println("db == null");
			}
			return this;
		} catch (NullPointerException e) {
			System.out.println("CaloriesDbAdapter/Open null pointer exception");
			return null;
		} catch (Exception ex) {
			System.out.println("CaloriesDbAdapter/Open exception");
			return null;
		} 
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long addCalories(int calories) {
		ContentValues values = new ContentValues();
		Calendar rightNow = Calendar.getInstance();
		long timestamp = rightNow.getTimeInMillis();
		values.put(KEY_EATEN_DATE, timestamp);
		values.put(KEY_CALORIES, calories);
		
		return db.insert(DATABASE_CALORIES_TABLE, null, values);
	}
	
	@SuppressWarnings("deprecation")
	public Cursor fetchTodayCalories() {	
		//compute the miliseconds that passed since yesterday
		Date d = new Date();
		int hours = d.getHours() + 3;
		int minutes = d.getMinutes();
		int seconds = d.getSeconds();
		
		long todayPassedMilisecs = hours*60*60*1000 + minutes*60*1000 + seconds*1000;
		
		Calendar rightNow = Calendar.getInstance();
		long timestamp = rightNow.getTimeInMillis();
		long yesterday = timestamp - todayPassedMilisecs; 		
		
		//select only calories eaten today
		return db.rawQuery("SELECT * FROM calories WHERE eaten_date > ?", new String[] {String.valueOf(yesterday)});
	}
	
	public Cursor fetchAllCalories() {
		return db.query(DATABASE_CALORIES_TABLE, new String[] {KEY_ROWID, KEY_CALORIES}, null, null, null, null, null);
	}
	
	public long setCaloricNeeds(int caloricNeeds) {
		String strFilter = "_id=1";
		ContentValues values = new ContentValues();
		values.put(KEY_CALORIC_NEED, caloricNeeds);
		
		Cursor c = db.rawQuery("SELECT COUNT(*) FROM users", null);
		if (c != null) {
			c.moveToFirst();
			if (c.getInt(0) == 0) {
				//table is empty -> insert values
				return db.insert(DATABASE_USERS_TABLE, null, values);
			} else { //update values
				return db.update(DATABASE_USERS_TABLE, values, strFilter, null);
			}			
		}
		
		return 0;
	}

	public Cursor fetchCaloricNeeds() {
		return db.query(DATABASE_USERS_TABLE, new String[] {KEY_CALORIC_NEED}, null, null, null, null, null);
	}

	public void deleteCaloriesTable() {
		db.execSQL("drop table calories");
	}

}

