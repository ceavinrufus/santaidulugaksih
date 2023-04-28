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
                        "Hai! Selamat datang di Simplycity!\n" + 
                        "Klik Enter untuk memulai game\n" +
                        "Klik Spasi untuk melihat help\n" +
                        "Klik w, a, s, d atau tombol panah untuk menggerakan Sim dalam permainan\n"+
                        "Selamat bermain!",
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
            SimPosition currentSimPosition = instance.getCurrentSim().getCurrentPosition();
            int keyChar = e.getKeyChar();
            if (keyChar == KeyEvent.VK_SPACE) {
                removeKeyListener(keyListener2);
                instance.displayGameMenu();
            } else if (keyChar == 'w'){
                if (currentSimPosition.getLokasi().getY() < 5){
                    currentSimPosition.getLokasi().setY(currentSimPosition.getLokasi().getY()+1);
                    repaint();
                }
            } else if (keyChar == 's'){
                if (currentSimPosition.getLokasi().getY() > 0){
                    currentSimPosition.getLokasi().setY(currentSimPosition.getLokasi().getY()-1);
                    repaint();
                }
            } else if (keyChar == 'a'){
                if (currentSimPosition.getLokasi().getX() > 0){
                    currentSimPosition.getLokasi().setX(currentSimPosition.getLokasi().getX()-1);
                    repaint();
                }
            } else if (keyChar == 'd'){
                if (currentSimPosition.getLokasi().getX() < 5){
                    currentSimPosition.getLokasi().setX(currentSimPosition.getLokasi().getX()+1);
                    repaint();
                }
            }
        }
    };

    private BufferedImage icon;
    private JLabel backgroundLabel;

    private boolean inGame = false;
    private boolean displayRumah = false;
    private HashMap<String, Sim> sims = new HashMap<String, Sim>();
    private Sim currentSim;

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

    public Sim getCurrentSim() {
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
                        SimPosition currentSimPosition = instance.getCurrentSim().getCurrentPosition();
                        message = "Rumah " + currentSim.getCurrentPosition().getRumah().getPemilik().getNamaLengkap()
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
                    case "Exit":
                        int confirm = JOptionPane.showConfirmDialog(null, "Yakin keluar dari game?",
                                "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (confirm == JOptionPane.YES_OPTION) {
                            inGame = false;
                            sims.clear();
                            currentSim = null;
                            addKeyListener(keyListener);
                            removeKeyListener(keyListener2);
                            JOptionPane.getRootFrame().dispose();
                            repaint();
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
        String[] anyHouseMenu = { "Move Room", "List Object", "Go To Object", "Action" }; // Di manapun bisa diakses
        String[] simHouseMenu = { "Upgrade House", "Edit Room" }; // Di rumah current Sim aja
        String[] options;

        // Conditional buat nentuin menu apa aja yang bakal ditampilin
        if (currentSim.getCurrentPosition().getRumah().getPemilik().getNamaLengkap()
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
                        break;
                    case "Move Room":
                        // Belom dicek karena belom bisa upgrade house
                        String selectedOption = pilihanRuangan();
                        if (selectedOption != null) {
                            currentSim.getCurrentPosition()
                                    .setRuang(currentSim.getCurrentPosition().getRumah().findRuangan(selectedOption));
                        }
                        break;
                    case "Edit Room":
                        break;
                    case "List Object":
                        displayListObject();
                        break;
                    case "Go To Object":
                        pilihanObject();
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

    private String pilihanRuangan() {
        // Aku pisah karena mau dipake untuk move room juga -Tina
        Rumah currentHouse = currentSim.getCurrentPosition().getRumah();
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
        return selectedOption;
    }

    private void pilihanObject(){
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
            JOptionPane.showMessageDialog(null, new JScrollPane(list), "Go To Object", JOptionPane.PLAIN_MESSAGE);
            String selectedOption = list.getSelectedValue();
            if (selectedOption != null) {
                currentSim.getCurrentPosition().getLokasi().move(listBarang.get(selectedOption).getX(), listBarang.get(selectedOption).getY());
                repaint();
            }
        }
    }

    private void action(){
        String[] listAksi= {"Kerja", "Olahraga", "Berkunjung", "Beli Barang"};
        ArrayList<String> listAksiBarang = new ArrayList<String>(Arrays.asList(listAksi));
        SimPosition simCurrentPosition = currentSim.getCurrentPosition();
        Furniture barang = simCurrentPosition.getRuang().getPeta().getElement(simCurrentPosition.getLokasi().getX(), simCurrentPosition.getLokasi().getY());
        listAksiBarang.add(barang.getNamaAksi());
        listAksiBarang.add("Back");
        listAksi = listAksiBarang.toArray(listAksi);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        for (String aksi: listAksi){
            JButton button = new JButton(aksi);
            button.addActionListener(e ->{
                if (aksi.equals("Kerja")){
                    currentSim.kerja(3); //Set 3 menit dulu, nanti diatur lg inputan waktunya.
                } else if (aksi.equals("Olahraga")){
                    currentSim.olahraga(3);
                } else if (aksi.equals("Berkunjung")){
                    currentSim.berkunjung();
                } else if (aksi.equals("Beli Barang")){
                    currentSim.beliBarang();
                } else if (aksi.equals("Back")){
                    JOptionPane.getRootFrame().dispose();
                }else {
                    if (barang != null){
                        barang.aksi(currentSim);
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
        // Untuk saat ini masih dengan asumsi tidak ada barang yang persis sama.
        String selectedOption = pilihanRuangan();
        // Aku pisah karena mau dipake untuk move room juga -Tina
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
            Random rand = new Random();
            int randX;
            int randY;
            do {
                randX = rand.nextInt(64);
                randY = rand.nextInt(64);
            } while (world.getPeta().getElement(randX, randY) != null);
            world.tambahRumah(rumah, randX, randY);
            rumah.setPemilik(sim);
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
                JOptionPane.showMessageDialog(null, "Berhasil mengganti Sim!", "Notification",
                        JOptionPane.INFORMATION_MESSAGE);
                repaint();
            }
        }
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
}
