package com.simplicity.Thread;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.gui.Game;
import com.simplicity.*;
import com.simplicity.Interface.Leaveable;

public class ThreadBangunRumah implements Leaveable {
    private volatile boolean shouldRun = false;
    // TODO: Karena lebih dari 1 hari, kemungkinan ada yang perlu dihandle
    private int initialCountdown = 60 * 18;
    private Sim currentSim;
    private Ruangan ruanganBaru;
    private Ruangan ruanganPatokan;
    private String arah;

    public ThreadBangunRumah(Sim currentSim, Ruangan ruanganBaru, Ruangan ruanganPatokan, String arah) {
        this.currentSim = currentSim;
        this.ruanganBaru = ruanganBaru;
        this.ruanganPatokan = ruanganPatokan;
        this.arah = arah;
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

        currentSim.getCurrentPosition().getRumah().tambahRuangan(ruanganBaru, arah,
                ruanganPatokan);
        JOptionPane.getRootFrame().dispose();
        Game.getInstance().repaint();
    }

    public void stop() {
        shouldRun = false;
    }

    public void start() {
        shouldRun = true;
    }

}
