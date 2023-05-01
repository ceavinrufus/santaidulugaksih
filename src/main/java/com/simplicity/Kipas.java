package com.simplicity;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;

public class Kipas extends Furniture {
    public Kipas() {
        super("Kipas");
        setPanjang(1);
        setLebar(1);
        setHarga(60);
    }

    @Override
    public String getNamaAksi() {
        return "Ngadem";
    }

    @Override
    public void aksi(Sim sim) {
        sim.getStats().tambahMood(40);
        sim.getStats().kurangKekenyangan(10);
        JOptionPane.showMessageDialog(null,
                "Ahh, ademnya...",
                "Kipas",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
