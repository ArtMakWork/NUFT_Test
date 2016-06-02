package com.artmakwork.nufttests.Utils;

import com.artmakwork.nufttests.POJO.Answer;
import com.artmakwork.nufttests.POJO.Exam;
import com.artmakwork.nufttests.POJO.Group;
import com.artmakwork.nufttests.POJO.MyTest;
import com.artmakwork.nufttests.POJO.Thema;
import com.artmakwork.nufttests.POJO.User;

import java.util.ArrayList;


public class UsedObjects {

    public static User user = new User( "0","Name","Surname","FatherName",000000,
                                        new Group(0,"GroupName",0,0),new Thema(0,"ThemaName"),
                                        new MyTest(0,"TestName"), 0, new ArrayList<String>());


    public static String jsonStringGroupsList = null;
    public static ArrayList<Group> arrayListGroup = new ArrayList<Group>();
    public static String [] massListGroup;

    public static String jsonStringThemaList = null;
    public static ArrayList<Thema> arrayListThema = new ArrayList<Thema>();
    public static String [] massListThema;

    public static String jsonStringTestList = null;
    public static ArrayList<MyTest> arrayListTest = new ArrayList<MyTest>();
    public static String [] massListTest;

    public static Exam myExam = new Exam();
    public static String jsonStringExamList = null;

    public static String jsonStringAnswerList = null;
    public static ArrayList<Answer> arrayListAnswer = new ArrayList<Answer>();

    public static String finalAnswer = "-1";
    public static int question_id;
    public static boolean multiChoise;





}
