package com.simplicity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.gui.Game;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Pair<Storable, Integer>> container;

    public Inventory() {
        container = new ArrayList<>();
    }

    public ArrayList<Pair<Storable, Integer>> getItems() {
        return container;
    }

    public void displayBarang() {
        for (Pair<Storable, Integer> item : container) {
            System.out.println(item.getKey().getNama() + " : " + item.getValue());
        }
    }

    public void addBarang(Storable barang, int jumlah) {
        for (Pair<Storable, Integer> item : container) {
            if (item.getKey().getNama().equals(barang.getNama())) {
                item.setValue(item.getValue() + jumlah);
                return;
            }
        }
        container.add(new Pair<>(barang, jumlah));
    }

    public void reduceBarang(Storable barang, int jumlah) throws IllegalArgumentException {
        for (Pair<Storable, Integer> item : container) {
            if (item.getKey().getNama().equals(barang.getNama())) {
                int newJumlah = item.getValue() - jumlah;
                if (newJumlah == 0) {
                    container.remove(item);
                } else if (newJumlah < 0) {
                    throw new IllegalArgumentException("Invalid jumlah!");
                } else {
                    item.setValue(newJumlah);
                }
                return;
            }
        }
    }

    public boolean isContains(String bahan) {
        boolean contains = false;
        int i = 0;
        while (contains && i < container.size()) {
            if (container.get(i).getKey().getNama().equals(bahan)) {
                contains = true;
            }
            i++;
        }
        return contains;
    }

    // GUI
    public void displayInventory(Class<? extends Storable> className) {
        // testing
        addBarang(new Kasur("Kasur Queen Size"), 5);
        addBarang(NonCookableFood.AYAM, 10);

        ArrayList<Storable> temp = new ArrayList<Storable>();

        int count = 0;
        for (Pair<? extends Storable, Integer> pair : container) {
            if (className.isAssignableFrom(pair.getKey().getClass())) {
                temp.add(count, pair.getKey());
                count++;
            }
        }

        // matriks data inventory
        String[][] tableData = new String[count][2];
        String[] columnNames = { "Item Name", "Quantity" };
        int index = 0;
        for (int i = 0; i < container.size(); i++) {
            if (className.isAssignableFrom(container.get(i).getKey().getClass())) {
                tableData[index][0] = container.get(i).getKey().getNama(); // Item name
                tableData[index][1] = String.valueOf(container.get(i).getValue()); // Quantity
                index++;
            }
        }

        // table data
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);

        // Menampilkan option pane
        String[] options = { "Info", "Back" }; // custom buttons
        int choice = JOptionPane.showOptionDialog(null, new JScrollPane(table), "Inventory", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Info
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                temp.get(selectedRow).displayInfo();
                displayInventory(className);
            } else {
                JOptionPane.showMessageDialog(null, "Kamu belum memilih barang!", "Sim Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (choice == 1) {
        }
    }
}
