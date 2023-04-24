package com.gui;

import com.simplicity.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SimPlicity extends JFrame {
    private static SimPlicity instance = new SimPlicity();

    private KeyListener keyListener;
    private BufferedImage icon;
    private JLabel backgroundLabel;
    private boolean displayWorld = false;
    private boolean displayHouse = false;

    ArrayList<Sim> sims = new ArrayList<Sim>();
    public Sim currentSim;

    private SimPlicity() {
        setTitle("Sim-Plicity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ngambil image buat dijadiin background
        ImageIcon backgroundImage = new ImageIcon("src/main/java/resources/images/background.png");
        backgroundLabel = new JLabel(backgroundImage);

        setMinimumSize(new Dimension(800, 1000)); // Set minimum size JFrame
        setPreferredSize(new Dimension(800, 1000)); // Set preferred size JFrame
        pack(); // Bikin JFrame fit ke preferred size

        // Add JLabel ke content pane JFrame
        getContentPane().add(backgroundLabel);

        try {
            icon = ImageIO.read(new File("src/main/java/resources/images/icon.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }

        keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    displayWorld = true;
                    repaint();
                    removeKeyListener(this);
                    displayGameMenu();
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    JOptionPane.showMessageDialog(SimPlicity.this, "Gatau mainin aja udah pokoknya", "Help",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        addKeyListener(keyListener);
        setFocusable(true);
        setVisible(true);
    }

    public static SimPlicity getInstance() {
        return instance;
    }

    // Menu game
    private void displayGameMenu() {
        ArrayList<String> optionsList = new ArrayList<>(Arrays.asList("View Sim Info", "View Current Location",
                "View Inventory", "House Menu", "Add Sim", "Change Sim", "Exit"));

        String[] options = optionsList.toArray(new String[0]);
        JList<String> list = new JList<>(options);
        JOptionPane.showMessageDialog(null, new JScrollPane(list), "Menu", JOptionPane.PLAIN_MESSAGE);
        String selectedOption = list.getSelectedValue();
        if (selectedOption != null) {
            switch (selectedOption) {
                case "View Sim Info":
                    String message = "Nama: " + currentSim.getNamaLengkap() + "\n" +
                            "Pekerjaan: " + currentSim.getPekerjaan() + "\n" +
                            // "Kesehatan: " + currentSim.getStatus().getKesehatan() + "\n" +
                            // "Kekenyangan: " + currentSim.getStatus().getKekenyangan() + "\n" +
                            // "Mood: " + currentSim.getStatus().getMood() + "\n" +
                            "Uang: " + currentSim.getUang();

                    JOptionPane.showMessageDialog(null, message, "Sim Info", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "View Current Location":
                    break;
                case "View Inventory":
                    currentSim.getInventory().displayInventory();
                    break;
                case "House Menu":
                    displayHouseMenu();
                case "Add Sim":
                    break;
                case "Change Sim":
                    break;
                case "Exit":
                    displayWorld = false;
                    addKeyListener(keyListener);
                    repaint();
                    break;
            }
        }
    }

    // House Menu
    private void displayHouseMenu() {
        boolean inHouse = true;
        boolean inRoom = true;

        ArrayList<String> houseList = new ArrayList<>(Arrays.asList("Upgrade House", "Move Room"));
        ArrayList<String> roomList = new ArrayList<>(
                Arrays.asList("Edit Room", "List Object", "Go To Object", "Action"));

        if (inRoom) {
            houseList.addAll(roomList);
        }
        houseList.add("Back");

        String[] options = houseList.toArray(new String[0]);
        JList<String> list = new JList<>(options);
        JOptionPane.showMessageDialog(this, new JScrollPane(list), "Menu", JOptionPane.PLAIN_MESSAGE);
        String selectedOption = list.getSelectedValue();
        if (selectedOption != null) {
            switch (selectedOption) {
                case "Upgrade House":
                    break;
                case "Move Room":
                    break;
                case "Edit Room":
                    break;
                case "List Object":
                    break;
                case "Go To Object":
                    break;
                case "Action":
                    break;
                case "Back":
                    displayGameMenu();
                    break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // displayHouse = true; // Buat ngetes doang
        // displayWorld = false; // Buat ngetes doang
        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(new File("src/main/java/resources/images/sea.jpg"));
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }
        if (displayWorld) {
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
        if (displayHouse) {
            Rumah rumahDummy = new Rumah(new Sim("Dummy"));
            rumahDummy.paint(g, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SimPlicity game = SimPlicity.getInstance();

        game.currentSim = new Sim("Cepus");
    }
}
