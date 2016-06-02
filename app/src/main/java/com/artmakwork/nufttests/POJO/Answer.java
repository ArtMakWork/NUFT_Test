package com.artmakwork.nufttests.POJO;

import java.util.ArrayList;


public class    Answer {
    private int questId;
    private ArrayList<String> arrListAnsw;

    public Answer(int questId, ArrayList<String> arrListAnsw) {
        this.questId = questId;
        this.arrListAnsw = arrListAnsw;
    }

    public Answer() {

    }

    public ArrayList<String> getArrListAnsw() {
        return arrListAnsw;
    }

    public void setArrListAnsw(ArrayList<String> arrListAnsw) {
        this.arrListAnsw = arrListAnsw;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }
}
