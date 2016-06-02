package com.artmakwork.nufttests.POJO;

public class Questions {

    private String question;
    private String answ_count;

    public Questions(String answ_count, String question) {
        this.answ_count = answ_count;
        this.question = question;
    }

    public String getQuestion () {
        return question;
    }

    public void setQuestion (String question) {
        this.question = question;
    }

    public String getAnsw_count () {
        return answ_count;
    }

    public void setAnsw_count (String answ_count) {
        this.answ_count = answ_count;
    }

    @Override
    public String toString() {
        return "ClassPojo [question = "+question+", answ_count = "+answ_count+"]";
    }
}
