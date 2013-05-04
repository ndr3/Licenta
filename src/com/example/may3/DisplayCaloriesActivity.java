package com.example.may3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DisplayCaloriesActivity extends Activity {
	private CaloriesDbAdapter dbHelper;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_calories);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		int message = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

		//add calories to DB
		dbHelper.addCalories(message); 
		
		//get all calories and compute the total sum
		Cursor c = dbHelper.fetchAllNotes();
		startManagingCursor(c);
		String[] from = new String[] { CaloriesDbAdapter.KEY_CALORIES};
		int sum = 0;
		for (String s : from) {
			sum += Integer.parseInt(s); 
		}		
		
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText("Today: " + String.valueOf(sum));
		
		setContentView(textView);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_calories, menu);
		return true;
	}

}