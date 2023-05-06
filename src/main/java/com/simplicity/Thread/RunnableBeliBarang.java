package com.simplicity.Thread;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.simplicity.Sim;
import com.simplicity.Interface.Leaveable;
import com.simplicity.Interface.Purchasable;

public class RunnableBeliBarang implements Leaveable {
    private volatile int decrement = 0;
    private int sisaWaktu;
    private Sim currentSim;
    private Purchasable barang;
    private int jumlahBarang;

    public RunnableBeliBarang(Sim currentSim, Purchasable barang, int jumlahBarang) {
        this.currentSim = currentSim;
        this.barang = barang;
        this.jumlahBarang = jumlahBarang;

        int lowerBound = 1;
        int upperBound = 5;
        int random_int = (int) (Math.random() * (upperBound - lowerBound + 1) + lowerBound);
        sisaWaktu = random_int * 30;
    }

    @Override
    public void run() {
        try {
            while (sisaWaktu != 0) {
                if (decrement > 0) {
                    sisaWaktu -= 1;
                    decrement -= 1;
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (sisaWaktu == 0) {
            ThreadManager.removeThread(this);
            currentSim.getInventory().addBarang(barang, jumlahBarang);
        }
    }

    @Override
    public String getNamaAksi() {
        return "Sim " + currentSim.getNamaLengkap() + ": Membeli " + jumlahBarang + " " + barang.getNama();
    }

    @Override
    public int getSisaWaktu() {
        return sisaWaktu;
    }

    @Override
    public void showCompleteMessage() {
        String message = String.format("Selamat! Pembelian %d %s berhasil.",
                jumlahBarang, barang.getNama());
        JOptionPane.showMessageDialog(null, message, "Notification",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void start(int decrement) {
        this.decrement = decrement;
    }
}
