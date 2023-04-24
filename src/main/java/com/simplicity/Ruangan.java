package com.simplicity;

public class Ruangan {
    private String namaRuangan;
    // private int noRuang;
    private Peta<Furniture> petaBarang = new Peta<Furniture>(6, 6);

    public Ruangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
    }

    public Ruangan(String namaRuangan, int noRuang) {
        this.namaRuangan = namaRuangan;
        // this.noRuang = noRuang;
    }

    public Peta<Furniture> getPeta() {
        return petaBarang;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    // public int getNoRuang() {
    // return noRuang;
    // }

    public void memasangBarang(Furniture barang, Boolean isHorizontal, int x, int y) {
        // belum dihandle buat checking spacenya, soon dibuat.
        if (isHorizontal) {
            for (int i = x; i < x + barang.getPanjang(); i++) {
                for (int j = y; j < y + barang.getLebar(); j++) {
                    petaBarang.setElement(i, j, barang);
                }
            }
        } else {
            for (int i = x; i < x + barang.getLebar(); i++) {
                for (int j = y; j < y + barang.getPanjang(); j++) {
                    petaBarang.setElement(i, j, barang);
                }
            }
        }
    }
}