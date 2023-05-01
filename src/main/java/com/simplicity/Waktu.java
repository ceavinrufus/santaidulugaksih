package com.simplicity;

import com.google.gson.*;
import com.simplicity.Interface.*;
import com.simplicity.AbstractClass.*;
import java.io.*;

public class Waktu {
    private static Waktu waktuTotal;
    private int waktu;

    // Singleton, private constructor
    private Waktu() {
        waktu = 0;
    }

    public synchronized static Waktu waktu() {
        if (waktuTotal == null) {
            waktuTotal = new Waktu();
        }

        return waktuTotal;
    }

    public synchronized void addWaktu(int tambahanWaktu) {
        waktu += tambahanWaktu;
    }

    public int getWaktu() {
        return waktu;
    }

    public void saveWaktu(String filename) {
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
                    "_waktu.json");
            gson.toJson(this, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Waktu loadWaktu(String filename) throws IOException {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Furniture.class, new FurnitureAdapter());
        gsonBuilder.registerTypeAdapter(Storable.class, new StorableAdapter());
        gsonBuilder.registerTypeAdapter(Food.class, new FoodAdapter());
        gsonBuilder.registerTypeAdapter(Purchasable.class, new PurchasableAdapter());
        gson = gsonBuilder.create();
        Waktu waktu = null;

        FileReader fileReader = new FileReader("src/main/java/saves/" + filename +
                "_waktu.json");
        waktu = gson.fromJson(fileReader, Waktu.class);
        fileReader.close();
        return waktu;
    }

    // public static void main(String[] args) throws InterruptedException {
    // Waktu w = Waktu.waktu();

    // Runnable runA = new Runnable() {
    // public void run() {
    // for (int i=0; i<3; i++) {
    // w.addWaktu(10);
    // System.out.println(w.getWaktu());
    // try {
    // Thread.sleep(3000);
    // } catch (InterruptedException e) {
    // return;
    // }
    // }
    // }
    // };

    // Thread ta = new Thread(runA, "threadA");
    // ta.start();

    // Runnable runB = new Runnable() {
    // public void run() {
    // for (int i=0; i<3; i++) {
    // w.addWaktu(1);
    // System.out.println(w.getWaktu());
    // }
    // }
    // };

    // Thread tb = new Thread(runB, "threadB");
    // tb.start();
    // }
}
