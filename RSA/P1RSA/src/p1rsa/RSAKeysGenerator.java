package p1rsa;

import java.io.BufferedWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Class that implements RSA Key´s generator methods to RSA encryption and decryption
 * @author Medina Villalpando Josué de Jesús
 */
public class RSAKeysGenerator {

    public PrivateKey PrivateKey;
    public PublicKey PublicKey;

    public RSAKeysGenerator() {
        this.PrivateKey = null;
        this.PublicKey = null;
    }

    public String parsePrivateKeyToStringPrivateKey() {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(this.PrivateKey.getEncoded());
        return bytesToString(pkcs8EncodedKeySpec.getEncoded());
    }

    public String parsePublicKeyToStringPublicKey() {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.PublicKey.getEncoded());
        return bytesToString(x509EncodedKeySpec.getEncoded());
    }

    public void genKeyPair(int size) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(size);
        KeyPair kp = kpg.genKeyPair();

        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        this.PrivateKey = privateKey;
        this.PublicKey = publicKey;
    }

    public void createFilePrivateKey(String path) throws IOException {
        try {
            Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            outFile.write(this.parsePrivateKeyToStringPrivateKey());
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createFilePublicKey(String path) {
        try {
            Writer outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            outFile.write(this.parsePublicKeyToStringPublicKey());
            outFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }
}
