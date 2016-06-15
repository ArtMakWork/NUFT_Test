package com.artmakwork.nufttests.Utils;


import android.util.Log;

import com.artmakwork.nufttests.POJO.Answer;
import com.artmakwork.nufttests.POJO.Exam;
import com.artmakwork.nufttests.POJO.Group;
import com.artmakwork.nufttests.POJO.MyTest;
import com.artmakwork.nufttests.POJO.Questions;
import com.artmakwork.nufttests.POJO.Thema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyJSONParser {


    public static ArrayList<Group> jsonToGroupList(String jsonString) {

        Group myGroup = null;
        ArrayList groupList = new ArrayList();

        JSONObject jsonRootObject = null;
        try {
            jsonRootObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArrayMas = null;
        try {
            jsonArrayMas = jsonRootObject.getJSONArray("GroupList");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i<jsonArrayMas.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArrayMas.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                myGroup = new Group(jsonObject.getInt("Group_id"), jsonObject.getString("Fakultet"),
                        jsonObject.getInt("Kurs"), jsonObject.getInt("Group_num"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            groupList.add(myGroup);
        }


        return groupList;
    }

    public static ArrayList<Thema> jsonToThemeList(String jsonString) {

        Thema myThema = null;
        ArrayList groupList = new ArrayList();

        JSONObject jsonRootObject = null;
        try {
            jsonRootObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArrayMas = null;
        try {
            jsonArrayMas = jsonRootObject.getJSONArray("ThemeList");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i<jsonArrayMas.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArrayMas.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                myThema = new Thema(jsonObject.getInt("Theme_id"), jsonObject.getString("Theme"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            groupList.add(myThema);
        }

        return groupList;
    }

    public static ArrayList<MyTest> jsonToMyTestList(String jsonString) {

        MyTest myThema = null;
        ArrayList groupList = new ArrayList();

        JSONObject jsonRootObject = null;
        try {
            jsonRootObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArrayMas = null;
        try {
            jsonArrayMas = jsonRootObject.getJSONArray("TestList");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i<jsonArrayMas.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArrayMas.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                myThema = new MyTest(jsonObject.getInt("Test_id"), jsonObject.getString("Test_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            groupList.add(myThema);
        }

        return groupList;
    }

    public static Exam jsonToExam(String jsonString){

        Exam exam = null;
        List<Questions> questions = new ArrayList<Questions>();

        JSONObject jsonRootObject = null;
        try {
            jsonRootObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArrayMas = null;
        try {
            jsonArrayMas = jsonRootObject.getJSONArray("questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i<jsonArrayMas.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArrayMas.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                questions.add(new Questions( jsonObject.getString("answ_count") ,
                        jsonObject.getString("question")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            exam = new Exam(questions,jsonRootObject.getString("variant_num"),jsonRootObject.getLong("time_pass")*1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exam;
    }

    public static ArrayList<Answer> jsonToQuestionList(String jsonString){

        ArrayList<Answer> list = new ArrayList<>();

        JSONObject jsonRootObject = null;
        try {
            jsonRootObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArrayMas = null;
        try {
            jsonArrayMas = jsonRootObject.getJSONArray("answers");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArrayMas.length(); i++) {
            JSONArray jsonArr = null;
            try {
                jsonArr = jsonArrayMas.getJSONArray(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<String> array = new ArrayList<>();
            for (int j = 0; j < jsonArr.length();j++){
                String jo = null;
                try {
                    jo = jsonArr.getString(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.add(jo);
            }
            list.add(new Answer(i,array));
        }
        return list;
    }

}
