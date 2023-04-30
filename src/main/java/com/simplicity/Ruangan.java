package com.simplicity;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.gui.Game;
import com.simplicity.Point;
import com.simplicity.AbstractClass.Furniture;

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
        } else {
            System.out.println("Barang tidak dapat dipasang karena lahan sudah digunakan.");
        }
    }

    public Boolean isSpaceAvailable(Furniture barang, Boolean isHorizontal, int x, int y) {
        Boolean isAvailable = true;
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

        return isAvailable;
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
        PlacedFurniture selectedBarang = petaRuangan.selectElement();

        if (selectedBarang != null) {
            Point newPoint = petaRuangan.getClosestElementCoordinate(currentPosition.getLokasi(), selectedBarang);
            currentSim.getCurrentPosition().setLokasi(newPoint);
        }
    }

    public void buyFurniture() {
        Sim currentSim = Game.getInstance().getCurrentSim();
        ArrayList<Furniture> listFurniture = new ArrayList<Furniture>();
        listFurniture.add(new Kasur("Kasur Single"));
        listFurniture.add(new Kasur("Kasur Queen Size"));
        listFurniture.add(new Kasur("Kasur King Size"));
        listFurniture.add(new Toilet());
        listFurniture.add(new Kompor("Kompor Gas"));
        listFurniture.add(new MejaKursi());
        listFurniture.add(new Jam());

        String[][] tableData = new String[listFurniture.size()][2];
        String[] columnNames = { "Furniture Name", "Price" };

        for (int i = 0; i < listFurniture.size(); i++) {
            tableData[i][0] = listFurniture.get(i).getNama(); // Furniture Name
            tableData[i][1] = String.valueOf(listFurniture.get(i).getHarga()); // Price
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);

        // Menampilkan option pane
        String[] options = { "Buy", "Back" };
        int choice = JOptionPane.showOptionDialog(
                null,
                new JScrollPane(table),
                "Edit Room Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            int selectedRow = table.getSelectedRow();
            double uangSim = currentSim.getUang();
            double hargaBarangTerpilih = Double.parseDouble(tableData[selectedRow][1]);

            String inputJumlah = JOptionPane.showInputDialog("Masukkan jumlah barang yang diinginkan: ");
            int jumlahBarangTerpilih = Integer.parseInt(inputJumlah);

            if (uangSim < hargaBarangTerpilih * jumlahBarangTerpilih) {
                JOptionPane.showMessageDialog(null,
                        "Maaf, uang kamu tidak cukup!",
                        "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                currentSim.setUang(uangSim - hargaBarangTerpilih * jumlahBarangTerpilih);
                Furniture barangTerpilih = listFurniture.get(selectedRow);
                currentSim.getInventory().addBarang(barangTerpilih, jumlahBarangTerpilih);
                String message = String.format("Selamat! Pembelian %d %s berhasil.", jumlahBarangTerpilih,
                        barangTerpilih.getNama());
                JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
        }
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
                    String inputX = JOptionPane.showInputDialog("Masukkan koordinat X: ");
                    int koordinatX = Integer.parseInt(inputX);
    
                    String inputY = JOptionPane.showInputDialog("Masukkan koordinat Y: ");
                    int koordinatY = Integer.parseInt(inputY);
                    
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
                currentSim.getInventory().addBarang(takenObject, 1);
            }
        }
    }

}