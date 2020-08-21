package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KelasRowModel implements Parcelable {
    private String id;
    private String kelas;
    private String wali_kelas;
    private String foto;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getWali_kelas() {
        return wali_kelas;
    }

    public void setWali_kelas(String wali_kelas) {
        this.wali_kelas = wali_kelas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.kelas);
        parcel.writeString(this.wali_kelas);
        parcel.writeString(this.foto);
    }

    public KelasRowModel(){

    }

    protected KelasRowModel(Parcel in){
        this.id = in.readString();
        this.kelas = in.readString();
        this.wali_kelas = in.readString();
        this.foto = in.readString();

    }
    public static final Creator<KelasRowModel> CREATOR = new Creator<KelasRowModel>() {
        @Override
        public KelasRowModel createFromParcel(Parcel parcel) {
            return new KelasRowModel(parcel);
        }

        @Override
        public KelasRowModel[] newArray(int i) {
            return new KelasRowModel[i];
        }
    };
}
