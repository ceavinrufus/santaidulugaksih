package com.simplicity.Thread;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.gui.Game;
import com.simplicity.*;
import com.simplicity.Interface.Leaveable;

public class RunnableBangunRumah implements Leaveable {
    private volatile boolean shouldRun = false;
    // TODO: Karena lebih dari 1 hari, kemungkinan ada yang perlu dihandle
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

        currentSim.getCurrentPosition().getRumah().tambahRuangan(ruanganBaru, arah,
                ruanganPatokan);
        Game.getInstance().repaint();
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
    public void stop() {
        shouldRun = false;
    }

    @Override
    public void start() {
        shouldRun = true;
    }

}
