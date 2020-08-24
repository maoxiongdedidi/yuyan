package com.yueyi.yuyinfanyi.bean;

public class CancelAccountBean {

    /**
     * applyTime : 2020-01-01 12：00：00
     * day : 7
     * finishTime : 2020-01-01 12：00：00
     * mobile : 18888888888
     */

    private String applyTime;
    private int day;
    private String finishTime;
    private String mobile;

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
