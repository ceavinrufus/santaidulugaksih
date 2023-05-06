package com.simplicity;

import javax.swing.JOptionPane;

import com.simplicity.AbstractClass.Furniture;

public class ArcadeMachine extends Furniture {
    public ArcadeMachine() {
        super("Arcade Machine");
        setPanjang(1);
        setLebar(1);
        setHarga(100);
    }

    @Override
    public String getNamaAksi() {
        return "Bermain";
    }

    @Override
    public void aksi(Sim sim) {
        Integer bermainTime = 0;
        Boolean inputValid = false;
        while (!inputValid) {
            String input = "";
            try {
                input = JOptionPane.showInputDialog(null, "Masukkan waktu bermain (detik): ");
                if (input == null) {
                    // Kalo pencet tombol close
                    return;
                } else {
                    bermainTime = Integer.parseInt(input);
                    inputValid = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Waktu bermain harus berbentuk bilangan bulat, lho!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        bermainTime += 10;

        mulaiAksi(bermainTime);

        sim.getStats().tambahMood(bermainTime / 10 * 10);
        sim.getStats().kurangKekenyangan(bermainTime / 10 * 10);
        sim.getStats().kurangKesehatan(bermainTime / 10 * 10);
        JOptionPane.showMessageDialog(null,
                "Waduh keasikan...",
                "Arcade Machine",
                JOptionPane.INFORMATION_MESSAGE);
        Waktu.getInstance().addWaktu(bermainTime);
    }
}
