package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;


public class JadwalModel implements Parcelable {
    private String id;
    private String mata_pelajaran;
    private String guru;
    private String jam;
    private String jam_Waktu;
    private String hari;
    private String kelas;

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getJam_Waktu() {
        return jam_Waktu;
    }

    public void setJam_Waktu(String jam_Waktu) {
        this.jam_Waktu = jam_Waktu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.mata_pelajaran);
        parcel.writeString(this.guru);
        parcel.writeString(this.jam);
        parcel.writeString(this.jam_Waktu);
        parcel.writeString(this.hari);
        parcel.writeString(this.kelas);

    }

    public JadwalModel(){

    }
    protected JadwalModel(Parcel in){
        this.id = in.readString();
        this.mata_pelajaran = in.readString();
        this.guru = in.readString();
        this.jam = in.readString();
        this.jam_Waktu = in.readString();
        this.hari = in.readString();
        this.kelas = in.readString();

    }

    public static final Creator<JadwalModel> CREATOR = new Creator<JadwalModel>() {
        @Override
        public JadwalModel createFromParcel(Parcel parcel) {
            return new JadwalModel(parcel);
        }

        @Override
        public JadwalModel[] newArray(int i) {
            return new JadwalModel[i];
        }
    };
}
