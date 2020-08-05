package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TugasModel implements Parcelable {
    private String id;
    private String namatugas;
    private String tugas;
    private String mata_pelajaran;
    private String guru;
    private String kelas;
    private String deadline;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamatugas() {
        return namatugas;
    }

    public void setNamatugas(String namatugas) {
        this.namatugas = namatugas;
    }

    public String getTugas() {
        return tugas;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
    }

    public String getMata_pelajaran() {
        return mata_pelajaran;
    }

    public void setMata_pelajaran(String mata_pelajaran) {
        this.mata_pelajaran = mata_pelajaran;
    }

    public String getGuru() {
        return guru;
    }

    public void setGuru(String guru) {
        this.guru = guru;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.namatugas);
        parcel.writeString(this.tugas);
        parcel.writeString(this.mata_pelajaran);
        parcel.writeString(this.guru);
        parcel.writeString(this.kelas);
        parcel.writeString(this.deadline);
        parcel.writeString(this.date);
    }

    public TugasModel(){

    }

    protected TugasModel(Parcel in){
        this.id = in.readString();
        this.namatugas = in.readString();
        this.tugas = in.readString();
        this.mata_pelajaran = in.readString();
        this.guru = in.readString();
        this.kelas = in.readString();
        this.deadline = in.readString();
        this.date = in.readString();

    }

    public static final Creator<TugasModel> CREATOR = new Creator<TugasModel>() {
        @Override
        public TugasModel createFromParcel(Parcel parcel) {
            return new TugasModel(parcel);
        }

        @Override
        public TugasModel[] newArray(int i) {
            return new TugasModel[i];
        }
    };
}
