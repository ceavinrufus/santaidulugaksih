package com.simplicity;

import java.util.*;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;
import com.simplicity.ExceptionHandling.IllegalLocationException;

public class Ruangan {
    private String namaRuangan;
    private Peta<Furniture> petaBarang = new Peta<Furniture>(6, 6);

    public Ruangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
    }

    public Peta<Furniture> getPeta() {
        return petaBarang;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public void memasangBarang(Furniture barang, Boolean isHorizontal, int x, int y) {
        barang.setIsHorizontal(isHorizontal);
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

    public Boolean isSpaceAvailable(Furniture barang, Boolean isHorizontal, int x, int y) {
        Boolean isAvailable = true;
        if (x < 6 && y < 6) {
            if (isHorizontal) {
                for (int i = x; i < x + barang.getPanjang(); i++) {
                    for (int j = y; j < y + barang.getLebar(); j++) {
                        if (petaBarang.getElement(i, j) != null)
                            isAvailable = false;
                    }
                }
            } else {
                for (int i = x; i < x + barang.getLebar(); i++) {
                    for (int j = y; j < y + barang.getPanjang(); j++) {
                        if (petaBarang.getElement(i, j) != null)
                            isAvailable = false;
                    }
                }
            }
        } else {
            isAvailable = false;
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

    @Override
    public String toString() {
        return namaRuangan;
    }
}