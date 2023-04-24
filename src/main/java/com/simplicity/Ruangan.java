package com.simplicity;

public class Ruangan {
    private int noRuang;
    private Peta<BarangNonMakanan> petaRuangan = new Peta<BarangNonMakanan>(6, 6);

    public Ruangan(int noRuang){
        this.noRuang = noRuang;
    }

    public void memasangBarang(BarangNonMakanan barang, Boolean isHorizontal, int x, int y) {
        // belum dihandle buat checking spacenya, soon dibuat.
        for (int i = x; i < barang.getPanjang(); i++) {
            for (int j = y; j < barang.getLebar(); j++) {
                if (isHorizontal) {
                    petaRuangan.setElement(i, j, barang);
                } else {
                    petaRuangan.setElement(j, i, barang);
                }
            }
        }
    }
}
