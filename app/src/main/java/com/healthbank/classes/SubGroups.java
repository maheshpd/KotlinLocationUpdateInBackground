package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubGroups {
    int GrpId = 0;
    int TemplateId = 0;
    int deleted = 0;
    int Sortorder = 0;
    int SubgroupId = 0;
    String GroupName = "";
    ArrayList<Questions> Questions = new ArrayList<>();

    public  SubGroups() {

    }

    public  SubGroups(JSONObject obj) {
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
            if (obj.has("SubgroupId")) {
                this.SubgroupId = obj.getInt("SubgroupId");
            }
            if (obj.has("Sortorder")) {
                this.Sortorder = obj.getInt("Sortorder");
            }
            if (obj.has("GroupName")) {
                this.GroupName = obj.getString("GroupName");
            }

            if (obj.has("Questions")) {
                JSONObject questionobj = obj.optJSONObject("Questions");
                if (questionobj != null) {
                    Questions question = new Questions(questionobj);
                    Questions.add(question);
                } else {
                    JSONArray questionarray = obj.optJSONArray("Questions");
                    if (questionarray != null) {
                        for (int i = 0; i < questionarray.length(); i++) {
                            Questions question = new Questions(questionarray.getJSONObject(i));
                            Questions.add(question);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGrpId() {
        return GrpId;
    }

    public void setGrpId(int grpId) {
        GrpId = grpId;
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

    public int getSortorder() {
        return Sortorder;
    }

    public void setSortorder(int sortorder) {
        Sortorder = sortorder;
    }

    public int getSubgroupId() {
        return SubgroupId;
    }

    public void setSubgroupId(int subgroupId) {
        SubgroupId = subgroupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public ArrayList<com.healthbank.classes.Questions> getQuestions() {
        return Questions;
    }

    public void setQuestions(ArrayList<com.healthbank.classes.Questions> questions) {
        Questions = questions;
    }
}
