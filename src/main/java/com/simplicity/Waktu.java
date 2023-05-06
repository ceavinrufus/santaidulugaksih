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

    public synchronized static Waktu getInstance() {
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
}
