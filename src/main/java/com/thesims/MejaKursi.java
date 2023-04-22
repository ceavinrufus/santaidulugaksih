package com.thesims;
import javax.swing.JOptionPane;

public class MejaKursi extends BarangNonMakanan implements Interactable {
    public MejaKursi(String nama, int panjang, int lebar, int harga) {
        super(nama, panjang, lebar, harga);
    }

    public String getNamaAksi() {
        return "Makan";
    }

    public void aksi(Sim sim) {
        BarangMakanan makanan;
        // Ambil makanan dari inventory
        sim.getStatus().increaseKekenyangan(makanan.getKekenyangan());
        sim.addAction(getNamaAksi());
        // Lanjut
    }
}