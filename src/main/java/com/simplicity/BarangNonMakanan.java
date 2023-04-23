package com.simplicity;

public abstract class BarangNonMakanan extends Barang {
    private int panjang;
    private int lebar;
    private int harga;
    private Point lokasi;

    public BarangNonMakanan(String nama, int panjang, int lebar, int harga) {
        super(nama);
        this.panjang = panjang;
        this.lebar = lebar;
        this.harga = harga;
        lokasi = null;
    }

    public int getPanjang(){
        return panjang;
    }

    public int getLebar(){
        return lebar;
    }

    public int getHarga(){
        return harga;
    }

    public void setPanjang(int panjang){
        this.panjang = panjang;
    }

    public void setLebar(int lebar){
        this.lebar = lebar;
    }

    public void setHarga(int harga){
        this.harga = harga;
    }
}