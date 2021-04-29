package p1rsa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Practice 1. RSA implementation Cryptography
 *
 * @author Medina Villalpando Josué de Jesús
 */
public class P1RSA {

    private JFrame frame;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JLabel l1;
    JTextArea tf1;
    JTextArea tf2;
    JTextArea tf3;
    JComboBox keys;
    JComboBox keys2;
    String fileEncrypt = "";
    String fileDecrypt = "";

    private void initialize() {
        b1 = new JButton("Generate keys");
        b2 = new JButton("Encrypt");
        b3 = new JButton("Decrypt");
        b4 = new JButton("Choose message file");
        b5 = new JButton("Choose encrypted file");
        l1 = new JLabel("RSA ENCRYPTION");
        tf1 = new JTextArea();
        tf2 = new JTextArea();
        tf3 = new JTextArea();
        keys = new JComboBox();
        keys2 = new JComboBox();

        frame = new JFrame();
        frame.setBounds(100, 100, 730, 489);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("RSA ENCRYPTION");

        b1.setBounds(300, 70, 200, 30);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    RSAKeysGenerator rsaK = new RSAKeysGenerator();
                    rsaK.genKeyPair(1024);
                    rsaK.createFilePrivateKey("dist\\alicia.pri");
                    rsaK.createFilePublicKey("dist\\alicia.pub");

                    RSAKeysGenerator rsaK2 = new RSAKeysGenerator();
                    rsaK2.genKeyPair(1024);
                    rsaK2.createFilePrivateKey("dist\\betito.pri");
                    rsaK2.createFilePublicKey("dist\\betito.pub");

                    RSAKeysGenerator rsaK3 = new RSAKeysGenerator();
                    rsaK3.genKeyPair(1024);
                    rsaK3.createFilePrivateKey("dist\\eva.pri");
                    rsaK3.createFilePublicKey("dist\\eva.pub");

                    JOptionPane.showMessageDialog(null, "Keys generated successfully");
                } catch (Exception ex) {
                    System.out.println("Error " + ex);
                }
            }
        });
        frame.add(b1);

        b2.setBounds(300, 300, 200, 30);
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (tf1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill the field");
                } else {
                    try {
                        RSAUtils rU = new RSAUtils();
                        rU.Encrypt(tf1.getText(), fileEncrypt, keys.getSelectedItem().toString());
                        JOptionPane.showMessageDialog(null, "Successfully encrypted message");
//                        tf2.setText(secure);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });
        frame.add(b2);

        b3.setBounds(300, 520, 200, 30);
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (tf2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill the field");
                } else {
                    try {
                        RSAUtils rU = new RSAUtils();
                        System.out.println("Llega");
                        String unsecure = rU.Decrypt(tf2.getText(), fileDecrypt, keys2.getSelectedItem().toString());
                        tf3.setText(unsecure);
                    } catch (Exception e) {
                        System.out.println(e);
                        JOptionPane.showMessageDialog(null, "Decryption error", "Error", 2);
                    }
                }
            }
        });
        frame.add(b3);

        b4.setBounds(390, 120, 200, 20);
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

        keys.setBounds(150, 120, 200, 20);

        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String extension = getFileExtension(listOfFiles[i]);
                if (extension.equals(".pub")) {
                    keys.addItem(listOfFiles[i].getName());
                    keys2.addItem(listOfFiles[i].getName());
                } else if (extension.equals(".pri")) {
                    keys2.addItem(listOfFiles[i].getName());
                }
            }
        }
        frame.add(keys);

        keys2.setBounds(150, 340, 200, 20);
        frame.add(keys2);

        l1.setBounds(350, 0, 100, 100);
        frame.add(l1);

        tf1.setLineWrap(true);
        tf1.setEditable(false);
        JScrollPane scroll = new JScrollPane(tf1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(150, 150, 500, 150);
        frame.add(scroll);

        tf2.setLineWrap(true);
        tf2.setEditable(false);
        scroll = new JScrollPane(tf2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(150, 360, 500, 150);
        frame.add(scroll);

        tf3.setLineWrap(true);
        tf3.setEditable(false);
        scroll = new JScrollPane(tf3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(150, 570, 500, 150);
        frame.add(scroll);

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public P1RSA() {
        initialize();
    }

    public static void main(String[] args) throws Exception {
        new P1RSA();
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

}
