package com.simplicity;

public class Kompor extends BarangNonMakanan implements Interactable {
    public Kompor(String nama, int panjang, int lebar, int harga) {
        super(nama, panjang, lebar, harga);
    }

    public String getNamaAksi() {
        return "Memasak";
    }

    public void aksi(Sim sim) {
        System.out.println("Buku resep:");   
    }
}