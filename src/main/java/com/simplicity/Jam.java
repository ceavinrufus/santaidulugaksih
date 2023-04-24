package com.simplicity;
import javax.swing.JOptionPane;

public class Jam extends Furniture {
    public Jam() {
        super("Jam");
        this.panjang = 1;
        this.lebar = 1;
        this.harga = 10;
    }

    @Override
    public String getNamaAksi() {
        return "Melihat Waktu";
    }

    @Override
    public void aksi(Sim sim) {
    }
}