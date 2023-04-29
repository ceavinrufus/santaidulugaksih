package com.gui;

import javax.swing.*;

import com.simplicity.Sim;
import com.simplicity.ExceptionHandling.SimNotCreatedException;

import java.awt.*;
import java.awt.event.*;;

public class MainMenu extends JFrame {
    private static MainMenu instance = new MainMenu();

    PixelatedButton startButton;
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
        Font font = new Font("Arial", Font.BOLD, 18);

        // Start button
        startButton = new PixelatedButton("Start", font);
        startButton.addMouseListener(new MouseAdapter() {
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

        // Help button
        helpButton = new PixelatedButton("Help", font);
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

        backgroundLabel.add(startButton);
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
        startButton.setBounds(getWidth() / 2 - buttonWidth / 2, 7 * getHeight() / 10, buttonWidth,
                buttonHeight);
        helpButton.setBounds(getWidth() / 2 - buttonWidth / 2, 7 * getHeight() / 10 + buttonHeight + 10, buttonWidth,
                buttonHeight);
    }
}
