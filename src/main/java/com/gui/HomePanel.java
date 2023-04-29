package com.gui;

import com.simplicity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HomePanel extends JPanel {
    private static HomePanel instance = new HomePanel(null);
    int keyChar;

    public int getKeyChar() {
        return keyChar;
    }

    public static HomePanel getInstance() {
        return instance;
    }

    public void setCurrentSim(Sim currentSim) {
        this.currentSim = currentSim;
    }

    private KeyAdapter keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            SimPosition currentSimPosition = currentSim.getCurrentPosition();
            keyChar = e.getKeyCode();
            if (keyChar == KeyEvent.VK_SPACE) {
                Game.getInstance().displayGameMenu();
            } else if ((keyChar == KeyEvent.VK_W) || (keyChar == KeyEvent.VK_UP)) {
                if (currentSimPosition.getLokasi().getY() < 5) {
                    currentSimPosition.getLokasi().setY(currentSimPosition.getLokasi().getY() + 1);
                    repaint();
                }
            } else if ((keyChar == KeyEvent.VK_S) || (keyChar == KeyEvent.VK_DOWN)) {
                if (currentSimPosition.getLokasi().getY() > 0) {
                    currentSimPosition.getLokasi().setY(currentSimPosition.getLokasi().getY() - 1);
                    repaint();
                }
            } else if ((keyChar == KeyEvent.VK_A) || (keyChar == KeyEvent.VK_LEFT)) {
                if (currentSimPosition.getLokasi().getX() > 0) {
                    currentSimPosition.getLokasi().setX(currentSimPosition.getLokasi().getX() - 1);
                    repaint();
                }
            } else if ((keyChar == KeyEvent.VK_D) || (keyChar == KeyEvent.VK_RIGHT)) {
                if (currentSimPosition.getLokasi().getX() < 5) {
                    currentSimPosition.getLokasi().setX(currentSimPosition.getLokasi().getX() + 1);
                    repaint();
                }
            }
        }
    };

    private Sim currentSim;

    public HomePanel(Sim currentSim) {
        this.currentSim = currentSim;
        setFocusable(true);
        addKeyListener(keyListener);
        requestFocusInWindow(); // Request focus for the panel after it has been added to the JFrame
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        currentSim.getCurrentPosition().getRumah().paint(g, getWidth(), getHeight());
    }
}
