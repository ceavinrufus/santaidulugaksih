package com.simplicity;
import javax.swing.JOptionPane;

public class MejaKursi extends Furniture {
    public MejaKursi() {
        super("Meja dan Kursi");
        this.panjang = 3;
        this.lebar = 3;
        this.harga = 50;
    }

    @Override
    public String getNamaAksi() {
        return "Makan";
    }

    @Override
    public void aksi(Sim sim) {
        Eatable makanan;
        // Ambil makanan dari inventory
        // sim.getStatus().increaseKekenyangan(makanan.getKekenyangan());
        // sim.addAction(getNamaAksi());
        // Lanjut
    }
}