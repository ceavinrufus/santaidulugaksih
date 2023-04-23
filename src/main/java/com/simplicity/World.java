package com.simplicity;

import java.util.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class World {
    // Singleton
    private static World instance = new World();
    private Peta<Rumah> petaRumah = new Peta<Rumah>(64, 64);
    
    private World() {}

    public static World getInstance() {
        return instance;
    }

    public Peta<Rumah> getPeta() {
        return petaRumah;
    }

    // Izin komen mas biar bisa dirun
    // public void tambahRumah(Rumah rumah) {
    //     if (rumah.getLokasi().getX() < 0 || rumah.getLokasi().getX() >= panjang
    //             || rumah.getLokasi().getY() < 0 || rumah.getLokasi().getY() >= lebar) {
    //         System.out.println("Lokasi rumah tidak valid");
    //         return;
    //     } else {
    //         if (lahanKosong.get(rumah.getLokasi().getX()).get(rumah.getLokasi().getY())) {
    //             daftarRumah.add(rumah);
    //             lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), false);
    //         } else {
    //             System.out.println("Lahan rumah sudah terisi");
    //         }
    //     }
    // }

    // public void hapusRumah(Rumah rumah) {
    //     if (rumah.getLokasi().getX() < 0 || rumah.getLokasi().getX() >= panjang
    //             || rumah.getLokasi().getY() < 0 || rumah.getLokasi().getY() >= lebar) {
    //         System.out.println("Lokasi rumah tidak valid");
    //         return;
    //     } else {
    //         if (lahanKosong.get(rumah.getLokasi().getX()).get(rumah.getLokasi().getY())) {
    //             System.out.println("Lahan rumah kosong");
    //         } else {
    //             daftarRumah.remove(rumah);
    //             lahanKosong.get(rumah.getLokasi().getX()).set(rumah.getLokasi().getY(), true);
    //         }
    //     }
    // }

    // public void printWorld() {
    //     for (int i = 0; i < panjang; i++) {
    //         for (int j = 0; j < lebar; j++) {
    //             if (lahanKosong.get(i).get(j)) {
    //                 System.out.print("Available");
    //             } else {
    //                 System.out.print("Occupied");
    //             }
    //         }
    //         System.out.println();
    //     }
    // }

    // GUI
    public void paint(Graphics g, int windowWidth, int windowHeight) {
        BufferedImage pattern = null;
        try {
            pattern = ImageIO.read(new File("src/main/java/resources/images/grass.png"));
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }

        // Calculate grid position and size
        float gridWidth = 64f;
        float gridHeight = 64f;
        float gridSize = Math.min(windowWidth, windowHeight) / gridWidth;
        float gridX = (windowWidth - gridSize * gridWidth) / 2f;
        float gridY = (windowHeight - gridSize * gridHeight) / 2f;

        // Draw background image only for the region behind the grid
        g.drawImage(pattern, (int) gridX, (int) gridY, (int) (gridSize * gridWidth), (int) (gridSize * gridHeight), null);

        int width = windowWidth - 64;
        int height = windowHeight - 64;
        float aspectRatio = (float) width / height;
        float xCenter = 32f * gridSize;
        float yCenter = 32f * gridSize;
        float xOffset = 32f;
        float yOffset = 44f;
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
            yOffset -= 12f;
            xOffset += (width - 64f * gridSize) / 2f;
        }

        // Gambar background grid
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/main/java/resources/images/grass.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int x = 0; x < 64; x++) {
            for (int y = 0; y < 64; y++) {
                float cellX = xOffset + (x - 32f) * gridSize + xCenter;
                float cellY = yOffset + (y - 32f) * gridSize + yCenter;
                if (cellX >= 0 && cellX + gridSize <= windowWidth && cellY >= 0 && cellY + gridSize <= windowHeight) {
                    g.drawImage(texture, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                }
            }
        }

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

        // Gambar grid
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
