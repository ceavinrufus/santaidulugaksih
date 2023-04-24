package com.simplicity;

public class Ruangan {
    private int noRuang;
    private Peta<BarangNonMakanan> petaRuangan = new Peta<BarangNonMakanan>(6, 6);

    public Ruangan(int noRuang){
        this.noRuang = noRuang;
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                petaRuangan.setElement(i, j, null);
            }
        }
    }

    public int getNoRuang() {
        return noRuang;
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
