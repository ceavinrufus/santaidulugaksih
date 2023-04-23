package com.simplicity;

import java.util.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class World {
    int panjang;
    int lebar;
    ArrayList<Rumah> daftarRumah;
    ArrayList<ArrayList<Boolean>> lahanKosong;

    public World(int panjang, int lebar) {
        this.panjang = panjang;
        this.lebar = lebar;
        daftarRumah = new ArrayList<>();
        lahanKosong = new ArrayList<>();
        for (int i = 0; i < panjang; i++) {
            lahanKosong.add(new ArrayList<>());
            for (int j = 0; j < lebar; j++) {
                lahanKosong.get(i).add(true);
            }
        }
    }

    public int getPanjang() {
        return panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public ArrayList<Rumah> getDaftarRumah() {
        return daftarRumah;
    }

    public void tambahRumah(Rumah rumah) {
        if (rumah.getLokasi().getX() < 0 || rumah.getLokasi().getX() >= panjang
                || rumah.getLokasi().getY() < 0 || rumah.getLokasi().getY() >= lebar) {
            System.out.println("Lokasi rumah tidak valid");
            return;
        } else {
            if (lahanKosong.get(rumah.getLokasi().getX()).get(rumah.getLokasi().getY())) {
                daftarRumah.add(rumah);
                lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), false);
            } else {
                System.out.println("Lahan rumah sudah terisi");
            }
        }
    }

    public void hapusRumah(Rumah rumah) {
        if (rumah.getLokasi().getX() < 0 || rumah.getLokasi().getX() >= panjang
                || rumah.getLokasi().getY() < 0 || rumah.getLokasi().getY() >= lebar) {
            System.out.println("Lokasi rumah tidak valid");
            return;
        } else {
            if (lahanKosong.get(rumah.getLokasi().getX()).get(rumah.getLokasi().getY())) {
                System.out.println("Lahan rumah kosong");
            } else {
                daftarRumah.remove(rumah);
                lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), true);
            }
        }
    }

    public void printWorld() {
        for (int i = 0; i < panjang; i++) {
            for (int j = 0; j < lebar; j++) {
                if (lahanKosong.get(i).get(j)) {
                    System.out.print("Available");
                } else {
                    System.out.print("Occupied");
                }
            }
            System.out.println();
        }
    }

    // Buat Gambar Koordinat di Map
    public static void paint(Graphics g, int windowWidth, int windowHeight) {
        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(new File("src/main/java/resources/images/sea.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pattern != null) {
            int patternWidth = pattern.getWidth(null);
            int patternHeight = pattern.getHeight(null);
            for (int x = 0; x < windowWidth; x += patternWidth) {
                for (int y = 0; y < windowHeight; y += patternHeight) {
                    g.drawImage(pattern, x, y, null);
                }
            }
        }

        pattern = null;
        try {
            pattern = ImageIO.read(new File("src/main/java/resources/images/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate grid position and size
        float gridWidth = 64f;
        float gridHeight = 64f;
        float gridSize = Math.min(windowWidth, windowHeight) / gridWidth;
        float gridX = (windowWidth - gridSize * gridWidth) / 2f;
        float gridY = (windowHeight - gridSize * gridHeight) / 2f;

        // Draw background image only for the region behind the grid
        g.drawImage(pattern, (int) gridX, (int) gridY, (int) (gridSize * gridWidth), (int) (gridSize * gridHeight),
                null);

        int width = windowWidth - 64;
        int height = windowHeight - 64;
        float aspectRatio = (float) width / height;
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

        // Set the color to white and fill a rectangle that covers the entire grid area
        Color gridBg = new Color(255, 255, 255, 90);
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                float cellX = xOffset + (x - 32f) * gridSize + xCenter;
                float cellY = yOffset + (y - 32f) * gridSize + yCenter;
                if (x == 0 && 63 - y == 0) { // Ini contoh kalau misal ada rumah di titik (0, 0)
                    g.setColor(Color.BLACK);
                    g.fillRect((int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize));
                } else {
                    g.setColor(gridBg);
                    g.fillRect((int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize));
                }
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
}
