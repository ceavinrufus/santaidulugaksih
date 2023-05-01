package com.simplicity;

import java.util.concurrent.TimeUnit;

import com.simplicity.AbstractClass.Food;
import com.simplicity.AbstractClass.Furniture;
import com.simplicity.Interface.Storable;

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
        Food food = new NonCookableFood("Ayam");
        sim.getInventory().displayInventory(Food.class);
        // Ambil makanan dari inventory
        for (Pair<Storable, Integer> item : sim.getInventory().getItems()) {
            if (item.getKey().getNama().equals(food.getNama())) {
                try {
                    TimeUnit.SECONDS.sleep(30);
                    sim.getInventory().reduceBarang(food, 1);
                    sim.getStats().tambahKekenyangan(food.getKekenyangan());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Waktu.waktu().addWaktu(30);
            }
        }
        // sim.addAction(getNamaAksi());
        // Lanjut
    }
}