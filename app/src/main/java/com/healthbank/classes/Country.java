package com.healthbank.classes;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Country {

    public final static String DEF_COUNTRY = "India";
    public final static String DEF_PROGRAM = "Affilate";
    private final static String JSON_FILE = "country.txt";
    private final static String MAIN_CAT = "country";
    private final static String SUB_CAT = "country";
    private final static String[] OBJ_NAMES = new String[]{"id", "name"};

    public static ArrayList<CountryClass> getCountryList(Context context) {
        String mainjson = AssetUtils.readFile(context, JSON_FILE);
        ArrayList<CountryClass> countries = new ArrayList<CountryClass>();
        try {
            JSONObject f_instance = new JSONObject(mainjson);
            JSONObject s_instance = f_instance.getJSONObject(MAIN_CAT);
            JSONArray f_array = s_instance.getJSONArray(SUB_CAT);
            CountryClass country;
            for (int i = 0; i < f_array.length(); i++) {
                JSONObject t_instance = f_array.getJSONObject(i);
                country = new CountryClass();
                country.setId(t_instance.getString(OBJ_NAMES[0]));
                country.setName(t_instance.getString(OBJ_NAMES[1]));
                countries.add(country);
            }
        } catch (Exception e) {
        }
        return countries;
    }

    public static ArrayList<String> getCountryArray(Context context) {
        ArrayList<CountryClass> countryList = new ArrayList<CountryClass>(getCountryList(context));
        ArrayList<String> countries = new ArrayList<String>();
        for (int i = 0; i < countryList.size(); i++) {
            CountryClass con = countryList.get(i);
            countries.add(con.getName());
        }
        return countries;
    }

    public static ArrayAdapter<String> getAdapter(Context context) {
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, getCountryArray(context.getApplicationContext()));
    }

    public static String getid(String countryname, Context context) {
        ArrayList<CountryClass> countryList = new ArrayList<CountryClass>(getCountryList(context));
        String countryid = null;
        for (int i = 0; i < countryList.size(); i++) {
            if (countryList.get(i).getName().equals(countryname)) {
                countryid = countryList.get(i).getId();
                break;
            }
        }
        return countryid;
    }


}
