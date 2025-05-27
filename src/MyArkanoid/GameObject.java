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
import java.io.Serializable;

/**
 * Created on 06/05/2025, 18:17:26 
 * @author manso - computer
 */
public class GameObject extends Rectangle implements Serializable{
    protected Color myColor;

    public GameObject(Color myColor, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.myColor = myColor;
    }
    
    

    public Color getMyColor() {
        return myColor;
    }

    public void setMyColor(Color myColor) {
        this.myColor = myColor;
    }
    
    private static final long serialVersionUID =1;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2025  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
