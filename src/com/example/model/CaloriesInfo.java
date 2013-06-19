package com.example.model;

public class CaloriesInfo {
//	private static CaloriesInfo instance =  null;
	
	private int todayCalories;
	private int caloricNeeds;
	private int remainingCalories;
	
	public CaloriesInfo() {
		
		todayCalories = 0;
		caloricNeeds = 0;
		remainingCalories = 0;
	}
	
//	public static CaloriesInfo getInstance() {
//		if (instance == null) {
//			instance = new CaloriesInfo();
//		}
//		
//		return instance;
//	}

	public void setTodayCalories(int todayCalories) {
		this.todayCalories = todayCalories;
	}

	public void setCaloricNeeds(int caloricNeeds) {
		this.caloricNeeds = caloricNeeds;
	}

	public int getTodayCalories() {
		return todayCalories;
	}

	public int getCaloricNeeds() {
		return caloricNeeds;
	}

	public int getRemainingCalories() {
		return remainingCalories;
	}

	public void setRemainingCalories(int remainingCalories) {
		this.remainingCalories = remainingCalories;
	}

}
