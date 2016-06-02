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
import android.widget.Toast;

import com.artmakwork.nufttests.POJO.Thema;
import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.MyConverter;
import com.artmakwork.nufttests.Utils.MyJSONParser;
import com.artmakwork.nufttests.Utils.MySpiner;
import com.artmakwork.nufttests.Utils.UsedObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.*;

public class C_SetThemaActivity extends AppCompatActivity {

    Spinner spSetThemaSelectThema;
    Button btnSetThemaNextActivity;
    Button btnExit;
    AlertDialog.Builder ad;

    private final OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_set_thema);

        spSetThemaSelectThema = (Spinner) findViewById(R.id.set_thema_act_sp_Thema);
        btnSetThemaNextActivity = (Button) findViewById(R.id.set_thema_act_btn_next);
        btnExit = (Button) findViewById(R.id.c_btn_exit);

        // get themas from server
        try {
            UsedObjects.jsonStringThemaList = new getThemasFromServer().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // парсить Джейсон + создать клас Группа
        UsedObjects.arrayListThema = MyJSONParser.jsonToThemeList(UsedObjects.jsonStringThemaList);
        UsedObjects.massListThema = MyConverter.arrListThemaToStringMass(UsedObjects.arrayListThema);

        // заполнить спинер
        MySpiner.addSpiner(this, spSetThemaSelectThema, UsedObjects.massListThema);


        btnSetThemaNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsedObjects.user.setUser_thema(getSelectThema(spSetThemaSelectThema.getSelectedItem().toString(),
                        UsedObjects.arrayListThema));

                Intent intent = new Intent(getApplicationContext(), D_SetTestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad = new AlertDialog.Builder(C_SetThemaActivity.this);
                ad.setTitle("Вихід");
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

    private Thema getSelectThema(String selectedItem, ArrayList<Thema> arrayListThema) {

        for(int i = 0; i <arrayListThema.size(); i++){
            if (selectedItem.equals(arrayListThema.get(i).toString())){
                return arrayListThema.get(i);
            }
        }
        return new Thema(0,"0");
    }

    class getThemasFromServer extends AsyncTask<Void, String, String> {

        int responseCode = 0;
        String myjsonString;

        @Override
        protected String doInBackground(Void... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act", "GetThemeList")
                    .build();

            Request request = new Request.Builder()
                    .url("http://UsedObjects.SERVER/zapiti.php")
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
