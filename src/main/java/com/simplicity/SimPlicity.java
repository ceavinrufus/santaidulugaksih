package com.simplicity;
import java.util.*;
import java.util.stream.Stream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SimPlicity extends JFrame {
    private JLabel label;
    private int gridSize = 64;
    private KeyListener keyListener;
    private BufferedImage icon;
    private JLabel backgroundLabel;
    private boolean displayGrid = false;
    private int xOffset;
    private int yOffset;

    public SimPlicity() {
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
                    displayGrid = true;
                    repaint();
                    removeKeyListener(this);
                    displayGameMenu();
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    JOptionPane.showMessageDialog(SimPlicity.this, "Gatau mainin aja udah pokoknya", "Help", JOptionPane.INFORMATION_MESSAGE);
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        };

        addKeyListener(keyListener);
        setFocusable(true);
        setVisible(true);
    }

    // Menu game
    private void displayGameMenu() {
        ArrayList<String> optionsList = new ArrayList<>(Arrays.asList("View Sim Info", "View Current Location", "View Inventory", "House Menu", "Add Sim", "Change Sim", "Exit"));
        
        String[] options = optionsList.toArray(new String[0]);
        JList<String> list = new JList<>(options);
        JOptionPane.showMessageDialog(this, new JScrollPane(list), "Menu", JOptionPane.PLAIN_MESSAGE);
        String selectedOption = list.getSelectedValue();
        if (selectedOption != null) {
            switch (selectedOption) {
                case "View Sim Info":
                    break;
                case "View Current Location":
                    break;
                case "View Inventory":
                    break;
                case "House Menu":
                    displayHouseMenu();
                case "Add Sim":
                    break;
                case "Change Sim":
                    break;
                case "Exit":
                    displayGrid = false;
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
        ArrayList<String> roomList = new ArrayList<>(Arrays.asList("Edit Room", "List Object", "Go To Object", "Action"));

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

    // Buat Gambar Koordinat di Map
    private void paintGrid(Graphics g){
        int width = getWidth() - 64;
        int height = getHeight() - 64;
        float aspectRatio = (float) width / height;
        float gridSize = Math.min(width, height) / 64f;
        float xCenter = 32f * gridSize;
        float yCenter = 32f * gridSize;
        float xOffset = 32f;
        float yOffset = 32f;
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
        Color gridBg = new Color(255, 255, 255, 90);
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                float cellX = xOffset + (x - 32f) * gridSize + xCenter;
                float cellY = yOffset + (y - 32f) * gridSize + yCenter;
                g.setColor(gridBg);
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

    private void paintGrass(Graphics g) {
        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(new File("src/main/java/resources/images/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Calculate grid position and size
        int gridWidth = 64;
        int gridHeight = 64;
        int gridSize = Math.min(getWidth(), getHeight()) / gridWidth;
        int gridX = (getWidth() - gridSize * gridWidth) / 2;
        int gridY = (getHeight() - gridSize * gridHeight) / 2;

        // Draw background image only for the region behind the grid
        g.drawImage(pattern, gridX, gridY, gridSize * gridWidth, gridSize * gridHeight, null);
    }

    private void paintSea(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(new File("src/main/java/resources/images/sea.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pattern != null) {
            int patternWidth = pattern.getWidth(null);
            int patternHeight = pattern.getHeight(null);
            for (int x = 0; x < width; x += patternWidth) {
                for (int y = 0; y < height; y += patternHeight) {
                    g.drawImage(pattern, x, y, null);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (displayGrid){
            paintSea(g);
            paintGrass(g);
            paintGrid(g);
        }
    }

    public static void main(String[] args) {
        SimPlicity game = new SimPlicity();
    }
}
