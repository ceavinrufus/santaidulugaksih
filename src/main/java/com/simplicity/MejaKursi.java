package com.simplicity;

import com.simplicity.AbstractClass.Food;
import com.simplicity.AbstractClass.Furniture;

public class MejaKursi extends Furniture {
    public MejaKursi() {
        super("Meja dan Kursi");
        setPanjang(3);
        setLebar(3);
        setHarga(50);
    }

    @Override
    public String getNamaAksi() {
        return "Makan";
    }

    @Override
    public void aksi(Sim sim) {
        sim.getInventory().displayInventory(Food.class);
    }

}