package com.simplicity;

import java.awt.GridLayout;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.gui.Game;
import com.simplicity.AbstractClass.Furniture;
import com.simplicity.ExceptionHandling.IllegalInputException;
import com.simplicity.Interface.*;

public class Sim {
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    // TODO: Gantii ini jadi 100
    private double uang = 1500;
    private Inventory inventory = new Inventory();
    private ArrayList<String> currentActions = new ArrayList<String>();
    private Stats stats = new Stats();
    private boolean isLibur = false;
    private SimPosition currentPosition;

    private int totalWorkTime = 0;
    private int waktuBolehGantiKerja = 720;
    private int waktuKerjaBelumDibayar = 0;
    private int waktuTidakTidur = 0;
    private int waktuTidakBuangAir = 0;
    private boolean isSehabisMakan = false;
    private boolean isSehabisTidur = false;
    private boolean isOnKunjungan = false;
    private boolean isDead = false;
    private int recentActionTime = 0;

    // Waktu Terpusat
    Waktu totalWaktu = Waktu.waktu();

    public Sim(String namaLengkap) {
        this.namaLengkap = namaLengkap;
        List<Pekerjaan> listPekerjaan = Arrays.asList(Pekerjaan.values());
        Collections.shuffle(listPekerjaan);
        this.pekerjaan = listPekerjaan.get(0);
    }

    public Sim(String namaLengkap, Rumah posisiRumah, Ruangan posisiRuangan) {
        this.namaLengkap = namaLengkap;
        List<Pekerjaan> listPekerjaan = Arrays.asList(Pekerjaan.values());
        Collections.shuffle(listPekerjaan);
        this.pekerjaan = listPekerjaan.get(0);
        currentPosition = new SimPosition(posisiRumah, posisiRuangan);
    }

    public class Stats {
        private int mood;
        private int kekenyangan;
        private int kesehatan;

        public Stats() {
            mood = 80;
            kekenyangan = 80;
            kesehatan = 80;
        }

        public int getMood() {
            return mood;
        }

        public int getKekenyangan() {
            return kekenyangan;
        }

        public int getKesehatan() {
            return kesehatan;
        }

        public void setMood(int mood) {
            this.mood = mood;
        }

        public void setKekenyangan(int kekenyangan) {
            this.kekenyangan = kekenyangan;
        }

        public void setKesehatan(int kesehatan) {
            this.kesehatan = kesehatan;
        }

        public void tambahMood(int mood) {
            this.mood += mood;
            if (this.mood > 100) {
                this.mood = 100;
            }
        }

        public void tambahKekenyangan(int kekenyangan) {
            this.kekenyangan += kekenyangan;
            if (this.kekenyangan > 100) {
                this.kekenyangan = 100;
            }
        }

        public void tambahKesehatan(int kesehatan) {
            this.kesehatan += kesehatan;
            if (this.kesehatan > 100) {
                this.kesehatan = 100;
            }
        }

        public void kurangMood(int mood) {
            this.mood -= mood;
            if (this.mood < 0) {
                this.mood = 0;
            }
        }

        public void kurangKekenyangan(int kekenyangan) {
            this.kekenyangan -= kekenyangan;
            if (this.kekenyangan < 0) {
                this.kekenyangan = 0;
            }
        }

        public void kurangKesehatan(int kesehatan) {
            this.kesehatan -= kesehatan;
            if (this.kesehatan < 0) {
                this.kesehatan = 0;
            }
        }
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getPekerjaan() {
        return pekerjaan.getNama();
    }

    public void setPekerjaan(Pekerjaan pekerjaan) {
        if (totalWorkTime >= 720) {
            this.pekerjaan = pekerjaan;
            uang -= pekerjaan.getGaji() * 0.5;
            totalWorkTime = 0;
            waktuBolehGantiKerja = 0;

        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double getUang() {
        return uang;
    }

    public void setUang(double uang) {
        this.uang = uang;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public int getRecentActionTime() {
        return recentActionTime;
    }

    public void setRecentActionTime(int waktu) {
        recentActionTime = waktu;
    }

    public SimPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(SimPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setWaktuTidakTidur(int waktu) {
        waktuTidakTidur = waktu;
    }

    public void trackTidur(int waktu) {
        if (!isSehabisTidur) {
            waktuTidakTidur += waktu;
        }
        if (waktuTidakTidur >= 600) {
            stats.kurangKesehatan(5);
            stats.kurangMood(5);
        }
    }

    public void setWaktuTidakBuangAir(int waktu) {
        waktuTidakBuangAir = waktu;
    }

    public void setSimDead() {
        isDead = true;
    }

    public void trackBuangAir(int waktu) {
        if (isSehabisMakan) {
            waktuTidakBuangAir += waktu;
        }
        if (waktuTidakBuangAir % 240 == 0 && waktuTidakBuangAir != 0) {
            stats.kurangKesehatan(5);
            stats.kurangMood(5);
        }
    }

    public void trackKunjungan(int waktu) {
        if (isOnKunjungan) {
            stats.kurangKekenyangan(waktu / 30 * 10);
            stats.tambahMood(waktu / 30 * 10);
        }
    }

    public void setIsSehabisTidur(boolean b) {
        isSehabisTidur = b;
    }

    public void setIsSehabisMakan(boolean b) {
        isSehabisMakan = b;
    }

    public boolean getIsSehabisMakan() {
        return isSehabisMakan;
    }

    public void setIsOnKunjungan(boolean b) {
        isOnKunjungan = b;
    }

    public void kerja() {
        Integer workingTime = 0;
        if (waktuBolehGantiKerja >= 720) {
            while (workingTime == 0 || workingTime % 120 != 0) {
                workingTime = inputActionTime();
            }
            try {
                TimeUnit.SECONDS.sleep(workingTime);
                totalWorkTime += workingTime;
                recentActionTime = workingTime;
                totalWaktu.addWaktu(workingTime);
                if (waktuKerjaBelumDibayar > 0) {
                    workingTime += waktuKerjaBelumDibayar;
                    waktuKerjaBelumDibayar = 0;
                }
                stats.kurangKekenyangan(workingTime / 30 * 10);
                stats.kurangMood(workingTime / 30 * 10);
                uang += pekerjaan.getGaji() * (workingTime % 240);
                waktuKerjaBelumDibayar += (workingTime - 240 * (workingTime % 240));
                isSehabisMakan = false;
                isSehabisTidur = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            JOptionPane.showMessageDialog(null, "Kerja selesai!", "Action finished", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void olahraga() {
        Integer workoutTime = 0;
        while (workoutTime % 20 != 0) {
            workoutTime = inputActionTime();
        }
        try {
            TimeUnit.SECONDS.sleep(workoutTime);
            recentActionTime = workoutTime;
            stats.tambahKesehatan(workoutTime / 20 * 5);
            stats.kurangKekenyangan(workoutTime / 20 * 5);
            stats.tambahMood(workoutTime / 20 * 10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        totalWaktu.addWaktu(workoutTime);
        isSehabisMakan = false;
        isSehabisTidur = false;
        JOptionPane.showMessageDialog(null, "Olahraga selesai!", "Action finished", JOptionPane.INFORMATION_MESSAGE);
    }

    public void memasak(CookableFood food) {
        boolean isBahanAda = true;
        for (String bahan : food.getResep()) {
            if (!inventory.isContains(bahan)) {
                isBahanAda = false;
            }
        }

        if (isBahanAda) {
            try {
                TimeUnit.SECONDS.sleep((int) 1.5 * food.getKekenyangan());
                for (String bahan : food.getResep()) {
                    for (Pair<Storable, Integer> item : inventory.getItems()) {
                        if (item.getKey().getNama().equals(bahan)) {
                            inventory.reduceBarang(item.getKey(), 1);
                            inventory.addBarang(food, 1);
                            stats.setMood(10);
                        }
                    }
                }
            } catch (InterruptedException e) {
                // do something
            }
            totalWaktu.addWaktu((int) 1.5 * food.getKekenyangan());
        }
    }

    public void beliBarang() {
        String[] buyOptions = { "Bahan Makanan", "Furniture" };
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        ArrayList<Purchasable> listPembelian = new ArrayList<Purchasable>();
        for (String option : buyOptions) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                switch (option) {
                    case "Bahan Makanan":
                        listPembelian.add(new NonCookableFood("Nasi"));
                        listPembelian.add(new NonCookableFood("Kentang"));
                        listPembelian.add(new NonCookableFood("Ayam"));
                        listPembelian.add(new NonCookableFood("Sapi"));
                        listPembelian.add(new NonCookableFood("Wortel"));
                        listPembelian.add(new NonCookableFood("Bayam"));
                        listPembelian.add(new NonCookableFood("Kacang"));
                        listPembelian.add(new NonCookableFood("Susu"));
                        break;
                    case "Furniture":
                        listPembelian.add(new Kasur("Kasur Single"));
                        listPembelian.add(new Kasur("Kasur Queen Size"));
                        listPembelian.add(new Kasur("Kasur King Size"));
                        listPembelian.add(new Toilet());
                        listPembelian.add(new Kompor("Kompor Gas"));
                        listPembelian.add(new MejaKursi());
                        listPembelian.add(new Jam());
                        break;
                }
                JOptionPane.getRootFrame().dispose();
            });
            panel.add(button);
        }

        String[] back = { "Back" };
        int choice = JOptionPane.showOptionDialog(null, panel, "Pilihan Pembelian", 
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, back, null);
        
        // Choosing Back Button
        if (choice == 0) { 
            return;
        }

        Sim currentSim = Game.getInstance().getCurrentSim();

        String[][] tableData = new String[listPembelian.size()][2];
        String[] columnNames = { "Name", "Price" };

        for (int i = 0; i < listPembelian.size(); i++) {
            tableData[i][0] = listPembelian.get(i).getNama(); // Furniture Name
            tableData[i][1] = String.valueOf(listPembelian.get(i).getHarga()); // Price
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);

        // Menampilkan option pane
        String[] options = { "Buy", "Back" };
        int buyChoice = JOptionPane.showOptionDialog(
                null,
                new JScrollPane(table),
                "Pilihan Pembelian",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (buyChoice == 0) {
            int selectedRow = table.getSelectedRow();
            double uangSim = currentSim.getUang();
            double hargaBarangTerpilih = Double.parseDouble(tableData[selectedRow][1]);

            String inputJumlah = JOptionPane.showInputDialog("Masukkan jumlah yang diinginkan: ");
            int jumlahBarangTerpilih = Integer.parseInt(inputJumlah);

            if (uangSim < hargaBarangTerpilih * jumlahBarangTerpilih) {
                JOptionPane.showMessageDialog(null,
                        "Maaf, uang kamu tidak cukup!",
                        "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                currentSim.setUang(uangSim - hargaBarangTerpilih * jumlahBarangTerpilih);
                Purchasable barangTerpilih = listPembelian.get(selectedRow);
                currentSim.getInventory().addBarang(barangTerpilih, jumlahBarangTerpilih);
                String message = String.format("Selamat! Pembelian %d %s berhasil.", jumlahBarangTerpilih,
                        barangTerpilih.getNama());
                JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
        }
    }

    public void interact(Furniture barang) {
        barang.aksi(this);
    }

    // GUI
    public int inputActionTime() {
        String input = "";
        try {
            input = JOptionPane.showInputDialog(null, "Masukkan waktu aksi: ");
            if (input == null) {
                // Kalo pencet tombol close
                JOptionPane.getRootFrame().dispose();
                return 0;
            } else {
                int time = Integer.parseInt(input);
                return time;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new NumberFormatException("Masukan harus berupa angka");
        }
    }
}
