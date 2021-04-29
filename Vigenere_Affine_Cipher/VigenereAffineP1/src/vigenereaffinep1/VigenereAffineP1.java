package vigenereaffinep1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Practice 1. Vigenere and Affine Cipher
 *
 * @author Medina Villalpando Josué de Jesús
 */
public class VigenereAffineP1 {

    private JFrame frame;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JLabel l1;
    JLabel keyC;
    JLabel keyD;
    JLabel titleCipher;
    JLabel alfaLC;
    JLabel betaLC;
    JLabel alfaLD;
    JLabel betaLD;
    JTextArea tf1;
    JTextArea tf2;
    JTextArea tf3;
    JTextField keyCTextF;
    JTextField keyDTextF;

    JTextField alfaFC;
    JTextField betaFC;
    JTextField alfaFD;
    JTextField betaFD;
    JTextField freeAlphabetF;

    JCheckBox keyCBox;
    JComboBox alphabetCombo;
    JComboBox cipherCombo;
    String fileEncrypt = "";
    String fileDecrypt = "";
    int flag = 2;

    private void initialize() {
        b2 = new JButton("Encrypt");
        b3 = new JButton("Decrypt");
        b4 = new JButton("Choose message file");
        b5 = new JButton("Choose encrypted file");
        l1 = new JLabel("Choose a cipher method (Vigenere/Affine)");
        keyC = new JLabel("Key to encrypt");
        keyD = new JLabel("Key to decrypt");
        alfaLC = new JLabel("Alfa");
        betaLC = new JLabel("Beta");
        alfaLD = new JLabel("Alfa");
        betaLD = new JLabel("Beta");
        titleCipher = new JLabel("");
        tf1 = new JTextArea();
        tf2 = new JTextArea();
        tf3 = new JTextArea();
        keyCTextF = new JTextField();
        keyDTextF = new JTextField();
        freeAlphabetF = new JTextField();
        alfaFC = new JTextField();
        betaFC = new JTextField();
        alfaFD = new JTextField();
        betaFD = new JTextField();
        cipherCombo = new JComboBox();
        alphabetCombo = new JComboBox();

        frame = new JFrame();
        frame.setBounds(100, 100, 900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Vigenere and Affine ciphers");

        alphabetCombo.setBounds(650, 60, 200, 20);
        alphabetCombo.addItem("Select an alphabet");
        alphabetCombo.addItem("Spanish");
        alphabetCombo.addItem("English");
        alphabetCombo.addItem("Otro");
        alphabetCombo.setVisible(false);
        alphabetCombo.addItemListener(new ItemChangeListener());
        frame.add(alphabetCombo);

        cipherCombo.setBounds(300, 60, 200, 20);
        cipherCombo.addItem("Select an option");
        cipherCombo.addItem("Vigenere");
        cipherCombo.addItem("Affine");
        cipherCombo.addItemListener(new ItemChangeListener());
        frame.add(cipherCombo);

        b2.setBounds(300, 300, 200, 30);
        b2.setVisible(false);
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (tf1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill the field");
                } else {
                    if (cipherCombo.getSelectedItem().toString().equals("Vigenere")) {
                        String key = keyCTextF.getText();
                        if (flag == 1) {
                            key = generateKeyVigenere();
                        }
                        Vigenere v = new Vigenere(fillAlphabetEnglish(), key);
                        String cipher = v.encrypt(tf1.getText().substring(0, tf1.getText().length() - 1), fileEncrypt);
                        tf3.setText(cipher);
                        JOptionPane.showMessageDialog(null, "Encrypted successfully");
                    } else if (cipherCombo.getSelectedItem().toString().equals("Affine")) {
                        flag = 2;
                        keyCBox.setEnabled(false);
                        int alfa = Integer.parseInt(alfaFC.getText());
                        int beta = Integer.parseInt(betaFC.getText());
                        char[] alphabet = null;
                        if (freeAlphabetF.getText().equals("")) {
                            if (alphabetCombo.getSelectedItem().toString().equals("Spanish")) {
                                alphabet = fillAlphabetSpanish();
                            } else if (alphabetCombo.getSelectedItem().toString().equals("English")) {
                                alphabet = fillAlphabetEnglish();
                            }
                        } else {
                            alphabet = fillAlphabetFree(freeAlphabetF.getText());
                        }
                        Affine a = new Affine(alfa, beta, alphabet);
                        int res = a.obtener_mcd(alfa, alphabet.length);
                        if (res != 1) {
                            System.out.println("No tiene inversa");
                            JOptionPane.showMessageDialog(null, "Alpha invalid");
                        } else {
                            System.out.println("Tiene inversa");
                            String cipher = a.encryptMessage(tf1.getText().substring(0, tf1.getText().length() - 1).toCharArray(), fileEncrypt);
                            System.out.println(cipher);
                            tf3.setText(cipher);
                            JOptionPane.showMessageDialog(null, "Encrypted successfully");
                        }
                    }
                }
            }
        });
        frame.add(b2);

        b3.setBounds(300, 520, 200, 30);
        b3.setVisible(false);
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (tf2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill the field");
                } else {
                    if (cipherCombo.getSelectedItem().toString().equals("Vigenere")) {
                        Vigenere v = new Vigenere(fillAlphabetEnglish(), keyDTextF.getText());
                        String msg = v.decrypt(tf2.getText().substring(0, tf2.getText().length() - 1), fileDecrypt);
                        System.out.println("Descifrado: " + msg);
                        tf3.setText(msg);
                        JOptionPane.showMessageDialog(null, "Decrypted successfully");
                    } else if (cipherCombo.getSelectedItem().toString().equals("Affine")) {
                        int alfa = Integer.parseInt(alfaFD.getText());
                        int beta = Integer.parseInt(betaFD.getText());
                        char[] alphabet = null;
                        if (alphabetCombo.getSelectedItem().toString().equals("Spanish")) {
                            alphabet = fillAlphabetSpanish();
                        } else if (alphabetCombo.getSelectedItem().toString().equals("English")) {
                            alphabet = fillAlphabetEnglish();
                        }
                        System.out.println(alphabet);
                        Affine a = new Affine(alfa, beta, alphabet);
                        int res = a.obtener_mcd(alfa, alphabet.length);
                        if (res != 1) {
//                            System.out.println("No tiene inversa");
                            JOptionPane.showMessageDialog(null, "Alpha invalid. Has no inverse");
                        } else {
//                            System.out.println("Tiene inversa");
                            String msg = a.decryptCipher(tf2.getText().substring(0, tf2.getText().length() - 1), fileDecrypt);
//                            System.out.println("Descifrado: " + msg);
                            tf3.setText(msg);
                            JOptionPane.showMessageDialog(null, "Decrypted successfully");
                        }
                    }
                }
            }
        });
        frame.add(b3);

        b4.setBounds(390, 120, 200, 20);
        b4.setVisible(false);
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser file = new JFileChooser();
                String aux = "";
                String texto = "";
                file.showOpenDialog(frame);

                fileEncrypt = file.getSelectedFile().getName().substring(0, file.getSelectedFile().getName().length() - 4);
                File abre = file.getSelectedFile();
                if (abre != null) {
                    try {
                        FileReader archivos = null;
                        try {
                            archivos = new FileReader(abre);
                        } catch (FileNotFoundException ex) {

                        }
                        BufferedReader lee = new BufferedReader(archivos);
                        while ((aux = lee.readLine()) != null) {
                            texto += aux + "\n";
                        }
                        try {
                            lee.close();
                            tf1.setText(texto);
                        } catch (IOException ex) {

                        }
                    } catch (IOException ex) {

                    }
                }
            }
        });
        frame.add(b4);

        b5.setBounds(390, 340, 200, 20);
        b5.setVisible(false);
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser file = new JFileChooser();
                String texto = "";
                file.showOpenDialog(frame);

                fileDecrypt = file.getSelectedFile().getName().substring(0, file.getSelectedFile().getName().length() - 4);
                try {
                    File myObj = file.getSelectedFile();
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        texto = texto + data;
                    }
                    tf2.setText(texto);
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        });
        frame.add(b5);

        l1.setBounds(280, 0, 250, 100);
        frame.add(l1);

        keyC.setBounds(150, 60, 500, 100);
        keyC.setVisible(false);
        frame.add(keyC);

        keyD.setBounds(150, 280, 500, 100);
        keyD.setVisible(false);
        frame.add(keyD);

        freeAlphabetF.setBounds(600, 90, 250, 20);
        freeAlphabetF.setVisible(false);
        frame.add(freeAlphabetF);

        alfaLC.setBounds(150, 60, 500, 100);
        alfaLC.setVisible(false);
        frame.add(alfaLC);

        betaLC.setBounds(250, 60, 500, 100);
        betaLC.setVisible(false);
        frame.add(betaLC);

        alfaLD.setBounds(150, 280, 500, 100);
        alfaLD.setVisible(false);
        frame.add(alfaLD);

        betaLD.setBounds(250, 280, 500, 100);
        betaLD.setVisible(false);
        frame.add(betaLD);

        titleCipher.setBounds(350, 40, 500, 100);
        titleCipher.setVisible(false);
        frame.add(titleCipher);

        tf1.setLineWrap(true);
        tf1.setEditable(false);
        tf1.setVisible(false);
        JScrollPane scroll = new JScrollPane(tf1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(150, 150, 500, 150);
        frame.add(scroll);

        tf2.setLineWrap(true);
        tf2.setEditable(false);
        tf2.setVisible(false);
        scroll = new JScrollPane(tf2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(150, 370, 500, 150);
        frame.add(scroll);

        tf3.setLineWrap(true);
        tf3.setEditable(false);
        tf3.setVisible(false);
        scroll = new JScrollPane(tf3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(150, 570, 500, 150);
        frame.add(scroll);

        keyCTextF.setBounds(150, 121, 200, 23);
        keyCTextF.setVisible(false);
        frame.add(keyCTextF);

        keyDTextF.setBounds(150, 340, 200, 23);
        keyDTextF.setVisible(false);
        frame.add(keyDTextF);

        alfaFC.setBounds(150, 120, 50, 23);
        alfaFC.setVisible(false);
        frame.add(alfaFC);

        betaFC.setBounds(250, 120, 50, 23);
        betaFC.setVisible(false);
        frame.add(betaFC);

        alfaFD.setBounds(150, 340, 50, 23);
        alfaFD.setVisible(false);
        frame.add(alfaFD);

        betaFD.setBounds(250, 340, 50, 23);
        betaFD.setVisible(false);
        frame.add(betaFD);

        keyCBox = new JCheckBox("Random");
        keyCBox.setVisible(false);
        keyCBox.setBounds(140, 75, 100, 30);
        keyCBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (cipherCombo.getSelectedItem().toString().equals("Affine")) {
                    if (e.getStateChange() == 1) {
                        flag = 1;
                        alfaFC.setText("");
                        betaFC.setText("");
                        alfaFC.setEditable(false);
                        betaFC.setEditable(false);
                    } else {
                        flag = 2;
                        alfaFC.setEditable(true);
                        betaFC.setEditable(true);
                    }
                } else {
                    if (e.getStateChange() == 1) {
                        flag = 1;
                        keyCTextF.setText("");
                        keyCTextF.setEditable(false);
                    } else {
                        flag = 2;
                        keyCTextF.setEditable(true);
                        keyCTextF.setEditable(true);
                    }
                }
            }
        });
        frame.add(keyCBox);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public VigenereAffineP1() {
        initialize();
    }

    public static void main(String[] args) throws Exception {
        new VigenereAffineP1();
    }

    class ItemChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) event.getItem();
                if (item.equals("Vigenere")) {
                    titleCipher.setText("Vigenere cipher");
                    titleCipher.setVisible(true);
                    keyC.setVisible(true);
                    keyD.setVisible(true);
                    keyCTextF.setVisible(true);
                    keyDTextF.setVisible(true);
                    alfaLC.setVisible(false);
                    betaLC.setVisible(false);
                    alfaLD.setVisible(false);
                    betaLD.setVisible(false);
                    alfaFC.setVisible(false);
                    betaFC.setVisible(false);
                    alfaFD.setVisible(false);
                    betaFD.setVisible(false);
                    alphabetCombo.setVisible(false);
                    freeAlphabetF.setVisible(false);
                } else if (item.equals("Affine")) {
                    titleCipher.setText("Affine cipher");
                    titleCipher.setVisible(true);
                    keyC.setVisible(false);
                    keyD.setVisible(false);
                    keyCTextF.setVisible(false);
                    keyDTextF.setVisible(false);
                    alfaLC.setVisible(true);
                    betaLC.setVisible(true);
                    alfaLD.setVisible(true);
                    betaLD.setVisible(true);
                    alfaFC.setVisible(true);
                    betaFC.setVisible(true);
                    alfaFD.setVisible(true);
                    betaFD.setVisible(true);
                    alphabetCombo.setVisible(true);
                    freeAlphabetF.setVisible(true);
                }
                keyCBox.setVisible(true);
                b2.setVisible(true);
                b3.setVisible(true);
                b4.setVisible(true);
                b5.setVisible(true);

                tf1.setVisible(true);
                tf2.setVisible(true);
                tf3.setVisible(true);
            }
        }
    }

    public static char[] fillAlphabetEnglish() {
        char[] alph = new char[26 + 1];
        String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        for (int i = 0; i <= alphabetString.length() - 1; i++) {
            alph[i] = alphabetString.charAt(i);
        }
        return alph;
    }

    public static char[] fillAlphabetSpanish() {
        char[] alph = new char[27 + 1];
        String alphabetString = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ ";
        for (int i = 0; i <= alphabetString.length() - 1; i++) {
            alph[i] = alphabetString.charAt(i);
        }
        return alph;
    }

    public static char[] fillAlphabetFree(String alphabetUser) {
        char[] alph = new char[alphabetUser.length()];
        for (int i = 0; i <= alphabetUser.length() - 1; i++) {
            alph[i] = alphabetUser.charAt(i);
        }
        return alph;
    }

    public static String generateKeyVigenere() {
        Random rand = new Random();
        int len = rand.nextInt(40);
        len += 1;
        final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();

    }
}
