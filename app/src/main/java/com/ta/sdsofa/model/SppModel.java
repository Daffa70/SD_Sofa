package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SppModel implements Parcelable {
    private String id;
    private String nisn;
    private String nama;
    private String kelas;
    private String untuk_bulan;
    private String tgl_pembayaran;
    private String bukti;
    private String status_pembayaran;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getUntuk_bulan() {
        return untuk_bulan;
    }

    public void setUntuk_bulan(String untuk_bulan) {
        this.untuk_bulan = untuk_bulan;
    }

    public String getTgl_pembayaran() {
        return tgl_pembayaran;
    }

    public void setTgl_pembayaran(String tgl_pembayaran) {
        this.tgl_pembayaran = tgl_pembayaran;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.nisn);
        parcel.writeString(this.kelas);
        parcel.writeString(this.untuk_bulan);
        parcel.writeString(this.tgl_pembayaran);
        parcel.writeString(this.bukti);
        parcel.writeString(this.status_pembayaran);
        parcel.writeString(this.nama);
    }
    public SppModel(){

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    protected SppModel(Parcel in){
        this.id = in.readString();
        this.nisn = in.readString();
        this.kelas = in.readString();
        this.untuk_bulan = in.readString();
        this.tgl_pembayaran = in.readString();
        this.bukti = in.readString();
        this.status_pembayaran = in.readString();
        this.nama = in.readString();
    }

    public static final Creator<SppModel> CREATOR = new Creator<SppModel>() {
        @Override
        public SppModel createFromParcel(Parcel parcel) {
            return new SppModel(parcel);
        }

        @Override
        public SppModel[] newArray(int i) {
            return new SppModel[i];
        }
    };
}
