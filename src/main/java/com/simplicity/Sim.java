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
    private Rumah rumah;
    private SimPosition currentPosition;

    public Sim(String namaLengkap, ArrayList<Pekerjaan> listPekerjaan) {
        this.namaLengkap = namaLengkap;
        // Collections.shuffle(listPekerjaan);
        // this.pekerjaan = listPekerjaan.get(0);
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


    public void interact(Interactable barang){
        barang.aksi(this);
    }
}
