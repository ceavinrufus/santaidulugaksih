package com.simplicity.AbstractClass;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.gui.Game;
import com.simplicity.*;
import com.simplicity.Interface.Storable;

public abstract class Food implements Storable {
    private String nama;
    private int kekenyangan;

    public Food(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    protected void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }

    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Furniture)) {
            return false;
        }
        Food food = (Food) o;
        return Objects.equals(nama, food.nama) &&
                kekenyangan == food.kekenyangan;
    }

    // GUI
    @Override
    public void displayInfo() {
        String message = "Nama: " + getNama() + "\n" +
                "Kekenyangan: " + kekenyangan + "\n";

        Object[] options = { "Makan", "Back" };
        int choice = JOptionPane.showOptionDialog(null, message, "Food Info", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Makan
            Sim sim = Game.getInstance().getCurrentSim();
            try {
                JOptionPane.getRootFrame().dispose();
                TimeUnit.SECONDS.sleep(30);
                sim.getInventory().reduceBarang(this, 1);
                sim.getStats().tambahKekenyangan(this.getKekenyangan());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Waktu.waktu().addWaktu(30);

            // if (true) {
            // } else {
            // JOptionPane.showOptionDialog(null, "Anda harus ke rumah anda dulu!", "Food
            // Info",
            // JOptionPane.DEFAULT_OPTION,
            // JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            // }
        } else if (choice == 1) {
            // Back
        }
    }
}
