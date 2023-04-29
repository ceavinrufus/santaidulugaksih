package com.simplicity;

import javax.swing.*;

import com.gui.Game;

public abstract class Furniture implements Storable, Purchasable {
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
            Sim currentSim = Game.getInstance().getCurrentSim();
            Rumah currentHouse = Game.getInstance().getCurrentSim().getCurrentPosition().getRumah();
            if (currentSim.equals(currentHouse.getPemilik())) {
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