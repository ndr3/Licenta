package com.example.may3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SetDailyCaloricNeedActivity extends Activity {
	private CaloriesDbAdapter dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_daily_caloric_need);
		
		dbHelper = new CaloriesDbAdapter(this);
		dbHelper.open();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_daily_caloric_need, menu);
		return true;
	}
	
	public void saveUserData(View view) {
		EditText editText = (EditText) findViewById(R.id.insert_weight_edittext);
		int weight = Integer.parseInt(editText.getText().toString());
		
		editText = (EditText) findViewById(R.id.insert_height_edittext);
		int height = Integer.parseInt(editText.getText().toString());
		
		editText = (EditText) findViewById(R.id.insert_age_edittext);
		int age = Integer.parseInt(editText.getText().toString());
		
		//convert to pounds and inches
		weight *= 2.2046;
		height /= 2.54;
		
		int bmr = (int) (655 + 4.3*weight + 4.7*height - 4.7*age);
		dbHelper.setCaloricNeed(bmr);
		
	}
}
