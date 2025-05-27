

package MyArkanoid;

import java.awt.*;

/**
 * Created on 06/05/2025, 18:20:10
 *
 * @author manso - computer
 */
public class Paddle extends Animation {

    public static String[] imgs = {"/images/paddle0.png",
        "/images/paddle1.png",
        "/images/paddle2.png"};

    public Paddle(Color myColor, int x, int y, int width, int height) {
        super(myColor, x, y, width, height);
        load(imgs);
    }
    
    public void loadImages(){
        load(imgs);
    }

    public void paint(Graphics gr) {
        gr.drawImage(frames[visibleFrame], x, y, width, height, null);

//        gr.setColor(myColor);
////        gr.fillRect(x, y, width, height);
//        gr.setColor(Color.DARK_GRAY);
//        gr.drawRect(x, y, width, height);

    }

    public void moveTo(int px) {
        this.x = px;
    }

    public void collide(Ball b) {
        if (b.intersects(this)) {
            b.vy *= -1;
            b.move();
        }
    }
    
    private static final long serialVersionUID =1;

}
