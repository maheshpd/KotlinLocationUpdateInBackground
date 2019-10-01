package com.healthbank.classes;

import android.util.Log;

import com.healthbank.model.SelectedTest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Option {
    int OptionId = 0;
    int Qid = 0;
    int TemplateId = 0;
    int deleted = 0;
    int GrpId = 0;
    int Sortorder = 0;
    String OptionType = "";
    String OptionList = "";
    ArrayList<Questions> SubQuestions = new ArrayList<>();
    String OptionName = "";
    boolean isselected = false;
    String OptionValue = "";
    JSONObject XmlObj = new JSONObject();


    public Option() {

    }

    public Option(JSONObject obj) {
        Log.e("option parsing called ", "Option Parsing caled " + obj.toString());
        try {
            if (obj.has("OptionId")) {
                this.OptionId = obj.getInt("OptionId");

            }
            if (obj.has("Qid")) {
                this.Qid = obj.getInt("Qid");
            }

            if (obj.has("TemplateId")) {
                this.TemplateId = obj.getInt("TemplateId");
            }

            if (obj.has("deleted")) {
                this.deleted = obj.getInt("deleted");
            }

            if (obj.has("GrpId")) {
                this.GrpId = obj.getInt("GrpId");
            }

            if (obj.has("Sortorder")) {
                this.Sortorder = obj.getInt("Sortorder");
            }

            if (obj.has("OptionType")) {
                this.OptionType = obj.getString("OptionType");
            }

            if (obj.has("OptionList")) {
                this.OptionList = obj.getString("OptionList");
            }

            if (obj.has("OptionName")) {
                this.OptionName = obj.getString("OptionName");
                Log.d("Option: ",OptionName);
            }
            if (obj.has("OptionValue")) {
                this.OptionValue = obj.getString("OptionValue");
                Log.e("OptionValue", this.OptionValue);
            } else {
                Log.e("OptionValue not available ", this.OptionValue);

            }
            if (obj.has("isselected")) {
                this.isselected = obj.getBoolean("isselected");
            }

            if (obj.has("SubQuestions")) {
                JSONObject questionobj = obj.optJSONObject("SubQuestions");
                if (questionobj != null) {
                    Questions question = new Questions(questionobj);
                    SubQuestions.add(question);
                } else {
                    JSONArray questionarray = obj.optJSONArray("SubQuestions");
                    if (questionarray != null) {
                        for (int i = 0; i < questionarray.length(); i++) {
                            Questions question = new Questions(questionarray.getJSONObject(i));
                            SubQuestions.add(question);
                        }
                    }
                }
            }
          /* if(obj.has("XmlObj"))
           {
               this.XmlObj
           }*/
        } catch (Exception e) {
            Log.e("error opt", "error opt " + e.toString());
            e.printStackTrace();
        }
    }

    /*JSONObject XmlObj=new JSONObject();

    public JSONObject getXmlObj() {
        return XmlObj;
    }

    public void setXmlObj(JSONObject xmlObj) {
        XmlObj = xmlObj;
    }*/

    public JSONObject getXmlObj() {
        return XmlObj;
    }

    public void setXmlObj(JSONObject xmlObj) {
        XmlObj = xmlObj;
    }

    public int getOptionId() {
        return OptionId;
    }

    public void setOptionId(int optionId) {
        OptionId = optionId;
    }

    public int getQid() {
        return Qid;
    }

    public void setQid(int qid) {
        Qid = qid;
    }

    public int getTemplateId() {
        return TemplateId;
    }

    public void setTemplateId(int templateId) {
        TemplateId = templateId;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getGrpId() {
        return GrpId;
    }

    public void setGrpId(int grpId) {
        GrpId = grpId;
    }

    public int getSortorder() {
        return Sortorder;
    }

    public void setSortorder(int sortorder) {
        Sortorder = sortorder;
    }

    public String getOptionType() {
        return OptionType;
    }

    public void setOptionType(String optionType) {
        OptionType = optionType;
    }

    public String getOptionList() {
        return OptionList;
    }

    public void setOptionList(String optionList) {
        OptionList = optionList;
    }

    public ArrayList<Questions> getSubQuestions() {
        return SubQuestions;
    }

    public void setSubQuestions(ArrayList<Questions> subQuestions) {
        SubQuestions = subQuestions;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public String getOptionValue() {
        return OptionValue;
    }

    public void setOptionValue(String optionValue) {
        this.OptionValue = optionValue;
    }

    public String getOptionName() {
        return OptionName;
    }

    public void setOptionName(String optionName) {
        OptionName = optionName;
    }

}
