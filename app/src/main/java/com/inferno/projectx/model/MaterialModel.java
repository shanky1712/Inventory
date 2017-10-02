package com.inferno.projectx.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saravana.subramanian on 8/30/17.
 */

public class MaterialModel implements Parcelable {


    private int mid;
    private String materialName;


    private String materialUnit;
    private String materialUnitPrice;
    private String materialPicture;
    private String selectedUnits;
    private boolean isMaterialSelected;

    public MaterialModel() {
    }

    public MaterialModel(int mid, String materialName, String materialUnit, String materialUnitPrice, String materialPicture) {
        this.mid = mid;
        this.materialName = materialName;
        this.materialUnit = materialUnit;
        this.materialUnitPrice = materialUnitPrice;
        this.materialPicture = materialPicture;
    }

    public MaterialModel(int mid, String materialName, String materialUnit, String materialUnitPrice, String materialPicture,
                         boolean isMaterialSelected) {
        this.mid = mid;
        this.materialName = materialName;
        this.materialUnit = materialUnit;
        this.materialUnitPrice = materialUnitPrice;
        this.materialPicture = materialPicture;
        this.isMaterialSelected = isMaterialSelected;
    }


    public MaterialModel(int mid, String materialName, String materialUnit, String materialUnitPrice, String materialPicture, String selectedUnits, boolean isMaterialSelected) {
        this.mid = mid;
        this.materialName = materialName;
        this.materialUnit = materialUnit;
        this.materialUnitPrice = materialUnitPrice;
        this.materialPicture = materialPicture;
        this.selectedUnits = selectedUnits;
        this.isMaterialSelected = isMaterialSelected;
    }


    protected MaterialModel(Parcel in) {
        mid = in.readInt();
        materialName = in.readString();
        materialUnit = in.readString();
        materialUnitPrice = in.readString();
        materialPicture = in.readString();
        selectedUnits = in.readString();
        isMaterialSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mid);
        dest.writeString(materialName);
        dest.writeString(materialUnit);
        dest.writeString(materialUnitPrice);
        dest.writeString(materialPicture);
        dest.writeString(selectedUnits);
        dest.writeByte((byte) (isMaterialSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MaterialModel> CREATOR = new Creator<MaterialModel>() {
        @Override
        public MaterialModel createFromParcel(Parcel in) {
            return new MaterialModel(in);
        }

        @Override
        public MaterialModel[] newArray(int size) {
            return new MaterialModel[size];
        }
    };

    public int getMid() {
        return mid;
    }

    public void setMid(int nid) {
        this.mid = nid;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getMaterialUnitPrice() {
        return materialUnitPrice;
    }

    public void setMaterialUnitPrice(String materialUnitPrice) {
        this.materialUnitPrice = materialUnitPrice;
    }

    public String getMaterialPicture() {
        return materialPicture;
    }

    public void setMaterialPicture(String materialPicture) {
        this.materialPicture = materialPicture;
    }

    public boolean isMaterialSelected() {
        return isMaterialSelected;
    }

    public void setMaterialSelected(boolean materialSelected) {
        isMaterialSelected = materialSelected;
    }

    public String getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(String selectedUnits) {
        this.selectedUnits = selectedUnits;
    }
}
