package com.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PixelatedButton extends JPanel {
    private String buttonText;
    private Font font;

    public PixelatedButton(String buttonText, Font font) {
        this.buttonText = buttonText;
        this.font = font;
        setPreferredSize(new Dimension(200, 50)); // button preferred size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // font & warna text
        g.setFont(font);
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
