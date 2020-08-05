package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AdminModel implements Parcelable {
    private String id;
    private String nama;
    private String alamat;
    private String nohp;
    private String jabatan;
    private String matapelajaran;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getMatapelajaran() {
        return matapelajaran;
    }

    public void setMatapelajaran(String matapelajaran) {
        this.matapelajaran = matapelajaran;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.id);
        dest.writeString(this.alamat);
        dest.writeString(this.jabatan);
        dest.writeString(this.matapelajaran);
        dest.writeString(this.nama);
        dest.writeString(this.nohp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AdminModel(){

    }

    protected AdminModel(Parcel in){
        this.id = in.readString();
        this.alamat = in.readString();
        this.jabatan = in.readString();
        this.nama = in.readString();
        this.matapelajaran = in.readString();
        this.nohp = in.readString();
    }

    public static final Creator<AdminModel> CREATOR = new Creator<AdminModel>() {
        @Override
        public AdminModel createFromParcel(Parcel source) {
            return new AdminModel(source);
        }

        @Override
        public AdminModel[] newArray(int i) {
            return new AdminModel[i];
        }
    };
}

