package com.simplicity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.gui.*;
import com.simplicity.AbstractClass.Furniture;

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

    private String namaPemilik;
    private Peta<Ruangan> petaRuangan = new Peta<Ruangan>(1, 1);
    // private int jumlahRuangan = 1;

    public Rumah(Ruangan ruangan) {
        petaRuangan.setElement(0, 0, ruangan);
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(Sim pemilik) {
        namaPemilik = pemilik.getNamaLengkap();
    }

    public Peta<Ruangan> getPeta() {
        return petaRuangan;
    }

    // public int getJumlahRuangan() {
    // return jumlahRuangan;
    // }

    public void tambahRuangan(Ruangan ruanganBaru, String arah, Ruangan ruanganPatokan) {
        int x;
        int y;
        int jumlahCol = petaRuangan.getColumn();
        int jumlahRow = petaRuangan.getRow();

        for (int i = 0; i < jumlahCol; i++) {
            for (int j = 0; j < jumlahRow; j++) {
                x = i;
                y = j;
                if (petaRuangan.getElement(x, y) != null) {
                    if ((ruanganPatokan.getNamaRuangan().equals(petaRuangan.getElement(x, y).getNamaRuangan()))) {
                        switch (arah.toLowerCase()) {
                            case "bawah":
                                if (y == 0) {
                                    petaRuangan.addTop();
                                    y += 1;
                                }
                                if (petaRuangan.getElement(x, y - 1) == null) {
                                    petaRuangan.setElement(x, y - 1, ruanganBaru);
                                }
                                break;
                            case "atas":
                                if (y == jumlahRow - 1) {
                                    petaRuangan.addBottom();
                                }
                                if (petaRuangan.getElement(x, y + 1) == null) {
                                    petaRuangan.setElement(x, y + 1, ruanganBaru);
                                }
                                break;
                            case "kanan":
                                if (x == jumlahCol - 1) {
                                    petaRuangan.addRight();
                                }
                                if (petaRuangan.getElement(x + 1, y) == null) {
                                    petaRuangan.setElement(x + 1, y, ruanganBaru);
                                }
                                break;
                            case "kiri":
                                if (x == 0) {
                                    petaRuangan.addLeft();
                                    x += 1;
                                }
                                if (petaRuangan.getElement(x - 1, y) == null) {
                                    petaRuangan.setElement(x - 1, y, ruanganBaru);
                                }
                                break;
                            default:
                                System.out.println("Arah tidak valid");
                                break;
                        }
                    }
                }
            }
        }
    }

    public void hapusRuangan(Ruangan ruangan) {
        for (int i = 0; i < petaRuangan.getRow(); i++) {
            for (int j = 0; j < petaRuangan.getColumn(); j++) {
                if (petaRuangan.getElement(i, j) == ruangan) {
                    petaRuangan.setElement(i, j, null);
                } else {
                    System.out.println("Ruangan tidak ditemukan");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Rumah " + namaPemilik;
    }

    // GUI
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
        SimPosition currentSimPosition = Game.getInstance().getCurrentSim().getCurrentPosition();
        String currentNamaRuangan = Game.getInstance().getCurrentSim().getCurrentPosition().getRuang()
                .getNamaRuangan();
        for (int x = 0; x < column; x++) {
            for (int y = 0; y < row; y++) {
                float cellX = xOffset + (x - column / 2) * gridSize + xCenter;
                float cellY = yOffset + (y - row / 2) * gridSize + yCenter;
                if (petaRuangan.getElement(x / 6, (row - y - 1) / 6) == null) {
                    g.setColor(Color.BLACK);
                    g.fillRect((int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize));
                } else {
                    // Barang
                    Furniture barang = petaRuangan.getElement(x / 6, (row - y - 1) / 6).getPeta().getElement(x % 6,
                            (row - y - 1) % 6);
                    if (barang != null) {
                        if (barang.getNama().equals("Toilet")) {
                            Image toilet = null;
                            try {
                                toilet = ImageIO.read(new File("src/main/java/resources/images/toilet.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(toilet, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                        } else if (barang.getNama().equals("Jam")) {
                            Image jam = null;
                            try {
                                jam = ImageIO.read(new File("src/main/java/resources/images/jam.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(jam, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                        } else {
                            g.setColor(Color.RED);
                            g.fillRect((int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize));
                        }
                    }
                    // Player
                    if (currentNamaRuangan.equals(petaRuangan.getElement(x / 6, (row - y - 1) / 6).getNamaRuangan())
                            && (x % 6 == currentSimPosition.getLokasi().getX()
                                    && (row - y - 1) % 6 == currentSimPosition.getLokasi().getY())) {
                        Image player = null;
                        int key = HomePanel.getInstance().getKeyChar();
                        if ((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP)) {
                            try {
                                player = ImageIO.read(new File("src/main/java/resources/images/player_back.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(player, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                        } else if ((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)) {
                            try {
                                player = ImageIO.read(new File("src/main/java/resources/images/player_right.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(player, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                        } else if ((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)) {
                            try {
                                player = ImageIO.read(new File("src/main/java/resources/images/player_left.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(player, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                        } else {
                            try {
                                player = ImageIO.read(new File("src/main/java/resources/images/player_front.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(player, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                        }
                    }
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
                g.setColor(new Color(156, 134, 112));
                g.drawRect((int) cellX, (int) cellY, (int) gridSize, (int) gridSize);
            }
        }
    }
}