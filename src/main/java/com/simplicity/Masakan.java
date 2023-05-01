package com.simplicity;

public class Masakan extends BarangMakanan {
    private BahanMakanan[] bahan;

    public Masakan(String nama, int kekenyangan) {
        super(nama, kekenyangan);
    }

    public void addBahan(BahanMakanan bahanBaru){
        if (bahan == null) {
            bahan = new BahanMakanan[1];
            bahan[0] = bahanBaru;
        } else {
            BahanMakanan[] newBahan = new BahanMakanan[bahan.length + 1];
            for(int i = 0; i < bahan.length; i++){
                newBahan[i] = bahan[i];
            }
            newBahan[bahan.length] = bahanBaru;
            bahan = newBahan;
        }
    }
}