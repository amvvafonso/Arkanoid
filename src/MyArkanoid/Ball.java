

package MyArkanoid;

import utils.ImageUtils;

import java.awt.*;


public class Ball  extends Animation{


    int vx = 3;
    int vy = 2;

    public static String[] fire_ball_imgs = {
            "/images/ball-fire0.png",
            "/images/ball-fire1.png",
            "/images/ball-fire2.png"};

    public Ball(Color myColor, int x, int y, int radius) {
        super(ImageUtils.Ball_color(),x, y, radius, radius);
    }


    public void paint(Graphics gr){
       gr.setColor(myColor);
       gr.fillOval(x, y, width, height);
       gr.setColor(Color.BLACK);
       gr.drawOval(x, y, width, height);
    }

    public void paintFire(Graphics gr){
        load(fire_ball_imgs);
        gr.setColor(myColor);
        gr.fillOval(x, y, width, height);
        gr.setColor(Color.BLACK);
        gr.drawOval(x, y, width, height);
        gr.drawImage(frames[visibleFrame], x-7, y-15, width+15, height+15, null);
    }


    public void move(){
        translate(vx, vy);
    }


    public boolean move(Rectangle bounds) throws ArkanoidException{
        //mover
        move();
        //bateu no fundo
        if( y + height >= bounds.height){
            GameView.btPause.setEnabled(false);
            throw new ArkanoidException("Perdeu o jogo");
        }
        
        ArkanoidGame.running = true;
        //resaltar
        if( this.x < 0 || this.x + this.width > bounds.width){
            this.vx *= -1;
            move();
        }
        if( this.y < 0 || this.y + this.height > bounds.height){
            this.vy *= -1;
            move();
        }
        return true;
    }



}
