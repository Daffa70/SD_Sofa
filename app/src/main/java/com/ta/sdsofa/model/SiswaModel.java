package com.ta.sdsofa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SiswaModel implements Parcelable {
    private String nisn;
    private String nama;
    private String alamat;
    private String nohp;
    private String nohporangtua;
    private String tanggal_lahir;
    private String tahun_masuk;
    private String wali_murid;
    private String kelas;
    private String id;
    private String foto;
    private String kota_lahir;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKota_lahir() {
        return kota_lahir;
    }

    public void setKota_lahir(String kota_lahir) {
        this.kota_lahir = kota_lahir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

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

    public String getNohporangtua() {
        return nohporangtua;
    }

    public void setNohporangtua(String nohporangtua) {
        this.nohporangtua = nohporangtua;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getTahun_masuk() {
        return tahun_masuk;
    }

    public void setTahun_masuk(String tahun_masuk) {
        this.tahun_masuk = tahun_masuk;
    }

    public String getWali_murid() {
        return wali_murid;
    }

    public void setWali_murid(String wali_murid) {
        this.wali_murid = wali_murid;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nisn);
        parcel.writeString(this.nama);
        parcel.writeString(this.alamat);
        parcel.writeString(this.nohp);
        parcel.writeString(this.nohporangtua);
        parcel.writeString(this.tanggal_lahir);
        parcel.writeString(this.tahun_masuk);
        parcel.writeString(this.wali_murid);
        parcel.writeString(this.kelas);
        parcel.writeString(this.id);
        parcel.writeString(this.foto);
        parcel.writeString(this.kota_lahir);
        parcel.writeString(this.email);
    }

    public SiswaModel(){

    }

    protected SiswaModel(Parcel in){
        this.nisn = in.readString();
        this.nama = in.readString();
        this.alamat = in.readString();
        this.nohp = in.readString();
        this.nohporangtua = in.readString();
        this.tanggal_lahir = in.readString();
        this.tahun_masuk = in.readString();
        this.wali_murid = in.readString();
        this.kelas = in.readString();
        this.id = in.readString();
        this.foto = in.readString();
        this.kota_lahir = in.readString();
        this.email = in.readString();
    }

    public static  final Creator<SiswaModel> CREATOR = new Creator<SiswaModel>() {
        @Override
        public SiswaModel createFromParcel(Parcel parcel) {
            return new SiswaModel(parcel);
        }

        @Override
        public SiswaModel[] newArray(int i) {
            return new SiswaModel[i];
        }
    };
}
