package com.simplicity;

public class Ruangan {
    private String namaRuangan;
    private int noRuang;
    private Peta<BarangNonMakanan> petaBarang = new Peta<BarangNonMakanan>(6, 6);

    public Ruangan(String namaRuangan, int noRuang) {
        this.namaRuangan = namaRuangan;
        this.noRuang = noRuang;
    }

    public Peta<BarangNonMakanan> getPeta() {
        return petaBarang;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public int getNoRuang() {
        return noRuang;
    }

    public void memasangBarang(BarangNonMakanan barang, Boolean isHorizontal, int x, int y) {
        // belum dihandle buat checking spacenya, soon dibuat.
        for (int i = x; i < barang.getPanjang(); i++) {
            for (int j = y; j < barang.getLebar(); j++) {
                if (isHorizontal) {
                    petaBarang.setElement(i, j, barang);
                } else {
                    petaBarang.setElement(j, i, barang);
                }
            }
        }
    }
}
