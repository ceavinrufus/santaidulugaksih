package com.thesims;

import java.util.*;

public class World {
    /*
     * World adalah dunia virtual tempat setiap Sim bisa bergerak dan berinteraksi
     * dengan objek-objek yang telah dibuat. World berbentuk 2 dimensi, dengan
     * ukuran total yang ditentukan dari panjang dan lebar dari World itu sendiri.
     * Di dalam world, akan ada rumah-rumah yang bisa dikunjungi.
     * 
     * World memiliki panjang, lebar, serta daftar apa saja rumah yang ada di dunia
     * tersebut.
     * 
     * Rumah ukurannya cuman 1x1 di koordinat world, untuk lokasi dari rumah
     * dibebaskan mau inputan user atau otomatis
     * 
     */

    int panjang;
    int lebar;
    List<List<Boolean>> lahanKosong;
    Map<Point, Rumah> daftarRumah;

    public World(int panjang, int lebar) {
        this.panjang = panjang;
        this.lebar = lebar;
        lahanKosong = new ArrayList<>(panjang);
        for (int i = 0; i < panjang; i++) {
            lahanKosong.add(new ArrayList<>(lebar));
            for (int j = 0; j < lebar; j++) {
                lahanKosong.get(i).add(true);
            }
        }
        daftarRumah = new HashMap<>();
    }

    public void addRumah(Rumah rumah) {
        if (daftarRumah.containsKey(rumah.getLokasi())) {
            System.out.println("Rumah sudah ada di lokasi tersebut");
        } else {
            daftarRumah.put(rumah.getLokasi(), rumah);
            lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), false);
        }
    }

    public void removeRumah(Rumah rumah) {
        if (daftarRumah.containsKey(rumah.getLokasi())) {
            daftarRumah.remove(rumah.getLokasi());
            lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), true);
        } else {
            System.out.println("Rumah tidak ada di lokasi tersebut");
        }
    }

    public void printWorld() {
        for (int i = 0; i < panjang; i++) {
            for (int j = 0; j < lebar; j++) {
                if (lahanKosong.get(i).get(j)) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }
}
