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
//::                                                               (c)2025   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 //////////////////////////////////////////////////////////////////////////////

package MyArkanoid;

import utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 13/05/2025, 18:05:26
 *
 * @author manso - computer
 */
public class Animation extends GameObject implements ActionListener {
    //sequencia de imagens

    transient protected Image[] frames;
    //index de visualizacao
    protected int visibleFrame = 0;
    // temporizador da animação
    protected Timer myTimer;

    public Animation(Color myColor, int x, int y, int width, int height) {
        super(myColor, x, y, width, height);
    }

    public void load(String[] resource) {
        frames = new Image[resource.length];
        //ler os frames
        for (int i = 0; i < resource.length; i++) {
            try {
                frames[i] = ImageUtils.loadImage(resource[i]);
            } catch (IOException ex) {
                Logger.getLogger(AnimatedImage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        myTimer = new Timer(10, this);
        myTimer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        visibleFrame = (visibleFrame + 1) % frames.length;
    }

}
