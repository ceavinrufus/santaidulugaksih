package com.simplicity;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;

public class Jam extends Furniture {

    public Jam() {
        super("Jam");
        setPanjang(1);
        setLebar(1);
        setHarga(10);
    }

    @Override
    public String getNamaAksi() {
        return "Melihat Waktu";
    }

    @Override
    public void aksi(Sim sim) {
        Waktu waktu = Waktu.waktu();
        int totalWaktu = waktu.getWaktu();
        int hariKe = totalWaktu % (12 * 60);
        int sisaWaktuSekon = totalWaktu - (hariKe * 12 * 60);
        int sisaWaktuMenit = sisaWaktuSekon / 60;
        String info = String.format("Waktu sisa hari ini: %d sekon atau %d menit", sisaWaktuSekon, sisaWaktuMenit);
        JOptionPane.showMessageDialog(null, info, "Info Waktu", JOptionPane.INFORMATION_MESSAGE);
    }
}