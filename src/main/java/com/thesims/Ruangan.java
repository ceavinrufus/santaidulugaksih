package com.thesims;

public class Ruangan {
    private int panjang;
    private int lebar;
    private String posisi;

    public Ruangan() {
        this.panjang = 6;
        this.lebar = 6;
        this.posisi = "Pusat";
    }

    public Ruangan(String posisi, Ruangan ruanganAcuan) {
        this.posisi = posisi;
        if (posisi.equals("Atas")) {
            this.panjang = ruanganAcuan.getPanjang();
            this.lebar = ruanganAcuan.getLebar();
        } else if (posisi.equals("Bawah")) {
            this.panjang = ruanganAcuan.getPanjang();
            this.lebar = ruanganAcuan.getLebar();
        } else if (posisi.equals("Kanan")) {
            this.panjang = ruanganAcuan.getLebar();
            this.lebar = ruanganAcuan.getPanjang();
        } else if (posisi.equals("Kiri")) {
            this.panjang = ruanganAcuan.getLebar();
            this.lebar = ruanganAcuan.getPanjang();
        }
    }

    public int getPanjang() {
        return panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public String getPosisi() {
        return posisi;
    }
}
