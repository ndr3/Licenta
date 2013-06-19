package com.example.view;

import com.example.utils.CaloriesDbAdapter;
import com.example.view.R;
import com.example.ctrl.CaloriesCtrlActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SetDailyCaloricNeedsActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.view.CALORIC_NEEDS";
	
	private CaloriesCtrlActivity caloriesCtrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_daily_caloric_needs);
		
		if (getApplicationContext() == null) {
			System.out.println("SetDailyCaloricNeedsActivity context == null");
		}
		CaloriesDbAdapter.getInstance(this);
		
		caloriesCtrl = new CaloriesCtrlActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_daily_caloric_needs, menu);
		return true;
	}
	
	public void computeDailyCaloricNeeds(View view) {
		EditText editText = (EditText) findViewById(R.id.insert_weight_edittext);
		int weight = Integer.parseInt(editText.getText().toString());
		
		editText = (EditText) findViewById(R.id.insert_height_edittext);
		int height = Integer.parseInt(editText.getText().toString());
		
		editText = (EditText) findViewById(R.id.insert_age_edittext);
		int age = Integer.parseInt(editText.getText().toString());
		
		//convert to pounds and inches
		weight *= 2.2046;
		height /= 2.54;
		
		RadioGroup radioGenderGroup = (RadioGroup) findViewById(R.id.gender_group);
		int selectedID = radioGenderGroup.getCheckedRadioButtonId();
		
		int bmr;
		if (selectedID == R.id.radio_female) {
			bmr = (int) (655 + 4.3*weight + 4.7*height - 4.7*age);
		} else {
			bmr = (int) (66 + 6.3*weight + 12.9*height - 6.8*age);
		}
		
		RadioGroup radioActivityGroup = (RadioGroup) findViewById(R.id.activity_group);
		selectedID = radioActivityGroup.getCheckedRadioButtonId();
		
		int caloricNeeds;
		switch (selectedID) {
			case R.id.radio_sedentary:
				caloricNeeds = bmr + bmr/5;
				break;
			case R.id.radio_lightly_active:
				caloricNeeds = bmr + 3*bmr/10;
				break;
			case R.id.radio_moderately_active:
				caloricNeeds = bmr + 2*bmr/5;
				break;
			case R.id.radio_very_active:
				caloricNeeds = bmr + bmr/2;
				break;
			case R.id.radio_extra_active:
				caloricNeeds = bmr + 3*bmr/5;
				break;
			default: caloricNeeds = 0;				
		}
		
		caloriesCtrl.setCaloricNeeds(caloricNeeds);
		
		Intent intent = new Intent(this, DisplayCaloricNeedsActivity.class);
		intent.putExtra(EXTRA_MESSAGE, String.valueOf(caloricNeeds));
		startActivity(intent);
	}
}
