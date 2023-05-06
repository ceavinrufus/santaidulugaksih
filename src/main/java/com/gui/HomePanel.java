package com.gui;

import com.simplicity.*;
import com.simplicity.AbstractClass.Furniture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePanel extends JPanel {
    int keyChar;
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    PixelatedButton interactButton;
    private Sim currentSim;

    public HomePanel(Sim currentSim) {
        this.currentSim = currentSim;
        setFocusable(true);
        addKeyListener(keyListener);

        // Button panel
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        // Pause button
        PixelatedButton pauseButton = new PixelatedButton("Pause");
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                Game.getInstance().displayGameMenu(HomePanel.this);
                requestFocusInWindow();
            }
        });

        // Help button
        PixelatedButton helpButton = new PixelatedButton("Help");
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Help
                Object[] options = { "Aku mengerti!" };
                JOptionPane.showOptionDialog(null,
                        "Hai! Selamat datang di Simplycity!\n" +
                                "Klik Enter untuk memulai game\n" +
                                "Klik Spasi untuk melihat help\n" +
                                "Klik w, a, s, d atau tombol panah untuk menggerakan Sim dalam permainan\n" +
                                "Selamat bermain!",
                        "Help",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);
                requestFocusInWindow();
            }
        });

        interactButton = new PixelatedButton("");
        interactButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentSim != null) {
                    SimPosition simCurrentPosition = Game.getInstance().getCurrentSim().getCurrentPosition();
                    Furniture barang = simCurrentPosition.getRuang()
                            .getBarangByKoordinat(simCurrentPosition.getLokasi());
                    currentSim.interact(barang);
                    Game.getInstance().trackSimsStats();
                }
                requestFocusInWindow();
            }
        });

        PixelatedButton actionButton = new PixelatedButton("Action");
        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentSim != null) {
                    Game.getInstance().action();
                }
                requestFocusInWindow();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Toggle visibility
                    pauseButton.setVisible(!pauseButton.isVisible());
                    helpButton.setVisible(!helpButton.isVisible());
                    actionButton.setVisible(!actionButton.isVisible());
                }
            }
        });

        buttonPanel.setOpaque(false);
        buttonPanel.add(pauseButton);
        buttonPanel.add(helpButton);
        interactButton.setVisible(false);
        buttonPanel.add(actionButton);
        buttonPanel.add(interactButton);
    }

    public int getKeyChar() {
        return keyChar;
    }

    public void setCurrentSim(Sim currentSim) {
        this.currentSim = currentSim;
    }

    private KeyAdapter keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            SimPosition currentSimPosition = currentSim.getCurrentPosition();
            keyChar = e.getKeyCode();
            if ((keyChar == KeyEvent.VK_W) || (keyChar == KeyEvent.VK_UP)) {
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
            } else {
                return;
            }

        }
    };

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        SimPosition simCurrentPosition = currentSim.getCurrentPosition();
        Furniture barang = simCurrentPosition.getRuang().getBarangByKoordinat(simCurrentPosition.getLokasi());
        if (barang != null) {
            interactButton.setText(barang.getNamaAksi());
            interactButton.setVisible(true);
        } else {
            interactButton.setVisible(false);
        }

        simCurrentPosition.getRumah().paint(g, getWidth(), getHeight(), keyChar);
    }
}
