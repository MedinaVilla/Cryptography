package modosoperacioncifrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Modos de Operación Este programa permite cifrar y descifrar mensaje mediante
 * los modos de operación ECB,CBC,CFB,OFB utilizando el algoritmo DES
 *
 * @author Github: MedinaVilla
 */
public class ModosOperacionCifrado {

    private JFrame frame;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JLabel l1;
    JLabel keyC;
    JLabel keyD;
    JLabel vectorIC;
    JLabel vectorID;
    JLabel titleCipher;

    JTextArea tf1;
    JTextArea tf2;
    JTextArea tf3;
    JTextField keyCTextF;
    JTextField keyDTextF;
    JTextField vectorICTextF;
    JTextField vectorIDTextF;

    JComboBox cipherCombo;
    String fileEncrypt = "";
    String fileDecrypt = "";
    byte[] image;

    private void initialize() {
        b2 = new JButton("Encrypt");
        b3 = new JButton("Decrypt");
        b4 = new JButton("Choose image file");
        b5 = new JButton("Choose image file");
        l1 = new JLabel("Choose an operation mode to encrypt - DES");
        keyC = new JLabel("Key to encrypt (8 bytes - DES)");
        keyD = new JLabel("Key to decrypt (8 bytes - DES)");
        vectorIC = new JLabel("Initialization Vector C0");
        vectorID = new JLabel("Initialization Vector C0");
        titleCipher = new JLabel("");
        tf1 = new JTextArea();
        tf2 = new JTextArea();
        tf3 = new JTextArea();
        keyCTextF = new JTextField();
        keyDTextF = new JTextField();
        vectorICTextF = new JTextField();
        vectorIDTextF = new JTextField();
        cipherCombo = new JComboBox();

        frame = new JFrame();
        frame.setBounds(100, 100, 900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Modes of operation using DES algorithm");

        cipherCombo.setBounds(300, 60, 200, 20);
        cipherCombo.addItem("Select an option");
        cipherCombo.addItem("ECB");
        cipherCombo.addItem("CBC");
        cipherCombo.addItem("CFB");
        cipherCombo.addItem("OFB");
        cipherCombo.addItemListener(new ItemChangeListener());
        frame.add(cipherCombo);

        b2.setBounds(300, 300, 200, 30);
        b2.setVisible(false);

        // Encrypt button
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (keyCTextF.getText().isEmpty() || vectorIC.getText().isEmpty() || image.length == 0) {
                    JOptionPane.showMessageDialog(null, "Fill the fields");
                } else {
                    try {
                        DES des = new DES(cipherCombo.getSelectedItem().toString());
                        des.encrypt(keyCTextF.getText(), vectorICTextF.getText(), image, fileEncrypt);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
        frame.add(b2);

        b3.setBounds(300, 540, 200, 30);
        b3.setVisible(false);
        // Encrypt button
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (keyDTextF.getText().isEmpty() || vectorIDTextF.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill the fields");
                } else {
                    try {
                        DES des = new DES(cipherCombo.getSelectedItem().toString());
                        des.decrypt(keyDTextF.getText(), vectorIDTextF.getText(), image, fileDecrypt);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
        frame.add(b3);

        b4.setBounds(650, 120, 200, 20);
        b4.setVisible(false);
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser file = new JFileChooser();
                    file.showOpenDialog(frame);
                    fileEncrypt = file.getSelectedFile().getName().substring(0, file.getSelectedFile().getName().length() - 4);
                    image = ImageUtils.getImage(file.getSelectedFile().toPath().toString());
                    tf1.setText("Image '" + file.getSelectedFile().getName() + "' selected succesfully");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        frame.add(b4);

        b5.setBounds(650, 340, 200, 20);
        b5.setVisible(false);
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser file = new JFileChooser();
                file.showOpenDialog(frame);
                fileDecrypt = file.getSelectedFile().getName().substring(0, file.getSelectedFile().getName().length() - 4);
                try {
                    image = ImageUtils.getImage(file.getSelectedFile().toPath().toString());
                    tf2.setText("Image '" + file.getSelectedFile().getName() + "' selected succesfully");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        frame.add(b5);

        l1.setBounds(280, 0, 250, 100);
        frame.add(l1);

        keyC.setBounds(150, 60, 500, 100);
        keyC.setVisible(false);
        frame.add(keyC);

        keyD.setBounds(150, 300, 500, 100);
        keyD.setVisible(false);
        frame.add(keyD);

        vectorIC.setBounds(400, 60, 500, 100);
        vectorIC.setVisible(false);
        frame.add(vectorIC);

        vectorID.setBounds(400, 300, 500, 100);
        vectorID.setVisible(false);
        frame.add(vectorID);

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
        scroll.setBounds(150, 390, 500, 150);
        frame.add(scroll);

        tf3.setLineWrap(true);
        tf3.setEditable(false);
        tf3.setVisible(false);
        scroll = new JScrollPane(tf3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(150, 590, 500, 150);
        frame.add(scroll);

        keyCTextF.setBounds(150, 121, 200, 23);
        keyCTextF.setVisible(false);
        frame.add(keyCTextF);

        keyDTextF.setBounds(150, 360, 200, 23);
        keyDTextF.setVisible(false);
        frame.add(keyDTextF);

        vectorICTextF.setBounds(400, 121, 200, 23);
        vectorICTextF.setVisible(false);
        frame.add(vectorICTextF);

        vectorIDTextF.setBounds(400, 360, 200, 23);
        vectorIDTextF.setVisible(false);
        frame.add(vectorIDTextF);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public ModosOperacionCifrado() {
        initialize();
    }

    public static void main(String[] args) throws Exception {
        new ModosOperacionCifrado();
    }

    class ItemChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                keyC.setVisible(true);
                keyD.setVisible(true);
                vectorIC.setVisible(true);
                vectorID.setVisible(true);
                keyCTextF.setVisible(true);
                keyDTextF.setVisible(true);
                vectorICTextF.setVisible(true);
                vectorIDTextF.setVisible(true);
                b2.setVisible(true);
                b3.setVisible(true);
                b4.setVisible(true);
                b5.setVisible(true);

                tf1.setVisible(true);
                tf2.setVisible(true);
                tf3.setVisible(true);
                String item = (String) event.getItem();

                if (item.equals("ECB")) {
                    titleCipher.setText("ECB / DES");
                    vectorIC.setVisible(false);
                    vectorID.setVisible(false);
                    vectorICTextF.setVisible(false);
                    vectorIDTextF.setVisible(false);
                } else if (item.equals("CBC")) {
                    titleCipher.setText("CBC / DES");
                    titleCipher.setVisible(true);
                } else if (item.equals("CFB")) {
                    titleCipher.setText("CFB / DES");
                    titleCipher.setVisible(true);
                } else if (item.equals("OFB")) {
                    titleCipher.setText("OFB / DES");
                    titleCipher.setVisible(true);
                }
            }
        }
    }
}
