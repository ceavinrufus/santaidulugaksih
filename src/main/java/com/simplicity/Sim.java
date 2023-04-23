package com.simplicity;
import java.util.*;

public class Sim {
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private int uang = 100;
    private Inventory inventory = new Inventory();
    private ArrayList<String> currentActions = new ArrayList<String>();
    private Stats status = new Stats();
    private boolean isLibur = false;
    private Point currentLocation;

    public Sim(String namaLengkap, ArrayList<Pekerjaan> listPekerjaan) {
        this.namaLengkap = namaLengkap;
        // Collections.shuffle(listPekerjaan);
        // this.pekerjaan = listPekerjaan.get(0);
        // currentLocation
    }

    public void interact(Interactable barang){
        barang.aksi(this);
    }
}
