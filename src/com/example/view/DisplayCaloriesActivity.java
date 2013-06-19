package com.example.view;

import com.example.utils.CaloriesDbAdapter;
import com.example.view.R;
import com.example.ctrl.CaloriesCtrlActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DisplayCaloriesActivity extends Activity{
		private CaloriesCtrlActivity caloriesCtrl;
		
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_display_calories);
			// Show the Up button in the action bar.
			setupActionBar();
			
			if (getApplicationContext() == null) {
				System.out.println("DisplayCaloriesActivity context == null");
			}
			CaloriesDbAdapter.getInstance(this);
			
			caloriesCtrl = new CaloriesCtrlActivity();
			
			int calories = 0;
			Intent intent = getIntent();
			calories = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

			//add calories to DB			
			try {
				caloriesCtrl.addCalories(calories);
			} catch (NullPointerException e) {
				System.out.println("DisplayCaloriesActivity/OnCreate null pointer exception");
			} catch (Exception ex) {
				System.out.println("DisplayCaloriesActivity/OnCreate exception");
			}
			
			TextView titleTextView = (TextView) findViewById(R.id.calories_management_textview);
			titleTextView.setTextSize(22);
			titleTextView.setTextColor(Color.rgb(45, 100, 180));
			titleTextView.setText("Calories management");
			
			TextView totalCaloriesTextView = (TextView) findViewById(R.id.total_calories_textview);
			totalCaloriesTextView.setTextSize(18);
			totalCaloriesTextView.setText("Consumed calories today: " + String.valueOf(caloriesCtrl.getTodayCalories()));	
			
			TextView remainingCaloriesTextView = (TextView) findViewById(R.id.remaining_calories_textview);
			remainingCaloriesTextView.setTextSize(18);
			remainingCaloriesTextView.setText("Remaining calories today: " + String.valueOf(caloriesCtrl.getRemainingCalories()));
			
			TextView targetCaloriesTextView = (TextView) findViewById(R.id.target_calories_textview);
			targetCaloriesTextView.setTextSize(18);
			targetCaloriesTextView.setText("From daily caloric needs: " + String.valueOf(caloriesCtrl.getCaloricNeeds()));
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
