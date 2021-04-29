package modosoperacioncifrado;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase que implementa los métodos para el cifrado y descifrado de las imágenes
 * mediante un modo de operación dado utilizando el algoritmo DES
 *
 * @author Github: MedinaVilla
 */
public class DES {

    private final static String alg = "DES"; //Algorithm to encrypt, can be AES
    private final String operationMode; // Operation mode
    private final String cIE; // Cipher Instance to encrypt
    private final String cID; // Cipher Instance to decrypt

    public DES(String operationMode) {
        this.operationMode = operationMode;
        this.cIE = alg + "/" + operationMode + "/PKCS5Padding";
        this.cID = alg + "/" + operationMode + "/NoPadding";
    }

    /**
     * Method that allows us to encrypt using Cipher library and save the image
     * encrypted in our directory
     *
     * @param key Key String to encrypt
     * @param iv Initialization vector String
     * @param image byte array of the image
     * @param fileName the name of the file to save
     * @throws Exception
     */
    public void encrypt(String key, String iv, byte[] image, String fileName) throws Exception {
        Integer header_size = 54;
        byte[] finalBytes = new byte[(int) image.length];
        byte[] encryptedBytes = new byte[(int) image.length - header_size];

        for (int i = 0, j = 0; i < image.length; i++) {
            if (i < header_size) {
                finalBytes[i] = image[i];
            } else {
                encryptedBytes[j++] = image[i];
            }
        }

        // Cipher library to encrypt
        Cipher cipher = Cipher.getInstance(cIE);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg); // Key obtention 
        IvParameterSpec ivParameterSpec = !operationMode.equals("ECB") ? new IvParameterSpec(iv.getBytes()) : null; // Initilization vector obtention
        if (ivParameterSpec != null) {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec); //Vector of initialization no needed
        }
        byte[] encrypted = cipher.doFinal(encryptedBytes);

        // Save the image block
        StringBuilder builder = new StringBuilder();
        builder.append(fileName).append("_e").append(operationMode).append(".bmp");
        for (int i = header_size, j = 0; i < image.length; i++, j++) {
            finalBytes[i] = encrypted[j];
        }
        ImageUtils.saveImage(builder.toString(), finalBytes);
        System.out.println("Encrypted image saved successfully - " + this.operationMode);

    }

    /**
     * Method that allows us to decrypt using Cipher library and save the image
     * decrypted in our directory
     *
     * @param key Key String to encrypt
     * @param iv Initialization vector String
     * @param image byte array of the image
     * @param fileName the name of the file to save
     * @throws Exception
     */
    public void decrypt(String key, String iv, byte[] image, String fileName) throws Exception {
        Integer header_size = 54;
        byte[] finalBytes = new byte[(int) image.length];
        byte[] encryptedBytes = new byte[(int) image.length - header_size];
        for (int i = 0, j = 0; i < image.length; i++) {
            if (i < header_size) {
                finalBytes[i] = image[i];
            } else {
                encryptedBytes[j++] = image[i];
            }
        }

        Cipher cipher = Cipher.getInstance(cID);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg); // Key obtention 
        IvParameterSpec ivParameterSpec = !operationMode.equals("ECB") ? new IvParameterSpec(iv.getBytes()) : null; // Initilization vector obtention
        if (ivParameterSpec != null) {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec); //Vector of initialization no needed
        }
        byte[] encrypted = cipher.doFinal(encryptedBytes);

        // Save the image block
        StringBuilder builder = new StringBuilder();
        builder.append(fileName).append("_d").append(operationMode).append(".bmp");
        for (int i = header_size, j = 0; i < image.length; i++, j++) {
            finalBytes[i] = encrypted[j];
        }
        ImageUtils.saveImage(builder.toString(), finalBytes);
        System.out.println("Decrypted image saved successfully - " + this.operationMode);
    }
}
