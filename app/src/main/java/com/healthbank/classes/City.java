package com.healthbank.classes;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class City {
    private final static String JSON_FILE = "city.txt";
    private final static String MAIN_CAT = "state";
    private final static String SUB_CAT = "city";
    private final static String[] OBJ_NAMES = new String[]{"stateid", "name", "cityid"};

    public static ArrayList<String> getArray(String stateid, Context context) {
        ArrayList<CityClass> list = new ArrayList<CityClass>(getList(context));
        ArrayList<String> allcities = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            CityClass city = list.get(i);
            if (city.getStateid().equals(stateid)) {
                //Log.i("City Name", city.getName());
                allcities.add(city.getName());
            }
        }
        return allcities;
    }

    public static ArrayList<CityClass> getList(Context context) {
        String mainjson = AssetUtils.readFile(context, JSON_FILE);
        ArrayList<CityClass> cities = new ArrayList<CityClass>();
        try {
            JSONObject f_instance = new JSONObject(mainjson);
            JSONObject s_instance = f_instance.getJSONObject(MAIN_CAT);
            JSONArray f_array = s_instance.getJSONArray(SUB_CAT);
            //Log.i("Cities", f_array.length()+"");
            CityClass city;
            for (int i = 0; i < f_array.length(); i++) {
                JSONObject t_instance = f_array.getJSONObject(i);
                city = new CityClass();
                city.setStateid(t_instance.getString(OBJ_NAMES[0]));
                city.setName(t_instance.getString(OBJ_NAMES[1]));
                city.setCityid(t_instance.getString(OBJ_NAMES[2]));
                cities.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static ArrayAdapter<String> getAdapter(String stateid, Context context) {
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, getArray(stateid, context.getApplicationContext()));
    }

    public static String getid(String cityname, Context context) {
        ArrayList<CityClass> cityList = new ArrayList<CityClass>(getList(context));
        String cityid = "-1";
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getName().equals(cityname)) {
                cityid = cityList.get(i).getCityid();
                break;
            }
        }
        return cityid;
    }

    public static String getName(String cityid, Context context) {
        ArrayList<CityClass> cityList = new ArrayList<CityClass>(getList(context));
        String cityName = "";
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getCityid().equals(cityid)) {
                cityName = cityList.get(i).getName();
                break;
            }
        }
        return cityName;
    }

}
