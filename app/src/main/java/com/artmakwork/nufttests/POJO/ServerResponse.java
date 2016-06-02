package com.artmakwork.nufttests.POJO;

/**
 * Created by HOME on 02.06.2016.
 */
public class ServerResponse {

    private String pointResp;
    private String txtResp;

    public ServerResponse(String pointResp, String txtResp) {
        this.pointResp = pointResp;
        this.txtResp = txtResp;
    }

    @Override
    public String toString() {
        return "Status: " + txtResp + " , Ваша оцінка: " + pointResp;
    }

    public String getPointResp() {
        return pointResp;
    }

    public void setPointResp(String pointResp) {
        this.pointResp = pointResp;
    }

    public String getTxtResp() {
        return txtResp;
    }

    public void setTxtResp(String txtResp) {
        this.txtResp = txtResp;
    }
}
