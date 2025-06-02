package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class ImageUtils {

    public static BufferedImage loadImage(String path) throws IOException {
        try (InputStream is = ImageUtils.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Resource not found: " + path);
            }
            return ImageIO.read(is);
        }
    }


    public static BufferedImage getImageFromIcon(Icon icon) {
        BufferedImage buffer = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return buffer;
    }

    public static BufferedImage convertToGrayscale(BufferedImage color) {
        BufferedImage gray = new BufferedImage(color.getWidth(), color.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(color, gray);
        return gray;
    }


    public static ImageIcon convertToGrayscale(Icon icon) {
        return new ImageIcon(ImageUtils.convertToGrayscale(getImageFromIcon(icon)));
    }
   
      
    //Redimensiona icon
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        //imagem do icone
        Image img = icon.getImage();
        //redimensionar a imagem
        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        //retornar um novo icone
        return new ImageIcon(img);
    }
    

    public static BufferedImage changeTransparency(BufferedImage original, float alpha) {
        // validar a compomente alpha
        if (alpha < 0.0f || alpha > 1.0f) {
            //lançar uma exceção
            throw new IllegalArgumentException("Alpha value must be between 0.0 and 1.0");
        }
        // criar uma nova imagem com do tipo ARGB  (A=transparency)
        BufferedImage transparentImage = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        //Criar o contexto gráfico da nova imagem
        Graphics2D g2d = transparentImage.createGraphics();
        //atualizar a transparência alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        //desenhar a imagem original no novo contexto gráfico
        g2d.drawImage(original, 0, 0, null);
        g2d.dispose();
        //devolver a imagem transparente 
        return transparentImage;
    }

    //Obtém um cor random de um array predefinido
    public static Color RandomColor(){
        Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GRAY};
        return colors[new Random().nextInt(colors.length)];
    }


    //Vai buscar as imagens para o background a partir de uma keyword
    //Devido a problemas com o obtensão automatica, colocamos manualmente no catch para prevenir
    //que o jogo crashe ou inicie sem imagens
    public static ArrayList<String> fetchBackgroundImages(/*int ii*/) {
        ArrayList<String> background_img_files_list = new ArrayList<>();
        try {
            File files = new File("/images/");

            File[] fileList = files.listFiles();

            for (File file : fileList){
                if (file.getName().contains("background")){
                    background_img_files_list.add(file.getAbsolutePath() + file.getName());
                }
            }
            //diferenciar ficheiros para usar no background

            System.out.println("Nomrla");
            return background_img_files_list;
        }
        catch (Exception es){
            background_img_files_list.add("/images/background2.jpg");
            background_img_files_list.add("/images/background3.jpg");
            background_img_files_list.add("/images/background4.jpg");
            background_img_files_list.add("/images/background1.png");
            return background_img_files_list;
        }


    }

    public static Color Ball_color() {
        return  RandomColor();
    }

}
