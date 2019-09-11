package com.healthbank.classes;

import org.json.JSONObject;

import java.util.ArrayList;

public class SaveAnswerdata {
    String ColumnName = "";
    String Ans = "";
    String Qid = "";
    String OptionType = "";
    JSONObject XmlObj = new JSONObject();

    public SaveAnswerdata(){

    }

    public SaveAnswerdata(JSONObject obj){
        try{
            if(obj.has("ColumnName"))
                this.ColumnName=obj.getString("ColumnName");

            if(obj.has("Ans"))
                this.Ans=obj.getString("Ans");

            if(obj.has("Qid"))
                this.Qid=obj.getString("Qid");

            if(obj.has("OptionType"))
                this.OptionType=obj.getString("OptionType");

            if(obj.has("XmlObj"))
            {
                JSONObject obj1=obj.optJSONObject("XmlObj");
                if(obj1!=null)
                {
                    this.XmlObj=obj1;
                }else {
                    String s=obj.optString("XmlObj");
                    if(s!=null)
                        this.XmlObj=new JSONObject(s);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getAnswer(ArrayList<SaveAnswerdata> data, String id) {
        String ans = "";
        for (int i = 0; i < data.size(); i++) {
            if (id.equalsIgnoreCase(data.get(i).getQid()))
                return data.get(i).getAns();
        }
        return ans;
    }

    public static JSONObject getXMLobj(ArrayList<SaveAnswerdata> data, String id) {
        JSONObject xmlobj=new JSONObject();
        for (int i = 0; i < data.size(); i++) {
            if (id.equalsIgnoreCase(data.get(i).getQid()))
                return data.get(i).getXmlObj();
        }
        return xmlobj;
    }

    public JSONObject getXmlObj() {
        return XmlObj;
    }

    public void setXmlObj(JSONObject xmlObj) {
        XmlObj = xmlObj;
    }

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }

    public String getAns() {
        return Ans;
    }

    public void setAns(String ans) {
        Ans = ans;
    }

    public String getQid() {
        return Qid;
    }

    public void setQid(String qid) {
        Qid = qid;
    }

    public String getOptionType() {
        return OptionType;
    }

    public void setOptionType(String optionType) {
        OptionType = optionType;
    }
}
