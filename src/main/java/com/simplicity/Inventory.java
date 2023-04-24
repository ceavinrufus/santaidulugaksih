package com.simplicity;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Pair<Furniture, Integer>> container;

    public Inventory() {
        container = new ArrayList<>();
    }

    public void displayBarang() {
        for (Pair<Furniture, Integer> item : container) {
            System.out.println(item.getKey().getNama() + " : " + item.getValue());
        }
    }

    public void addBarang(Furniture Furniture, int jumlah) {
        for (Pair<Furniture, Integer> item : container) {
            if (item.getKey().getNama().equals(Furniture.getNama())) {
                item.setValue(item.getValue() + jumlah);
                return;
            }
        }
        container.add(new Pair<>(Furniture, jumlah));
    }

    public void reduceBarang(Furniture Furniture, int jumlah) {
        for (Pair<Furniture, Integer> item : container) {
            if (item.getKey().getNama().equals(Furniture.getNama())) {
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
