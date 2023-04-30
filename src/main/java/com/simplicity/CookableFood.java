package com.simplicity;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public enum CookableFood implements Eatable {
    NASI_AYAM("Nasi Ayam", "Nasi,Ayam", 16),
    NASI_KARI("Nasi Kari", "Nasi,Kentang,Wortel,Sapi", 30),
    SUSU_KACANG("Susu Kacang", "Susu,Kacang", 5),
    TUMIS_SAYUR("Tumis Sayur", "Wortel,Bayam", 5),
    BISTIK("Bistik", "Kentang,Sapi", 22);

    private String nama;
    private String[] resep;
    private int kekenyangan;

    private CookableFood(String nama, String resep, int kekenyangan) {
        this.nama = nama;
        this.resep = resep.split(",");
        this.kekenyangan = kekenyangan;
    }

    public String getNama() {
        return nama;
    }

    public String[] getResep() {
        return resep;
    }

    @Override
    public int getKekenyangan() {
        return kekenyangan;
    }

    // GUI
    @Override
    public void displayInfo() {
        String message = "Nama: " + nama + "\n" +
                "Kekenyangan: " + kekenyangan + "\n";

        Object[] options = { "Makan", "Back" };
        JOptionPane.showOptionDialog(null, message, "Food Info", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    public static void displayResep() {
        ArrayList<CookableFood> resep = new ArrayList<>(Arrays.asList(CookableFood.values()));

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