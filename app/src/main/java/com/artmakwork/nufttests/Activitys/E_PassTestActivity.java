package com.artmakwork.nufttests.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.artmakwork.nufttests.POJO.Answer;
import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.ExamsAdapter;
import com.artmakwork.nufttests.Utils.MyJSONParser;
import com.artmakwork.nufttests.Utils.UsedObjects;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class E_PassTestActivity extends AppCompatActivity {

    ListView listView;
    Button btnSendData, btnExit;
    AlertDialog.Builder ad,ad1,ad2;
    TextView tvtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_pass_test);

        listView = (ListView) findViewById(R.id.pas_test_act_lv_exam);
        btnSendData = (Button)findViewById(R.id.pas_test_act_btn_send);
        btnExit = (Button)findViewById(R.id.e_btn_exit);
        tvtimer = (TextView)findViewById(R.id.e_tv_timer);



        if (UsedObjects.myExam.getTime_pass()!=0){
            //START Timer
            new CountDownTimer(UsedObjects.myExam.getTime_pass(), 1000) {
                public void onTick(long millisUntilFinished) {
                    UsedObjects.myExam.setTime_pass(millisUntilFinished);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(UsedObjects.myExam.getTime_pass()));
                    long secondsFull = Long.valueOf(UsedObjects.myExam.getTime_pass());
                    secondsFull = secondsFull - (minutes)*60*1000;
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(secondsFull);
                    tvtimer.setText(minutes + ":" + seconds);
                }
                public void onFinish() {
                    gotoNewActivity();
                }
            }.start();
        }else{
          gotoNewActivity();
        }

        saveAnswerinSrting();

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

    private void gotoNewActivity() {
        Log.d("419","time stop in passtest");
        tvtimer.setText("Час вийшов");
        tvtimer.setTextColor(getResources().getColor(R.color.colorRed));
        saveAnswerinSrting();
        Intent afinalActivityIntent = new Intent(getApplicationContext(),G_sendDataActivity.class);
        startActivity(afinalActivityIntent);
        finish();
    }

    private void saveAnswerinSrting() {
        if (UsedObjects.question_id == -1){
            UsedObjects.question_id = 0;
        }

        UsedObjects.user.getUser_answerList().set(UsedObjects.question_id, UsedObjects.finalAnswer);

        Log.d("419", "user.getUser_answerList() after add  = " + UsedObjects.user.getUser_answerList().toString());
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Повернення заборонено.", Toast.LENGTH_SHORT).show();
    }



}
