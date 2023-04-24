package com.simplicity;
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
        String input = JOptionPane.showInputDialog(null, "Masukkan jam kerja:");
        int jamKerja = 0;
        try {
            jamKerja = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Input tidak valid, masukkan angka saja!");
            return;
        }
    }
}