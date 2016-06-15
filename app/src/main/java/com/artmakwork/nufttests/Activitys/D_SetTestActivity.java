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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artmakwork.nufttests.POJO.MyTest;
import com.artmakwork.nufttests.POJO.Thema;
import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.MyConverter;
import com.artmakwork.nufttests.Utils.MyJSONParser;
import com.artmakwork.nufttests.Utils.MySpiner;
import com.artmakwork.nufttests.Utils.UsedObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class D_SetTestActivity extends AppCompatActivity {

    Spinner spSetTestTest;
    Button btnStartTest, btnBackTest, btnExit;
    AlertDialog.Builder ad;

    String isTestPass = "";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_set_test_activity);

        spSetTestTest = (Spinner) findViewById(R.id.set_test_act_sp_Test);
        btnStartTest = (Button) findViewById(R.id.set_test_act_btn_startTest);
        btnBackTest = (Button) findViewById(R.id.set_test_act_btn_beck);
        btnExit = (Button) findViewById(R.id.d_btn_exit);

        try {
            UsedObjects.jsonStringTestList = new getTestsFromServer().execute(UsedObjects.user.getUser_thema()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // парсить Джейсон + создать клас Группа
        UsedObjects.arrayListTest = MyJSONParser.jsonToMyTestList(UsedObjects.jsonStringTestList);
        UsedObjects.massListTest = MyConverter.arrListMyTestToStringMass(UsedObjects.arrayListTest);

        // заполнить спинер
        MySpiner.addSpiner(this, spSetTestTest, UsedObjects.massListTest);

        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //получить айди теста
                UsedObjects.user.setUser_test(getSelectTest(spSetTestTest.getSelectedItem().toString(),
                        UsedObjects.arrayListTest));

                //check Is Already Pass

                try {
                    isTestPass = new isTestAlreadyPass().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (isTestPass.equals("0")) {
                    //передать его на на следующее активить
                    Intent intent = new Intent(getApplicationContext(), DA_GetTestFromServer.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Ви вже пройшли цей тест, оберіть інший", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnBackTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), C_SetThemaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad = new AlertDialog.Builder(D_SetTestActivity.this);
                ad.setTitle("Вийти?");
                ad.setMessage("Завершити программу?");
                ad.setNegativeButton(android.R.string.no, null);
                ad.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                });
                ad.create();
                ad.show();
            }
        });
    }

    private MyTest getSelectTest(String selectedItem, ArrayList<MyTest> arrayListThema) {

        for(int i = 0; i <arrayListThema.size(); i++){
            if (selectedItem.equals(arrayListThema.get(i).toString())){
                return arrayListThema.get(i);
            }
        }
        return new MyTest(0,"0");
    }

    class getTestsFromServer extends AsyncTask<Thema, String, String> {

        int responseCode = 0;
        String myjsonString;

        @Override
        protected String doInBackground(Thema... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act", "GetTestListByThemeId")
                    .add("theme_id", String.valueOf(UsedObjects.user.getUser_thema().getTheme_id()))
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

    class isTestAlreadyPass extends AsyncTask<Void, String, String> {

        int responseCode = 0;
        String myjsonString;

        @Override
        protected String doInBackground(Void... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act", "checkResultByUserIdAndTestId")
                    .add("test_id", String.valueOf(UsedObjects.user.getUser_test().getTest_id()))
                    .add("user_id", String.valueOf(UsedObjects.user.getUser_id()))
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
