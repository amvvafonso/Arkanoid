//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2024   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
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

/**
 * Created on 06/05/2024, 16:34:46
 *
 * @author IPT - computer
 * @version 1.0
 */
public class ImageUtils {
       /**
     * retorna o objeto que representa a imagem de um recurso
     *
     * @param path path para a imagem "/folder/resource.img"
     * @return imagem
     * @throws IOException erros de I/O
     */
    public static BufferedImage loadImage(String path) throws IOException {
        try (InputStream is = ImageUtils.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Resource not found: " + path);
            }
            return ImageIO.read(is);
        }
    }

    /**
     * Converte um icone para uma buffered image
     * @param icon icone
     * @return imagem
     */
    public static BufferedImage getImageFromIcon(Icon icon) {
        BufferedImage buffer = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return buffer;
    }
    
      /**
     * converte uma umagem para tons de cinza
     * @param color imagem colorida
     * @return imagem cinza
     */
    public static BufferedImage convertToGrayscale(BufferedImage color) {
        BufferedImage gray = new BufferedImage(color.getWidth(), color.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(color, gray);
        return gray;
    }

    /**
     * converte um icone para tons de cinza
     * @param icon icone colorido
     * @return icone em tons de cinza
     */
    public static ImageIcon convertToGrayscale(Icon icon) {
        return new ImageIcon(ImageUtils.convertToGrayscale(getImageFromIcon(icon)));
    }
   
      
    /**
     * redimensiona um icone
     *
     * @param icon icone
     * @param width largura
     * @param height altura
     * @return icone redimensionado
     */
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        //imagem do icone
        Image img = icon.getImage();
        //redimensionar a imagem
        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        //retornar um novo icone
        return new ImageIcon(img);
    }
    
    /**
     * Changes the transparency (alpha) of a BufferedImage.
     *
     * @param original imagem original.
     * @param alpha transparência [0.0,1.0] 0.0-transparent)  1.0-opaca
     * @return imagem transparente
     */
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


    public static Color RandomColor(){
        Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GRAY};
        return colors[new Random().nextInt(colors.length)];
    }
    //
    //public static int i_para_Background_img_list=0;
    public static /*String*/ ArrayList<String> Background_img_list(/*int ii*/) {

        String folder_path_name = "src/images";
        int i = 0;
        File background_image_folder = new File(folder_path_name);
        String img_name_checker = "background";
        File[] img_files_list = background_image_folder.listFiles();//lista de ficheiros no folder
        ArrayList<String> background_img_files_list = new ArrayList<String>();
        //diferenciar ficheiros para usar no background
        for (File file : img_files_list) {
            if (file.getName().contains(img_name_checker)) {
                background_img_files_list.add("/images/"+file.getName());
            }
        }
        //assert files != null;
       /* while(i<background_img_files_list.size()){
        System.out.println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII "+ background_img_files_list.get(i));i++;}
    */
        //return background_img_files_list.get(ii);
        return background_img_files_list;
    }

    public static Color Ball_color() {



        return  RandomColor();
    }
     //
}
