package com.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CountdownPanel extends JPanel {
    private JLabel countdownLabel;
    private JFrame frame;

    public CountdownPanel(int detik, JFrame frame) {
        this.frame = frame;
        // create a label with initial countdown text
        int countDown = detik;
        countdownLabel = new JLabel("10 seconds remaining");
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);

        // add the label to the top of the panel
        add(countdownLabel, "Center");

        // start a countdown timer in a separate thread
        new Thread(() -> {
            int count = countDown;
            while (count >= 0) {
                int innerCount = count;
                try {
                    // update the label text with the remaining time
                    SwingUtilities.invokeLater(() -> {
                        countdownLabel.setText(innerCount + " seconds remaining");
                    });

                    // wait for 1 second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
            }

            // update the label text after the countdown is finished
            SwingUtilities.invokeLater(() -> {
                countdownLabel.setText("Time's up!");
                frame.dispose(); // close the window
            });
        }).start();
    }

}
