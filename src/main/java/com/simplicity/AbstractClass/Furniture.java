package com.simplicity.AbstractClass;

import java.util.Objects;

import javax.swing.*;

import com.gui.Game;
import com.simplicity.Ruangan;
import com.simplicity.Rumah;
import com.simplicity.Sim;
import com.simplicity.ExceptionHandling.IllegalInputException;
import com.simplicity.ExceptionHandling.IllegalLocationException;
import com.simplicity.Interface.Purchasable;
import com.simplicity.Interface.Storable;

public abstract class Furniture implements Storable, Purchasable {
    private String nama;
    private int panjang;
    private int lebar;
    private int harga;
    private boolean isHorizontal;

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

    public boolean getIsHorizontal() {
        return isHorizontal;
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

    public void setIsHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
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

                isHorizontal = (inputOrientation == 1) ? false : true;

                JTextField inputX = new JTextField();
                JTextField inputY = new JTextField();
                Object[] messageInput = {
                        "X:", inputX,
                        "Y:", inputY
                };

                Boolean inputValid = false;
                while (!inputValid) {
                    int option = JOptionPane.showConfirmDialog(null, messageInput, "Input Point",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            int koordinatX = Integer.parseInt(inputX.getText());
                            int koordinatY = Integer.parseInt(inputY.getText());
                            if ((koordinatX < 0 || koordinatX >= 6) || (koordinatY < 0 || koordinatY >= 6)) {
                                throw new IllegalLocationException("Pastikan x sama y kamu di antara 0-5, ya!");
                            } else {
                                if (isHorizontal) {
                                    if (koordinatX + this.getPanjang() > 6 || koordinatY + this.getLebar() > 6) {
                                        throw new IllegalLocationException("Waduh, gak muat!");
                                    }
                                } else {
                                    if (koordinatX + this.getLebar() > 6 || koordinatY + this.getPanjang() > 6) {
                                        throw new IllegalLocationException("Waduh, gak muat!");
                                    }
                                }
                            }

                            Ruangan currentRuang = currentSim.getCurrentPosition().getRuang();
                            if (currentRuang.isSpaceAvailable(this, isHorizontal, koordinatX, koordinatY)) {
                                currentRuang.memasangBarang(this, isHorizontal, koordinatX, koordinatY);
                                currentSim.getInventory().reduceBarang(this, 1);
                                inputValid = true;
                                Game.getInstance().repaint();
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Maaf, Barang tidak dapat dipasang karena lahan sudah digunakan.",
                                        "Notification",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Nilai koordinat harus berbentuk bilangan bulat, lho!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (IllegalLocationException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Notification",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        break;
                    }
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