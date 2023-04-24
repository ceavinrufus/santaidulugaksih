package com.simplicity;
import javax.swing.JOptionPane;

public class Toilet extends Furniture {
    public Toilet() {
        super("Toilet");
        this.panjang = 1;
        this.lebar = 1;
        this.harga = 50;
    }

    @Override
    public String getNamaAksi() {
        return "Buang Air";
    }

    @Override
    public void aksi(Sim sim) {
        // Cek hari ini udah makan atau belum
        // Kalau udah, berak
        // Kalau belom, ya gabisa berak
    }
}