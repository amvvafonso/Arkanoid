
package Arkanoid;

import javax.swing.*;
/**
 * <p>Deals with the game exceptions</p>
 * @author Afonso Viana
 */

public class ArkanoidException extends Exception{

    public ArkanoidException(String msg) {
        super(msg);
    }
    public void showError(){
        JOptionPane.showMessageDialog(null, getMessage(), "Arkanoid Error", JOptionPane.WARNING_MESSAGE);
    }

    public void showMessage(){
        JOptionPane.showMessageDialog(null, getMessage(), "Arkanoid Result", JOptionPane.INFORMATION_MESSAGE);
    }


}
