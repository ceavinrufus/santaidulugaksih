package com.simplicity;

public abstract class Furniture implements Storable {
    private String nama;
    protected int panjang;
    protected int lebar;
    protected int harga;

    public Furniture(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public int getPanjang() {
        return panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public int getHarga() {
        return harga;
    }

    public abstract String getNamaAksi();

    public abstract void aksi(Sim sim);
}