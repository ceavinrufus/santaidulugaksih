package com.simplicity;

import java.util.Random;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;

public class Cermin extends Furniture {
    public Cermin() {
        super("Cermin");
        setPanjang(1);
        setLebar(1);
        setHarga(20);
    }

    @Override
    public String getNamaAksi() {
        return "Becermin";
    }

    @Override
    public void aksi(Sim sim) {
        Random rand = new Random();
        int randomNum = rand.nextInt(10);
        if (randomNum % 2 == 1) {
            sim.getStats().tambahMood(30);
            JOptionPane.showMessageDialog(null,
                    "Makin mood deh!",
                    "Cermin",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            sim.getStats().kurangMood(20);
            JOptionPane.showMessageDialog(null,
                    "Duh jadi gak mood :(",
                    "Cermin",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
