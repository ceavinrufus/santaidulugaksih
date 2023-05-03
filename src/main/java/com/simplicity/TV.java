package com.simplicity;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;

public class TV extends Furniture {
    public TV() {
        super("TV");
        setPanjang(1);
        setLebar(1);
        setHarga(75);
    }

    @Override
    public String getNamaAksi() {
        return "Nonton";
    }

    @Override
    public void aksi(Sim sim) {
        Integer menontonTime = 0;
        Boolean inputValid = false;
        while (!inputValid) {
            String input = "";
            try {
                input = JOptionPane.showInputDialog(null, "Masukkan waktu menonton (detik): ");
                if (input == null) {
                    // Kalo pencet tombol close
                    return;
                } else {
                    menontonTime = Integer.parseInt(input);
                    if (menontonTime < 30) {
                        JOptionPane.showMessageDialog(null, "Film apa yang punya durasi segitu? Minimal 30 detik :D",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        inputValid = true;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Waktu menonton harus berbentuk bilangan bulat, lho!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        try {
            TimeUnit.SECONDS.sleep(menontonTime);
            int batasWajar = 40;
            if (menontonTime > batasWajar) {
                sim.getStats().kurangKesehatan((menontonTime - batasWajar));
            }
            sim.getStats().tambahMood(menontonTime);
            sim.getStats().kurangKekenyangan(menontonTime);
            JOptionPane.showMessageDialog(null,
                    "Seru banget filmnya!",
                    "TV",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException e) {

        }
        Waktu.getInstance().addWaktu(menontonTime);
    }
}
