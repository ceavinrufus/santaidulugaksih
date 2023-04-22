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

    public TheSims() {
        setTitle("The Sims");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            background = ImageIO.read(new File("src/main/java/resources/images/background.jpeg"));
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }

        label = new JLabel("Press space to start the game");
        label.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(label);

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
        JOptionPane.showMessageDialog(this, "Use the arrow keys to move the character", "Help",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public static void main(String[] args) {
        TheSims game = new TheSims();
    }
}
