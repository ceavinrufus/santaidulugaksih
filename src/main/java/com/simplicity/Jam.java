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
        int waktuHariIni = totalWaktu;
        while (waktuHariIni >= 720) {
            waktuHariIni -= 720;
        }
        int sisaWaktuHariIni = (12*60) - waktuHariIni;
        int sisaWaktuHariIniMenit = (int) sisaWaktuHariIni / 60;
        String info = String.format("Waktu sisa hari ini: %d sekon atau %d menit", sisaWaktuHariIni, sisaWaktuHariIniMenit);
        JOptionPane.showMessageDialog(null, info, "Info Waktu", JOptionPane.INFORMATION_MESSAGE);
    }
}