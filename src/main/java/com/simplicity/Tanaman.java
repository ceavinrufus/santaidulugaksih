package com.simplicity;

import javax.swing.JOptionPane;
import com.gui.Game;

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

        Game.getInstance().getCurrentSim().setActiveStatus(getNamaAksi());
        Game.getInstance().mulaiAksi(plantTime);

        sim.getStats().tambahMood(30);
        sim.getStats().kurangKekenyangan(30);
        JOptionPane.showMessageDialog(null,
                "Seneng banget abis nyiram tanaman!",
                "Tanaman",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
