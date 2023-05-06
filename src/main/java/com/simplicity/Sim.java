package com.simplicity;

import java.util.*;

import javax.swing.*;

import com.gui.Game;
import com.simplicity.AbstractClass.Furniture;

public class Sim {
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private double uang = 100;
    private Inventory inventory = new Inventory();
    private Stats stats = new Stats();
    private SimPosition currentPosition;
    private String activeStatus;

    private int totalWorkTime = 0;
    private int waktuBolehGantiKerja = 720;
    private int waktuKerjaBelumDibayar = 0;
    private int waktuTidakTidur = 0;
    private int waktuTidakBuangAir = 0;
    private boolean isSehabisMakan = false;
    private boolean isSehabisTidur = false;
    private boolean isOnKunjungan = false;
    public int sleepTimeAkumulasi = 0;
    private int hariKeAwal = 0;
    private int hariKeAkhir = 0;

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

        public synchronized int getMood() {
            return mood;
        }

        public synchronized int getKekenyangan() {
            return kekenyangan;
        }

        public synchronized int getKesehatan() {
            return kesehatan;
        }

        public synchronized void tambahMood(int mood) {
            this.mood += mood;
            if (this.mood > 100) {
                this.mood = 100;
            }
        }

        public synchronized void tambahKekenyangan(int kekenyangan) {
            this.kekenyangan += kekenyangan;
            if (this.kekenyangan > 100) {
                this.kekenyangan = 100;
            }
        }

        public synchronized void tambahKesehatan(int kesehatan) {
            this.kesehatan += kesehatan;
            if (this.kesehatan > 100) {
                this.kesehatan = 100;
            }
        }

        public synchronized void kurangMood(int mood) {
            this.mood -= mood;
            if (this.mood < 0) {
                this.mood = 0;
            }
        }

        public synchronized void kurangKekenyangan(int kekenyangan) {
            this.kekenyangan -= kekenyangan;
            if (this.kekenyangan < 0) {
                this.kekenyangan = 0;
            }
        }

        public synchronized void kurangKesehatan(int kesehatan) {
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
            JOptionPane.showMessageDialog(null,
                    String.format("Pekerjaan berhasil diganti! Sekarang kamu adalah %s", pekerjaan.getNama()),
                    "Action finished", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Yah, kamu belum bisa ganti pekerjaan nih:(", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public synchronized Inventory getInventory() {
        return inventory;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
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

    public void setHariKeAwal(int a) {
        hariKeAwal += a;
    }

    public void setHariKeAkhir(int a) {
        hariKeAkhir += a;
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

    public void trackTidur(int t) {
        if (!isSehabisTidur) {
            waktuTidakTidur += t;
        }
        if (isSehabisTidur && hariKeAkhir > hariKeAwal) {
            isSehabisTidur = false;
            waktuTidakTidur += Jam.getWaktuHariIni();
        }
        if (waktuTidakTidur >= 600) {
            stats.kurangKesehatan(5);
            stats.kurangMood(5);
        }
    }

    public void setWaktuTidakBuangAir(int waktu) {
        waktuTidakBuangAir = waktu;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sim)) {
            return false;
        }
        Sim sim = (Sim) o;
        return Objects.equals(namaLengkap, sim.getNamaLengkap());
    }

    public void kerja() {
        Integer workingTime = 0;
        if (waktuBolehGantiKerja >= 720) {
            Boolean inputValid = false;
            while (!inputValid) {
                workingTime = inputActionTime();
                if (workingTime == null) {
                    return;
                }
                if (workingTime != 0 && workingTime % 120 == 0) {
                    inputValid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Waktu kerja harus kelipatan 120!", "Input tidak valid",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

            Game.getInstance().getCurrentSim().setActiveStatus("Kerja");
            Game.getInstance().mulaiAksi(workingTime);

            totalWorkTime += workingTime;
            if (waktuKerjaBelumDibayar > 0) {
                workingTime += waktuKerjaBelumDibayar;
                waktuKerjaBelumDibayar = 0;
            }
            stats.kurangKekenyangan(workingTime / 30 * 10);
            stats.kurangMood(workingTime / 30 * 10);
            uang += pekerjaan.getGaji() * (workingTime / 240);
            waktuKerjaBelumDibayar += (workingTime - 240 * (workingTime / 240));
            JOptionPane.showMessageDialog(null, "Kerja selesai!", "Action finished", JOptionPane.INFORMATION_MESSAGE);
            Game.getInstance().trackSimsStats(workingTime);
        }
    }

    public void olahraga() {
        Integer workoutTime = 0;
        Boolean inputValid = false;
        while (!inputValid) {
            workoutTime = inputActionTime();
            if (workoutTime == null) {
                return;
            }
            if (workoutTime != 0 && workoutTime % 20 == 0) {
                inputValid = true;
            } else {
                JOptionPane.showMessageDialog(null, "Waktu kerja harus kelipatan 20!", "Input tidak valid",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        Game.getInstance().getCurrentSim().setActiveStatus("Olahraga");
        Game.getInstance().mulaiAksi(workoutTime);

        stats.tambahKesehatan(workoutTime / 20 * 5);
        stats.kurangKekenyangan(workoutTime / 20 * 5);
        stats.tambahMood(workoutTime / 20 * 10);
        isSehabisMakan = false;
        isSehabisTidur = false;
        JOptionPane.showMessageDialog(null, "Olahraga selesai!", "Action finished", JOptionPane.INFORMATION_MESSAGE);
        Game.getInstance().trackSimsStats(workoutTime);
    }

    public void interact(Furniture barang) {
        barang.aksi(this);
    }

    // GUI
    private Integer inputActionTime() {
        String input = "";
        try {
            input = JOptionPane.showInputDialog(null, "Masukkan waktu aksi: ");
            if (input == null) {
                // Kalo pencet tombol close
                return null;
            } else {
                Integer time = Integer.parseInt(input);
                return time;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Masukan harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
