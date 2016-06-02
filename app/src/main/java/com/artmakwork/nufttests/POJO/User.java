package com.artmakwork.nufttests.POJO;

import java.util.ArrayList;

public class User {

    private String  user_id;
    private String  user_name;
    private String  user_surname;
    private String  user_fatherName;
    private long    user_zalik;
    private Group   user_group;
    private Thema   user_thema;
    private MyTest  user_test;
    private int     user_var_test;
    private ArrayList<String> user_answerList = new ArrayList<String>();

    public User(String user_id, String user_name, String user_surname, String user_fatherName, long user_zalik,
                Group user_group, Thema user_thema, MyTest user_test, int user_var_test, ArrayList<String> user_answerList) {

        this.user_id = user_id;
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_fatherName = user_fatherName;
        this.user_zalik = user_zalik;
        this.user_group = user_group;
        this.user_thema = user_thema;
        this.user_test = user_test;
        this.user_var_test = user_var_test;
        this.user_answerList = user_answerList;

    }



    @Override
    public String toString() {
        return  user_id+"|"+
                user_name +"|"+
                user_surname +"|"+
                user_fatherName +"|"+
                user_zalik +"|"+
                user_group.toString()+"|"+
                user_thema.toString()+"|"+
                user_test.toString()+"|"+
                user_var_test+"|"+
                user_answerList.toString();
    }

    public Group getUser_group() {
        return user_group;
    }

    public void setUser_group(Group user_group) {
        this.user_group = user_group;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public long getUser_zalik() {
        return user_zalik;
    }

    public void setUser_zalik(long user_zalik) {
        this.user_zalik = user_zalik;
    }

    public ArrayList<String> getUser_answerList() {
        return user_answerList;
    }

    public void setUser_answerList(ArrayList<String> user_answerList) {
        this.user_answerList = user_answerList;
    }

    public MyTest getUser_test() {
        return user_test;
    }

    public void setUser_test(MyTest user_test) {
        this.user_test = user_test;
    }

    public Thema getUser_thema() {
        return user_thema;
    }

    public void setUser_thema(Thema user_thema) {
        this.user_thema = user_thema;
    }

    public String getUser_fatherName() {
        return user_fatherName;
    }

    public void setUser_fatherName(String user_fatherName) {
        this.user_fatherName = user_fatherName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getUser_var_test() {
        return user_var_test;
    }

    public void setUser_var_test(int user_var_test) {
        this.user_var_test = user_var_test;
    }
}
