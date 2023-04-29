package com.gui;

import com.simplicity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WorldPanel extends JPanel {
    private static WorldPanel instance = new WorldPanel();

    public static WorldPanel getInstance() {
        return instance;
    }

    public WorldPanel() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyChar = e.getKeyCode();
                if (keyChar == KeyEvent.VK_SPACE) {
                    Game.getInstance().displayGameMenu();
                }
            }
        });
        requestFocusInWindow(); // Request focus for the panel after it has been added to the JFrame
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(new File("src/main/java/resources/images/sea.jpg"));
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }
        if (pattern != null) {
            int patternWidth = pattern.getWidth(null);
            int patternHeight = pattern.getHeight(null);
            for (int x = 0; x < getWidth(); x += patternWidth) {
                for (int y = 0; y < getHeight(); y += patternHeight) {
                    g.drawImage(pattern, x, y, null);
                }
            }
        }
        World.getInstance().paint(g, getWidth(), getHeight());
    }
}
