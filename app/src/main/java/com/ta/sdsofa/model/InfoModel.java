package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoModel implements Parcelable{
    private String id;
    private String judul;
    private String isi;
    private String tanggal;
    private String id_penulis;
    private String subjek;

    public String getSubjek() {
        return subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getId_penulis() {
        return id_penulis;
    }

    public void setId_penulis(String id_penulis) {
        this.id_penulis = id_penulis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.isi);
        dest.writeString(this.tanggal);
        dest.writeString(this.id_penulis);
        dest.writeString(this.subjek);
    }

    public InfoModel(){

    }

    protected InfoModel(Parcel in){
        this.id = in.readString();
        this.judul = in.readString();
        this.isi = in.readString();
        this.tanggal = in.readString();
        this.id_penulis = in.readString();
        this.subjek = in.readString();
    }

    public static final Creator<InfoModel> CREATOR = new Creator<InfoModel>() {
        @Override
        public InfoModel createFromParcel(Parcel parcel) {
            return new InfoModel(parcel);
        }

        @Override
        public InfoModel[] newArray(int i) {
            return new InfoModel[i];
        }
    };
}

