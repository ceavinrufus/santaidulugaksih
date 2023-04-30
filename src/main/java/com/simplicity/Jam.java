package com.simplicity;

import com.simplicity.AbstractClass.Furniture;

public class Jam extends Furniture {

    public Jam() {
        super("Jam");
        setPanjang(1);
        setLebar(1);
        setHarga(10);
    }

    @Override
    public String getNamaAksi() {
        return "Melihat Waktu";
    }

    @Override
    public void aksi(Sim sim) {
        Waktu waktu = Waktu.waktu();
        int totalWaktu = waktu.getWaktu();
        int jam = (int) totalWaktu / 3600;
        int menit = (int) (totalWaktu - (jam * 3600)) / 60;
        int sekon = totalWaktu - jam * 3600 - menit * 60;
    }
}