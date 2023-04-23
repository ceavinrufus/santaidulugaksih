package com.simplicity;

import java.util.*;
public enum Pekerjaan {
    BADUT_SULAP("Badut Sulap", 15),
    KOKI("Koki", 30),
    POLISI("Polisi", 35),
    PROGRAMMER("Programmer", 45),
    DOKTER("Dokter", 50);

    private String nama;
    private int gaji;

    Pekerjaan(String nama, int gaji) {
        this.nama = nama;
        this.gaji = gaji;
    }

    public String getNama() {
        return nama;
    }

    public int getGaji() {
        return gaji;
    }

    public void setNama(String nama){
        this.nama=nama;
    }
    public void setGaji(int gaji){
        this.gaji=gaji;
    }
}
