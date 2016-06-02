package com.artmakwork.nufttests.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.UsedObjects;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class A_GetGroupListActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_get_group_list);

        //получить данные с сервера
        try {
            UsedObjects.jsonStringGroupsList = new getGroupFromServer().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Переход на следующее активити
        if(UsedObjects.jsonStringGroupsList != null){
            Intent intent = new Intent(this,B_LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    class getGroupFromServer extends AsyncTask< Void, String, String> {

        String myjsonString;
        int responseCode = 0;

        @Override
        protected String doInBackground(Void... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act", "GetGroupList")
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

