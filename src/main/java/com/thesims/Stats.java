package com.thesims;

public class Stats {

    private int mood;
    private int kekenyangan;
    private int kesehatan;

    public Stats() {
        mood = 80;
        kekenyangan = 80;
        kesehatan = 80;
    }

    public int getMood() {
        return mood;
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    public int getKesehatan() {
        return kesehatan;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }

    public void setKesehatan(int kesehatan) {
        this.kesehatan = kesehatan;
    }

    public void tambahMood(int tambah) {
        mood += tambah;
    }

    public void tambahKekenyangan(int tambah) {
        kekenyangan += tambah;
    }

    public void tambahKesehatan(int tambah) {
        kesehatan += tambah;
    }

    public void kurangiMood(int kurang) {
        mood -= kurang;
    }

    public void kurangiKekenyangan(int kurang) {
        kekenyangan -= kurang;
    }

    public void kurangiKesehatan(int kurang) {
        kesehatan -= kurang;
    }
}