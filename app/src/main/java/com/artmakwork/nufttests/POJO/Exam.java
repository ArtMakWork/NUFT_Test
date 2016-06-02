package com.artmakwork.nufttests.POJO;


import java.util.List;

public class Exam {

    private String variant_num;
    private List<Questions> questions;


    public Exam(List<Questions> questions, String variant_num) {
        this.questions = questions;
        this.variant_num = variant_num;
    }

    public Exam() {

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
        return "ClassPojo [variant_num = "+variant_num+", questions = "+questions+"]";
    }
}