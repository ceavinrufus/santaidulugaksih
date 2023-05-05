package com.simplicity;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.util.List;

import com.simplicity.Interface.Leaveable;
import com.simplicity.Thread.RunnableBangunRumah;
import com.simplicity.Thread.RunnableBeliBarang;
import com.simplicity.Thread.ThreadManager;

import com.simplicity.AbstractClass.Furniture;

public class Jam extends Furniture {

    public Jam() {
        super("Jam");
        setPanjang(1);
        setLebar(1);
        setHarga(10);
    }

    @Override
    public String getNamaAksi() {
        return "Melihat Waktu";
    }

    @Override
    public void aksi(Sim sim) {
        String[] options = { "Lihat Waktu Pusat", "Lihat Waktu Sisa Aksi Pasif" };
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                switch (option) {
                    case "Lihat Waktu Pusat":
                        displayWaktuPusat();
                        break;
                    case "Lihat Waktu Sisa Aksi Pasif":
                        displayWaktuAksiPasif();
                        break;
                }
            });
            panel.add(button);
        }
        JOptionPane.showOptionDialog(null, panel, "Lihat Waktu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
    }

    private void displayWaktuPusat() {
        Waktu waktu = Waktu.getInstance();
        int totalWaktu = waktu.getWaktu();
        int waktuHariIni = totalWaktu;
        while (waktuHariIni >= 720) {
            waktuHariIni -= 720;
        }
        int sisaWaktuHariIni = (12 * 60) - waktuHariIni;
        int sisaWaktuHariIniMenit = (int) sisaWaktuHariIni / 60;
        String info = String.format("Waktu sisa hari ini: %d sekon atau %d menit", sisaWaktuHariIni,
                sisaWaktuHariIniMenit);
        JOptionPane.showMessageDialog(null, info, "Info Waktu", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayWaktuAksiPasif() {
        List<Leaveable> threadlist = ThreadManager.getInstance();
        String[][] tableData = new String[threadlist.size()][2];
        String[] columnNames = { "Aksi", "Sisa Waktu" };

        for (int i = 0; i < threadlist.size(); i++) {
            if (threadlist.get(i) instanceof RunnableBangunRumah) {
                tableData[i][0] = "Bangun Rumah";
                tableData[i][1] = String.valueOf(threadlist.get(i).getSisaWaktu());
            } else if (threadlist.get(i) instanceof RunnableBeliBarang) {
                RunnableBeliBarang barangTerkirim = (RunnableBeliBarang) threadlist.get(i);
                tableData[i][0] = String.format("Beli Barang (%s)", barangTerkirim.getBarang().getNama());
                tableData[i][1] = String.valueOf(threadlist.get(i).getSisaWaktu());
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);

        // Panjang kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(175);
        table.getColumnModel().getColumn(1).setPreferredWidth(25);

        String[] options = { "Back" };
        JOptionPane.showOptionDialog(null, new JScrollPane(table), "Lihat Waktu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

}