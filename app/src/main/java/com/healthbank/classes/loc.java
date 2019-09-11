package com.healthbank.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class loc {
    String CustomerId = "";
    String Name = "";
    String parentBranchId = "";
    ArrayList<Department> maindept = new ArrayList<>();

    public loc(JSONObject obj) {
        try {
            if (obj.has("CustomerId"))
                this.CustomerId = obj.getString("CustomerId");

            if (obj.has("Name"))
                this.Name = obj.getString("Name");

            if (obj.has("parentBranchId"))
                this.parentBranchId = obj.getString("parentBranchId");

            if (obj.has("maindept")) {
                JSONObject objloc = obj.optJSONObject("maindept");
                if (objloc != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Department>() {
                    }.getType();
                    Department d = gson.fromJson(objloc.toString(), type);
                    this.maindept.add(d);
                } else {
                    JSONArray array = obj.optJSONArray("maindept");
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Department>>() {
                    }.getType();
                    ArrayList<Department> d = gson.fromJson(array.toString(), type);
                    this.maindept.addAll(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getParentBranchId() {
        return parentBranchId;
    }

    public void setParentBranchId(String parentBranchId) {
        this.parentBranchId = parentBranchId;
    }

    public ArrayList<Department> getMaindept() {
        return maindept;
    }

    public void setMaindept(ArrayList<Department> maindept) {
        this.maindept = maindept;
    }

    @Override
    public String toString() {
        return Name;
    }
}
