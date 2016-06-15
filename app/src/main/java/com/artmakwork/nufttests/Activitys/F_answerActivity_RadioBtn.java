package com.artmakwork.nufttests.Activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.artmakwork.nufttests.POJO.Answer;
import com.artmakwork.nufttests.POJO.Exam;
import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.MyJSONParser;
import com.artmakwork.nufttests.Utils.UsedObjects;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class F_answerActivity_RadioBtn extends AppCompatActivity {

    ListView listView;
    Button back;
    TextView tvQuestTitle, tvtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_answer_activity__radio_btn);

        listView = (ListView) findViewById(R.id.fa_rbg);
        back = (Button) findViewById(R.id.fa_btn_back);
        tvQuestTitle = (TextView) findViewById(R.id.fa_tv_qestion);
        tvtimer = (TextView) findViewById(R.id.f_tv_timer);

        tvQuestTitle.setText(UsedObjects.myExam.getQuestions().get(UsedObjects.question_id).getQuestion());

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
                Log.d("419", "time stop in answers");
                tvtimer.setText("Час вийшов");
                tvtimer.setTextColor(getResources().getColor(R.color.colorRed));
            }
        }.start();

        // show answers
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_list_item_multiple_choice,
                                    android.R.id.text1,
                                    UsedObjects.arrayListAnswer.get(UsedObjects.question_id).getArrListAnsw());
        listView.setAdapter(adapter);

        if (UsedObjects.multiChoise==false){
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }

        //back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelectedAnswer() == true){
                    Intent intent = new Intent(getApplicationContext(), E_PassTestActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Оберіть менше варіантів",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Повернення заборонено.", Toast.LENGTH_SHORT).show();
    }

    private boolean checkSelectedAnswer(){
        boolean countTrue = true;
        String finalAnswer = "-1";
        int count = 0;
        int correctCount = 0;
        if (listView.getChoiceMode()== AbsListView.CHOICE_MODE_SINGLE){
            Log.d("419", String.valueOf(String.valueOf(listView.getCheckedItemPosition())));
            finalAnswer = String.valueOf(listView.getCheckedItemPosition());
            UsedObjects.finalAnswer = finalAnswer;
        }else{
            SparseBooleanArray sbArray = listView.getCheckedItemPositions();
            for (int i = 0; i < sbArray.size(); i++) {
                int key = sbArray.keyAt(i);
                if (sbArray.get(key)){
                    Log.d("419", String.valueOf(key));

                    if (correctCount >= Integer.valueOf(UsedObjects.myExam.getQuestions().get(UsedObjects.question_id).getAnsw_count())){
                        Toast.makeText(getApplicationContext(),"Обрано більше ніж можливо",Toast.LENGTH_SHORT).show();
                        countTrue = false;
                    }else{
                        if(count == 0){
                            finalAnswer = String.valueOf(key);
                        }else{
                            finalAnswer = finalAnswer+"," + String.valueOf(key);
                        }
                        count++;
                        correctCount++;
                    }
                }
            }
            UsedObjects.finalAnswer = finalAnswer;
        }
    return countTrue;
    }
}
