
package MyArkanoid;

import javax.swing.*;


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
