package com.healthbank.classes;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class State {
	
	private final static String JSON_FILE = "state.txt";
	private final static String MAIN_CAT = "country";
	private final static String SUB_CAT = "state";
	private final static String[] OBJ_NAMES = new String[]{"countryid","name","stateid"};
	
	public static ArrayList<String> getArray(String countryid, Context context){
		ArrayList<StateClass> list = new ArrayList<StateClass>(getList(context));
		ArrayList<String> allstate = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			StateClass state = list.get(i);
			if(state.getCountryid().equals(countryid)){
				//Log.i("State Name", state.getName());
				allstate.add(state.getName());
			}
		}
		return allstate;
	}
	
	public static ArrayList<StateClass> getList(Context context){
		
		String mainjson = AssetUtils.readFile(context, JSON_FILE);
		ArrayList<StateClass> states = new ArrayList<StateClass>();
		try{
			JSONObject f_instance = new JSONObject(mainjson);
			JSONObject s_instance = f_instance.getJSONObject(MAIN_CAT);
			JSONArray f_array = s_instance.getJSONArray(SUB_CAT);
			//Log.i("States", f_array.length()+"");
			StateClass state;
			for(int i=0;i<f_array.length();i++){
				JSONObject t_instance = f_array.getJSONObject(i);
				state = new StateClass();
				state.setCountryid(t_instance.getString(OBJ_NAMES[0]));
				state.setName(t_instance.getString(OBJ_NAMES[1]));
				state.setStateid(t_instance.getString(OBJ_NAMES[2]));
				states.add(state);
			}
		}catch(Exception e){
		}
		return states;
	}
	public static ArrayAdapter<String> getAdapter(String countryid, Context context){
		return new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,getArray(countryid,context.getApplicationContext()));
	}
	
	public static String getid(String statename, Context context){
		ArrayList<StateClass> stateList = new ArrayList<StateClass>(getList(context));
		String stateid= null;
		for(int i=0;i<stateList.size();i++){
			if(stateList.get(i).getName().equals(statename)){
				stateid = stateList.get(i).getStateid();
				break;
			}
		}
		return stateid; 
	}
	
	public static String getName(String stateid, Context context){
		ArrayList<StateClass> stateList = new ArrayList<StateClass>(getList(context));
		String statename= "";
		for(int i=0;i<stateList.size();i++){
			if(stateList.get(i).getStateid().equals(stateid)){
				statename = stateList.get(i).getName();
				break;
			}
		}
		return statename; 
	}

}
