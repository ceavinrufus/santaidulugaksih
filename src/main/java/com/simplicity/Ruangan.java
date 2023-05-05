package com.simplicity;

import java.util.*;
import javax.swing.*;
import javax.swing.JOptionPane;

import com.gui.Game;
import com.simplicity.AbstractClass.Furniture;
import com.simplicity.Interface.Storable;
import com.simplicity.ExceptionHandling.*;

public class Ruangan {
    private String namaRuangan;
    private int jumlahBarangTerpasang = 0;
    private Peta<PlacedFurniture> petaBarang = new Peta<PlacedFurniture>(6, 6);

    public Ruangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
    }

    public Peta<PlacedFurniture> getPeta() {
        return petaBarang;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    @Override
    public String toString() {
        return namaRuangan;
    }

    public int getJumlahBarangTerpasang() {
        return jumlahBarangTerpasang;
    }

    public Furniture getBarangByID(int ID) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                PlacedFurniture temp = petaBarang.getElement(i, j);
                if (temp != null) {
                    if (temp.getID() == ID) {
                        return temp.getBarang();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ruangan)) {
            return false;
        }
        Ruangan rumah = (Ruangan) o;
        return Objects.equals(namaRuangan, rumah.getNamaRuangan());
    }

    public Furniture getBarangByKoordinat(Point koordinat) {
        PlacedFurniture temp = petaBarang.getElement(koordinat.getX(), koordinat.getY());
        if (temp != null) {
            return temp.getBarang();
        }
        return null;
    }

    private class PlacedFurniture {
        private Furniture barang;
        private int ID;
        private ArrayList<Point> koordinat;

        private PlacedFurniture(Furniture barang, Boolean isHorizontal, int x, int y) {
            this.barang = barang;
            this.ID = jumlahBarangTerpasang + 1;

            this.koordinat = new ArrayList<Point>();
            if (isHorizontal) {
                for (int i = x; i < x + barang.getPanjang(); i++) {
                    for (int j = y; j < y + barang.getLebar(); j++) {
                        koordinat.add(new Point(i, j));
                    }
                }
            } else {
                for (int i = x; i < x + barang.getLebar(); i++) {
                    for (int j = y; j < y + barang.getPanjang(); j++) {
                        koordinat.add(new Point(i, j));
                    }
                }
            }
            jumlahBarangTerpasang++;
        }

        public Furniture getBarang() {
            return barang;
        }

        public int getID() {
            return ID;
        }

        public ArrayList<Point> getKoordinat() {
            return koordinat;
        }

        public void setBarang(Furniture barang) {
            this.barang = barang;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setKoordinat(ArrayList<Point> koordinat) {
            this.koordinat = koordinat;
        }

        @Override
        public String toString() {
            return barang.getNama();
        }
    }

    public int countBarang(Furniture barang) {
        ArrayList<Integer> listID = new ArrayList<Integer>();
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                PlacedFurniture element = petaBarang.getElement(i, j);
                if (element != null) {
                    if (element.getBarang().equals(barang) && !listID.contains(element.getID())) {
                        count++;
                        listID.add(element.getID());
                    }
                }
            }
        }
        return count;
    }

    public Boolean isSpaceAvailable(Furniture barang, Boolean isHorizontal, int x, int y) {
        Boolean isAvailable = true;
        if (x < 6 && y < 6) {
            if (isHorizontal) {
                for (int i = x; i < x + barang.getPanjang(); i++) {
                    for (int j = y; j < y + barang.getLebar(); j++) {
                        if (petaBarang.getElement(i, j) != null)
                            isAvailable = false;
                    }
                }
            } else {
                for (int i = x; i < x + barang.getLebar(); i++) {
                    for (int j = y; j < y + barang.getPanjang(); j++) {
                        if (petaBarang.getElement(i, j) != null)
                            isAvailable = false;
                    }
                }
            }
        } else {
            isAvailable = false;
        }

        return isAvailable;
    }

    public void memasangBarang(Furniture barang, Boolean isHorizontal, int x, int y) {
        barang.setIsHorizontal(isHorizontal);
        PlacedFurniture placedObject = new PlacedFurniture(barang, isHorizontal, x, y);

        if (isSpaceAvailable(barang, isHorizontal, x, y)) {
            if (isHorizontal) {
                for (int i = x; i < x + barang.getPanjang(); i++) {
                    for (int j = y; j < y + barang.getLebar(); j++) {
                        petaBarang.setElement(i, j, placedObject);
                    }
                }
            } else {
                for (int i = x; i < x + barang.getLebar(); i++) {
                    for (int j = y; j < y + barang.getPanjang(); j++) {
                        petaBarang.setElement(i, j, placedObject);
                    }
                }
            }
        }
    }

    public void mengambilBarang(Furniture takenObject, int ID) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                PlacedFurniture placedObject = petaBarang.getElement(i, j);
                if (placedObject != null) {
                    String namaPlacedObject = placedObject.getBarang().getNama();
                    String namaTakenObject = takenObject.getNama();
                    if (namaPlacedObject.equals(namaTakenObject) && placedObject.getID() == ID) {
                        petaBarang.setElement(i, j, null);
                    }
                }
            }
        }
    }

    public Furniture findBarang(String name) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (petaBarang.getElement(i, j) != null) {
                    Furniture placedObject = petaBarang.getElement(i, j).getBarang();
                    if (placedObject.getNama().equals(name)) {
                        return placedObject;
                    }
                }
            }
        }
        return null;
    }

    public Boolean isBarangAtKoordinat(Furniture barang, int x, int y) {
        Boolean isAtKoordinat = false;
        PlacedFurniture placedObject = petaBarang.getElement(x, y);
        if (placedObject != null) {
            if (placedObject.getBarang().getNama().equals(barang.getNama())) {
                isAtKoordinat = true;
            }
        }
        return isAtKoordinat;
    }

    public int getIDBarangAtKoordinat(int x, int y) {
        PlacedFurniture placedObject = petaBarang.getElement(x, y);
        if (placedObject != null) {
            return placedObject.getID();
        } else {
            return 0;
        }
    }

    public void goToObject() {
        Sim currentSim = Game.getInstance().getCurrentSim();
        SimPosition currentPosition = currentSim.getCurrentPosition();
        Peta<PlacedFurniture> petaRuangan = currentPosition.getRuang().getPeta();
        PlacedFurniture selectedBarang = petaRuangan.selectElement("Pilih objek yang ingin kamu tuju");

        if (selectedBarang != null) {
            Point newPoint = petaRuangan.getClosestElementCoordinate(currentPosition.getLokasi(), selectedBarang);
            currentSim.getCurrentPosition().setLokasi(newPoint);
        }
    }

    public void putObject() {
        Sim currentSim = Game.getInstance().getCurrentSim();
        currentSim.getInventory().displayInventory(Storable.class);
    }

    public void takeObject() {
        Sim currentSim = Game.getInstance().getCurrentSim();
        HashMap<String, Point> listBarang = new HashMap<String, Point>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                PlacedFurniture placedObject = petaBarang.getElement(i, j);
                if (placedObject != null) {
                    Furniture barang = placedObject.getBarang();
                    if (barang != null) {
                        listBarang.put(barang.getNama(), new Point(i, j));
                    }
                }
            }
        }

        String[] objectOptions = {};
        ArrayList<String> listObjects = new ArrayList<String>(Arrays.asList(objectOptions));

        for (String x : listBarang.keySet()) {
            listObjects.add(x);
        }

        objectOptions = listObjects.toArray(objectOptions);
        if (objectOptions.length == 0) {
            JOptionPane.showMessageDialog(null,
                    "Tidak ada barang di ruangan ini!\nCoba beli dan pasang barang dulu ya!",
                    "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JList<String> list = new JList<>(objectOptions);
            JOptionPane.showMessageDialog(null, new JScrollPane(list), "Take Object", JOptionPane.PLAIN_MESSAGE);
            String selectedOption = list.getSelectedValue();
            if (selectedOption != null) {
                Ruangan currentRoom = currentSim.getCurrentPosition().getRuang();
                Furniture takenObject = currentRoom.findBarang(selectedOption);
                int idBarang;
                if (countBarang(takenObject) > 1) {
                    JTextField inputX = new JTextField();
                    JTextField inputY = new JTextField();
                    Object[] messageInput = {
                            "X:", inputX,
                            "Y:", inputY
                    };
                    
                    int koordinatX = 0;
                    int koordinatY = 0;
                    Boolean inputValid = false;
                    while (!inputValid) {
                        int option = JOptionPane.showConfirmDialog(null, messageInput, "Input Posisi Barang yang Diambil",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                koordinatX = Integer.parseInt(inputX.getText());
                                koordinatY = Integer.parseInt(inputY.getText());
                                if ((koordinatX < 0 || koordinatX >= 6) || (koordinatY < 0 || koordinatY >= 6)) {
                                    throw new IllegalLocationException("Pastikan x sama y kamu di antara 0-5, ya!");
                                } else {
                                    inputValid = true;
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null,
                                        "Pastikan x sama y kamu berupa angka, ya!",
                                        "Notification", JOptionPane.INFORMATION_MESSAGE);
                            } catch (IllegalLocationException e) {
                                JOptionPane.showMessageDialog(null,
                                        e.getMessage(),
                                        "Notification", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            return;
                        }
                    }

                    if (!isBarangAtKoordinat(takenObject, koordinatX, koordinatY)) {
                        JOptionPane.showMessageDialog(null,
                                "Maaf, koordinat tidak sesuai dengan posisi barang!",
                                "Notification", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    idBarang = getIDBarangAtKoordinat(koordinatX, koordinatY);
                } else {
                    idBarang = getIDBarangAtKoordinat(listBarang.get(selectedOption).getX(),
                            listBarang.get(selectedOption).getY());
                }

                currentRoom.mengambilBarang(takenObject, idBarang);
                currentSim.getInventory().addBarang(takenObject, 1);
            }
        }
    }

    public void moveObject() {
        Sim currentSim = Game.getInstance().getCurrentSim();
        HashMap<String, Point> listBarang = new HashMap<String, Point>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                PlacedFurniture placedObject = petaBarang.getElement(i, j);
                if (placedObject != null) {
                    Furniture barang = placedObject.getBarang();
                    if (barang != null) {
                        listBarang.put(barang.getNama(), new Point(i, j));
                    }
                }
            }
        }

        String[] objectOptions = {};
        ArrayList<String> listObjects = new ArrayList<String>(Arrays.asList(objectOptions));

        for (String x : listBarang.keySet()) {
            listObjects.add(x);
        }

        objectOptions = listObjects.toArray(objectOptions);
        if (objectOptions.length == 0) {
            JOptionPane.showMessageDialog(null,
                    "Tidak ada barang di ruangan ini!\nCoba beli dan pasang barang dulu ya!",
                    "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JList<String> list = new JList<>(objectOptions);
            JOptionPane.showMessageDialog(null, new JScrollPane(list), "Move Object", JOptionPane.PLAIN_MESSAGE);
            String selectedOption = list.getSelectedValue();
            if (selectedOption != null) {
                // Memilih barang yang akan dipindahkan
                Ruangan currentRoom = currentSim.getCurrentPosition().getRuang();
                Furniture takenObject = currentRoom.findBarang(selectedOption);
                int idBarang;
                if (countBarang(takenObject) > 1) {
                    JTextField inputX = new JTextField();
                    JTextField inputY = new JTextField();
                    Object[] messageInput = {
                            "X:", inputX,
                            "Y:", inputY
                    };
                    
                    int koordinatX = 0;
                    int koordinatY = 0;
                    Boolean inputValid = false;
                    while (!inputValid) {
                        int option = JOptionPane.showConfirmDialog(null, messageInput, "Input Posisi Barang yang Diambil",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                koordinatX = Integer.parseInt(inputX.getText());
                                koordinatY = Integer.parseInt(inputY.getText());
                                if ((koordinatX < 0 || koordinatX >= 6) || (koordinatY < 0 || koordinatY >= 6)) {
                                    throw new IllegalLocationException("Pastikan x sama y kamu di antara 0-5, ya!");
                                } else {
                                    inputValid = true;
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null,
                                        "Pastikan x sama y kamu berupa angka, ya!",
                                        "Notification", JOptionPane.INFORMATION_MESSAGE);
                            } catch (IllegalLocationException e) {
                                JOptionPane.showMessageDialog(null,
                                        e.getMessage(),
                                        "Notification", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            return;
                        }
                    }

                    if (!isBarangAtKoordinat(takenObject, koordinatX, koordinatY)) {
                        JOptionPane.showMessageDialog(null,
                                "Maaf, koordinat tidak sesuai dengan posisi barang!",
                                "Notification", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    idBarang = getIDBarangAtKoordinat(koordinatX, koordinatY);
                } else {
                    idBarang = getIDBarangAtKoordinat(listBarang.get(selectedOption).getX(), listBarang.get(selectedOption).getY());
                }
                currentRoom.mengambilBarang(takenObject, idBarang);

                // Memilih koordinat tujuan
                Rumah currentHouse = Game.getInstance().getCurrentSim().getCurrentPosition().getRumah();
                if (currentSim.getNamaLengkap().equals(currentHouse.getNamaPemilik())) {
                    // Cari posisi
                    // Pasang
                    Boolean isHorizontal = true; // default value 
                    if (takenObject.getPanjang() != takenObject.getLebar()) {
                        String[] orientationOptions = { "Vertikal", "Horizontal" };
        
                        int inputOrientation = JOptionPane.showOptionDialog(
                                null, "Pilih Orientasi", "Pasang Barang",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                null, orientationOptions, orientationOptions[0]);
        
                        isHorizontal = (inputOrientation == 1) ? true : false;
                    }
    
                    JTextField inputX = new JTextField();
                    JTextField inputY = new JTextField();
                    Object[] messageInput = {
                            "X:", inputX,
                            "Y:", inputY
                    };
    
                    Boolean inputValid = false;
                    while (!inputValid) {
                        int option = JOptionPane.showConfirmDialog(null, messageInput, "Input Posisi Pemasangan Barang",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                int koordinatX = Integer.parseInt(inputX.getText());
                                int koordinatY = Integer.parseInt(inputY.getText());
                                if ((koordinatX < 0 || koordinatX >= 6) || (koordinatY < 0 || koordinatY >= 6)) {
                                    throw new IllegalLocationException("Pastikan x sama y kamu di antara 0-5, ya!");
                                } else {
                                    if (isHorizontal) {
                                        if (koordinatX + takenObject.getPanjang() > 6 || koordinatY + takenObject.getLebar() > 6) {
                                            throw new IllegalLocationException("Waduh, gak muat!");
                                        }
                                    } else {
                                        if (koordinatX + takenObject.getLebar() > 6 || koordinatY + takenObject.getPanjang() > 6) {
                                            throw new IllegalLocationException("Waduh, gak muat!");
                                        }
                                    }
                                }
    
                                Ruangan currentRuang = currentSim.getCurrentPosition().getRuang();
                                if (currentRuang.isSpaceAvailable(takenObject, isHorizontal, koordinatX, koordinatY)) {
                                    currentRuang.memasangBarang(takenObject, isHorizontal, koordinatX, koordinatY);
                                    inputValid = true;
                                    Game.getInstance().repaint();
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Maaf, Barang tidak dapat dipasang karena lahan sudah digunakan.",
                                            "Notification",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Nilai koordinat harus berbentuk bilangan bulat, lho!",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            } catch (IllegalLocationException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Notification",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    String[] options = { "Back" };
                    JOptionPane.showOptionDialog(null, "Anda harus ke rumah anda dulu!", "Furniture Info",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                }
            }
        }
    }
}