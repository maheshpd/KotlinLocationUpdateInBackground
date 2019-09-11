package com.healthbank.classes;


import com.healthbank.R;

/**
 * Created by it1 on 5/31/2018.
 */

public class HomeData {
    String Name = "";
    int id = R.drawable.icon_medicinebox;
    int backgroundcolor = R.color.blue;
    String Visiblename = "";
    String speciality = "";
    String template_group = "";
    String sort_order = "";
    String istemplate = "";
    String url = "";

    public HomeData() {

    }

    public HomeData(String name, int id) {
        this.id = id;
        this.Name = name;
    }

    public HomeData(String name, int id, int color) {
        this.id = id;
        this.Visiblename = name;
        this.template_group = name;
        this.backgroundcolor = color;
    }

    public String getVisiblename() {
        return Visiblename;
    }

    public void setVisiblename(String visiblename) {
        Visiblename = visiblename;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getTemplate_group() {
        return template_group;
    }

    public void setTemplate_group(String template_group) {
        this.template_group = template_group;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getIstemplate() {
        return istemplate;
    }

    public void setIstemplate(String istemplate) {
        this.istemplate = istemplate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(int backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
}
