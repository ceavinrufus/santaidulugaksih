package com.simplicity;

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

    public void tambahMood(int mood) {
        this.mood += mood;
        if (this.mood > 100)  {
            this.mood = 100;
        }
    }

    public void tambahKekenyangan(int kekenyangan) {
        this.kekenyangan += kekenyangan;
        if (this.kekenyangan > 100)  {
            this.kekenyangan = 100;
        }
    }

    public void tambahKesehatan(int kesehatan) {
        this.kesehatan += kesehatan;
        if (this.kesehatan > 100)  {
            this.kesehatan = 100;
        }
    }

    public void kurangMood(int mood) {
        this.mood -= mood;
        if (this.mood < 0) {
            this.mood = 0;
        }
    }

    public void kurangKekenyangan(int kekenyangan) {
        this.kekenyangan -= kekenyangan;
        if (this.kekenyangan < 0) {
            this.kekenyangan = 0;
        }
    }

    public void kurangKesehatan(int kesehatan) {
        this.kesehatan -= kesehatan;
        if (this.kesehatan < 0) {
            this.kesehatan = 0;
        }
    }
}