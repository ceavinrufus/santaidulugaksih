package com.simplicity;

public enum NonCookableFood implements Eatable {
    NASI_AYAM("Nasi Ayam", "Nasi,Ayam", 16),
    NASI_KARI("Nasi Kari", "Nasi,Kentang,Wortel,Sapi", 30),
    SUSU_KACANG("Susu Kacang", "Susu,Kacang", 5),
    TUMIS_SAYUR("Tumis Sayur", "Wortel,Bayam", 5),
    BISTIK("Bistik", "Kentang,Sapi", 22);
    
    private String nama;
    private String[] resep;
    private int kekenyangan;

    private NonCookableFood(String nama, String resep, int kekenyangan) {
        this.nama = nama;
        this.resep = resep.split(",");
        this.kekenyangan = kekenyangan;
    }

    public String getNama(){
        return nama;
    }

    public String[] getResep(){
        return resep;
    }

    @Override
    public int getKekenyangan(){
        return kekenyangan;
    }
}