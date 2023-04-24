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
    private Rumah rumah;
    private SimPosition currentPosition;
    private int totalWorkTime = 0;
    private int changeJobTime = 720;

    private class SimPosition {
        private Rumah rumah;
        private Ruangan ruang;
        private Point lokasi;

        public SimPosition(Rumah rumah, Ruangan ruang) {
            setPosition(rumah, ruang);
        }

        public Rumah getRumah() {
            return rumah;
        }

        public Ruangan getRuang() {
            return ruang;
        }

        public void setPosition(Rumah rumah, Ruangan ruang) {
            this.rumah = rumah;
            this.ruang = ruang;
        }

        public void setRumah(Rumah rumah) {
            this.rumah = rumah;
        }

        public void setRuang(Ruangan ruang) {
            this.ruang = ruang;
        }
    }

    public Sim(String namaLengkap) {
        this.namaLengkap = namaLengkap;
        List<Pekerjaan> listPekerjaan = Arrays.asList(Pekerjaan.values());
        Collections.shuffle(listPekerjaan);
        this.pekerjaan = listPekerjaan.get(0);
        // currentLocation
        rumah = new Rumah(this);
        Ruangan ruang = rumah.getPeta().getElement(0, 0);
        currentPosition = new SimPosition(rumah, ruang);
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setPekerjaan(Pekerjaan pekerjaan) {
        if (totalWorkTime >= 720) {
            this.pekerjaan = pekerjaan;
            uang -= pekerjaan.getGaji() * 0.5;
            totalWorkTime = 0;
            changeJobTime = 0;
        }
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

    public void set(Stats stats) {
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
            // set kekenyangan
            // set mood
            totalWorkTime += workingTime;
            uang += pekerjaan.getGaji() / 240 * workingTime;
        }
    }

    public void interact(Furniture Furniture) {
        Furniture.aksi(this);
    }
}
