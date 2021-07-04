package com.example.test1;

public class DailyList {

    public int sNum;
    public String sStartDate;
    public String sEndDate;
    public String sStartTime;
    public String sEndTime;
    public String sText;

    public int getsNum() {
        return sNum;
    }

    public String getsStartDate() {
        return sStartDate;
    }
    public String getsEndDate() {
        return sEndDate;
    }
    public String getsStartTime() {
        return sStartTime;
    }
    public String getsEndTime() {
        return sEndTime;
    }
    public String getsText() {
        return sText;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public void setsStartDate(String sStartDate) {
        this.sStartDate = sStartDate;
    }
    public void setsEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }
    public void setsStartTime(String sStartTime) {
        this.sStartTime = sStartTime;
    }
    public void setsEndTime(String sEndTime) {
        this.sEndTime = sEndTime;
    }
    public void setsText(String sText) {
        this.sText = sText;
    }
}
