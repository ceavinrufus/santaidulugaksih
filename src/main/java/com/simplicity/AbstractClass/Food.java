package com.simplicity.AbstractClass;

import javax.swing.JOptionPane;

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

    // GUI
    @Override
    public void displayInfo() {
        String message = "Nama: " + nama + "\n" +
                "Kekenyangan: " + kekenyangan + "\n";

        Object[] options = { "Makan", "Back" };
        JOptionPane.showOptionDialog(null, message, "Food Info", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
}
