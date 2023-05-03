package com.simplicity.Thread;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.simplicity.Sim;
import com.simplicity.Interface.Leaveable;
import com.simplicity.Interface.Purchasable;

public class ThreadBeliBarang implements Leaveable {
    private volatile boolean shouldRun = false;
    private int initialCountdown = 25;
    private Sim currentSim;
    private Purchasable barang;
    private int jumlahBarang;

    public ThreadBeliBarang(Sim currentSim, Purchasable barang, int jumlahBarang) {
        this.currentSim = currentSim;
        this.barang = barang;
        this.jumlahBarang = jumlahBarang;
    }

    @Override
    public void run() {
        while (initialCountdown != 0) {
            try {
                if (shouldRun) {
                    initialCountdown -= 1;
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentSim.getInventory().addBarang(barang, jumlahBarang);
        // String message = String.format("Selamat! Pembelian %d %s berhasil.",
        // jumlahBarang, barang.getNama());
        // JOptionPane.showMessageDialog(null, message, "Notification",
        // JOptionPane.INFORMATION_MESSAGE);
    }

    public void stop() {
        shouldRun = false;
    }

    public void start() {
        shouldRun = true;
    }

}
