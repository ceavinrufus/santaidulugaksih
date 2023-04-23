package com.simplicity;

import java.util.*;

public class Rumah {
    /*
     * Rumah adalah lingkungan virtual berisi ruangan-ruangan. Pertama kali dimulai,
     * rumah sim hanya memiliki 1 ruangan yaitu kamar berukuran 6x6. Sim dapat
     * melakukan upgrade terhadap rumah dengan menambahkan ruangan-ruangan baru.
     * Satu ruangan hanya dapat terhubung dengan 4 ruangan lainnya (atas, bawah,
     * kanan, dan kiri).
     * 
     * Rumah memiliki lokasi (x,y) dan daftar ruangan apa saja yang ada pada rumah
     * tersebut
     * 
     */

    String nama;
    Point lokasi;
    List<Ruangan> daftarRuangan;

    public Rumah(String nama, int x, int y) {
        this.nama = nama;
        this.lokasi = new Point(x, y);
        daftarRuangan = new ArrayList<>();
    }

    public String getNama() {
        return nama;
    }

    public Point getLokasi() {
        return lokasi;
    }

    public List<Ruangan> getDaftarRuangan() {
        return daftarRuangan;
    }

    public void tambahRuangan(Ruangan ruangan) {
        daftarRuangan.add(ruangan);
    }
}