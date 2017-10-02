package com.inferno.projectx.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saravana.subramanian on 8/21/17.
 */


public class ContractorModel  implements Parcelable{

    private int nid;
    private String contractorName;
    private String contractorPhone;
    private String contractorAddress;
    private String contractorImageURL;


    public ContractorModel(int nid, String contractorName, String contractorPhone, String contractorAddress, String contractorImageURL) {
        this.nid = nid;
        this.contractorName = contractorName;
        this.contractorPhone = contractorPhone;
        this.contractorAddress = contractorAddress;
        this.contractorImageURL = contractorImageURL;
    }


    protected ContractorModel(Parcel in) {
        nid = in.readInt();
        contractorName = in.readString();
        contractorPhone = in.readString();
        contractorAddress = in.readString();
        contractorImageURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nid);
        dest.writeString(contractorName);
        dest.writeString(contractorPhone);
        dest.writeString(contractorAddress);
        dest.writeString(contractorImageURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContractorModel> CREATOR = new Creator<ContractorModel>() {
        @Override
        public ContractorModel createFromParcel(Parcel in) {
            return new ContractorModel(in);
        }

        @Override
        public ContractorModel[] newArray(int size) {
            return new ContractorModel[size];
        }
    };

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getContractorPhone() {
        return contractorPhone;
    }

    public void setContractorPhone(String contractorPhone) {
        this.contractorPhone = contractorPhone;
    }

    public String getContractorAddress() {
        return contractorAddress;
    }

    public void setContractorAddress(String contractorAddress) {
        this.contractorAddress = contractorAddress;
    }

    public String getContractorImageURL() {
        return contractorImageURL;
    }

    public void setContractorImageURL(String contractorImageURL) {
        this.contractorImageURL = contractorImageURL;
    }
}
