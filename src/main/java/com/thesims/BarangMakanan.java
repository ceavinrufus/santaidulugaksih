package com.thesims;

public class BarangMakanan extends Barang {
    private int kekenyangan;

    public BarangMakanan(String nama, int kekenyangan) {
        super(nama);
        this.kekenyangan = kekenyangan;
    }

    public int getKekenyangan(){
        return kekenyangan;
    }

    public void setKekenyangan(int kekenyangan){
        this.kekenyangan = kekenyangan;
    }
}