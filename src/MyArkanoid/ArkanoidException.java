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

import javax.swing.*;

/**
 * Created on 13/05/2025, 17:10:46 
 * @author manso - computer
 */
public class ArkanoidException extends Exception{

    public ArkanoidException(String msg) {
        super(msg);
    }
    public void show(){
        JOptionPane.showMessageDialog(null, getMessage(), "Arkanoid Error", JOptionPane.WARNING_MESSAGE);
    }
     //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 202505131710L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2025  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
