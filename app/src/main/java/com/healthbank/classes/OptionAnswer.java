package com.healthbank.classes;

import android.util.Log;

import java.util.ArrayList;

public class OptionAnswer {
    String id = "";
    String ans = "";

    public OptionAnswer() {

    }

    public OptionAnswer(String id, String ans) {
        this.ans = ans;
        this.id = id;
        Log.e("id111111", "id " + id + "&&&&&&&&&& " + ans);
    }

    public static String getAnswer(ArrayList<OptionAnswer> data, String id) {
        String ans = "";
        Log.e("datattttt", "size " + data.size() + " " + id);
        for (int i = 0; i < data.size(); i++) {
            Log.e("datattttt", "in if" + data.get(i).getId() + "~ " + id);
            if (id.equalsIgnoreCase(data.get(i).getId()))
                return data.get(i).getAns();
        }
        return ans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

}
