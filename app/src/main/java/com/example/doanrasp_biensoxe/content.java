package com.example.doanrasp_biensoxe;

public class content {
    private String  mCode, mArriveTime, mExitTime;
    private int mOrder;

    public content() {
    }

    public int getmOrder() {
        return mOrder;
    }

    public void setmOrder(int mOrder) {
        this.mOrder = mOrder;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmArriveTime() {
        return mArriveTime;
    }

    public void setmArriveTime(String mArriveTime) {
        this.mArriveTime = mArriveTime;
    }

    public String getmExitTime() {
        return mExitTime;
    }

    public void setmExitTime(String mExitTime) {
        this.mExitTime = mExitTime;
    }

    public content(int mOrder, String mCode, String mArriveTime, String mExitTime) {
        this.mOrder = mOrder;
        this.mCode = mCode;
        this.mArriveTime = mArriveTime;
        this.mExitTime = mExitTime;

    }
}
