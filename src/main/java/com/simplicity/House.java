package com.simplicity;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class House {
    public House() {
    }

    // Buat Gambar Koordinat di Map
    public static void paint(Graphics g, int windowWidth, int windowHeight){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, windowWidth, windowHeight);

        int width = windowWidth - 6;
        int height = windowHeight - 6;
        float aspectRatio = (float) width / height;
        float gridSize = Math.min(width, height) / 6f;
        float xCenter = 3f * gridSize;
        float yCenter = 3f * gridSize;
        float xOffset = 3f;
        float yOffset = 3f;
        if (aspectRatio > 1f) {
            // wide screen, align grid to vertical center
            gridSize = height / 6f;
            xCenter = width / 2f;
            yCenter = 3f * gridSize;
            yOffset += (height - 6f * gridSize) / 2f;
        } else if (aspectRatio < 1f) {
            // narrow screen, align grid to horizontal center
            gridSize = width / 6f;
            xCenter = 3f * gridSize;
            yCenter = height / 2f;
            xOffset += (width - 6f * gridSize) / 2f;
        }

        // Draw the repeating background texture
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/main/java/resources/images/floor.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                float cellX = xOffset + (x - 3f) * gridSize + xCenter;
                float cellY = yOffset + (y - 3f) * gridSize + yCenter;
                if (cellX >= 0 && cellX + gridSize <= windowWidth && cellY >= 0 && cellY + gridSize <= windowHeight) {
                    g.drawImage(texture, (int) cellX, (int) cellY, (int) (gridSize), (int) (gridSize), null);
                }
            }
        }

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                float cellX = xOffset + (x - 3f) * gridSize + xCenter;
                float cellY = yOffset + (y - 3f) * gridSize + yCenter;
                g.setColor(Color.BLACK);
                g.drawRect((int) cellX, (int) cellY, (int) gridSize, (int) gridSize);
            }
        }
    }
}