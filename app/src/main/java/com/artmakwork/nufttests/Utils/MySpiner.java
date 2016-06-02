package com.artmakwork.nufttests.Utils;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.artmakwork.nufttests.R;

public class MySpiner {

    //заполнение спинера
    public static void addSpiner(Context context,Spinner spiner, String[] dataList){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.my_simple_spiner_item, R.id.spin_item_tv_name, dataList);
        spiner.setAdapter(adapter);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




}
