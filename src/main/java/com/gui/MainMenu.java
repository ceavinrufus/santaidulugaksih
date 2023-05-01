package com.gui;

import javax.swing.*;

import com.google.gson.Gson;

import com.simplicity.*;
import com.simplicity.ExceptionHandling.SimNotCreatedException;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;;

public class MainMenu extends JFrame {
    private static MainMenu instance = new MainMenu();

    PixelatedButton newGameButton;
    PixelatedButton loadGameButton;
    PixelatedButton helpButton;

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

        // Pixelated font
        Font font = new Font("Minecraft", Font.BOLD, 18);

        // Start button
        newGameButton = new PixelatedButton("New Game");
        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                Game.resetInstance();
                // WorldPanel.getInstance();
                Game game = Game.getInstance();
                // game.tabbedPane.remove(game.homePanel);
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
                String inputFileName = JOptionPane.showInputDialog(null, "Masukkan nama file: ", "Load Game",
                        JOptionPane.QUESTION_MESSAGE);
                Game.resetInstance();
                // WorldPanel.resetInstance();
                Game game = Game.getInstance();
                // game.tabbedPane.remove(game.homePanel);
                try {
                    // World.resetInstance();
                    World world = game.loadWorld(inputFileName);
                    game.setSims(game.loadSims(inputFileName));
                    game.setCurrentSim(game.loadCurrentSim(inputFileName));
                    game.runGame();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Help button
        helpButton = new PixelatedButton("Help");
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
        backgroundLabel.add(helpButton);
        setVisible(true);
        setFocusable(true);
    }

    public static MainMenu getInstance() {
        return instance;
    }

    private void updateButtonPosition() {
        int buttonWidth = 200;
        int buttonHeight = 50;
        newGameButton.setBounds(getWidth() / 3 - buttonWidth / 2, 27 * getHeight() / 40, buttonWidth,
                buttonHeight);
        helpButton.setBounds(2 * getWidth() / 3 - buttonWidth / 2, 27 * getHeight() / 40, buttonWidth,
                buttonHeight);
        loadGameButton.setBounds(getWidth() / 2 - buttonWidth / 2, 27 * getHeight() / 40, buttonWidth,
                buttonHeight);
    }
}