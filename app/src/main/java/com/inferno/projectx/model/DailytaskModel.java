package com.inferno.projectx.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by saravana.subramanian on 8/22/17.
 */

public class DailytaskModel implements Parcelable{

    private int wid;
    private int cid;
    private String date;
    public boolean isWorkerSelected;



    public DailytaskModel() {
    }

    public DailytaskModel(int wid, int cid, String date, boolean isWorkerSelected) {
        this.wid = wid;
        this.cid = cid;
        this.date = date;
        this.isWorkerSelected = isWorkerSelected;
        Log.i("Report Json",wid+"-"+cid+"-"+date);
    }


    protected DailytaskModel(Parcel in) {
        wid = in.readInt();
        cid = in.readInt();
        date = in.readString();
        isWorkerSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wid);
        dest.writeInt(cid);
        dest.writeString(date);
        dest.writeByte((byte) (isWorkerSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DailytaskModel> CREATOR = new Creator<DailytaskModel>() {
        @Override
        public DailytaskModel createFromParcel(Parcel in) {
            return new DailytaskModel(in);
        }

        @Override
        public DailytaskModel[] newArray(int size) {
            return new DailytaskModel[size];
        }
    };

    public int getWid() {
        return wid;
    }

    public void setWid(int nid) {
        this.wid = wid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isWorkerSelected() {
        return isWorkerSelected;
    }

    public void setWorkerSelected(boolean workerSelected) {
        isWorkerSelected = workerSelected;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int rid) {
        this.cid = cid;
    }
}
