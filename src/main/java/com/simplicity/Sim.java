package com.simplicity;

import java.util.*;

public class Sim {
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private double uang = 100;
    private Inventory inventory = new Inventory();
    private ArrayList<String> currentActions = new ArrayList<String>();
    private Stats stats = new Stats();
    private boolean isLibur = false;
    private SimPosition currentPosition;
    private int totalWorkTime = 0;
    private int changeJobTime = 720;

    public Sim(String namaLengkap, Rumah posisiRumah, Ruangan posisiRuangan) {
        this.namaLengkap = namaLengkap;
        List<Pekerjaan> listPekerjaan = Arrays.asList(Pekerjaan.values());
        Collections.shuffle(listPekerjaan);
        this.pekerjaan = listPekerjaan.get(0);
        currentPosition = new SimPosition(posisiRumah, posisiRuangan);
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getPekerjaan() {
        return pekerjaan.getNama();
    }

    public void setPekerjaan(Pekerjaan pekerjaan) {
        if (totalWorkTime >= 720) {
            this.pekerjaan = pekerjaan;
            uang -= pekerjaan.getGaji() * 0.5;
            totalWorkTime = 0;
            changeJobTime = 0;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double getUang() {
        return uang;
    }

    public void setUang(int uang) {
        this.uang = uang;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public SimPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(SimPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void kerja(int workingTime) {
        if (changeJobTime >= 720 && workingTime % 120 == 0) {
            stats.kurangKekenyangan(workingTime / 30 * 10);
            stats.kurangMood(workingTime / 30 * 10);
            totalWorkTime += workingTime;
            uang += pekerjaan.getGaji() / 240 * workingTime;
        }
    }

    public void olahraga(int workoutTime) {
        if (workoutTime % 20 == 0) {
            stats.tambahKesehatan(workoutTime / 20 * 5);
            stats.kurangKekenyangan(workoutTime / 20 * 5);
            stats.tambahMood(workoutTime / 20 * 10);
        }
    }

    public void interact(Furniture Furniture) {
        Furniture.aksi(this);
    }
}
