package com.artmakwork.nufttests.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.artmakwork.nufttests.POJO.Answer;
import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.ExamsAdapter;
import com.artmakwork.nufttests.Utils.MyJSONParser;
import com.artmakwork.nufttests.Utils.UsedObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class E_PassTestActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    ListView listView;
    Button btnSendData, btnExit;
    AlertDialog.Builder ad,ad1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_pass_test);

        listView = (ListView) findViewById(R.id.pas_test_act_lv_exam);
        btnSendData = (Button)findViewById(R.id.pas_test_act_btn_send);
        btnExit = (Button)findViewById(R.id.e_btn_exit);

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

        Log.d("419","user.getUser_answerList() onCreate = "+UsedObjects.user.getUser_answerList().toString());

        if (UsedObjects.question_id == -1){
            UsedObjects.question_id = 0;
        }

        UsedObjects.user.getUser_answerList().set(UsedObjects.question_id, UsedObjects.finalAnswer);

        Log.d("419", "user.getUser_answerList() after add  = " + UsedObjects.user.getUser_answerList().toString());

        // view lv
        ExamsAdapter adapter = new ExamsAdapter(getApplicationContext(),UsedObjects.myExam);
        listView.setAdapter(adapter);

        // go to Question Activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String countCorrectAnswer = UsedObjects.myExam.getQuestions().get(position).getAnsw_count();
                UsedObjects.question_id = position;
                if (countCorrectAnswer.equals("1")){
                    UsedObjects.multiChoise = false;
                }else {
                    UsedObjects.multiChoise = true;
                }
                Intent intent = new Intent(getApplicationContext(),F_answerActivity_RadioBtn.class);
                startActivity(intent);
                finish();
            }
        });

        //send Result on Server
        btnSendData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean withoutAnswer = false;
                for (int i = 0; i<UsedObjects.user.getUser_answerList().size();i++){
                    if (UsedObjects.user.getUser_answerList().get(i).equals("-1")){
                        withoutAnswer = true;
                    }
                }
                if(withoutAnswer == true){
                    ad1 = new AlertDialog.Builder(E_PassTestActivity.this);
                    ad1.setTitle("Відправка відповідей");
                    ad1.setMessage("Ви не відповіди на всі запитання, \n Відповіді будь збережені, без змоги перездачі!!!");
                    ad1.setNegativeButton(android.R.string.no, null);
                    ad1.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent finalActivityIntent = new Intent(getApplicationContext(),G_sendDataActivity.class);
                            startActivity(finalActivityIntent);
                            finish();
                        }
                    });
                    ad1.create();
                    ad1.show();
                }else{
                    Intent finalActivityIntent = new Intent(getApplicationContext(),G_sendDataActivity.class);
                    startActivity(finalActivityIntent);
                    finish();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad = new AlertDialog.Builder(E_PassTestActivity.this);
                ad.setTitle("Вихід");
                ad.setMessage("Завершити программу? \n Відповіді будь збережені, без змоги перездачі!!! ");
                ad.setNegativeButton(android.R.string.no, null);
                ad.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent finalActivityIntent = new Intent(getApplicationContext(),G_sendDataActivity.class);
                        startActivity(finalActivityIntent);
                        finish();
                    }
                });
                ad.create();
                ad.show();
            }
        });


    }

    private void showArrayListAnswer(ArrayList<Answer> arr) {
        for(int i = 0;i<arr.size();i++){
            for(int j = 0;j<arr.get(i).getArrListAnsw().size();j++) {
                Log.d("419","["+i+"]["+j+"]"+arr.get(i).getArrListAnsw().get(j));
            }
        }
    }

    private void showArrayListString(ArrayList<String> arr) {
        for(int i = 0;i<arr.size();i++){
            Log.d("419","["+i+"]"+arr.get(i));
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
                    .url("http://nuft.esy.es/zapiti.php")
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
                    .url("http://nuft.esy.es/zapiti.php")
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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Повернення заборонено.", Toast.LENGTH_SHORT).show();
    }



}
