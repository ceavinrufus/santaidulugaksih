package com.simplicity;

import com.simplicity.AbstractClass.Furniture;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Kompor extends Furniture {
    public Kompor(String nama) throws IllegalArgumentException {
        super(nama);
        if (nama.equals("Kompor Gas")) {
            setPanjang(2);
            setLebar(1);
            setHarga(100);
        } else if (nama.equals("Kompor Listrik")) {
            setPanjang(1);
            setLebar(1);
            setHarga(200);
        } else {
            throw new IllegalArgumentException("Nama kompor invalid!");
        }
    }

    @Override
    public String getNamaAksi() {
        return "Memasak";
    }

    @Override
    public void aksi(Sim sim) {
        Resep.displayResep(sim);
    }
}