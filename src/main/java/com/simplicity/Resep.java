package com.simplicity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public enum Resep {
    NASI_AYAM("Nasi Ayam", "Nasi,Ayam", 16),
    NASI_KARI("Nasi Kari", "Nasi,Kentang,Wortel,Sapi", 30),
    SUSU_KACANG("Susu Kacang", "Susu,Kacang", 5),
    TUMIS_SAYUR("Tumis Sayur", "Wortel,Bayam", 5),
    BISTIK("Bistik", "Kentang,Sapi", 22);

    private String nama;
    private String[] resep;
    private int kekenyangan;

    private Resep(String nama, String resep, int kekenyangan) {
        this.nama = nama;
        this.resep = resep.split(",");
        this.kekenyangan = kekenyangan;
    }

    private String getNama() {
        return nama;
    }

    private String[] getResep() {
        return resep;
    }

    private int getKekenyangan() {
        return kekenyangan;
    }

    public static void displayResep() {
        ArrayList<Resep> resep = new ArrayList<>(Arrays.asList(Resep.values()));

        String[][] tableData = new String[resep.size()][3];
        String[] columnNames = { "Masakan", "Resep", "Kekenyangan" };
        for (int i = 0; i < resep.size(); i++) {
            tableData[i][0] = resep.get(i).getNama(); // Item name
            tableData[i][1] = String.join(" + ", resep.get(i).getResep()); // Resep
            tableData[i][2] = String.valueOf(resep.get(i).getKekenyangan()); // Kekenyangan
        }

        // table data
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);

        // Panjang kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        // Menampilkan option pane
        String[] options = { "Masak", "Cancel" }; // custom buttons
        int choice = JOptionPane.showOptionDialog(null, new JScrollPane(table), "Buku Resep",
                JOptionPane.DEFAULT_OPTION,
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
