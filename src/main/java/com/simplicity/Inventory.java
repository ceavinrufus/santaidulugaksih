package com.simplicity;

import javax.swing.*;
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

    // GUI
    public void displayInventory() {
        // testing
        // addBarang(new Kasur("Kasur Queen Size"), 5);
        // addBarang(new Kasur("Kasur King Size"), 10);

        // matriks data inventory
        String[][] tableData = new String[container.size()][2];
        for (int i = 0; i < container.size(); i++) {
            tableData[i][0] = container.get(i).getKey().getNama(); // Replace getNama() with the actual method to get
                                                                   // the name of the item
            tableData[i][1] = String.valueOf(container.get(i).getValue()); // get the item quantity and convert it to a
                                                                           // String
        }

        // table data
        String[] columnNames = { "Item", "Quantity" };
        JTable table = new JTable(tableData, columnNames);

        // Menampilkan option pane
        String[] options = { "Pasang", "Cancel" }; // custom buttons
        int choice = JOptionPane.showOptionDialog(null, new JScrollPane(table), "Inventory", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // get item dari the tabel
        if (choice == 0) {
            // Pasang
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedOption = (String) table.getValueAt(selectedRow, 0);
                System.out.println("Selected option: " + selectedOption);
            }
        } else if (choice == 1) {
            // Cancel
        }
    }
}
