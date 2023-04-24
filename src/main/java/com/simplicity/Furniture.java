package com.simplicity;

import javax.swing.JOptionPane;

import com.gui.SimPlicity;

public abstract class Furniture implements Storable {
    private String nama;
    protected int panjang;
    protected int lebar;
    protected int harga;

    public Furniture(String nama) {
        this.nama = nama;
    }

    @Override
    public String getNama() {
        return nama;
    }

    public int getPanjang() {
        return panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public int getHarga() {
        return harga;
    }

    @Override
    public void displayInfo() {
        String message = "Nama: " + nama + "\n" +
                "Dimensi: " + panjang + "x" + lebar + "\n" +
                "Harga: " + harga + "\n";

        Object[] options = { "Pasang", "Back" };
        int choice = JOptionPane.showOptionDialog(null, message, "Furniture Info", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Pasang
            if (SimPlicity.getCurrentSim()
                    .equals(SimPlicity.getCurrentSim().getCurrentPosition().getRumah().getPemilik())) {
                // Cari posisi
                // Pasang
            } else {
                JOptionPane.showOptionDialog(null, "Anda harus ke rumah anda dulu!", "Food Info",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            }
        } else if (choice == 1) {
            // Back
        }
    }

    public abstract String getNamaAksi();

    public abstract void aksi(Sim sim);
}