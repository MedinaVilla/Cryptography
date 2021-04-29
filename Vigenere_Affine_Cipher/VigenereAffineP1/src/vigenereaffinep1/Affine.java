package vigenereaffinep1;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author MedinaVilla
 */
public class Affine {

    char[] alphabet;
    int alfa;
    int beta;

    public Affine(int alfa, int beta, char[] alphabet) {
        this.alfa = alfa;
        this.beta = beta;
        this.alphabet = alphabet;
    }

    int obtener_mcd(int a, int n) {
        System.out.println(a);
        System.out.println(n);
        if (n == 0) {
            return a;
        } else {
            return obtener_mcd(n, a % n);
        }
    }

    String encryptMessage(char[] msg, String fileName) {
        /// Cipher Text initially empty 
        String cipher = "";
        for (int i = 0; i < msg.length; i++) {
            cipher = cipher + (char) alphabet[((((alfa * getPositionAlphabet(msg[i])) + beta) % alphabet.length))];
        }

        try {
            Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\" + fileName + "AFFINE_C.aff"), "UTF-8"));
            outFile.write(cipher);
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return cipher;
    }

    String decryptCipher(String cipher, String fileName) {
        String msg = "";

        for (int i = 0; i < cipher.length()-1; i++) {
            /*Applying decryption formula a^-1 ( x - b ) mod m  */
            int ecludidesRes = euclidesFunExtended();
            System.out.println(ecludidesRes);
            msg = msg + (char) alphabet[(((ecludidesRes * ((getPositionAlphabet(cipher.charAt(i)) - beta)) % alphabet.length)))];
        }

        try {
            Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\" + fileName + "_D.aff"), "UTF-8"));
            outFile.write(msg);
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return msg;
    }

    int euclidesFunExtended() {
        int inverse = 0;
        int flag = 0;
        //Find a^-1 (the multiplicative inverse of a  
        //in the group of integers modulo m.)  
        for (int i = 0; i < alphabet.length; i++) {
            flag = (alfa * i) % alphabet.length;
            // Check if (a*i)%26 == 1, 
            // then i will be the multiplicative inverse of a 
            if (flag == 1) {
                inverse = i;
            }
        }
        return inverse;

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
