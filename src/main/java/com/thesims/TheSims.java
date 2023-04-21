package com.thesims;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TheSims extends JFrame implements KeyListener {
    private JLabel label;

    public TheSims() {
        setTitle("The Sims");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Press space!");
        label.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(label);
        // displayInitialMenu();

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
        int choice = JOptionPane.showOptionDialog(this, "Hi!", "Menu", JOptionPane.DEFAULT_OPTION,
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
        String[] options = {"Go to work", "Sleep", "Quit game"};
        int choice = JOptionPane.showOptionDialog(this, "What do you want to do?", "Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice != JOptionPane.CLOSED_OPTION) {
            switch (choice) {
                case 0:
                    label.setText("You are going to work");
                    break;
                case 1:
                    label.setText("You are going to sleep");
                    break;
                case 2:
                    label.setText("Game over");
                    System.exit(0);
            }
        }
    }

    private void displayHelp() {
        JOptionPane.showMessageDialog(this, "Use the arrow keys to move the character", "Help",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        TheSims game = new TheSims();
    }
}
