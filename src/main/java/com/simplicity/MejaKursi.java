package com.simplicity;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

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
        //SUS
        Food food = new NonCookableFood("Ayam");
        sim.getInventory().displayInventory(Food.class);
        // Ambil makanan dari inventory
        for (Pair<Storable, Integer> item : sim.getInventory().getItems()) {
            if (item.getKey().getNama().equals(food.getNama())) {
                mulaiAksi(30);

                sim.getInventory().reduceBarang(food, 1);
                sim.getStats().tambahKekenyangan(food.getKekenyangan());
                JOptionPane.showMessageDialog(null,
                        "Yummy!",
                        "Meja dan Kursi",
                        JOptionPane.INFORMATION_MESSAGE);
                
                Waktu.getInstance().addWaktu(30);
            }
        }
    }
}