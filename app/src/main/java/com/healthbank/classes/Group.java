package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Group {
    int GrpId = 0;
    int TemplateId = 0;
    int deleted = 0;
    int Sortorder = 0;
    int SubgroupId = 0;
    int DoctorId = 0;
    int AssistantId = 0;
    String GroupName = "";
    String Layout = "";
    ArrayList<SubGroups> SubGroups = new ArrayList<>();


    public Group() {

    }

    public Group(JSONObject obj) {
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
            if (obj.has("DoctorId")) {
                this.DoctorId = obj.getInt("DoctorId");
            }
            if (obj.has("AssistantId")) {
                this.AssistantId = obj.getInt("AssistantId");
            }
            if (obj.has("GroupName")) {
                this.GroupName = obj.getString("GroupName");
            }

            if (obj.has("Layout")) {
                this.Layout = obj.getString("Layout");
            }

            if (obj.has("SubGroups")) {
                JSONObject subgroupobj = obj.optJSONObject("SubGroups");
                if (subgroupobj != null) {
                    SubGroups question = new SubGroups(subgroupobj);
                    SubGroups.add(question);
                } else {
                    JSONArray subarray = obj.optJSONArray("SubGroups");
                    if (subarray != null) {
                        for (int i = 0; i < subarray.length(); i++) {
                            SubGroups question = new SubGroups(subarray.getJSONObject(i));
                            SubGroups.add(question);
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

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public int getAssistantId() {
        return AssistantId;
    }

    public void setAssistantId(int assistantId) {
        AssistantId = assistantId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getLayout() {
        return Layout;
    }

    public void setLayout(String layout) {
        Layout = layout;
    }

    public ArrayList<com.healthbank.classes.SubGroups> getSubGroups() {
        return SubGroups;
    }

    public void setSubGroups(ArrayList<com.healthbank.classes.SubGroups> subGroups) {
        SubGroups = subGroups;
    }
}
