package com.simplicity;

import javax.swing.*;

import com.simplicity.AbstractClass.Food;

public class CookableFood extends Food {
    private String[] resep;

    public CookableFood(String nama) {
        super(nama);

        if (nama.equals("Nasi Ayam")) {
            this.resep = new String[] { "Nasi", "Ayam" };
            setKekenyangan(16);
        } else if (nama.equals("Nasi Kari")) {
            this.resep = new String[] { "Nasi", "Kentang", "Wortel", "Sapi" };
            setKekenyangan(30);
        } else if (nama.equals("Susu Kacang")) {
            this.resep = new String[] { "Susu", "Kacang" };
            setKekenyangan(5);
        } else if (nama.equals("Tumis Sayur")) {
            this.resep = new String[] { "Wortel", "Bayam" };
            setKekenyangan(5);
        } else if (nama.equals("Bistik")) {
            this.resep = new String[] { "Kentang", "Sapi" };
            setKekenyangan(22);
        }
    }

    public String[] getResep() {
        return resep;
    }
}