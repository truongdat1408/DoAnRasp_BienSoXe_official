package com.example.doanrasp_biensoxe;

import androidx.annotation.NonNull;

public class Content {
    private String  mCode, mArriveTime, mName;
    private int mOrder;

    public Content() {
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

    public String getmName() {
        return mName;
    }

    public void setmExitTime(String mName) {
        this.mName = mName;
    }

    public Content(int mOrder, String mCode, String mArriveTime, String mName) {
        this.mOrder = mOrder;
        this.mCode = mCode;
        this.mArriveTime = mArriveTime;
        this.mName = mName;
    }

    @NonNull
    @Override
    public String toString() {
        String x = "mOrder:"+String.valueOf(getmOrder())+" mCode: "+getmCode()+" mArrTime:"+getmArriveTime()
                + " mExitTime:"+getmName();
        return x;
    }
}
