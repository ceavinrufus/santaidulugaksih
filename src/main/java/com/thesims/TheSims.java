package com.thesims;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TheSims extends JFrame implements KeyListener {
    private JLabel label;
    private BufferedImage background;
    private int gridSize = 64;
    private boolean displayGrid = false;
    private int xOffset;
    private int yOffset;

    public TheSims() {
        setTitle("The Sims");
        setMinimumSize(new Dimension(800, 800)); // Set the minimum size of the JFrame
        setPreferredSize(new Dimension(800, 800)); // Set the preferred size of the JFrame
        pack(); // Resize the JFrame to fit the preferred size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            background = ImageIO.read(new File("src/main/java/resources/images/background.jpeg"));
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            displayInitialMenu();
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    private void displayInitialMenu() {
        String[] options = {"Start Game", "Help", "Exit"};
        int choice = JOptionPane.showOptionDialog(this, "What do you want to do?", "Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice != JOptionPane.CLOSED_OPTION) {
            switch (choice) {
                case 0:
                    displayGrid = true;
                    repaint();
                    displayGameMenu();
                    break;
                case 1:
                    displayHelp();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    private void displayGameMenu() {
        String[] options = {"Go to work", "Go to school", "Sleep", "Quit game"};
        int choice = JOptionPane.showOptionDialog(this, "What do you want to do?", "Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice != JOptionPane.CLOSED_OPTION) {
            switch (choice) {
                case 0:
                    label.setText("You are going to work");
                    break;
                case 1:
                    label.setText("You are going to school");
                    break;
                case 2:
                    label.setText("You are going to sleep");
                    break;
                case 3:
                    label.setText("Game over");
                    System.exit(0);
                    break;
            }
        }
    }

    private void displayHelp() {
        JOptionPane.showMessageDialog(this, "Use the arrow keys to move the character", "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void paintGrid(Graphics g){
        int width = getWidth() - 64;
        int height = getHeight() - 64;
        float aspectRatio = (float) width / height;
        float gridSize = Math.min(width, height) / 64f;
        float xCenter = 32f * gridSize;
        float yCenter = 32f * gridSize;
        float xOffset = 32f;
        float yOffset = 40f;
        if (aspectRatio > 1f) {
            // wide screen, align grid to vertical center
            gridSize = height / 64f;
            xCenter = width / 2f;
            yCenter = 32f * gridSize;
            yOffset += (height - 64f * gridSize) / 2f;
        } else if (aspectRatio < 1f) {
            // narrow screen, align grid to horizontal center
            gridSize = width / 64f;
            xCenter = 32f * gridSize;
            yCenter = height / 2f;
            xOffset += (width - 64f * gridSize) / 2f;
        }

        // Set the color to green and fill a rectangle that covers the entire grid area
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                float cellX = xOffset + (x - 32f) * gridSize + xCenter;
                float cellY = yOffset + (y - 32f) * gridSize + yCenter;
                g.setColor(Color.GREEN);
                g.fillRect((int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize));
            }
        }
        
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                float cellX = xOffset + (x - 32f) * gridSize + xCenter;
                float cellY = yOffset + (y - 32f) * gridSize + yCenter;
                g.setColor(Color.BLACK);
                g.drawRect((int) cellX, (int) cellY, (int) gridSize, (int) gridSize);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (background != null && !displayGrid) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
        if (displayGrid){
            paintGrid(g);
        }
    }

    public static void main(String[] args) {
        TheSims game = new TheSims();
    }
}
