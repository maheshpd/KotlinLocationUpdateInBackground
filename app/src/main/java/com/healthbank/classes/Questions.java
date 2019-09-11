package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Questions {
    int Qid = 0;
    int TemplateId = 0;
    int deleted = 0;
    int GrpId = 0;
    int IsCompulsory = 0;
    int Sortorder = 0;
    int IsDependent = 0;
    int ParentId = 0;
    String Name = "";
    String Answer = "";
    String OptionType = "";
    ArrayList<Option> Option = new ArrayList<>();
    ArrayList<Option> suboption = new ArrayList<>();
    String Layout = "";

    public Questions() {

    }

    public Questions(JSONObject obj) {
        try {
            if (obj.has("GrpId")) {
                this.GrpId = obj.getInt("GrpId");
            }

            if (obj.has("TemplateId")) {
                this.TemplateId = obj.getInt("TemplateId");
            }

            if (obj.has("deleted")) {
                this.deleted = obj.getInt("deleted");
            }
            if (obj.has("Qid")) {
                this.Qid = obj.getInt("Qid");
            }
            if (obj.has("Sortorder")) {
                this.Sortorder = obj.getInt("Sortorder");
            }
            if (obj.has("IsCompulsory")) {
                this.IsCompulsory = obj.getInt("IsCompulsory");
            }
            if (obj.has("IsDependent")) {
                this.IsDependent = obj.getInt("IsDependent");
            }
            if (obj.has("ParentId")) {
                this.ParentId = obj.getInt("ParentId");
            }

            if (obj.has("Name")) {
                this.Name = obj.getString("Name");
            }
            if (obj.has("Layout")) {
                this.Layout = obj.getString("Layout");
            }

            if (obj.has("Option")) {
                JSONObject optobj = obj.optJSONObject("Option");
                if (optobj != null) {
                    /*Gson gson = new Gson();
                    Type type = new TypeToken<Option>() {
                    }.getType();
                    Option opt = gson.fromJson(optobj.toString(), type);*/

                    Option opt = new Option(optobj);
                    Option.add(opt);
                } else {
                    JSONArray questionarray = obj.optJSONArray("Option");
                    if (questionarray != null) {
                     /*   Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Option>>() {
                        }.getType();
                        ArrayList<Option> optdata = gson.fromJson(questionarray.toString(), type);
                        if (optdata != null)
                            Option.addAll(optdata);*/
                        for (int i = 0; i < questionarray.length(); i++) {
                            Option opt = new Option(questionarray.getJSONObject(i));
                            Option.add(opt);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<com.healthbank.classes.Option> getSuboption() {
        return suboption;
    }

    public void setSuboption(ArrayList<com.healthbank.classes.Option> suboption) {
        this.suboption = suboption;
    }

    public String getLayout() {
        return Layout;
    }

    public void setLayout(String layout) {
        Layout = layout;
    }

    public String getOptionType() {
        return OptionType;
    }

    public void setOptionType(String optionType) {
        OptionType = optionType;
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

    public int getIsCompulsory() {
        return IsCompulsory;
    }

    public void setIsCompulsory(int isCompulsory) {
        IsCompulsory = isCompulsory;
    }

    public int getSortorder() {
        return Sortorder;
    }

    public void setSortorder(int sortorder) {
        Sortorder = sortorder;
    }

    public int getIsDependent() {
        return IsDependent;
    }

    public void setIsDependent(int isDependent) {
        IsDependent = isDependent;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<com.healthbank.classes.Option> getOption() {
        return this.Option;
    }

    public void setOption(ArrayList<com.healthbank.classes.Option> option) {
        this.Option = option;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
