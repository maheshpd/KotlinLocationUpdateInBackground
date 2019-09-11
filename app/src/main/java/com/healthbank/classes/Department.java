package com.healthbank.classes;

public class Department {
    String Id = "";
    String Name = "";
    String ParentId = "";

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    @Override
    public String toString() {
        return Name;
    }
}

