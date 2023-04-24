package com.simplicity;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Pair<Storable, Integer>> container;

    public Inventory() {
        container = new ArrayList<>();
    }

    public void displayBarang() {
        for (Pair<Storable, Integer> item : container) {
            System.out.println(item.getKey().getNama() + " : " + item.getValue());
        }
    }

    public void addBarang(Storable Storable, int jumlah) {
        for (Pair<Storable, Integer> item : container) {
            if (item.getKey().getNama().equals(Storable.getNama())) {
                item.setValue(item.getValue() + jumlah);
                return;
            }
        }
        container.add(new Pair<>(Storable, jumlah));
    }

    public void reduceBarang(Storable Storable, int jumlah) {
        for (Pair<Storable, Integer> item : container) {
            if (item.getKey().getNama().equals(Storable.getNama())) {
                int newJumlah = item.getValue() - jumlah;
                if (newJumlah <= 0) {
                    container.remove(item);
                } else {
                    item.setValue(newJumlah);
                }
                return;
            }
        }
    }
}
