package com.artmakwork.nufttests.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.artmakwork.nufttests.Utils.MyJSONParser;
import com.artmakwork.nufttests.Utils.UsedObjects;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DA_GetTestFromServer extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get Exams From SERVER
        try {
            UsedObjects.jsonStringExamList = new getExamsFromServer().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //parse server answer
        UsedObjects.myExam = MyJSONParser.jsonToExam(UsedObjects.jsonStringExamList);
        UsedObjects.user.setUser_var_test(Integer.parseInt(UsedObjects.myExam.getVariant_num()));


        //get Answers From SERVER
        try {
            UsedObjects.jsonStringAnswerList = new getAnswerFromServer().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Парсить ансвер
        UsedObjects.arrayListAnswer = MyJSONParser.jsonToQuestionList(UsedObjects.jsonStringAnswerList);

        if(UsedObjects.user.getUser_answerList().size()<UsedObjects.myExam.getQuestions().size()){
            UsedObjects.user.getUser_answerList().clear();
            for (int i = 0;i<UsedObjects.myExam.getQuestions().size();i++){
                UsedObjects.user.getUser_answerList().add(i, "-1");
            }
        }

        Log.d("419", "user.getUser_answerList() onCreate = " + UsedObjects.user.getUser_answerList().toString());



        if(UsedObjects.arrayListAnswer.size()!=0){
            Intent intent = new Intent(getApplicationContext(), E_PassTestActivity.class);
            startActivity(intent);
            finish();
            super.onCreate(savedInstanceState);
        }else{
            Intent intent = new Intent(getApplicationContext(), DA_GetTestFromServer.class);
            startActivity(intent);
            finish();
            super.onCreate(savedInstanceState);
        }



    }

    class getExamsFromServer extends AsyncTask<Void, String, String> {

        int responseCode = 0;
        String myjsonString;

        @Override
        protected String doInBackground(Void... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act", "GetQuestionListByTestId")
                    .add("test_id", String.valueOf(UsedObjects.user.getUser_test().getTest_id()))
                    .build();

            Request request = new Request.Builder()
                    .url(UsedObjects.SERVER)
                    .post(formBody)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if ((responseCode = response.code()) == 200) {
                try {
                    myjsonString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (!response.isSuccessful()) try {
                throw new IOException("Unexpected code " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return myjsonString;
        }
    }

    class getAnswerFromServer extends AsyncTask<Void, String, String> {

        int responseCode = 0;
        String myjsonString;

        @Override
        protected String doInBackground(Void... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act", "GetAnswersByTestIdAndVariantNum")
                    .add("test_id", String.valueOf(UsedObjects.user.getUser_test().getTest_id()))
                    .add("variant_num", String.valueOf(UsedObjects.myExam.getVariant_num()))
                    .build();

            Request request = new Request.Builder()
                    .url(UsedObjects.SERVER)
                    .post(formBody)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if ((responseCode = response.code()) == 200) {
                try {
                    myjsonString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (!response.isSuccessful()) try {
                throw new IOException("Unexpected code " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return myjsonString;
        }
    }

}
