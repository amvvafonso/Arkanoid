package Arkanoid;

import java.awt.*;

/**
 * <p>Brick model</p>
 * @author Prof Manso
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




}
