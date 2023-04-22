package com.thesims;
import javax.swing.JOptionPane;

public class Toilet extends BarangNonMakanan implements Interactable {
    public Toilet(String nama, int panjang, int lebar, int harga) {
        super(nama, panjang, lebar, harga);
    }

    public String getNamaAksi() {
        return "Buang Air";
    }

    public void aksi(Sim sim) {
        // Cek hari ini udah makan atau belum
        // Kalau udah, berak
        // Kalau belom, ya gabisa berak
    }
}