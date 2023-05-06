package com.simplicity.Thread;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.gui.Game;
import com.simplicity.*;
import com.simplicity.Interface.Leaveable;

public class RunnableBangunRumah implements Leaveable {
    private volatile int decrement = 0;
    private int sisaWaktu = 60 * 18;
    private Sim currentSim;
    private Ruangan ruanganBaru;
    private Ruangan ruanganPatokan;
    private String arah;

    public RunnableBangunRumah(Sim currentSim, Ruangan ruanganBaru, Ruangan ruanganPatokan, String arah) {
        this.currentSim = currentSim;
        this.ruanganBaru = ruanganBaru;
        this.ruanganPatokan = ruanganPatokan;
        this.arah = arah;
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
            currentSim.getCurrentPosition().getRumah().tambahRuangan(ruanganBaru, arah,
                    ruanganPatokan);
            Game.getInstance().repaint();
        }
    }

    @Override
    public String getNamaAksi() {
        return "Sim " + currentSim.getNamaLengkap() + ": Membangun ruangan " + ruanganBaru.getNamaRuangan();
    }

    @Override
    public int getSisaWaktu() {
        return sisaWaktu;
    }

    @Override
    public void showCompleteMessage() {
        String message = String.format("Upgrade rumah berhasil. Ruangan %s berhasil dibuat di %s ruangan %s!",
                ruanganBaru.getNamaRuangan(), arah.toLowerCase(),
                ruanganPatokan.getNamaRuangan());
        JOptionPane.showMessageDialog(null, message, "Notification",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void start(int decrement) {
        this.decrement = decrement;
    }
}
