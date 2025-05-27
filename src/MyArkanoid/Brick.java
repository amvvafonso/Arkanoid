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

import java.awt.*;

/**
 * Created on 06/05/2025, 18:07:05 
 * @author manso - computer
 */
public class Brick extends GameObject{

    
    boolean isVisible = true;

    public Brick(Color myColor, int x, int y, int width, int height) {
        super(myColor,x, y, width, height);
    }
    
    
    public void paint(Graphics gr){
        if( !isVisible)
            return;
        
        gr.setColor(myColor);
        gr.fillRect(x, y, width, height);
        gr.setColor(Color.DARK_GRAY);
        gr.drawRect(x, y, width, height);
        
    }



    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202505061807L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2025  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
