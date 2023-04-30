package com.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Cursor;

import javax.swing.JButton;

public class PixelatedButton extends JButton {
    private String buttonText;
    private boolean isPressed;
    private boolean isHovered;

    public PixelatedButton(String buttonText) {
        this.buttonText = buttonText;
        setPreferredSize(new Dimension(200, 50)); // button preferred size

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                isHovered = false;
                isPressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    public void setText(String buttonText) {
        this.buttonText = buttonText;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw click animation
        if (isPressed) {
            g.setColor(new Color(128, 128, 128, 128));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        // draw hover animation
        else if (isHovered) {
            g.setColor(new Color(255, 255, 255, 128));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        // font & warna text
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/resources/font/ArcadeGamer.otf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        Font customSizedFont = customFont.deriveFont(Font.BOLD, 20);

        g.setFont(customSizedFont);
        g.setColor(Color.BLACK);

        // text di tengah tengah
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(buttonText)) / 2;
        int y = (fm.getAscent() + (getHeight() - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(buttonText, x, y);

        // button border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

    }
}
