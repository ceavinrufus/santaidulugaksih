package com.simplicity;

import java.util.*;

public class Ruangan {
    private String namaRuangan;
    private Peta<Furniture> petaBarang = new Peta<Furniture>(6, 6);

    public Ruangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
    }

    public Ruangan(String namaRuangan, int noRuang) {
        this.namaRuangan = namaRuangan;
    }

    public Peta<Furniture> getPeta() {
        return petaBarang;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public void displayBarang() {
        TreeSet<Furniture> listBarang = new TreeSet<Furniture>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Furniture barang = petaBarang.getElement(i, j);
                if (barang != null) {
                    listBarang.add(barang);
                }
            }
        }
        
        int idx = 1;
        for (Furniture barang : listBarang) {
            System.out.printf("%d. %s\n", idx, barang.getNama());
            idx++;
        }
    }

    public void memasangBarang(Furniture barang, Boolean isHorizontal, int x, int y) {
        if (isSpaceAvailable(barang, isHorizontal, x, y)) {
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
        } else {
            System.out.println("Barang tidak dapat dipasang karena lahan sudah digunakan.");
        }
    }

    public Boolean isSpaceAvailable(Furniture barang, Boolean isHorizontal, int x, int y) {
        Boolean isAvailable = true;
        if (isHorizontal) {
            for (int i = x; i < x + barang.getPanjang(); i++) {
                for (int j = y; j < y + barang.getLebar(); j++) {
                    if (petaBarang.getElement(i, j) != null) isAvailable = false;
                }
            }
        } else {
            for (int i = x; i < x + barang.getLebar(); i++) {
                for (int j = y; j < y + barang.getPanjang(); j++) {
                    if (petaBarang.getElement(i, j) != null) isAvailable = false;
                }
            }
        }

        return isAvailable;
    }

    public void mengambilBarang(Furniture takenObject) {
        // So far, asumsi barang unik.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Furniture placedObject = petaBarang.getElement(i, j);
                if (placedObject.getNama().equals(takenObject.getNama())) {
                    petaBarang.setElement(i, j, null);
                }
            }
        }
    }

    public Furniture findBarang(String name) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Furniture placedObject = petaBarang.getElement(i, j);
                if (placedObject.getNama().equals(name)) {
                    return placedObject;
                }
            }
        }
        return null;
    }
}