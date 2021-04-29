package vigenereaffinep1;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author MedinaVilla
 */
public class Vigenere {

    char[] alphabet;
    String key;

    public Vigenere(char[] alphabet, String key) {
        this.alphabet = alphabet;
        this.key = key;
    }

    public String encrypt(String text, String fileName) {
        String key  = this.key.toUpperCase();
        System.out.println(key);
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (this.getPositionAlphabet(c)!=-1) {
                res += alphabet[((this.getPositionAlphabet(c)+ this.getPositionAlphabet(key.charAt(j))) % alphabet.length)];
                j = ++j % key.length();
            } else {
                continue;
            }
        }

        try {
            Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\" + fileName + "VIGENERE_C.vig"), "UTF-8"));
            outFile.write(res);
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\" + fileName + "VIGENERE_KEY.vig"), "UTF-8"));
            outFile.write("Your key: " + key);
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }

    public String decrypt(String text, String fileName) {
        String key  = this.key.toUpperCase();
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c >= 'A' || c <= 'Z' || c == ' ') {
                 res += alphabet[((this.getPositionAlphabet(c) - this.getPositionAlphabet(key.charAt(j)) + alphabet.length) % alphabet.length)];
                j = ++j % key.length();
            } else {
                continue;
            }
        }

        try {
            Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\" + fileName + "_D.vig"), "UTF-8"));
            outFile.write(res);
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }

    int getPositionAlphabet(char s) {
        int position = -1;
        for (int i = 0; i < alphabet.length; i++) {
            if (s == alphabet[i]) {
                position = i;
            }
        }
        return position;
    }
}
