package com.simplicity;

public enum NonCookableFood implements Eatable {
    NASI_AYAM("Nasi Ayam", "Nasi,Ayam", 16),
    NASI_KARI("Nasi Kari", "Nasi,Kentang,Wortel,Sapi", 30),
    SUSU_KACANG("Susu Kacang", "Susu,Kacang", 5),
    TUMIS_SAYUR("Tumis Sayur", "Wortel,Bayam", 5),
    BISTIK("Bistik", "Kentang,Sapi", 22);
    
    private String namaMakanan;
    private String[] resep;
    private int kekenyangan;

    private NonCookableFood(String namaMakanan, String resep, int kekenyangan) {
        this.namaMakanan = namaMakanan;
        this.resep = resep.split(",");
        this.kekenyangan = kekenyangan;
    }

    public String getNamaMakanan(){
        return namaMakanan;
    }

    public String[] getResep(){
        return resep;
    }

    public int getKekenyangan(){
        return kekenyangan;
    }
}