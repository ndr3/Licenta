package com.example.may3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
		
		dbHelper = new CaloriesDbAdapter(this);
		dbHelper.open();
		
		Intent intent = getIntent();
		int message = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

		//add calories to DB
		dbHelper.addCalories(message); 
		
		//get all calories and compute the total sum
		Cursor c = dbHelper.fetchTodayCalories();
		int consumedCalories = 0;
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			consumedCalories += c.getInt(1);
		}

		TextView titleTextView = (TextView) findViewById(R.id.calories_management_textview);
		titleTextView.setTextSize(22);
		titleTextView.setTextColor(Color.rgb(45, 100, 180));
		titleTextView.setText("Calories management");
		
		TextView totalCaloriesTextView = (TextView) findViewById(R.id.total_calories_textview);
		totalCaloriesTextView.setTextSize(18);
		totalCaloriesTextView.setText("Consumed calories today: " + String.valueOf(consumedCalories));	
		
		Cursor dailyCaloricNeedsCursor = dbHelper.caloricNeed();
		dailyCaloricNeedsCursor.moveToFirst();
		int caloricNeeds = dailyCaloricNeedsCursor.getInt(0);
		
		TextView remainingCaloriesTextView = (TextView) findViewById(R.id.remaining_calories_textview);
		remainingCaloriesTextView.setTextSize(18);
		remainingCaloriesTextView.setText("Remaining calories today: " + String.valueOf(caloricNeeds - consumedCalories));
		
		TextView targetCaloriesTextView = (TextView) findViewById(R.id.target_calories_textview);
		targetCaloriesTextView.setTextSize(18);
		targetCaloriesTextView.setText("From daily caloric needs: " + String.valueOf(caloricNeeds));
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
