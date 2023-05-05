package com.simplicity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.simplicity.AbstractClass.Furniture;
import com.simplicity.Interface.Storable;

public class Kompor extends Furniture {
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
    }

    public Kompor(String nama) throws IllegalArgumentException {
        super(nama);
        if (nama.equals("Kompor Gas")) {
            setPanjang(2);
            setLebar(1);
            setHarga(100);
        } else if (nama.equals("Kompor Listrik")) {
            setPanjang(1);
            setLebar(1);
            setHarga(200);
        } else {
            throw new IllegalArgumentException("Nama kompor invalid!");
        }
    }

    @Override
    public String getNamaAksi() {
        return "Memasak";
    }

    private boolean isBahanAda(Inventory inventory, CookableFood masakan) {
        boolean isBahanAda = true;
        for (String bahan : masakan.getResep()) {
            if (!inventory.contains(bahan)) {
                isBahanAda = false;
            }
        }
        return isBahanAda;
    }

    @Override
    public void aksi(Sim sim) {
        ArrayList<Resep> resep = new ArrayList<>(Arrays.asList(Resep.values()));

        String[][] tableData = new String[resep.size()][3];
        String[] columnNames = { "Masakan", "Resep", "Kekenyangan" };
        for (int i = 0; i < resep.size(); i++) {
            tableData[i][0] = resep.get(i).nama; // Item name
            tableData[i][1] = String.join(" + ", resep.get(i).resep); // Resep
            tableData[i][2] = String.valueOf(resep.get(i).kekenyangan); // Kekenyangan
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

        CookableFood masakan = null;
        if (choice == 0) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedOption = (String) table.getValueAt(selectedRow, 0);
                masakan = new CookableFood(selectedOption);
            } else {
                JOptionPane.showMessageDialog(null, "Kamu belum memilih masakan!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (masakan != null) {
            if (isBahanAda(sim.getInventory(), masakan)) {
                // TODO: Ini kayaknya perlu dipindah ke bawah gasih?
                // TimeUnit.SECONDS.sleep((int) 1.5 * masakan.getKekenyangan());
                int masakTime = (int) 1.5 * masakan.getKekenyangan();

                mulaiAksi(masakTime);

                for (String bahan : masakan.getResep()) {
                    for (Pair<Storable, Integer> item : sim.getInventory().getItems()) {
                        if (item.getKey().getNama().equals(bahan)) {
                            sim.getInventory().reduceBarang(item.getKey(), 1);
                            sim.getInventory().addBarang(masakan, 1);
                            sim.getStats().setMood(10);
                        }
                    }
                }
                
                JOptionPane.showMessageDialog(null, "Masakan selesai dimasak!", "Action finished",
                        JOptionPane.INFORMATION_MESSAGE);
                Waktu.getInstance().addWaktu((int) 1.5 * masakan.getKekenyangan());
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Anda tidak memiliki semua bahan yang diperlukan!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}