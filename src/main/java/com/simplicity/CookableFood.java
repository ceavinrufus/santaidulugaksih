package com.simplicity;

public enum CookableFood implements Eatable {
    NASI("Nasi", 5, 5),
    KENTANG("Kentang", 3, 4),
    AYAM("Ayam", 10, 8),
    SAPI("Sapi", 12, 15),
    WORTEL("Wortel", 3, 2),
    BAYAM("Bayam", 3, 2),
    KACANG("Kacang", 2, 2),
    SUSU("Susu", 2, 1);

    private String nama;
    private int harga;
    private int kekenyangan;

    private CookableFood(String nama, int harga, int kekenyangan) {
        this.nama = nama;
        this.harga = harga;
        this.kekenyangan = kekenyangan;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    @Override
    public int getKekenyangan() {
        return kekenyangan;
    }
}