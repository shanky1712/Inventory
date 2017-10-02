package com.inferno.projectx.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saravana.subramanian on 8/22/17.
 */

public class WorkerModel implements Parcelable{

    private int uid;
    private int rid;
    private String workerName;
    private String workerPhone;
    private String workerAge;
    private String workerAddress;
    private String workerImageURL;
    public boolean isWorkerSelected;



    public WorkerModel() {
    }

    public WorkerModel(int uid, int rid, String workerName, String workerPhone, String workerAge,
                       String workerAddress, String workerImageURL, boolean isWorkerSelected) {
        this.uid = uid;
        this.rid = rid;
        this.workerName = workerName;
        this.workerPhone = workerPhone;
        this.workerAge = workerAge;
        this.workerAddress = workerAddress;
        this.workerImageURL = workerImageURL;
        this.isWorkerSelected = isWorkerSelected;
    }


    protected WorkerModel(Parcel in) {
        uid = in.readInt();
        rid = in.readInt();
        workerName = in.readString();
        workerPhone = in.readString();
        workerAge = in.readString();
        workerAddress = in.readString();
        workerImageURL = in.readString();
        isWorkerSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeInt(rid);
        dest.writeString(workerName);
        dest.writeString(workerPhone);
        dest.writeString(workerAge);
        dest.writeString(workerAddress);
        dest.writeString(workerImageURL);
        dest.writeByte((byte) (isWorkerSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WorkerModel> CREATOR = new Creator<WorkerModel>() {
        @Override
        public WorkerModel createFromParcel(Parcel in) {
            return new WorkerModel(in);
        }

        @Override
        public WorkerModel[] newArray(int size) {
            return new WorkerModel[size];
        }
    };

    public int getUid() {
        return uid;
    }

    public void setUid(int nid) {
        this.uid = nid;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public String getWorkerAddress() {
        return workerAddress;
    }

    public void setWorkerAddress(String workerAddress) {
        this.workerAddress = workerAddress;
    }

    public String getWorkerImageURL() {
        return workerImageURL;
    }

    public void setWorkerImageURL(String workerImageURL) {
        this.workerImageURL = workerImageURL;
    }

    public boolean isWorkerSelected() {
        return isWorkerSelected;
    }

    public void setWorkerSelected(boolean workerSelected) {
        isWorkerSelected = workerSelected;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getWorkerAge() {
        return workerAge;
    }

    public void setWorkerAge(String workerAge) {
        this.workerAge = workerAge;
    }
}
