package com.thesims;

public abstract class Barang {
    private String nama;

    public Barang(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}