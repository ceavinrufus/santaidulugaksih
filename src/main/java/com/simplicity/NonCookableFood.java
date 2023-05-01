package com.simplicity;

import com.simplicity.AbstractClass.Food;
import com.simplicity.Interface.Purchasable;

public class NonCookableFood extends Food implements Purchasable {
    private int harga;

    public NonCookableFood(String nama) {
        super(nama);

        if (nama.equals("Nasi")) {
            this.harga = 5;
            setKekenyangan(5);
        } else if (nama.equals("Kentang")) {
            this.harga = 3;
            setKekenyangan(4);
        } else if (nama.equals("Ayam")) {
            this.harga = 10;
            setKekenyangan(8);
        } else if (nama.equals("Sapi")) {
            this.harga = 12;
            setKekenyangan(15);
        } else if (nama.equals("Wortel")) {
            this.harga = 3;
            setKekenyangan(2);
        } else if (nama.equals("Bayam")) {
            this.harga = 3;
            setKekenyangan(2);
        } else if (nama.equals("Kacang")) {
            this.harga = 2;
            setKekenyangan(2);
        } else if (nama.equals("Susu")) {
            this.harga = 2;
            setKekenyangan(1);
        }
    }

    public int getHarga() {
        return harga;
    }
}