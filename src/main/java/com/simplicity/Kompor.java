package com.simplicity;

public class Kompor extends Furniture {
    public Kompor(String nama) throws IllegalArgumentException {
        super(nama);
        if (nama.equals("Kompor Gas")) {
            this.panjang = 2;
            this.lebar = 1;
            this.harga = 100;
        } else if (nama.equals("Kompor Listrik")) {
            this.panjang = 1;
            this.lebar = 1;
            this.harga = 200;
        } else {
            throw new IllegalArgumentException("Nama kasur invalid!");
        }
    }

    @Override
    public String getNamaAksi() {
        return "Memasak";
    }

    @Override
    public void aksi(Sim sim) {
        NonCookableFood.displayResep();
    }
}