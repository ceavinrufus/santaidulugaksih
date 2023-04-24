package com.simplicity;

public class Ruangan {
    private String namaRuangan;
    private int noRuang;
    private Peta<Furniture> petaBarang = new Peta<Furniture>(6, 6);

    public Ruangan(String namaRuangan, int noRuang) {
        this.namaRuangan = namaRuangan;
        this.noRuang = noRuang;
    }

    public Peta<Furniture> getPeta() {
        return petaBarang;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public int getNoRuang() {
        return noRuang;
    }

    public void memasangBarang(Furniture Furniture, Boolean isHorizontal, int x, int y) {
        // belum dihandle buat checking spacenya, soon dibuat.
        for (int i = x; i < Furniture.getPanjang(); i++) {
            for (int j = y; j < Furniture.getLebar(); j++) {
                if (isHorizontal) {
                    petaBarang.setElement(i, j, Furniture);
                } else {
                    petaBarang.setElement(j, i, Furniture);
                }
            }
        }
    }
}
