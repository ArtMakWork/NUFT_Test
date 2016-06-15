package com.artmakwork.nufttests.POJO;


import java.util.List;

public class Exam {

    private String variant_num;
    private long time_pass;
    private List<Questions> questions;


    public Exam(List<Questions> questions, String variant_num, long time_pass) {
        this.questions = questions;
        this.variant_num = variant_num;
        this.time_pass = time_pass;
    }

    public Exam() {

    }

    public long getTime_pass() {
        return time_pass;
    }

    public void setTime_pass(long time_pass) {
        this.time_pass = time_pass;
    }

    public String getVariant_num () {
        return variant_num;
    }

    public void setVariant_num (String variant_num) {
        this.variant_num = variant_num;
    }

    public List<Questions> getQuestions () {
        return questions;
    }

    public void setQuestions (List<Questions> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "ClassPojo [variant_num = "+variant_num+", questions = "+questions+", time_pass = "+time_pass+"]";
    }
}