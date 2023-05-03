package com.simplicity;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;

public class Tanaman extends Furniture {
    public Tanaman() {
        super("Tanaman");
        setPanjang(1);
        setLebar(1);
        setHarga(20);
    }

    @Override
    public String getNamaAksi() {
        return "Menyiram Tanaman";
    }

    @Override
    public void aksi(Sim sim) {
        int plantTime = 60;
        try {
            TimeUnit.SECONDS.sleep(plantTime);
            sim.getStats().tambahMood(30);
            sim.getStats().kurangKekenyangan(30);
            JOptionPane.showMessageDialog(null,
                    "Seneng banget abis nyiram tanaman!",
                    "Tanaman",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException e) {

        }
        Waktu.getInstance().addWaktu(plantTime);
    }
}
