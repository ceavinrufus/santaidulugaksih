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

    // public void setPekerjaan()

    public int getUang() {
        return uang;
    }

    public void setUang(int uang) {
        this.uang = uang;
    }

    public Stats getStatus() {
        return status;
    }

    public void setStatus(Stats status) {
        this.status = status;
    }

    public SimPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(SimPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void interact(Interactable barang){
        barang.aksi(this);
    }
}
