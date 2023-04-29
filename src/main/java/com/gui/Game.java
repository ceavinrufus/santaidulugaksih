package com.gui;

import com.simplicity.*;
import com.simplicity.Point;

// import com.google.gson.*;
// import org.json.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import com.simplicity.ExceptionHandling.*;

public class Game extends JFrame {
    private static Game instance = new Game();
    private static MainMenu mainMenu = MainMenu.getInstance();

    // private boolean displayRumah = false;
    private HashMap<String, Sim> sims = new HashMap<String, Sim>();
    private Sim currentSim;

    JTabbedPane tabbedPane;

    private Game() {
        setTitle("Sim-Plicity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        add(tabbedPane);

        setMinimumSize(new Dimension(800, 1000)); // Set minimum size JFrame
        setPreferredSize(new Dimension(800, 1000)); // Set preferred size JFrame
        pack(); // Bikin JFrame fit ke preferred size

        try {
            BufferedImage icon = ImageIO.read(new File("src/main/java/resources/images/icon.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }
    }

    public static Game getInstance() {
        return instance;
    }

    public Sim getCurrentSim() {
        return currentSim;
    }

    public void setCurrentSim(Sim currentSim) {
        this.currentSim = currentSim;
    }

    // Menu game
    public void displayGameMenu() {
        ArrayList<String> optionsList = new ArrayList<>(Arrays.asList("View Sim Info", "View Current Location",
                "View Inventory", "House Menu", "Add Sim", "Change Sim", "Save", "Exit"));

        String[] options = optionsList.toArray(new String[0]);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                switch (option) {
                    case "View Sim Info":
                        String message = "Nama: " + currentSim.getNamaLengkap() + "\n" +
                                "Pekerjaan: " + currentSim.getPekerjaan() + "\n" +
                                "Kesehatan: " + currentSim.getStats().getKesehatan() + "\n" +
                                "Kekenyangan: " + currentSim.getStats().getKekenyangan() + "\n" +
                                "Mood: " + currentSim.getStats().getMood() + "\n" +
                                "Uang: " + currentSim.getUang();
                        JOptionPane.showMessageDialog(null, message, "Sim Info", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "View Current Location":
                        SimPosition currentSimPosition = instance.getCurrentSim().getCurrentPosition();
                        message = "Rumah " + currentSim.getCurrentPosition().getRumah().getNamaPemilik()
                                + "\n" +
                                "Ruangan: " + currentSim.getCurrentPosition().getRuang().getNamaRuangan();
                        JOptionPane.showMessageDialog(null, message, "Current Location",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "View Inventory":
                        currentSim.getInventory().displayInventory(Storable.class);
                        break;
                    case "House Menu":
                        JOptionPane.getRootFrame().dispose();
                        displayHouseMenu();
                        break;
                    case "Add Sim":
                        try {
                            makeNewSim();
                        } catch (SimNotCreatedException exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Change Sim":
                        changeSim();
                        break;
                    case "Save":
                        // try {
                        // save("save");
                        // } catch (IOException exception) {
                        // JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                        // JOptionPane.ERROR_MESSAGE);
                        // }
                        break;
                    case "Exit":
                        int confirm = JOptionPane.showConfirmDialog(null, "Yakin keluar dari game?",
                                "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (confirm == JOptionPane.YES_OPTION) {
                            sims.clear();
                            currentSim = null;
                            setVisible(false);
                            mainMenu.setVisible(true);
                            JOptionPane.getRootFrame().dispose();
                        }
                        break;
                }
            });
            panel.add(button);
        }

        int dialogResult = JOptionPane.showOptionDialog(null, panel, "Game Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

    }

    // House Menu
    private void displayHouseMenu() {
        String[] anyHouseMenu = { "Move Room", "List Object", "Go To Object", "Action" }; // Di manapun bisa diakses
        String[] simHouseMenu = { "Upgrade House", "Edit Room" }; // Di rumah current Sim aja
        String[] options;

        // Conditional buat nentuin menu apa aja yang bakal ditampilin
        if (currentSim.getCurrentPosition().getRumah().getNamaPemilik()
                .equals(currentSim.getNamaLengkap())) {
            options = new String[anyHouseMenu.length + simHouseMenu.length + 1];
            System.arraycopy(anyHouseMenu, 0, options, 0, anyHouseMenu.length);
            System.arraycopy(simHouseMenu, 0, options, anyHouseMenu.length, simHouseMenu.length);
        } else {
            options = new String[anyHouseMenu.length + 1];
            System.arraycopy(anyHouseMenu, 0, options, 0, anyHouseMenu.length);
        }
        options[options.length - 1] = "Back";

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Add button listener
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                switch (option) {
                    case "Upgrade House":
                        currentSim.upgradeRumah();
                        break;
                    case "Move Room":
                        // Belom dicek karena belom bisa upgrade house
                        Ruangan selectedRuangan = currentSim.getCurrentPosition().getRumah().getPeta()
                                .selectElement();
                        if (selectedRuangan != null) {
                            currentSim.getCurrentPosition().setRuang(selectedRuangan);
                        }
                        break;
                    case "Edit Room":
                        editRoom();
                        break;
                    case "List Object":
                        displayListObject();
                        break;
                    case "Go To Object":
                        goToObject();
                        break;
                    case "Action":
                        action();
                        break;
                    case "Back":
                        JOptionPane.getRootFrame().dispose();
                        break;
                }
            });
            panel.add(button);
        }

        int dialogResult = JOptionPane.showOptionDialog(null, panel, "House Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

        if (dialogResult == JOptionPane.CLOSED_OPTION) {
            JOptionPane.getRootFrame().dispose();
            displayGameMenu();
        }
    }

    private void goToObject() {
        SimPosition currentPosition = currentSim.getCurrentPosition();
        Peta<Furniture> petaRuangan = currentPosition.getRuang().getPeta();
        Furniture selectedBarang = petaRuangan.selectElement();

        if (selectedBarang != null) {
            Point newPoint = petaRuangan.getClosestElementCoordinate(currentPosition.getLokasi(), selectedBarang);
            currentSim.getCurrentPosition().setLokasi(newPoint);
            repaint();
        }
    }

    private void editRoom() {
        String[] options = { "Buy Object", "Take Object", "Put Object", "Back" };
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                switch (option) {
                    case "Buy Object":
                        buyFurniture();
                        break;
                    case "Take Object":
                        takeObject();
                        break;
                    case "Put Object":
                        putObject();
                        break;
                    case "Back":
                        JOptionPane.getRootFrame().dispose();
                        break;
                }
            });
            panel.add(button);
        }

        int dialogResult = JOptionPane.showOptionDialog(null, panel, "Edit Room Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

        if (dialogResult == JOptionPane.CLOSED_OPTION) {
            JOptionPane.getRootFrame().dispose();
            displayHouseMenu();
        }
    }

    private void buyFurniture() {
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

    private void takeObject() {
        HashMap<String, com.simplicity.Point> listBarang = new HashMap<String, com.simplicity.Point>();
        Peta<Furniture> petaBarang = currentSim.getCurrentPosition().getRuang().getPeta();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Furniture barang = (Furniture) petaBarang.getElement(i, j);
                if (barang != null) {
                    listBarang.putIfAbsent(barang.getNama(), new com.simplicity.Point(i, j));
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
                currentRoom.mengambilBarang(takenObject);
                repaint();
                currentSim.getInventory().addBarang(takenObject, 1);
            }
        }
    }

    private void putObject() {
        currentSim.getInventory().displayInventory(Storable.class);
    }

    private void action() {
        String[] listAksi = { "Kerja", "Olahraga", "Berkunjung", "Beli Barang" };
        ArrayList<String> listAksiBarang = new ArrayList<String>(Arrays.asList(listAksi));
        SimPosition simCurrentPosition = currentSim.getCurrentPosition();
        Furniture barang = simCurrentPosition.getRuang().getPeta().getElement(simCurrentPosition.getLokasi().getX(),
                simCurrentPosition.getLokasi().getY());

        if (barang != null) {
            listAksiBarang.add(barang.getNamaAksi());
        }
        listAksiBarang.add("Back");

        listAksi = listAksiBarang.toArray(listAksi);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        for (String aksi : listAksi) {
            JButton button = new JButton(aksi);
            button.addActionListener(e -> {
                if (aksi.equals("Kerja")) {
                    currentSim.kerja(3); // Set 3 menit dulu, nanti diatur lg inputan waktunya.
                } else if (aksi.equals("Olahraga")) {
                    currentSim.olahraga(3);
                } else if (aksi.equals("Berkunjung")) {
                    currentSim.berkunjung();
                    repaint();
                } else if (aksi.equals("Beli Barang")) {
                    // currentSim.beliBarang();
                } else if (aksi.equals("Back")) {
                    JOptionPane.getRootFrame().dispose();
                } else {
                    if (barang != null) {
                        currentSim.interact(barang);
                    }
                }
            });
            panel.add(button);
        }

        int dialogResult = JOptionPane.showOptionDialog(null, panel, "List of Actions", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

        if (dialogResult == JOptionPane.CLOSED_OPTION) {
            JOptionPane.getRootFrame().dispose();
            displayHouseMenu();
        }
    }

    private void displayListObject() {
        Rumah currentHouse = currentSim.getCurrentPosition().getRumah();
        Ruangan selectedRuangan = currentHouse.getPeta().selectElement();

        if (selectedRuangan != null) {
            selectedRuangan.getPeta().displayList();
        }
    }

    public Sim makeNewSim() throws SimNotCreatedException {
        World world = World.getInstance();

        String nama = "";
        Sim sim = null;

        while (nama.length() < 4 || nama.length() > 16) {
            try {
                nama = JOptionPane.showInputDialog(null, "Masukkan nama:");
                if (nama == null) {
                    // Kalo pencet tombol close
                    JOptionPane.getRootFrame().dispose();
                    return null;
                } else {
                    // Validasi nama
                    if (nama.length() < 4 || nama.length() > 16) {
                        throw new IllegalNameException("Nama harus terdiri dari 4-16 karakter.");
                    } else {
                        sim = new Sim(nama);
                        // Cek udah ada Sim dengan nama yang sama belom
                        if (sims.putIfAbsent(sim.getNamaLengkap(), sim) != null) {
                            throw new IllegalNameException(
                                    String.format("Sim dengan nama '%s' sudah ada",
                                            sim.getNamaLengkap()));
                        }
                    }
                }
            } catch (IllegalNameException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw new SimNotCreatedException();
            }
        }

        Ruangan ruangan = new Ruangan("Main Room");
        // Masukin barang barang basic ke ruangan
        ruangan.memasangBarang(new Kasur("Kasur Single"), true, 2, 0);
        ruangan.memasangBarang(new Kompor("Kompor Gas"), false, 0, 2);
        ruangan.memasangBarang(new Jam(), true, 2, 5);
        ruangan.memasangBarang(new MejaKursi(), true, 3, 3);
        ruangan.memasangBarang(new Toilet(), true, 0, 5);

        try {
            Rumah rumah = new Rumah(ruangan);
            Random rand = new Random();
            int randX;
            int randY;
            do {
                randX = rand.nextInt(64);
                randY = rand.nextInt(64);
            } while (world.getPeta().getElement(randX, randY) != null);
            world.tambahRumah(rumah, randX, randY);
            rumah.setNamaPemilik(sim);
            sim.setCurrentPosition(new SimPosition(rumah, ruangan));
        } catch (IllegalLocationException e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new SimNotCreatedException();
        }

        // Mengecek apakah Add Sim atau tidak
        if (sims.size() > 1) {
            JOptionPane.showMessageDialog(null,
                    "Sim berhasil ditambahkan!\nUntuk mengubah Sim, silakan akses opsi Change Sim.", "Notification",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        return sim;
    }

    private void changeSim() {
        String[] simOptions = {};
        ArrayList<String> listSims = new ArrayList<String>(Arrays.asList(simOptions));
        for (String x : sims.keySet()) {
            if (!(currentSim.getNamaLengkap().equals(x))) {
                listSims.add(x);
            }
        }
        simOptions = listSims.toArray(simOptions);
        if (simOptions.length == 0) {
            JOptionPane.showMessageDialog(null,
                    "Sejauh ini kamu baru punya satu Sim, nih!\nCoba buat Sim baru dulu dengan memilih menu 'Add Sim'",
                    "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JList<String> list = new JList<>(simOptions);
            JOptionPane.showMessageDialog(null, new JScrollPane(list), "Ganti Sim", JOptionPane.PLAIN_MESSAGE);
            String selectedOption = list.getSelectedValue();
            if (selectedOption != null) {
                currentSim = sims.get(selectedOption);
                HomePanel.getInstance().setCurrentSim(currentSim);
                JOptionPane.showMessageDialog(null, "Berhasil mengganti Sim!", "Notification",
                        JOptionPane.INFORMATION_MESSAGE);
                repaint();
            }
        }
    }

    // private void save(String filename) throws IOException {
    // Gson gson = new Gson();
    // try {
    // FileWriter fileWriter = new FileWriter("src/main/java/saves/" + filename +
    // ".json");
    // fileWriter.write("[");
    // gson.toJson(World.getInstance(), fileWriter);
    // fileWriter.write(",");
    // gson.toJson(sims, fileWriter);
    // fileWriter.write("]");
    // fileWriter.close();
    // JOptionPane.showMessageDialog(null, "Berhasil menyimpan data!",
    // "Notification",
    // JOptionPane.INFORMATION_MESSAGE);
    // } catch (IOException e) {
    // JOptionPane.showMessageDialog(null, "Gagal menyimpan data!", "Error",
    // JOptionPane.ERROR_MESSAGE);
    // }
    // }

    // private World loadWorld(String filename) throws IOException {
    // Gson gson = new Gson();
    // World world = null;
    // try {
    // FileReader fileReader = new FileReader("src/main/java/saves/" + filename +
    // ".json");
    // JsonReader jsonReader = new JsonReader(fileReader);
    // jsonReader.beginArray();
    // world = gson.fromJson(jsonReader, World.class);
    // jsonReader.endArray();
    // jsonReader.close();
    // fileReader.close();
    // } catch (IOException e) {
    // JOptionPane.showMessageDialog(null, "Gagal membaca data!", "Error",
    // JOptionPane.ERROR_MESSAGE);
    // }
    // return world;
    // }

    public void runGame() {
        setVisible(true);
        setFocusable(true);
        mainMenu.setVisible(false);
        HomePanel homePanel = HomePanel.getInstance();
        WorldPanel worldPanel = WorldPanel.getInstance();
        homePanel.setCurrentSim(currentSim);
        tabbedPane.addTab("House Map", homePanel);
        tabbedPane.addTab("World Map", worldPanel);
        // add(homePanel);
        homePanel.requestFocusInWindow();
        // displayRumah = true;
    }
}
