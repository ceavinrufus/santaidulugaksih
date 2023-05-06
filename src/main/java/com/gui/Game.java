package com.gui;

import com.simplicity.*;
import com.simplicity.Point;
import com.simplicity.AbstractClass.*;
import com.simplicity.Interface.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.simplicity.ExceptionHandling.*;
import com.simplicity.Interface.Storable;
import com.simplicity.Thread.*;

public class Game extends JFrame {
    private static Game instance = new Game();
    private static MainMenu mainMenu = MainMenu.getInstance();
    public Waktu totalWaktu = Waktu.getInstance();

    // private boolean displayRumah = false;
    private HashMap<String, Sim> sims = new HashMap<String, Sim>();
    // private World world = new World();
    private Sim currentSim;
    HomePanel homePanel;
    JTabbedPane tabbedPane;

    private Game() {
        setTitle("Sim-Plicity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Get the selected tab index
                int selectedIndex = tabbedPane.getSelectedIndex();

                // Get the component at the selected tab index
                Component selectedComponent = tabbedPane.getComponentAt(selectedIndex);

                // Request focus on the selected component
                selectedComponent.requestFocusInWindow();
            }
        });
        tabbedPane.setFocusable(false);
        add(tabbedPane);

        setMinimumSize(new Dimension(900, 1000)); // Set minimum size JFrame
        setPreferredSize(new Dimension(900, 1000)); // Set preferred size JFrame
        pack(); // Bikin JFrame fit ke preferred size

        try {
            BufferedImage icon = ImageIO.read(new File("src/main/java/resources/images/icon.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // close the parent JFrame
            }
        });
    }

    public static Game getInstance() {
        return instance;
    }

    public Sim getCurrentSim() {
        return currentSim;
    }

    public void setSims(HashMap<String, Sim> sims) {
        this.sims = sims;
    }

    public void setCurrentSim(Sim currentSim) {
        this.currentSim = currentSim;
    }

    public Waktu getTotalWaktu() {
        return totalWaktu;
    }

    public void setTotalWaktu(Waktu totalWaktu) {
        this.totalWaktu = totalWaktu;
    }

    public void mulaiAksi(Integer sisaWaktu) {
        Waktu.getInstance().addWaktu(sisaWaktu);
        ThreadManager.startAllThreads();
        while (sisaWaktu != 0) {
            try {
                sisaWaktu -= 1;
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ThreadManager.stopAllThreads();
    }

    // Menu game
    public void displayGameMenu(Component parentComponent) {
        String[] anyHouseMenu = { "View Sim Info", "View Current Location", "View Inventory", "Move Room",
                "List Object", "Go To Object", "Add Sim", "Change Sim" };
        String[] simHouseMenu = { "Upgrade House", "Edit Room" }; // Di rumah current Sim aja
        String[] options;

        // Conditional buat nentuin menu apa aja yang bakal ditampilin
        if (currentSim.getCurrentPosition().getRumah().getNamaPemilik()
                .equals(currentSim.getNamaLengkap())) {
            options = new String[anyHouseMenu.length + simHouseMenu.length + 2];
            System.arraycopy(anyHouseMenu, 0, options, 0, anyHouseMenu.length);
            System.arraycopy(simHouseMenu, 0, options, anyHouseMenu.length, simHouseMenu.length);
        } else {
            options = new String[anyHouseMenu.length + 2];
            System.arraycopy(anyHouseMenu, 0, options, 0, anyHouseMenu.length);
        }
        options[options.length - 2] = "Save";
        options[options.length - 1] = "Exit";

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
                        message = "Rumah " + currentSim.getCurrentPosition().getRumah().getNamaPemilik()
                                + "\n" +
                                "Ruangan: " + currentSim.getCurrentPosition().getRuang().getNamaRuangan();
                        JOptionPane.showMessageDialog(null, message, "Current Location",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "View Inventory":
                        currentSim.getInventory().displayInventory(Storable.class);
                        break;
                    case "Add Sim":
                        try {
                            makeNewSim();
                            repaint();
                        } catch (SimNotCreatedException exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Change Sim":
                        changeSim();
                        break;
                    case "Upgrade House":
                        upgradeRumah();
                        break;
                    case "Move Room":
                        // Belom dicek karena belom bisa upgrade house
                        Peta<Ruangan> petaRumah = currentSim.getCurrentPosition().getRumah().getPeta();
                        if (petaRumah.getColumn() == 1 && petaRumah.getRow() == 1) {
                            JOptionPane.showMessageDialog(null,
                                    "Sejauh ini kamu baru punya satu ruangan, nih!\nCoba upgrade rumah kamu dulu untuk menambah ruangan.",
                                    "Sayang sekali :(", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                        Ruangan selectedRuangan = petaRumah.selectElement(currentSim.getCurrentPosition().getRuang(),
                                "Pilih ruangan yang dituju");
                        if (selectedRuangan != null) {
                            currentSim.getCurrentPosition().setRuang(selectedRuangan);
                            repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Kamu belum memilih ruangan!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Edit Room":
                        editRoom();
                        break;
                    case "List Object":
                        displayListObject();
                        break;
                    case "Go To Object":
                        currentSim.getCurrentPosition().getRuang().goToObject();
                        repaint();
                        break;
                    case "Save":
                        try {
                            String saveName = JOptionPane.showInputDialog(null, "Masukkan nama save file",
                                    "Save Game", JOptionPane.QUESTION_MESSAGE);
                            World.getInstance().saveWorld(saveName);
                            saveSims(saveName);
                            saveCurrentSim(saveName);
                            totalWaktu.saveWaktu(saveName);
                        } catch (IOException exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Exit":
                        int confirm = JOptionPane.showConfirmDialog(null, "Yakin keluar dari game?",
                                "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // sims.clear();
                            // currentSim = null;
                            // setVisible(false);
                            // mainMenu.setVisible(true);
                            // JOptionPane.getRootFrame().dispose();
                            System.exit(0);
                        }
                        break;
                }
            });
            panel.add(button);
        }

        JOptionPane.showOptionDialog(parentComponent, panel, "Game Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

    }

    private void editRoom() {
        String[] options = { "Buy Object", "Move Object", "Take Object", "Put Object", "Back" };
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        Ruangan currentRoom = currentSim.getCurrentPosition().getRuang();

        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                switch (option) {
                    case "Buy Object":
                        displayEtalase("Furniture");
                        break;
                    case "Move Object":
                        currentRoom.moveObject();
                        break;
                    case "Take Object":
                        currentRoom.takeObject();
                        repaint();
                        break;
                    case "Put Object":
                        currentRoom.putObject();
                        repaint();
                        break;
                    case "Back":
                        JOptionPane.getRootFrame().dispose();
                        break;
                }
            });
            panel.add(button);
        }

        JOptionPane.showOptionDialog(null, panel, "Edit Room Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
    }

    public void action() {
        String[] listAksi = { "Kerja", "Olahraga", "Berkunjung", "Beli Barang", "Back" };

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        for (String aksi : listAksi) {
            JButton button = new JButton(aksi);
            button.addActionListener(e -> {
                if (aksi.equals("Kerja")) {
                    currentSim.kerja();
                    trackSimsStats();
                } else if (aksi.equals("Olahraga")) {
                    currentSim.olahraga();
                    trackSimsStats();
                } else if (aksi.equals("Berkunjung")) {
                    if (sims.size() == 1) {
                        JOptionPane.showMessageDialog(null,
                                "Kamu hanya sendiri di dunia ini",
                                "Notification", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        Peta<Rumah> petaRumah = World.getInstance().getPeta();
                        Rumah selectedRumah = petaRumah.selectElement(currentSim.getCurrentPosition().getRumah(),
                                "Mau berkunjung ke rumah siapa?");
                        if (selectedRumah != null) {
                            Point sourcePoint = petaRumah
                                    .getElementCoordinate(currentSim.getCurrentPosition().getRumah());
                            Point destPoint = petaRumah.getElementCoordinate(selectedRumah);
                            int distance = Math.round(sourcePoint.distance(destPoint));
                            JOptionPane.getRootFrame().dispose();
                            mulaiAksi(distance);

                            currentSim.setIsOnKunjungan(true);
                            currentSim.setRecentActionTime(distance);
                            currentSim.setCurrentPosition(
                                    new SimPosition(selectedRumah, selectedRumah.findRuangan("Main Room")));
                            trackSimsStats();
                            JOptionPane.showMessageDialog(null, "Sudah sampai!", "Action finished",
                                    JOptionPane.INFORMATION_MESSAGE);
                            repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Kamu belum memilih rumah!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (aksi.equals("Beli Barang")) {
                    beliBarang();
                } else if (aksi.equals("Back")) {
                    JOptionPane.getRootFrame().dispose();
                }
            });
            panel.add(button);
        }

        JOptionPane.showOptionDialog(null, panel, "List of Actions", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
    }

    private void beliBarang() {
        String[] buyOptions = { "Bahan Makanan", "Furniture" };
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        int[] count = { 0 };

        for (String option : buyOptions) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                count[0]++;
                displayEtalase(option);
            });
            panel.add(button);
        }

        String[] back = { "Back" };
        JOptionPane.showOptionDialog(null, panel, "Pilihan Pembelian",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, back, null);
    }

    public void displayEtalase(String kategori) {
        Sim currentSim = Game.getInstance().getCurrentSim();
        ArrayList<Purchasable> listPembelian = new ArrayList<Purchasable>();
        switch (kategori) {
            case "Bahan Makanan":
                listPembelian.add(new NonCookableFood("Nasi"));
                listPembelian.add(new NonCookableFood("Kentang"));
                listPembelian.add(new NonCookableFood("Ayam"));
                listPembelian.add(new NonCookableFood("Sapi"));
                listPembelian.add(new NonCookableFood("Wortel"));
                listPembelian.add(new NonCookableFood("Bayam"));
                listPembelian.add(new NonCookableFood("Kacang"));
                listPembelian.add(new NonCookableFood("Susu"));
                break;
            case "Furniture":
                listPembelian.add(new Kasur("Kasur Single"));
                listPembelian.add(new Kasur("Kasur Queen Size"));
                listPembelian.add(new Kasur("Kasur King Size"));
                listPembelian.add(new Toilet());
                listPembelian.add(new Kompor("Kompor Gas"));
                listPembelian.add(new MejaKursi());
                listPembelian.add(new Jam());
                listPembelian.add(new Cermin());
                listPembelian.add(new ArcadeMachine());
                listPembelian.add(new Kipas());
                listPembelian.add(new Tanaman());
                listPembelian.add(new Shower());
                listPembelian.add(new TV());
                listPembelian.add(new Komputer());
                break;
        }

        String[][] tableData = new String[listPembelian.size()][2];
        String[] columnNames = { "Name", "Price" };

        for (int i = 0; i < listPembelian.size(); i++) {
            tableData[i][0] = listPembelian.get(i).getNama(); // Name
            tableData[i][1] = String.valueOf(listPembelian.get(i).getHarga()); // Price
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
        int buyChoice = JOptionPane.showOptionDialog(
                null,
                new JScrollPane(table),
                "Pilihan Pembelian",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (buyChoice == 0) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                double uangSim = currentSim.getUang();
                double hargaBarangTerpilih = Double.parseDouble(tableData[selectedRow][1]);

                String inputJumlah = JOptionPane.showInputDialog("Masukkan jumlah yang diinginkan: ");
                int jumlahBarangTerpilih = Integer.parseInt(inputJumlah);

                if (uangSim < hargaBarangTerpilih * jumlahBarangTerpilih) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, uang kamu tidak cukup!",
                            "Notification", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    currentSim.setUang(uangSim - hargaBarangTerpilih * jumlahBarangTerpilih);
                    Purchasable barangTerpilih = listPembelian.get(selectedRow);
                    RunnableBeliBarang beliBarang = new RunnableBeliBarang(currentSim, barangTerpilih,
                            jumlahBarangTerpilih);
                    String message = String.format("Silakan menunggu pengiriman %d %s selama %d detik",
                            jumlahBarangTerpilih, barangTerpilih.getNama(), beliBarang.getSisaWaktu());
                    JOptionPane.showMessageDialog(null, message, "Pembelian Berhasil",
                            JOptionPane.INFORMATION_MESSAGE);
                    ThreadManager.addThread(beliBarang);
                    Thread t = new Thread(beliBarang);
                    t.start();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Kamu belum memilih barang!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            return;
        }
    }

    private void displayListObject() {
        Rumah currentHouse = currentSim.getCurrentPosition().getRumah();
        Ruangan selectedRuangan = currentHouse.getPeta().selectElement("Posisi sekarang: "
                + currentSim.getCurrentPosition().getRuang().getNamaRuangan());

        if (selectedRuangan != null) {
            selectedRuangan.getPeta().displayList();
        }
    }

    public void upgradeRumah() {
        int cost = 1500;
        if (currentSim.getUang() >= cost) {
            try {
                // tambah ruangan
                String namaRuangan = "";
                while (namaRuangan.length() < 1 || namaRuangan.length() > 10) {
                    try {
                        namaRuangan = JOptionPane.showInputDialog(null, "Masukkan nama ruangan yang ingin dibuat:",
                                "Add Room",
                                JOptionPane.QUESTION_MESSAGE);
                        if (namaRuangan == null) {
                            // Kalo pencet tombol close
                            return;
                        } else {
                            // Validasi nama
                            if (namaRuangan.length() < 1 || namaRuangan.length() > 10) {
                                throw new IllegalInputException("Nama harus terdiri dari 1-10 karakter.");
                            } else {
                                // Lanjut
                                // Ini gatau mau dibikin apakah nama ruangan gaboleh sama
                                break;
                            }
                        }
                    } catch (IllegalInputException error) {
                        JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                Ruangan ruanganBaru = new Ruangan(namaRuangan);
                Peta<Ruangan> petaRumah = currentSim.getCurrentPosition().getRumah().getPeta();
                Ruangan ruanganPatokan = petaRumah.selectElement("Pilih ruangan patokan");
                if (ruanganPatokan == null) {
                    JOptionPane.showMessageDialog(null, "Kamu belum memilih ruangan!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] opsiArah = { "Atas", "Bawah", "Kiri", "Kanan" };
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 1));
                for (String arah : opsiArah) {
                    JButton button = new JButton(arah);
                    button.addActionListener(e -> {
                        currentSim.setUang(currentSim.getUang() - cost);
                        RunnableBangunRumah bangunRumah = new RunnableBangunRumah(currentSim, ruanganBaru,
                                ruanganPatokan,
                                arah);
                        ThreadManager.addThread(bangunRumah);
                        Thread t = new Thread(bangunRumah);
                        t.start();
                        JOptionPane.getRootFrame().dispose();
                    });
                    panel.add(button);
                }
                JOptionPane.showOptionDialog(null, panel, "Mau ditambah di mananya?", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sayang sekali, uangmu belum cukup untuk melakukan upgrade rumah!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Sim makeNewSim() throws SimNotCreatedException {
        String nama = "";
        Sim sim = null;

        while (nama.length() < 1 || nama.length() > 16) {
            try {
                nama = JOptionPane.showInputDialog(null, "Masukkan nama:", "Add Sim", JOptionPane.QUESTION_MESSAGE);
                if (nama == null) {
                    return null;
                } else {
                    // Validasi nama
                    if (nama.length() < 1 || nama.length() > 16) {
                        throw new IllegalInputException("Nama harus terdiri dari 1-16 karakter.");
                    } else {
                        sim = new Sim(nama);
                        // Cek udah ada Sim dengan nama yang sama belom
                        if (sims.containsKey(sim.getNamaLengkap())) {
                            throw new IllegalInputException(
                                    String.format("Sim dengan nama '%s' sudah ada",
                                            sim.getNamaLengkap()));
                        }
                    }
                }
            } catch (IllegalInputException error) {
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
        ruangan.memasangBarang(new Shower(), true, 1, 5);
        Rumah rumah = new Rumah(ruangan);

        try {
            JTextField inputX = new JTextField();
            JTextField inputY = new JTextField();
            Object[] messageInput = {
                    "X:", inputX,
                    "Y:", inputY
            };

            Boolean inputValid = false;
            do {
                // Testing for Random Location
                String[] initialOptions = { "OK", "Cancel", "Random" };
                int option = JOptionPane.showOptionDialog(null, messageInput, "Kamu mau bangun rumah di mana?",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, initialOptions, initialOptions[0]);
                if (option == 0) {
                    int koordinatX = Integer.parseInt(inputX.getText());
                    int koordinatY = Integer.parseInt(inputY.getText());
                    if ((koordinatX < 0 || koordinatX >= 64) || (koordinatY < 0 || koordinatY >= 64)) {
                        throw new IllegalLocationException("Pastikan x sama y kamu di antara 0-63, ya!");
                    } else if (World.getInstance().getPeta().getElement(koordinatX, koordinatY) != null) {
                        JOptionPane.showMessageDialog(null,
                                "Maaf, Rumah tidak bisa dibangun karena lahan sudah digunakan.",
                                "Notification",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        World.getInstance().tambahRumah(rumah, koordinatX, koordinatY);
                        rumah.setNamaPemilik(sim);
                        sim.setCurrentPosition(new SimPosition(rumah, ruangan));
                        inputValid = true;
                    }
                } else if (option == 1) {
                    return null;
                } else if (option == 2) {
                    Random random = new Random();
                    int koordinatX = random.nextInt(64);
                    int koordinatY = random.nextInt(64);
                    if (World.getInstance().getPeta().getElement(koordinatX, koordinatY) != null) {
                        JOptionPane.showMessageDialog(null,
                                "Maaf, Rumah tidak bisa dibangun karena lahan sudah digunakan.",
                                "Notification",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        World.getInstance().tambahRumah(rumah, koordinatX, koordinatY);
                        rumah.setNamaPemilik(sim);
                        sim.setCurrentPosition(new SimPosition(rumah, ruangan));
                        inputValid = true;
                    }
                }
            } while (!inputValid);
        } catch (IllegalLocationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new SimNotCreatedException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Nilai koordinat harus berbentuk bilangan bulat, lho!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new SimNotCreatedException();
        }

        if (sims.putIfAbsent(sim.getNamaLengkap(), sim) != null) {
            JOptionPane.showMessageDialog(null,
                    String.format("Sim dengan nama '%s' sudah ada",
                            sim.getNamaLengkap()),
                    "Error", JOptionPane.ERROR_MESSAGE);
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
                homePanel.setCurrentSim(currentSim);
                JOptionPane.showMessageDialog(null, "Berhasil mengganti Sim!", "Notification",
                        JOptionPane.INFORMATION_MESSAGE);
                repaint();
            }
        }
    }

    public void trackSimsStats() {
        sims.forEach((key, value) -> {
            value.trackTidur(value.getRecentActionTime());
            value.trackBuangAir(value.getRecentActionTime());
            value.trackKunjungan(value.getRecentActionTime());
            if (value.getStats().getKekenyangan() == 0 || value.getStats().getKesehatan() == 0
                    || value.getStats().getMood() == 0) {
                sims.remove(key);
                JOptionPane.showMessageDialog(null,
                        String.format("Sim %s mati, Anda tidak lagi bisa memainkan sim ini!", key), "Sim mati",
                        JOptionPane.INFORMATION_MESSAGE);
                if (currentSim.equals(value)) {
                    if (sims.size() == 0) {
                        JOptionPane.showMessageDialog(null, String.format("Terima kasih sudah bermain Sim-Plicity!"),
                                "Game Over!",
                                JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    } else {
                        changeSim();
                    }
                }
            }

        });
    }

    private void saveSims(String filename) throws IOException {
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
                    "_sims.json");
            gson.toJson(sims, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCurrentSim(String filename) throws IOException {
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
                    "_currentSim.json");
            gson.toJson(currentSim, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Sim> loadSims(String filename) throws IOException {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Furniture.class, new FurnitureAdapter());
        gsonBuilder.registerTypeAdapter(Storable.class, new StorableAdapter());
        gsonBuilder.registerTypeAdapter(Food.class, new FoodAdapter());
        gsonBuilder.registerTypeAdapter(Purchasable.class, new PurchasableAdapter());
        gson = gsonBuilder.create();
        HashMap<String, Sim> sims = null;
        FileReader fileReader = new FileReader("src/main/java/saves/" + filename +
                "_sims.json");
        sims = gson.fromJson(fileReader, new TypeToken<HashMap<String, Sim>>() {
        }.getType());
        fileReader.close();
        return sims;
    }

    public Sim loadCurrentSim(String filename) throws IOException {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Furniture.class, new FurnitureAdapter());
        gsonBuilder.registerTypeAdapter(Storable.class, new StorableAdapter());
        gsonBuilder.registerTypeAdapter(Food.class, new FoodAdapter());
        gsonBuilder.registerTypeAdapter(Purchasable.class, new PurchasableAdapter());
        gson = gsonBuilder.create();
        Sim sim = null;
        FileReader fileReader = new FileReader("src/main/java/saves/" + filename +
                "_currentSim.json");
        sim = gson.fromJson(fileReader, Sim.class);
        fileReader.close();
        return sim;
    }

    public void runGame() {
        setVisible(true);
        setFocusable(true);
        mainMenu.setVisible(false);
        homePanel = new HomePanel(currentSim);
        // WorldPanel worldPanel = new WorldPanel(world);
        homePanel.setCurrentSim(currentSim);
        tabbedPane.addTab("House Map", homePanel);
        tabbedPane.addTab("World Map", WorldPanel.getInstance());
        // add(homePanel);
        // displayRumah = true;
    }
}
