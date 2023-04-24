package com.simplicity;

public class SimPosition {
    private Rumah rumah;
    private Ruangan ruang;
    
    public SimPosition(Rumah rumah, Ruangan ruang) {
        setPosition(rumah, ruang);
    }
    
    public Rumah getRumah() {
        return rumah;
    }

    public Ruangan getRuang() {
        return ruang;
    }
    
    public void setPosition (Rumah rumah, Ruangan ruang) {
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
