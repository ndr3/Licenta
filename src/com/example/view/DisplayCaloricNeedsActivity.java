package com.example.view;

import com.example.view.R;
import com.example.ctrl.CaloriesCtrlActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DisplayCaloricNeedsActivity extends Activity {
	CaloriesCtrlActivity caloriesCtrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_caloric_needs);
		
		caloriesCtrl = new CaloriesCtrlActivity();
		
		String caloricNeeds = String.valueOf(caloriesCtrl.getCaloricNeeds());
		
		TextView textView = (TextView) findViewById(R.id.caloric_needs_textview);
		textView.setTextSize(17);
		textView.setTextColor(Color.rgb(45, 0, 180));
		textView.setText("Your caloric needs are " + caloricNeeds + " per day!");
		
		textView = (TextView) findViewById(R.id.description_textview);
		textView.setTextSize(16);
		textView.setTextColor(Color.rgb(45, 50, 9));
		textView.setText("This is the number of calories you can eat every day and maintain your current weight. \n\n"
				+ "In order to lose weight, you must create a calorie deficit. It is easier and healthier to cut back your calorie intake a little bit at a time. \n\n"
				+ "Please check with your doctor before significantly changing your diet or starting a new exercise regime. \n\n");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_caloric_needs, menu);
		return true;
	}

}
