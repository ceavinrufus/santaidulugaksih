package com.simplicity.AbstractClass;

import java.util.Objects;

import javax.swing.*;

import com.gui.Game;
import com.simplicity.Purchasable;
import com.simplicity.Ruangan;
import com.simplicity.Rumah;
import com.simplicity.Sim;
import com.simplicity.Storable;

public abstract class Furniture implements Storable, Purchasable {
    private String nama;
    private int panjang;
    private int lebar;
    private int harga;

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

    protected void setPanjang(int panjang) {
        this.panjang = panjang;
    }

    protected void setLebar(int lebar) {
        this.lebar = lebar;
    }

    protected void setHarga(int harga) {
        this.harga = harga;
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
        Furniture furniture = (Furniture) o;
        return Objects.equals(nama, furniture.nama) &&
                panjang == furniture.panjang &&
                lebar == furniture.lebar &&
                harga == furniture.harga;
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
            Sim currentSim = Game.getInstance().getCurrentSim();
            Rumah currentHouse = Game.getInstance().getCurrentSim().getCurrentPosition().getRumah();
            if (currentSim.getNamaLengkap().equals(currentHouse.getNamaPemilik())) {
                // Cari posisi
                // Pasang
                String[] orientationOptions = { "Horizontal", "Vertikal" };

                int inputOrientation = JOptionPane.showOptionDialog(
                        null, "Pilih Orientasi", "Pasang Barang",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, orientationOptions, orientationOptions[0]);

                Boolean isHorizontal = (inputOrientation == 1) ? false : true;

                String inputX = JOptionPane.showInputDialog("Masukkan koordinat X: ");
                int koordinatX = Integer.parseInt(inputX);

                String inputY = JOptionPane.showInputDialog("Masukkan koordinat Y: ");
                int koordinatY = Integer.parseInt(inputY);

                Ruangan currentRuang = currentSim.getCurrentPosition().getRuang();
                Boolean isAvailable = currentRuang.isSpaceAvailable(this, isHorizontal, koordinatX, koordinatY);
                if (isAvailable) {
                    currentRuang.memasangBarang(this, isHorizontal, koordinatX, koordinatY);
                    currentSim.getInventory().reduceBarang(this, 1);
                    Game.getInstance().repaint();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, Barang tidak dapat dipasang karena lahan sudah digunakan.",
                            "Notification", JOptionPane.INFORMATION_MESSAGE);
                }

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