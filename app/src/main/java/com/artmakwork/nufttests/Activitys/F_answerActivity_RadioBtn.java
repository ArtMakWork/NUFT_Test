package com.artmakwork.nufttests.Activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class F_answerActivity_RadioBtn extends AppCompatActivity {

    ListView listView;
    Button back;
    TextView tvQuestTitle;
    private static Activity activity;

    public static Activity getActivity() {
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_answer_activity__radio_btn);

        listView = (ListView) findViewById(R.id.fa_rbg);
        back = (Button) findViewById(R.id.fa_btn_back);
        tvQuestTitle = (TextView) findViewById(R.id.fa_tv_qestion);

        tvQuestTitle.setText(UsedObjects.myExam.getQuestions().get(UsedObjects.question_id).getQuestion());

        // show answers
        //AnswerAdapter adapter = new AnswerAdapter(getApplicationContext(),answerList.get(question_id));
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
                String finalAnswer = null;
                int count = 0;
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
                                if(count == 0){
                                    finalAnswer = String.valueOf(key);
                                }else{
                                    finalAnswer = finalAnswer+"," + String.valueOf(key);
                                }
                                count++;
                            }
                        }
                    UsedObjects.finalAnswer = finalAnswer;
                    }
                Intent intent = new Intent(getApplicationContext(), E_PassTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Повернення заборонено.", Toast.LENGTH_SHORT).show();
    }

}
