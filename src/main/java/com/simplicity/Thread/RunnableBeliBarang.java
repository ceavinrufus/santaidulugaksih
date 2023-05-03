package com.simplicity.Thread;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.simplicity.Sim;
import com.simplicity.Interface.Leaveable;
import com.simplicity.Interface.Purchasable;

public class RunnableBeliBarang implements Leaveable {
    private volatile boolean shouldRun = false;
    private int sisaWaktu = 25;
    private Sim currentSim;
    private Purchasable barang;
    private int jumlahBarang;

    public RunnableBeliBarang(Sim currentSim, Purchasable barang, int jumlahBarang) {
        this.currentSim = currentSim;
        this.barang = barang;
        this.jumlahBarang = jumlahBarang;
    }

    @Override
    public void run() {
        while (sisaWaktu != 0) {
            try {
                if (shouldRun) {
                    sisaWaktu -= 1;
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentSim.getInventory().addBarang(barang, jumlahBarang);
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
    public void stop() {
        shouldRun = false;
    }

    @Override
    public void start() {
        shouldRun = true;
    }
}
