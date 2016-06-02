package com.artmakwork.nufttests.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.artmakwork.nufttests.POJO.ServerResponse;
import com.artmakwork.nufttests.POJO.Thema;
import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.UsedObjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class G_sendDataActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    String myjsonString;
    public static int responseCode = 0;
    String isDataSaved = null;

    Button btnExit, btnTryAgain;
    TextView tvRequest,tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_send_data);

        btnExit = (Button) findViewById(R.id.g_btn_exit);
        btnTryAgain = (Button) findViewById(R.id.g_btn_tryagain);
        tvRequest = (TextView) findViewById(R.id.g_tv_request);
        tvResult = (TextView) findViewById(R.id.g_tv_result);

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i< UsedObjects.user.getUser_answerList().size();i++){
            jsonArray.put(UsedObjects.user.getUser_answerList().get(i).toString());
        }


        Log.d("419","JSON→"+jsonArray);
        //send
        try {
            isDataSaved = new sendDataOnServer().execute(jsonArray).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        ServerResponse serverResponse = null;

        try {
            serverResponse = jsonToServerResponse(isDataSaved);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (serverResponse.getTxtResp().equals("Results saved")){
            tvRequest.setText(serverResponse.getTxtResp());
            tvRequest.setTextColor(getResources().getColor(R.color.colorGreen));
            tvResult.setText("Ваш результат: " + serverResponse.getPointResp());
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }else{
            btnExit.setVisibility(View.INVISIBLE);
            btnTryAgain.setVisibility(View.VISIBLE);

            tvRequest.setText(serverResponse.getTxtResp());
            tvRequest.setTextColor(getResources().getColor(R.color.colorRed));
            tvResult.setText("Ваш результат: не збережено");

            btnTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), E_PassTestActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


    class sendDataOnServer extends AsyncTask< JSONArray, String, String> {



        @Override
        protected String doInBackground(JSONArray... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act", "AddNewResult")
                    .add("user_id",UsedObjects.user.getUser_id())
                    .add("test_id", String.valueOf(UsedObjects.user.getUser_test().getTest_id()))
                    .add("variant_num", String.valueOf(UsedObjects.myExam.getVariant_num()))
                    .add("answs",params[0].toString())
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(0);
    }

    private ServerResponse jsonToServerResponse (String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return new ServerResponse(jsonObject.getString("points"), jsonObject.getString("response"));
    }


}
