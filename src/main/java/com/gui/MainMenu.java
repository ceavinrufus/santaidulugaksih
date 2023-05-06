package com.gui;

import javax.swing.*;

import com.simplicity.*;
import com.simplicity.ExceptionHandling.SimNotCreatedException;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainMenu extends JFrame {
    private static MainMenu instance = new MainMenu();

    PixelatedButton newGameButton;
    PixelatedButton loadGameButton;

    public MainMenu() {
        setTitle("Sim-Plicity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ngambil image buat dijadiin background
        ImageIcon backgroundImage = new ImageIcon("src/main/java/resources/images/simplicity.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);

        setMinimumSize(new Dimension(800, 1000)); // Set minimum size JFrame
        setPreferredSize(new Dimension(800, 1000)); // Set preferred size JFrame
        pack(); // Bikin JFrame fit ke preferred size

        // Add JLabel ke content pane JFrame
        add(backgroundLabel);

        // Start button
        newGameButton = new PixelatedButton("New Game");
        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                Game game = Game.getInstance();
                Sim sim;
                try {
                    sim = game.makeNewSim();
                    if (sim != null) {
                        game.setCurrentSim(sim);
                        game.runGame();
                    }
                } catch (SimNotCreatedException e) {
                    sim = null;
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Load button
        loadGameButton = new PixelatedButton("Load Game");
        loadGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                String inputFileName = JOptionPane.showInputDialog(null, "Masukkan nama file:", "Load Game",
                        JOptionPane.QUESTION_MESSAGE);
                if (inputFileName == null) {
                } else {
                    Game game = Game.getInstance();
                    try {
                        World loadedWorld = World.getInstance().loadWorld(inputFileName);
                        for (int i = 0; i < loadedWorld.getPeta().getRow(); i++) {
                            for (int j = 0; j < loadedWorld.getPeta()
                                    .getColumn(); j++) {
                                if (loadedWorld.getPeta().getElement(j, i) != null) {
                                    World.getInstance().getPeta().setElement(j, i,
                                            loadedWorld.getPeta().getElement(j, i));
                                }
                            }
                        }
                        game.setSims(game.loadSims(inputFileName));
                        for (Sim sim : game.getSims().values()) {
                            sim.getCurrentPosition().setRumah(World.getInstance()
                                    .findRumah(sim.getNamaLengkap()));
                            sim.getCurrentPosition().setRuang(World.getInstance()
                                    .findRumah(sim.getNamaLengkap())
                                    .findRuangan(sim.getCurrentPosition().getRuang().getNamaRuangan()));
                        }

                        game.setCurrentSim(game.loadCurrentSim(inputFileName));
                        game.getCurrentSim().getCurrentPosition().setRumah(World.getInstance()
                                .findRumah(game.getCurrentSim().getNamaLengkap()));
                        game.getCurrentSim().getCurrentPosition().setRuang(World.getInstance()
                                .findRumah(game.getCurrentSim().getNamaLengkap())
                                .findRuangan(game.getCurrentSim().getCurrentPosition().getRuang().getNamaRuangan()));
                        game.getTotalWaktu().addWaktu(game.getTotalWaktu().loadWaktu(inputFileName).getWaktu());
                        game.runGame();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Save file tidak ditemukan!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        updateButtonPosition();
        // Add component listener to update button position when JFrame is resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateButtonPosition();
            }
        });

        backgroundLabel.add(newGameButton);
        backgroundLabel.add(loadGameButton);
        setVisible(true);
        setFocusable(true);
    }

    public static MainMenu getInstance() {
        return instance;
    }

    private void updateButtonPosition() {
        int buttonWidth = 200;
        int buttonHeight = 50;
        newGameButton.setBounds(getWidth() / 2 - buttonWidth - 10, 27 * getHeight() / 40, buttonWidth,
                buttonHeight);
        loadGameButton.setBounds(getWidth() / 2 + 10, 27 * getHeight() / 40, buttonWidth,
                buttonHeight);
    }
}