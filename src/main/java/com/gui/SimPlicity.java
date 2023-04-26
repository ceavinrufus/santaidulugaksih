package com.gui;

import com.simplicity.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.simplicity.ExceptionHandling.*;

public class SimPlicity extends JFrame {
    private static SimPlicity instance = new SimPlicity();

    private KeyAdapter keyListener = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                // Start
                removeKeyListener(keyListener);
                instance.runGame();
            } else if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                // Help
                Object[] options = { "Aku mengerti!" };
                JOptionPane.showOptionDialog(null,
                        "Gatau mainin aja udah pokoknya",
                        "Help",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

            }
        }
    };
    private KeyAdapter keyListener2 = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                removeKeyListener(keyListener2);
                instance.displayGameMenu();
            }
        }
    };

    private BufferedImage icon;
    private JLabel backgroundLabel;
    private static boolean inGame = false;
    private static boolean displayRumah = false;

    private static HashMap<String, Sim> sims = new HashMap<String, Sim>();
    private static Sim currentSim;

    private SimPlicity() {
        setTitle("Sim-Plicity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ngambil image buat dijadiin background
        ImageIcon backgroundImage = new ImageIcon("src/main/java/resources/images/background.png");
        backgroundLabel = new JLabel(backgroundImage);

        setMinimumSize(new Dimension(800, 1000)); // Set minimum size JFrame
        setPreferredSize(new Dimension(800, 1000)); // Set preferred size JFrame
        pack(); // Bikin JFrame fit ke preferred size

        // Add JLabel ke content pane JFrame
        getContentPane().add(backgroundLabel);

        try {
            icon = ImageIO.read(new File("src/main/java/resources/images/icon.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.out.println("Error loading background image");
        }

        addKeyListener(keyListener);

        setFocusable(true);
        setVisible(true);
    }

    public static SimPlicity getInstance() {
        return instance;
    }

    public static Sim getCurrentSim() {
        return currentSim;
    }

    // Buat debug aja
    private void printListeners(KeyListener keyListener) {
        System.out.println("listener: " + keyListener);
        System.out.println("listeners: ");
        for (KeyListener listener : instance.getKeyListeners()) {
            System.out.println(listener);
        }
    }

    // Menu game
    public void displayGameMenu() {
        ArrayList<String> optionsList = new ArrayList<>(Arrays.asList("View Sim Info", "View Current Location",
                "View Inventory", "House Menu", "Add Sim", "Change Sim", "Exit"));

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
                        // handle View Current Location option
                        break;
                    case "View Inventory":
                        currentSim.getInventory().displayInventory(Storable.class);
                        break;
                    case "House Menu":
                        JOptionPane.getRootFrame().dispose();
                        displayHouseMenu();
                        break;
                    case "Add Sim":
                        // handle Add Sim option
                        try {
                            makeNewSim();
                        } catch (SimNotCreatedException exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Change Sim":
                        // handle Change Sim option
                        break;
                    case "Exit":
                        int confirm = JOptionPane.showConfirmDialog(null, "Yakin keluar dari game?",
                                "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (confirm == JOptionPane.YES_OPTION) {
                            inGame = false;
                            addKeyListener(keyListener);
                            removeKeyListener(keyListener2);
                            JOptionPane.getRootFrame().dispose();
                            repaint();
                        } else if (confirm == JOptionPane.NO_OPTION) {

                        }
                        break;
                }
            });
            panel.add(button);
        }

        int dialogResult = JOptionPane.showOptionDialog(null, panel, "Game Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

        if (inGame && dialogResult == JOptionPane.CLOSED_OPTION) {
            addKeyListener(keyListener2);
        }
    }

    // House Menu
    private void displayHouseMenu() {
        boolean inHouse = true;
        boolean inRoom = true;

        ArrayList<String> houseList = new ArrayList<>(Arrays.asList("Upgrade House", "Move Room"));
        ArrayList<String> roomList = new ArrayList<>(
                Arrays.asList("Edit Room", "List Object", "Go To Object", "Action"));

        if (inRoom) {
            houseList.addAll(roomList);
        }
        houseList.add("Back");

        String[] options = houseList.toArray(new String[0]);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                switch (option) {
                    case "Upgrade House":
                        break;
                    case "Move Room":
                        break;
                    case "Edit Room":
                        break;
                    case "List Object":
                        displayListObject();
                        break;
                    case "Go To Object":
                        break;
                    case "Action":
                        break;
                    case "Back":
                        JOptionPane.getRootFrame().dispose();
                        displayGameMenu();
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

    private void displayListObject() {
        Rumah currentHouse = currentSim.getCurrentPosition().getRumah();
        // Untuk saat ini masih dengan asumsi tidak ada barang yang persis sama.
        TreeSet<String> setOfRoomName = new TreeSet<String>();

        Peta<Ruangan> petaRuangan = currentHouse.getPeta();
        for (int i = 0; i < petaRuangan.getColumn(); i++) {
            for (int j = 0; j < petaRuangan.getRow(); j++) {
                Ruangan ruang = petaRuangan.getElement(i, j);
                if (ruang != null) {
                    setOfRoomName.add(ruang.getNamaRuangan());
                }
            }
        }
        ArrayList<String> listRoomName = new ArrayList<>(setOfRoomName);

        String[] roomOptions = listRoomName.toArray(new String[0]);
        JList<String> list = new JList<>(roomOptions);
        String header = "Pilihan Ruangan";
        JOptionPane.showMessageDialog(null, new JScrollPane(list), header, JOptionPane.PLAIN_MESSAGE);
        String selectedOption = list.getSelectedValue();
        Ruangan selectedRuang = currentHouse.findRuangan(selectedOption);

        if (selectedOption != null) {
            HashSet<Furniture> listBarang = new HashSet<Furniture>();
            Peta<Furniture> petaBarang = selectedRuang.getPeta();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    Furniture barang = (Furniture) petaBarang.getElement(i, j);
                    if (barang != null) {
                        listBarang.add(barang);
                    }
                }
            }

            StringBuilder message = new StringBuilder("");
            int idx = 1;
            for (Furniture barang : listBarang) {
                message.append(String.format("%d. %s\n", idx, barang.getNama()));
                idx++;
            }
            JOptionPane.showMessageDialog(null, message, "List Object", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (inGame) {
            if (displayRumah) {
                currentSim.getCurrentPosition().getRumah().paint(g, getWidth(), getHeight());
            } else {
                BufferedImage pattern = null;
                try {
                    pattern = ImageIO.read(new File("src/main/java/resources/images/sea.jpg"));
                } catch (IOException e) {
                    System.out.println("Error loading background image");
                }
                if (pattern != null) {
                    int patternWidth = pattern.getWidth(null);
                    int patternHeight = pattern.getHeight(null);
                    for (int x = 0; x < getWidth(); x += patternWidth) {
                        for (int y = 0; y < getHeight(); y += patternHeight) {
                            g.drawImage(pattern, x, y, null);
                        }
                    }
                }
                World.getInstance().paint(g, getWidth(), getHeight());
            }
        }
    }

    private Sim makeNewSim() throws SimNotCreatedException {
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
        ruangan.memasangBarang(new Jam(), true, 0, 5);
        ruangan.memasangBarang(new MejaKursi(), true, 3, 3);

        try {
            Rumah rumah = new Rumah(ruangan);
            world.tambahRumah(rumah, 0, 0);
            rumah.setPemilik(sim);
            sim.setCurrentPosition(new SimPosition(rumah, ruangan));
        } catch (IllegalLocationException e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new SimNotCreatedException();
        }

        return sim;
    }

    public void runGame() {
        Sim sim = null;
        try {
            sim = makeNewSim();
        } catch (SimNotCreatedException e) {
            sim = null;
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (sim != null) {
            currentSim = sim;
            inGame = true;
            displayRumah = true;
            repaint();
            displayGameMenu();
        } else {
            addKeyListener(keyListener);
        }
    }

    public static void main(String[] args) {
        SimPlicity game = SimPlicity.getInstance();
    }
}
