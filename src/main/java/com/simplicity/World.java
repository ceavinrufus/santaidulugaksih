package com.simplicity;

import java.util.*;

public class World {
    int panjang;
    int lebar;
    List<Rumah> daftarRumah;
    List<List<Boolean>> lahanKosong;

    public World(int panjang, int lebar) {
        this.panjang = panjang;
        this.lebar = lebar;
        daftarRumah = new ArrayList<>();
        lahanKosong = new ArrayList<>();
        for (int i = 0; i < panjang; i++) {
            lahanKosong.add(new ArrayList<>());
            for (int j = 0; j < lebar; j++) {
                lahanKosong.get(i).add(true);
            }
        }
    }

    public int getPanjang() {
        return panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public List<Rumah> getDaftarRumah() {
        return daftarRumah;
    }

    public void tambahRumah(Rumah rumah) {
        if (rumah.getLokasi().getX() < 0 || rumah.getLokasi().getX() >= panjang
                || rumah.getLokasi().getY() < 0 || rumah.getLokasi().getY() >= lebar) {
            System.out.println("Lokasi rumah tidak valid");
            return;
        } else {
            if (lahanKosong.get(rumah.getLokasi().getX()).get(rumah.getLokasi().getY())) {
                daftarRumah.add(rumah);
                lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), false);
            } else {
                System.out.println("Lahan rumah sudah terisi");
            }
        }
    }

    public void hapusRumah(Rumah rumah) {
        if (rumah.getLokasi().getX() < 0 || rumah.getLokasi().getX() >= panjang
                || rumah.getLokasi().getY() < 0 || rumah.getLokasi().getY() >= lebar) {
            System.out.println("Lokasi rumah tidak valid");
            return;
        } else {
            if (lahanKosong.get(rumah.getLokasi().getX()).get(rumah.getLokasi().getY())) {
                System.out.println("Lahan rumah kosong");
            } else {
                daftarRumah.remove(rumah);
                lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), true);
            }
        }
    }

    public void printWorld() {
        for (int i = 0; i < panjang; i++) {
            for (int j = 0; j < lebar; j++) {
                if (lahanKosong.get(i).get(j)) {
                    System.out.print("Available");
                } else {
                    System.out.print("Occupied");
                }
            }
            System.out.println();
        }
    }
}
