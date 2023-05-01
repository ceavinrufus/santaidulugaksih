package com.simplicity;

import java.util.concurrent.TimeUnit;

import com.simplicity.AbstractClass.Furniture;

public class Toilet extends Furniture {
    public Toilet() {
        super("Toilet");
        setPanjang(1);
        setLebar(1);
        setHarga(50);
    }

    @Override
    public String getNamaAksi() {
        return "Buang Air";
    }

    @Override
    public void aksi(Sim sim) {
        // Cek hari ini udah makan atau belum
        // Kalau udah
        try {
            TimeUnit.SECONDS.sleep(10);
            sim.getStats().kurangKekenyangan(20);
            sim.getStats().tambahMood(10);
            sim.setWaktuTidakBuangAir(0);
        } catch (InterruptedException e) {
            // do something
        }
        Waktu.waktu().addWaktu(10);
        // Kalau belom, ya gabisa berak
    }
}