package com.artmakwork.nufttests.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.artmakwork.nufttests.POJO.Group;
import com.artmakwork.nufttests.POJO.User;
import com.artmakwork.nufttests.R;
import com.artmakwork.nufttests.Utils.MyConverter;
import com.artmakwork.nufttests.Utils.MyJSONParser;
import com.artmakwork.nufttests.Utils.MySpiner;
import com.artmakwork.nufttests.Utils.UsedObjects;


import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class B_LoginActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    AlertDialog.Builder ad;
    Button btnLoginActEnter, btnExit;
    EditText etLoginActNumZalik,etLoginActSurName, etLoginActName, etLoginActNumZalikDouble,etLoginActFather;
    Spinner spLoginActGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_login);

        btnLoginActEnter = (Button) findViewById(R.id.log_act_btn_enter);
        etLoginActName = (EditText) findViewById(R.id.log_act_edtTxt_name);
        etLoginActSurName = (EditText) findViewById(R.id.log_act_edtTxt_surName);
        etLoginActNumZalik = (EditText) findViewById(R.id.log_act_edtTxt_numZalik);
        etLoginActFather = (EditText) findViewById(R.id.log_act_edtTxt_fatherName);
        etLoginActNumZalikDouble = (EditText) findViewById(R.id.log_act_edtTxt_numZalik_double);
        spLoginActGroup = (Spinner) findViewById(R.id.log_act_spn_group);
        btnExit = (Button) findViewById(R.id.b_btn_exit);

        // парсить Джейсон + создать клас Группа
        UsedObjects.arrayListGroup = MyJSONParser.jsonToGroupList(UsedObjects.jsonStringGroupsList);
        UsedObjects.massListGroup = MyConverter.arrListGroupToStringMass(UsedObjects.arrayListGroup);

        // заполнить спинер
        MySpiner.addSpiner(this, spLoginActGroup, UsedObjects.massListGroup);

        btnLoginActEnter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((etLoginActName.getText().length() == 0) ||
                        (etLoginActSurName.getText().length() == 0) ||
                        (etLoginActFather.getText().length() == 0) ||
                        (etLoginActNumZalik.getText().length() == 0) ||
                        (etLoginActNumZalikDouble.getText().length() == 0) ||
                        (Long.parseLong(etLoginActNumZalik.getText().toString()) !=
                                Long.parseLong(etLoginActNumZalikDouble.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Перевірте введні данні", Toast.LENGTH_LONG).show();
                } else {
                    // получить данные с едит полей
                    UsedObjects.user.setUser_name(etLoginActName.getText().toString());
                    UsedObjects.user.setUser_surname(etLoginActSurName.getText().toString());
                    UsedObjects.user.setUser_fatherName(etLoginActFather.getText().toString());
                    UsedObjects.user.setUser_zalik(Long.parseLong(etLoginActNumZalik.getText().toString()));

                    UsedObjects.user.setUser_group(getUserGroup(spLoginActGroup.getSelectedItem().toString(),
                            UsedObjects.arrayListGroup));

                    // передать юзера на server
                    try {
                        UsedObjects.user.setUser_id(new sendUserOnServer().execute(UsedObjects.user).get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (String.valueOf(UsedObjects.user.getUser_id()).equals("Wrong credit card number") == false) {

                        //Переход на следующее активити
                        Intent intent = new Intent(getApplicationContext(), C_SetThemaActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Перевірте номер заліковки", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad = new AlertDialog.Builder(B_LoginActivity.this);
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

    private Group getUserGroup(String selectedItem, ArrayList<Group> arrayListGroup) {

        for(int i = 0; i <arrayListGroup.size(); i++){
            if (selectedItem.equals(arrayListGroup.get(i).toString())){
                return arrayListGroup.get(i);
            }
        }
        return new Group(0,"0",0,0);
    }

    class sendUserOnServer extends AsyncTask<User, String, String>{

        int responseCode = 0;
        String serverUserId;
        @Override
        protected String doInBackground(User... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("act","CheckUser")
                    .add("name", UsedObjects.user.getUser_name())
                    .add("surname", UsedObjects.user.getUser_surname())
                    .add("add_name", UsedObjects.user.getUser_fatherName())
                    .add("zalik", String.valueOf(UsedObjects.user.getUser_zalik()))
                    .add("group_id", String.valueOf(UsedObjects.user.getUser_group().getGroupId()))
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
                    serverUserId = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (!response.isSuccessful()) try {
                throw new IOException("Unexpected code " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return serverUserId;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Повернення заборонено.",Toast.LENGTH_SHORT).show();
    }

}


