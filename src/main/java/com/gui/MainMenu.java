package com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;

public class MainMenu extends JFrame {
    PixelatedButton startButton;
    PixelatedButton helpButton;

    public MainMenu() {
        setTitle("Sim-Plicity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ngambil image buat dijadiin background
        ImageIcon backgroundImage = new ImageIcon("src/main/java/resources/images/background.png");
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
            public void mouseClicked(MouseEvent e) {
                SimPlicity.getInstance();
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

        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    // Start
                    removeKeyListener(this);
                    new Game();
                } else if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    // Help
                    Object[] options = { "Aku mengerti!" };
                    JOptionPane.showOptionDialog(null,
                            "Gatau mainin aja udah pokoknya",
                            "Help",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]);

                }
            }
        };
        addKeyListener(keyListener);
        setVisible(true);
        setFocusable(true);
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
