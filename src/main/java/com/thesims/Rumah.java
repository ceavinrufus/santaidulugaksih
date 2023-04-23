package com.thesims;

import java.util.*;

/*
 Rumah adalah lingkungan virtual berisi ruangan-ruangan. Pertama kali dimulai, rumah sim hanya memiliki 1 ruangan yaitu kamar berukuran 6x6. 
 Sim dapat melakukan upgrade terhadap rumah dengan menambahkan ruangan-ruangan baru. Satu ruangan hanya dapat terhubung dengan 4 ruangan lainnya 
 (atas, bawah, kanan, dan kiri). Pemosisian ini akan direpresentasikan dalam bentuk linked list dalam linked list.
 
 Rumah memiliki lokasi (x,y) dan daftar ruangan apa saja yang ada pada rumah tersebut.
 */

public class Rumah {

    String nama;
    Point lokasi;
    List<List<Ruangan>> daftarRuangan;

    public Rumah(String nama, int x, int y) {
        this.nama = nama;
        this.lokasi = new Point(x, y);
        daftarRuangan = new LinkedList<>();
        daftarRuangan.add(new LinkedList<>());
        daftarRuangan.get(0).add(new Ruangan());
    }

    public String getNama() {
        return nama;
    }

    public Point getLokasi() {
        return lokasi;
    }

    public List<List<Ruangan>> getDaftarRuangan() {
        return daftarRuangan;
    }

    public void addRuangan(Ruangan ruangan) {
        if ((ruangan.getPosisi()).isEqual("Atas")) {
            if (daftarRuangan.get(0).get(0).getAtas() == null) {
                daftarRuangan.get(0).get(0).setAtas(ruangan);
                ruangan.setBawah(daftarRuangan.get(0).get(0));
                daftarRuangan.get(0).add(0, ruangan);
            } else {
                System.out.println("Ruangan atas sudah ada");
            }
        } else if ((ruangan.getPosisi()).isEqual("Bawah")) {
            if (daftarRuangan.get(0).get(0).getBawah() == null) {
                daftarRuangan.get(0).get(0).setBawah(ruangan);
                ruangan.setAtas(daftarRuangan.get(0).get(0));
                daftarRuangan.get(0).add(ruangan);
            } else {
                System.out.println("Ruangan bawah sudah ada");
            }
        } else if ((ruangan.getPosisi()).isEqual("Kanan")) {
            if (daftarRuangan.get(0).get(0).getKanan() == null) {
                daftarRuangan.get(0).get(0).setKanan(ruangan);
                ruangan.setKiri(daftarRuangan.get(0).get(0));
                daftarRuangan.get(0).add(ruangan);
            } else {
                System.out.println("Ruangan kanan sudah ada");
            }
        } else if ((ruangan.getPosisi()).isEqual("Kiri")) {
            if (daftarRuangan.get(0).get(0).getKiri() == null) {
                daftarRuangan.get(0).get(0).setKiri(ruangan);
                ruangan.setKanan(daftarRuangan.get(0).get(0));
                daftarRuangan.get(0).add(0, ruangan);
            } else {
                System.out.println("Ruangan kiri sudah ada");
            }
        } else {
            System.out.println("Posisi ruangan tidak valid");
        }
    }
}