package com.thesims;

import java.util.*;

public class World {

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

    public int getPanjang() {
        return panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public List<List<Boolean>> getLahanKosong() {
        return lahanKosong;
    }

    public Map<Point, Rumah> getDaftarRumah() {
        return daftarRumah;
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
}
