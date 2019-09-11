package com.healthbank.classes;

import java.util.ArrayList;

public class StateClass {
	
	private String countryid;
	private String name;
	private String stateid;
	private String StateName;
	private ArrayList<CityClass> c;
	public String getCountryid() {
		return countryid;
	}
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStateid() {
		return stateid;
	}
	public void setStateid(String stateid) {
		this.stateid = stateid;
	}
	public String getStateName() {
		return StateName;
	}
	public void setStateName(String stateName) {
		StateName = stateName;
	}
	public ArrayList<CityClass> getC() {
		return c;
	}
	public void setC(ArrayList<CityClass> c) {
		this.c = c;
	}

}
