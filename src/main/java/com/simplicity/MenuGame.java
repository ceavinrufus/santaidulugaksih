package com.simplicity;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuGame {
    private boolean isStarted = false;
    private Sim currentSim;
    private ArrayList<Pekerjaan> daftarPekerjaan;

    // Getter & Setter
    public boolean getIsStarted() {
        return isStarted;
    }

    public Sim getCurrentSim() {
        return currentSim;
    }

    public ArrayList<Pekerjaan> getDaftarPekerjaan() {
        return daftarPekerjaan;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public void setCurrentSim(Sim currentSim) {
        this.currentSim = currentSim;
    }

    // Main Function
    public void startGame() {
        isStarted = true;
        Scanner input = new Scanner(System.in);
        String nama = input.nextLine();
        currentSim = new Sim(nama);
        currentSim.setStats(new Stats());
        currentSim.setUang(100);
        Inventory inventory = new Inventory();
        Kasur kasur = new Kasur("Kasur Single");
        Toilet toilet = new Toilet();
        Kompor kompor = new Kompor("Kompor Gas");
        MejaKursi mejaKursi = new MejaKursi();
        Jam jam = new Jam();
        Ruangan ruangan = new Ruangan("Ruangan Utama");
        ruangan.memasangBarang(kasur, false, 5, 2);
        ruangan.memasangBarang(toilet, true, 0, 5);
        ruangan.memasangBarang(jam, true, 0, 0);
        ruangan.memasangBarang(kompor, true, 2, 5);
        ruangan.memasangBarang(mejaKursi, true, 1, 1);
        Rumah rumah = new Rumah(ruangan);
        rumah.setNamaPemilik(currentSim);
        World world = World.getInstance();
    }

    public void help() {
        System.out.println("Hai! Selamat datang di Simplicity!\nBerikut merupakan panduan bermain game ini.");
    }

    public void exit() {
        System.out.println("Terima kasih telah bermain Simplicity!");
        isStarted = false;
    }

    public void viewSimInfo() {
        if (isStarted) {
            System.out.println("Berikut merupakan informasi dari Sim:");
            System.out.println("Nama: " + currentSim.getNamaLengkap());
            System.out.println("Pekerjaan: " + currentSim.getPekerjaan());
            System.out.println("Kesehatan: " + currentSim.getStats().getKesehatan());
            System.out.println("Kekenyangan: " + currentSim.getStats().getKekenyangan());
            System.out.println("Mood: " + currentSim.getStats().getMood());
            System.out.println("Uang: " + currentSim.getUang());
        } else {
            System.out.println("Maaf, Anda harus memulai permainan terlebih dahulu.");
        }
    }

    public void addSim() {
        //
    }

    public void changeSim() {
        //
    }

    public void viewCurrentLocation() {
        if (isStarted) {
            System.out.println("Berikut merupakan informasi lokasimu saat ini:");
            System.out.println("Rumah: ");
            System.out.println("Ruangan: ");
        } else {
            System.out.println("Maaf, Anda harus memulai permainan terlebih dahulu.");
        }
    }

    public void viewInventory() {
        if (isStarted) {
            System.out.println("Berikut isi dari inventorymu saat ini: ");
        } else {
            System.out.println("Maaf, Anda harus memulai permainan terlebih dahulu.");
        }
    }

    public void upgradeHouse() {
        if (isStarted) {
            //
        } else {
            System.out.println("Maaf, Anda harus memulai permainan terlebih dahulu.");
        }
    }

    public void moveRoom() {
        if (isStarted) {
            //
        } else {
            System.out.println("Maaf, Anda harus memulai permainan terlebih dahulu.");
        }
    }

    public void editRoom() {
        if (isStarted) {
            //
        } else {
            System.out.println("Maaf, Anda harus memulai permainan terlebih dahulu.");
        }
    }

    public void displayBeforeGame() {
        //
    }

    public void displayGame() {
        //
    }

    public void displayAksiBarang() {
        //
    }

    public void listObject() {
        //
    }

    public void goToObject() {
        //
    }

    public void action() {
        //
    }
}
