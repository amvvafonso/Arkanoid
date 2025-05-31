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

import java.awt.*;

/**
 * Created on 06/05/2025, 17:50:01 
 * @author manso - computer
 */
public class Ball  extends Animation{



    int vx = 3;
    int vy = 2;

    public static String[] fire_ball_imgs = {
            "/images/ball-fire0.png",
            "/images/ball-fire1.png",
            "/images/ball-fire2.png"};

    public Ball(Color myColor, int x, int y, int radius) {
        super(ImageUtils.Ball_color(),x, y, radius, radius);
        load(fire_ball_imgs);
    }

    public void loadfire_ball_imgs(){
        load(fire_ball_imgs);
    }

    public void paint(Graphics gr){
        //
         gr.drawImage(frames[visibleFrame], x-12, y-7, width+15, height+15, null);
        //
       gr.setColor(myColor);
       gr.fillOval(x, y, width, height);
       gr.setColor(Color.BLACK);
       gr.drawOval(x, y, width, height);
    }
    
    public void move(){
        translate(vx, vy);
    }
   
     public void move2(){
        translate(vx, vy);
    }
      public void move3(){
        translate(vx, vy);
    }
       public void move4(){
        translate(vx, vy);
    }
    
    
    public void move( Rectangle bounds) throws ArkanoidException{
        //mover
        move();
        //bateu no fundo
        if( y + height >= bounds.height){
            throw new ArkanoidException("Perdeu o jogo");
        }
        
        
        //resaltar
        if( this.x < 0 || this.x + this.width > bounds.width){
            this.vx *= -1;
            move();
        }
        if( this.y < 0 || this.y + this.height > bounds.height){
            this.vy *= -1;
            move();
        }
        
    }
private static final long serialVersionUID =1;


}
