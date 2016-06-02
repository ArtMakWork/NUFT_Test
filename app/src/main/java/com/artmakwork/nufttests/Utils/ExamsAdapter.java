package com.artmakwork.nufttests.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artmakwork.nufttests.POJO.Exam;
import com.artmakwork.nufttests.POJO.Questions;
import com.artmakwork.nufttests.R;

/**
 * Created by HOME on 31.05.2016.
 */
public class ExamsAdapter extends BaseAdapter {

    Exam exam;
    LayoutInflater layoutInflater;

    public ExamsAdapter(Context context, Exam exam) {
        this.exam = exam;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return exam.getQuestions().size();
    }

    @Override
    public Object getItem(int position) {
        return exam.getQuestions().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view = layoutInflater.inflate(R.layout.my_simple_listview_quest_item,parent,false);
        }
        Questions questions = getQestion(position);
        TextView tv = (TextView) view.findViewById(R.id.lv_item_tv_quest_txt);
        tv.setText(position+1+") "+questions.getQuestion());

        return view;
    }

    public Questions getQestion (int position){

        return (Questions) getItem(position);
    }
}
