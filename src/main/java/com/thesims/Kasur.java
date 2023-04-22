package com.thesims;
import javax.swing.JOptionPane;

public class Kasur extends BarangNonMakanan implements Interactable {
    public Kasur(String nama, int panjang, int lebar, int harga) {
        super(nama, panjang, lebar, harga);
    }

    public String getNamaAksi() {
        return "Tidur";
    }

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