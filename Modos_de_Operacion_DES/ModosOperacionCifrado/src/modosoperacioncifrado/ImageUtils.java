package modosoperacioncifrado;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Clase que implementa métodos para el manejo de las imágenes utilizadas en la aplicación
 * @author Github: MedinaVilla
 */
public class ImageUtils {

    /**
     * Method to get the image and convert it to a byte array
     * @param imagePath Path of the image
     * @return an array of bytes of the image
     * @throws IOException
     */
    public static byte[] getImage(String imagePath) throws IOException {
        File imgPath = new File(imagePath);
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "bmp", baos);
        baos.flush();
        baos.close();
        return baos.toByteArray();
    }

    /**
     * Method to save the image in a file in the application directory
     * @param imagePath Path of the image
     * @param imageBytes Byte array of the image
     * @throws IOException
     */
    public static void saveImage(String imagePath, byte[] imageBytes) throws IOException {
        InputStream in = new ByteArrayInputStream(imageBytes);
        BufferedImage bImageFromConvert = ImageIO.read(in);
        ImageIO.write(bImageFromConvert, "bmp", new File(imagePath));
    }
}
