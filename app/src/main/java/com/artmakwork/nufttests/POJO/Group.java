package com.artmakwork.nufttests.POJO;


public class Group{

    private int groupId;
    private String fakultetName;
    private int kurs;
    private int groupNum;

    public Group(int groupId, String fakultetName, int kurs, int groupNum) {
        this.fakultetName = fakultetName;
        this.groupId = groupId;
        this.groupNum = groupNum;
        this.kurs = kurs;
    }

    @Override
    public String toString() {
        return fakultetName+" "+kurs+"-"+groupNum;
    }

    public String getFakultetName() {
        return fakultetName;
    }

    public void setFakultetName(String fakultetName) {
        this.fakultetName = fakultetName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getKurs() {
        return kurs;
    }

    public void setKurs(int kurs) {
        this.kurs = kurs;
    }
}
