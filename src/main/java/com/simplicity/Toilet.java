package com.simplicity;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

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
        if (sim.getIsSehabisMakan()) {
            try {
                TimeUnit.SECONDS.sleep(10);
                sim.getStats().kurangKekenyangan(20);
                sim.getStats().tambahMood(10);
                sim.setWaktuTidakBuangAir(0);
            } catch (InterruptedException e) {

            }
            Waktu.waktu().addWaktu(10);
        } else {
            JOptionPane.showMessageDialog(null, "Kok bisa udah buang air padahal belum makan?", "Sus",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        // Kalau belom, ya gabisa berak
    }
}