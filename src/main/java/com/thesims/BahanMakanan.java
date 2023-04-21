package com.thesims;

public class BahanMakanan extends BarangMakanan {
    private int harga;

    public BahanMakanan(String nama, int kekenyangan, int harga) {
        super(nama, kekenyangan);
        this.harga = harga;
    }

    public int getHarga(){
        return harga;
    }

    public void setHarga(int harga){
        this.harga = harga;
    }
}