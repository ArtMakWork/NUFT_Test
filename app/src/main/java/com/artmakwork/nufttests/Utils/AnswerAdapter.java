package com.artmakwork.nufttests.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.artmakwork.nufttests.POJO.Answer;
import com.artmakwork.nufttests.R;

/**
 * Created by HOME on 31.05.2016.
 */
public class AnswerAdapter extends BaseAdapter {

    Answer answer;
    LayoutInflater layoutInflater;

    public AnswerAdapter(Context context, Answer answer) {
        this.answer = answer;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return answer.getArrListAnsw().size();
    }

    @Override
    public Object getItem(int position) {
        return answer.getArrListAnsw().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view = layoutInflater.inflate(R.layout.my_simple_listview_answer_cb_item,parent,false);
        }
        String answerFromServerString = getAnswerFromServer(position);
        CheckBox cb = (CheckBox) view.findViewById(R.id.lv_item_cb);

        cb.setText(position+1+") "+ answer.getArrListAnsw().get(position));

        return view;
    }

    public String getAnswerFromServer (int position){

        return String.valueOf((getItem(position)));
    }
}
