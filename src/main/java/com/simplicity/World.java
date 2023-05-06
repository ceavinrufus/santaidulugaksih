package com.simplicity;

import java.io.*;
import com.google.gson.*;
import com.simplicity.Interface.*;
import com.simplicity.AbstractClass.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.gui.Game;
import com.simplicity.ExceptionHandling.IllegalLocationException;

public class World {
    private static World instance = new World();
    private Peta<Rumah> petaRumah = new Peta<Rumah>(64, 64);

    public World() {
    }

    public static World getInstance() {
        return instance;
    }

    public Peta<Rumah> getPeta() {
        return petaRumah;
    }

    public void tambahRumah(Rumah rumah, int x, int y) throws IllegalLocationException {
        if (x < 0 || x >= petaRumah.getColumn() || y < 0 || y >= petaRumah.getRow()) {
            throw new IllegalLocationException("Lokasi rumah tidak valid");
        } else {
            if (petaRumah.getElement(x, y) == null) {
                petaRumah.setElement(x, y, rumah);
            } else {
                throw new IllegalLocationException("Lahan rumah sudah terisi");
            }
        }
    }

    public void hapusRumah(int x, int y) throws IllegalLocationException {
        if (x < 0 || x >= petaRumah.getColumn() || y < 0 || y >= petaRumah.getRow()) {
            throw new IllegalLocationException("Lokasi rumah tidak valid");
        } else {
            if (petaRumah.getElement(x, y) != null) {
                petaRumah.setElement(x, y, null);
            } else {
                throw new IllegalLocationException("Lahan rumah kosong");
            }
        }
    }

    public Rumah findRumah(String namaPemilik) {
        for (int i = 0; i < petaRumah.getRow(); i++) {
            for (int j = 0; j < petaRumah.getColumn(); j++) {
                if (petaRumah.getElement(j, i) != null) {
                    if (petaRumah.getElement(j, i).getNamaPemilik().equals(namaPemilik)) {
                        return petaRumah.getElement(j, i);
                    }
                }
            }
        }
        return null;
    }

    public void saveWorld(String filename) throws IOException {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Furniture.class, new FurnitureAdapter());
        gsonBuilder.registerTypeAdapter(Storable.class, new StorableAdapter());
        gsonBuilder.registerTypeAdapter(Food.class, new FoodAdapter());
        gsonBuilder.registerTypeAdapter(Purchasable.class, new PurchasableAdapter());
        gson = gsonBuilder.create();
        try {
            FileWriter fileWriter = new FileWriter("src/main/java/saves/" + filename +
                    "_world.json");
            gson.toJson(this, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World loadWorld(String filename) throws IOException {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Furniture.class, new FurnitureAdapter());
        gsonBuilder.registerTypeAdapter(Storable.class, new StorableAdapter());
        gsonBuilder.registerTypeAdapter(Food.class, new FoodAdapter());
        gsonBuilder.registerTypeAdapter(Purchasable.class, new PurchasableAdapter());
        gson = gsonBuilder.create();
        World world = null;
        FileReader fileReader = new FileReader("src/main/java/saves/" + filename +
                "_world.json");
        world = gson.fromJson(fileReader, World.class);
        fileReader.close();
        return world;
    }

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
        g.drawImage(pattern, (int) gridX, (int) gridY, (int) (gridSize * gridWidth), (int) (gridSize * gridHeight),
                null);

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
                Rumah rumah = petaRumah.getElement(x, 63 - y);
                if (rumah != null) {
                    Sim currentSim = Game.getInstance().getCurrentSim();
                    // Di mana player berada
                    if (currentSim.getCurrentPosition().getRumah().equals(rumah)) {
                        g.setColor(Color.BLUE);
                    } else {
                        if (rumah.getNamaPemilik().equals(currentSim.getNamaLengkap())) {
                            // Rumahnya player
                            g.setColor(Color.RED);
                        } else {
                            // Rumahnya orang
                            g.setColor(Color.BLACK);
                        }
                    }
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
