/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ulpgc.dis.practica8;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Andres
 */
public class Lienzo extends JPanel {

    private BufferedImage image;
    private BufferedImage originalImage;
    private BufferedImage filteredImage;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
    
    protected Dimension getImagen(){
        return new Dimension(originalImage.getWidth(), originalImage.getHeight());
    }

    protected void setImage(File file) {
        try {
            originalImage = ImageIO.read(file);
            image = originalImage;
        } catch (Exception e) {
            System.out.print(e);
        }

        this.repaint();
    }

    protected void filter(int i, File file) {
        Mat m2 = ImageFilter.threshold(Imgcodecs.imread(file.getAbsolutePath()), i);
        filteredImage = (BufferedImage) HighGui.toBufferedImage(m2);
        image = filteredImage;
        this.repaint();
    }

}

class ImageFilter {
    public static Mat threshold(Mat imagen_original, Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(),
                imagen_original.cols(),
                CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(),
                imagen_original.cols(),
                CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original,
                imagenGris,
                Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris,
                imagenUmbralizada,
                umbral,
                255,
                Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        return imagenUmbralizada;
    }

}
