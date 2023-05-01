package com.gui;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class TabbedPane {
    public TabbedPane() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        window.add(tabbedPane);

        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();

        tabbedPane.add("Sim1", label1);
        tabbedPane.add("Sim2", label2);

        window.setVisible(true);
    }
}
