package com.artmakwork.nufttests.POJO;

/**
 * Created by HOME on 26.05.2016.
 */
public class MyTest {

    private int Test_id;
    private String Test_name;

    public MyTest(int test_id, String test_name) {
        Test_id = test_id;
        Test_name = test_name;
    }

    @Override
    public String toString() {
        return Test_name;
    }

    public int getTest_id() {
        return Test_id;
    }

    public void setTest_id(int test_id) {
        Test_id = test_id;
    }

    public String getTest_name() {
        return Test_name;
    }

    public void setTest_name(String test_name) {
        Test_name = test_name;
    }
}
