package com.thesims;

import java.util.*;

public class Sim {
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private int uang;
    private Inventory inventory;
    private ArrayList<String> currentActions;
    private Stats status;
    private boolean isLibur;
    private Point currentLocation;
    private Rumah currentHouse;

    public Sim(String namaLengkap, List<Pekerjaan> listPekerjaan) {
        this.namaLengkap = namaLengkap;
        Collections.shuffle(listPekerjaan);
        this.pekerjaan = listPekerjaan.get(0);
        this.uang = 100;
        inventory = new Inventory();
        currentActions = new ArrayList<>();
        status = new Stats();
        isLibur = false;
        // currentLocation
        currentHouse = new Rumah();
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }
}
