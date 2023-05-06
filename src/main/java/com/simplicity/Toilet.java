package com.simplicity;

import javax.swing.JOptionPane;
import com.gui.Game;

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
        if (sim.getIsSehabisMakan()) {
            Game.getInstance().mulaiAksi(10);

            sim.getStats().kurangKekenyangan(20);
            sim.getStats().tambahMood(10);
            sim.setWaktuTidakBuangAir(0);
            JOptionPane.showMessageDialog(null,
                    "Ah legaa..",
                    "Toilet",
                    JOptionPane.INFORMATION_MESSAGE);
            Waktu.getInstance().addWaktu(10);
        } else {
            JOptionPane.showMessageDialog(null, "Kok bisa udah buang air padahal belum makan?", "Sus",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}