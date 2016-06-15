package com.artmakwork.nufttests.Activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.UsedObjects;

public class A_CheckInternetConnection extends AppCompatActivity {

    AlertDialog.Builder ad;
    Button btnOpenSettings, btnExit;
    TextView tv_massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__check_internet_connection);

        btnExit = (Button)findViewById(R.id.aa_btn_exit);
        btnOpenSettings = (Button)findViewById(R.id.aa_open_settings);
        tv_massage = (TextView)findViewById(R.id.aa_tv_mas);

        //check connection
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            tv_massage.setVisibility(View.VISIBLE);

        }else{
            Intent intent = new Intent(this,AB_GetGroupListActivity.class);
            startActivity(intent);
            finish();
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad = new AlertDialog.Builder(A_CheckInternetConnection.this);
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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Повернення заборонено.", Toast.LENGTH_SHORT).show();
    }
}
