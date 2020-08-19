package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AbsenModel implements Parcelable {
    private String id;
    private String status_kehadiran;
    private String tanggal;
    private String nisn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus_kehadiran() {
        return status_kehadiran;
    }

    public void setStatus_kehadiran(String status_kehadiran) {
        this.status_kehadiran = status_kehadiran;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.status_kehadiran);
        parcel.writeString(this.tanggal);
        parcel.writeString(this.nisn);
    }
    public AbsenModel(){

    }
    protected AbsenModel(Parcel in){
        this.id = in.readString();
        this.status_kehadiran = in.readString();
        this.tanggal = in.readString();
        this.nisn = in.readString();
    }

    public static final Creator<AbsenModel> CREATOR = new Creator<AbsenModel>() {
        @Override
        public AbsenModel createFromParcel(Parcel parcel) {
            return new AbsenModel(parcel);
        }

        @Override
        public AbsenModel[] newArray(int i) {
            return new AbsenModel[i];
        }
    };
}
