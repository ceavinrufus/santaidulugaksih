package com.simplicity;

public class SimPosition {
    private Rumah rumah;
    private Ruangan ruang;
    private Point lokasi;

    public SimPosition(Rumah rumah, Ruangan ruang) {
        setPosition(rumah, ruang);
        lokasi = new Point(0, 0);
    }

    public Rumah getRumah() {
        return rumah;
    }

    public Ruangan getRuang() {
        return ruang;
    }

    public Point getLokasi() {
        return lokasi;
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
