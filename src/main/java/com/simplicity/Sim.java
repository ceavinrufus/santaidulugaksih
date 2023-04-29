package com.simplicity;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Sim {
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private double uang = 100;
    private Inventory inventory = new Inventory();
    private ArrayList<String> currentActions = new ArrayList<String>();
    private Stats stats = new Stats();
    private boolean isLibur = false;
    private SimPosition currentPosition;
    private int totalWorkTime = 0;
    private int changeJobTime = 720;
    private int waktuKerjaBelumDibayar = 0;
    private int waktuTidakTidur = 0;
    private int waktuTidakBuangAir = 0;

    // Waktu Terpusat
    public Waktu totalWaktu = Waktu.waktu();

    public World world = World.getInstance();

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
            changeJobTime = 0;

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

    public SimPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(SimPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void addWaktuTidakTidur(int waktu) {
        waktuTidakTidur += waktu;
    }

    public void checkTidakTidur() {
        if (waktuTidakTidur >= 600) {
            stats.kurangKesehatan(5);
            stats.kurangMood(5);
        }
    }

    public void trackTidur(int waktu) {
        addWaktuTidakTidur(waktu);
        checkTidakTidur();
    }

    public void kerja(int workingTime) {
        if (changeJobTime >= 720 && workingTime % 120 == 0) {
            try {
                TimeUnit.SECONDS.sleep(workingTime);
                totalWorkTime += workingTime;
                if (waktuKerjaBelumDibayar > 0) {
                    workingTime += waktuKerjaBelumDibayar;
                    waktuKerjaBelumDibayar = 0;
                    stats.kurangKekenyangan(workingTime / 30 * 10);
                    stats.kurangMood(workingTime / 30 * 10);
                    uang += pekerjaan.getGaji() * (workingTime % 240);
                    waktuKerjaBelumDibayar += (workingTime - 240 * (workingTime % 240));
                }
            } catch (InterruptedException e) {
                // do something
            }
        }
    }

    public void olahraga(int workoutTime) {
        if (workoutTime % 20 == 0) {
            try {
                TimeUnit.SECONDS.sleep(workoutTime);
                stats.tambahKesehatan(workoutTime / 20 * 5);
                stats.kurangKekenyangan(workoutTime / 20 * 5);
                stats.tambahMood(workoutTime / 20 * 10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }            
            totalWaktu.addWaktu(workoutTime);
            trackTidur(workoutTime);
        }
    }

    public void tidur(int sleepTime) {
        if (sleepTime >= 240) {
            try {
                TimeUnit.SECONDS.sleep(sleepTime);
                stats.tambahMood(sleepTime / 240 * 30);
                stats.tambahKesehatan(sleepTime / 240 * 20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            totalWaktu.addWaktu(sleepTime);
            waktuTidakTidur = 0;
        }
    }

    public void makan(Eatable food) {
        for (Pair<Storable, Integer> item : inventory.getItems()) {
            if (item.getKey().getNama().equals(food.getNama())) {
                try {
                    TimeUnit.SECONDS.sleep(30);
                    inventory.reduceBarang(food, 1);
                    stats.tambahKekenyangan(food.getKekenyangan());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                totalWaktu.addWaktu(30);
            }
        }
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

    public void berkunjung(Sim tujuan) {

    }

    public void buangAir() {
        try {
            TimeUnit.SECONDS.sleep(10);
            stats.kurangKekenyangan(20);
            stats.tambahMood(10);
            waktuTidakBuangAir = 0;
        } catch (InterruptedException e) {
            // do something
        }
        totalWaktu.addWaktu(10);
    }

    public void upgradeRumah() {
        int cost = 1500;
        if (uang > cost) {
            try {
                TimeUnit.SECONDS.sleep(18*60);
                // tambah ruangan
                if (currentPosition.getRumah().getPemilik().equals(this)) {
                    Scanner scanner = new Scanner(System.in);
                    String namaRuangan = scanner.nextLine();
                    Ruangan baru = new Ruangan(namaRuangan);
                    String arah = scanner.nextLine();
                    String ruanganPatokan = scanner.nextLine();
                    // currentPosition.getRumah().tambahRuangan(baru, arah, ruanganPatokan);
                }
                uang -= cost;
            } catch (InterruptedException e) {
                // do something
            }
            totalWaktu.addWaktu(18*60);
        }
    }

    public void beliBarang(){
        
    }

    public void interact(Furniture Furniture) {
        Furniture.aksi(this);
    }
}
