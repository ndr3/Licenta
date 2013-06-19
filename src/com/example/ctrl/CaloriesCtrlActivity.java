package com.example.ctrl;

import android.database.Cursor;
import com.example.model.CaloriesInfo;
import com.example.utils.CaloriesDbAdapter;

public class CaloriesCtrlActivity {
	private CaloriesInfo caloriesInfo;
	private CaloriesDbAdapter caloriesDbAdapter = null;
	
	public CaloriesCtrlActivity() {
		caloriesInfo = new CaloriesInfo(); 
		caloriesDbAdapter = CaloriesDbAdapter.getInstance(null);
		if (caloriesDbAdapter == null) 
			System.out.println("adapter null");
		
		//fill the model		
		try {
			caloriesDbAdapter.setCaloricNeeds(1800);
			caloriesDbAdapter.addCalories(150);
			caloriesDbAdapter.addCalories(400);
			
			
			
			Cursor caloricNeedsCursor = caloriesDbAdapter.fetchCaloricNeeds();
			caloricNeedsCursor.moveToFirst();			
			int caloricNeeds = caloricNeedsCursor.getInt(0);
			
			caloriesInfo.setCaloricNeeds(caloricNeeds);
			
			Cursor todayCaloriesCursor = caloriesDbAdapter.fetchTodayCalories();
			int consumedCalories = 0;		
			for (todayCaloriesCursor.moveToFirst(); !todayCaloriesCursor.isAfterLast(); todayCaloriesCursor.moveToNext()) {
				consumedCalories += todayCaloriesCursor.getInt(1);
			}
			
			caloriesInfo.setTodayCalories(consumedCalories);		
			caloriesInfo.setRemainingCalories(caloricNeeds - consumedCalories);
			
		} catch (Exception e) {			
			System.out.println("Ctrl/Constructor exception");
			e.printStackTrace();
		}
	}
	
	public void addCalories(int calories) {		
		try {
		int todayCalories = caloriesInfo.getTodayCalories();
	
		caloriesInfo.setTodayCalories(todayCalories + calories);
		caloriesDbAdapter.addCalories(calories);
		
		} catch(NullPointerException e) {
			System.out.println("Ctrl/addCalories null pointer exception");
		} catch (Exception ex) {
			System.out.println("Ctrl/getCalories exception");
		}
	}
	
	public int getTodayCalories() {
		return caloriesInfo.getTodayCalories();
	}
	
	public void setCaloricNeeds(int caloricNeeds) {
		caloriesInfo.setCaloricNeeds(caloricNeeds);
		caloriesDbAdapter.setCaloricNeeds(caloricNeeds);
	}
	
	public int getCaloricNeeds() {
		try {
			Cursor caloricNeedsCursor = caloriesDbAdapter.fetchCaloricNeeds();
			caloricNeedsCursor.moveToFirst();
			return caloricNeedsCursor.getInt(0);
		} catch (NullPointerException e) {
			System.out.println("Ctrl/getCaloricNeeds null pointer exception");
			return 0;
		} catch (Exception ex) {
			System.out.println("Ctrl/getCaloricNeeds exception");
			return 0;
		}
	}
	
	public int getRemainingCalories() {
		try {
			return caloriesInfo.getCaloricNeeds() - caloriesInfo.getTodayCalories();
		} catch (NullPointerException e) {
			System.out.println("Ctrl/getRemainingCalories null pointer exception");
			return 0;
		} catch (Exception ex) {
			System.out.println("Ctrl/getRemainingCalories exception");
			return 0;
		}
	}
}
