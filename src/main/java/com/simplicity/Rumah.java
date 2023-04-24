package com.simplicity;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Rumah {
    /*
     * Rumah adalah lingkungan virtual berisi ruangan-ruangan. Pertama kali dimulai,
     * rumah sim hanya memiliki 1 ruangan yaitu kamar berukuran 6x6. Sim dapat
     * melakukan upgrade terhadap rumah dengan menambahkan ruangan-ruangan baru.
     * Satu ruangan hanya dapat terhubung dengan 4 ruangan lainnya (atas, bawah,
     * kanan, dan kiri).
     * 
     * Rumah memiliki lokasi (x,y) dan daftar ruangan apa saja yang ada pada rumah
     * tersebut
     * 
     */

    private Sim pemilik;
    private Peta<Ruangan> petaRuangan = new Peta<Ruangan>(1, 1);
    private int jumlahRuangan = 1;

    public Rumah(Sim pemilik) {
        this.pemilik = pemilik;
        petaRuangan.setElement(0, 0, new Ruangan("Main Room", jumlahRuangan));
    }

    public Sim getPemilik() {
        return pemilik;
    }

    public Peta<Ruangan> getPeta() {
        return petaRuangan;
    }

    public int getJumlahRuangan() {
        return jumlahRuangan;
    }

    public void tambahRuangan(Ruangan ruanganBaru, String arah, Ruangan ruanganPatokan) {
        int x;
        int y;

        for (int i = 0; i < petaRuangan.getRow(); i++) {
            for (int j = 0; j < petaRuangan.getColumn(); j++) {
                if (petaRuangan.getElement(i, j) == ruanganPatokan) {
                    x = i;
                    y = j;

                    switch (arah.toLowerCase()) {
                        case "atas":
                            petaRuangan.addRow();
                            if (petaRuangan.getElement(x, y + 1) != null) {
                                System.out.println("Ruangan sudah ada");
                                petaRuangan.removeRow(petaRuangan.getRow() - 1);
                            } else {
                                petaRuangan.setElement(x, y + 1, ruanganBaru);
                                jumlahRuangan++;
                            }
                            break;
                        case "bawah":
                            petaRuangan.addRow();
                            if (y != 0) {
                                if (petaRuangan.getElement(x, y - 1) != null) {
                                    System.out.println("Ruangan sudah ada");
                                    petaRuangan.removeRow(petaRuangan.getRow() - 1);
                                } else {
                                    petaRuangan.setElement(x, y - 1, ruanganBaru);
                                    jumlahRuangan++;
                                }
                            } else {
                                petaRuangan.setElement(x, y + 1, ruanganPatokan);
                                petaRuangan.setElement(x, y, ruanganBaru);
                                jumlahRuangan++;
                            }
                            break;
                        case "kanan":
                            petaRuangan.addColumn();
                            if (petaRuangan.getElement(x + 1, y) != null) {
                                System.out.println("Ruangan sudah ada");
                                petaRuangan.removeColumn(petaRuangan.getColumn() - 1);
                            } else {
                                petaRuangan.setElement(x + 1, y, ruanganBaru);
                                jumlahRuangan++;
                            }
                            break;
                        case "kiri":
                            petaRuangan.addColumn();
                            if (x != 0) {
                                if (petaRuangan.getElement(x - 1, y) != null) {
                                    System.out.println("Ruangan sudah ada");
                                    petaRuangan.removeColumn(petaRuangan.getColumn() - 1);
                                } else {
                                    petaRuangan.setElement(x - 1, y, ruanganBaru);
                                    jumlahRuangan++;
                                }
                            } else {
                                petaRuangan.setElement(x + 1, y, ruanganPatokan);
                                petaRuangan.setElement(x, y, ruanganBaru);
                                jumlahRuangan++;
                            }
                            break;
                        default:
                            System.out.println("Arah tidak valid");
                            break;
                    }
                } else {
                    System.out.println("Ruangan tidak ditemukan");
                }
            }
        }
    }

    public void hapusRuangan(Ruangan ruangan) {
        for (int i = 0; i < petaRuangan.getRow(); i++) {
            for (int j = 0; j < petaRuangan.getColumn(); j++) {
                if (petaRuangan.getElement(i, j) == ruangan) {
                    petaRuangan.setElement(i, j, null);
                    jumlahRuangan--;
                } else {
                    System.out.println("Ruangan tidak ditemukan");
                }
            }
        }
    }

    public void printRumah() {
        int x = (petaRuangan.getColumn() * 6) + 1;
        int y = (petaRuangan.getRow() * 6) + 1;

        for (int i = y - 1; i >= 0; i--) {
            for (int j = 0; j < x; j++) {
                if (i == 0 || i == y - 1) {
                    System.out.print("-");
                } else if (j == 0 || j == x - 1) {
                    System.out.print("|");
                } else if (i % 6 == 0 && j % 6 == 0) {
                    System.out.print(petaRuangan.getElement(j / 6, i / 6).getNoRuang());
                } else if (i % 6 == 0) {
                    System.out.print("-");
                } else if (j % 6 == 0) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    // Buat Gambar Koordinat di Peta
    public void paint(Graphics g, int windowWidth, int windowHeight) {
        int row = petaRuangan.getRow() * 6;
        int column = petaRuangan.getColumn() * 6;
        int width = windowWidth - column;
        int height = windowHeight - row;
        float aspectRatio = (float) width / height;
        float gridSize = Math.min(height / (float) row, width / (float) column);
        float xCenter = (float) (column / 2) * gridSize;
        float yCenter = (float) (row / 2) * gridSize;
        float xOffset = (float) column / 2f;
        float yOffset = (float) row / 2f;
        if (aspectRatio > 1f) {
            // wide screen, align grid to vertical center
            xCenter = width / 2f;
            yCenter = (float) (row / 2f) * gridSize;
            yOffset += (height - (float) row * gridSize) / 2f;
        } else if (aspectRatio < 1f) {
            // narrow screen, align grid to horizontal center
            xCenter = (float) (column / 2f) * gridSize;
            yCenter = height / 2f;
            xOffset += (width - (float) column * gridSize) / 2f;
        }

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, windowWidth, windowHeight);

        // Gambar background grid
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/main/java/resources/images/floor.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int x = 0; x < column; x++) {
            for (int y = 0; y < row; y++) {
                float cellX = xOffset + (x - column / 2) * gridSize + xCenter;
                float cellY = yOffset + (y - row / 2) * gridSize + yCenter;
                if (cellX >= 0 && cellX + gridSize <= windowWidth && cellY >= 0 && cellY + gridSize <= windowHeight) {
                    g.drawImage(texture, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                }
            }
        }

        Color gridBg = new Color(255, 255, 255, 90);
        for (int x = 0; x < column; x++) {
            for (int y = 0; y < row; y++) {
                float cellX = xOffset + (x - column / 2) * gridSize + xCenter;
                float cellY = yOffset + (y - row / 2) * gridSize + yCenter;
                if (petaRuangan.getElement(x / 6, (row - y - 1) / 6) == null) { // Ini contoh kalau misal ada rumah di
                                                                                // titik (0, 0)
                    g.setColor(Color.BLACK);
                    g.fillRect((int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize));
                } else {
                    g.setColor(gridBg);
                    g.fillRect((int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize));
                }
            }
        }

        // Gambar grid
        for (int x = 0; x < column; x++) {
            for (int y = 0; y < row; y++) {
                float cellX = xOffset + (x - column / 2) * gridSize + xCenter;
                float cellY = yOffset + (y - row / 2) * gridSize + yCenter;
                g.setColor(Color.BLACK);
                g.drawRect((int) cellX, (int) cellY, (int) gridSize, (int) gridSize);
            }
        }
    }
}