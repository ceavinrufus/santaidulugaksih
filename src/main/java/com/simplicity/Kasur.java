package com.simplicity;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;

public class Kasur extends Furniture {
    public Kasur(String nama) throws IllegalArgumentException {
        super(nama);
        if (nama.equals("Kasur Single")) {
            setPanjang(4);
            setLebar(1);
            setHarga(50);
        } else if (nama.equals("Kasur Queen Size")) {
            setPanjang(4);
            setLebar(2);
            setHarga(100);
        } else if (nama.equals("Kasur King Size")) {
            setPanjang(5);
            setLebar(2);
            setHarga(150);
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
            String input = "";
            try {
                input = JOptionPane.showInputDialog(null, "Masukkan waktu tidur (detik): ");
                if (input == null) {
                    // Kalo pencet tombol close
                    return;
                } else {
                    sleepTime = Integer.parseInt(input);
                    if (sleepTime < 240) {
                        JOptionPane.showMessageDialog(null, "Minimal tidur selama 240 detik", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Waktu tidur harus berbentuk bilangan bulat, lho!", "Error",
                        JOptionPane.ERROR_MESSAGE);
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
            Waktu.waktu().addWaktu(sleepTime);
            sim.setWaktuTidakTidur(0);
            sim.setIsSehabisTidur(true);
            sim.setIsSehabisMakan(false);
            JOptionPane.showMessageDialog(null, "Enaknya tidur " + sleepTime + " detik!", "Kasur",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}