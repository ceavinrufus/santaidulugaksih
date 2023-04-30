package com.simplicity;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class Kasur extends Furniture {
    public Kasur(String nama) throws IllegalArgumentException {
        super(nama);
        if (nama.equals("Kasur Single")) {
            this.panjang = 4;
            this.lebar = 1;
            this.harga = 50;
        } else if (nama.equals("Kasur Queen Size")) {
            this.panjang = 4;
            this.lebar = 2;
            this.harga = 100;
        } else if (nama.equals("Kasur King Size")) {
            this.panjang = 5;
            this.lebar = 2;
            this.harga = 150;
        } else {
            throw new IllegalArgumentException("Nama kasur invalid!");
        }
    }

    @Override
    public String getNamaAksi() {
        return "Tidur";
    }

    @Override
    public void aksi(Sim sim) {
        Integer sleepTime = 0;
        while (sleepTime < 240) {
            try {
                String input = JOptionPane.showInputDialog(null, "Masukkan sleepTime:");
                if (input == null) {
                    // Kalo pencet tombol close
                    JOptionPane.getRootFrame().dispose();
                } else {
                    sleepTime = Integer.parseInt(input);
                    // Validasi sleepTime
                    if (sleepTime < 240) {
                    } else {

                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Masukkan angka yang valid", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        Waktu totalWaktu = Waktu.waktu();
        if (sleepTime >= 240) {
            try {
                TimeUnit.SECONDS.sleep(sleepTime);
                sim.getStats().tambahMood(sleepTime / 240 * 30);
                sim.getStats().tambahKesehatan(sleepTime / 240 * 20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            totalWaktu.addWaktu(sleepTime);
            sim.setWaktuTidakTidur(0);
            sim.setIsSehabisTidur(true);
            sim.setIsSehabisMakan(false);
        }
    }
}